package de.johnpork;

import de.johnpork.commands.FriendlyFireCommand;
import de.johnpork.listeners.FriendlyFireListener;
import de.johnpork.managers.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.sql.Statement;

public final class FriendlyFire extends JavaPlugin {
    private Database database;


    private PlayerManager playerManager;
    @Override
    public void onEnable() {
     databaseSetup();
     playerManager = new PlayerManager(this);

     getCommand("friendlyfire").setExecutor(new FriendlyFireCommand(playerManager));
     getServer().getPluginManager().registerEvents(new FriendlyFireListener(playerManager),this);


    }
    private void databaseSetup(){
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        database = new Database(this.getConfig());
        try {
            database.connect();
        } catch (SQLException e) {
            System.out.println("Friendly Fire disabled because of an Error involving SQL");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        System.out.println("FriendlyFire Database connected");
        String createTableSQL = "CREATE TABLE IF NOT EXISTS minecraft_players ("
                + "uuid VARCHAR(36) PRIMARY KEY" // UUID is 36 characters long
                + ");";
        try {
            Statement statement = database.getConnection().createStatement();
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        database.disconnect();
    }

    public Database getDatabase() {
        return database;
    }
}
