package de.johnpork.managers;

import de.johnpork.FriendlyFire;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager {
    private FriendlyFire plugin;

    private ArrayList<Player> players;

    public PlayerManager(FriendlyFire plugin){
        this.plugin = plugin;
        players = new ArrayList<>();
        loadPlayersFromDatabase();

    }


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player){
        players.add(player);
        savePlayerToDatabase(player);
    }
    public void removePlayer(Player player){
        players.remove(player);
    }
    private void loadPlayersFromDatabase(){
        PreparedStatement statement = null;
        try {
            statement = plugin.getDatabase().getConnection().prepareStatement("SELECT uuid FROM minecraft_players");

        ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String uuidString = resultSet.getString("uuid");
                    UUID uuid = UUID.fromString(uuidString);

                    Player player = Bukkit.getPlayer(uuid); // Get player from server by UUID
                    if (player != null && !players.contains(player)) {
                        players.add(player);
                    }
                }

                } catch(SQLException e){
                    throw new RuntimeException(e);
                }



        }

        private void savePlayerToDatabase(Player player){
            try {
                PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("INSERT INTO minecraft_players (uuid) VALUES (?) ON DUPLICATE KEY UPDATE uuid=uuid");
                statement.setString(1, player.getUniqueId().toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        private void removePlayerFromDatabase(Player player){
            PreparedStatement statement = null;
            try {
                statement = plugin.getDatabase().getConnection().prepareStatement("DELETE FROM minecraft_players WHERE uuid = ?");
                statement.setString(1, player.getUniqueId().toString());
                statement.executeUpdate();
            } catch (SQLException e) {
              e.printStackTrace();
            }


        }
}