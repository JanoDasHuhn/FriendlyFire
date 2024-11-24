package de.johnpork;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection connection;

    private FileConfiguration config;
    public Database(FileConfiguration config){
        this.config = config;
    }
    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + config.getString("hostname") + ":"
        + config.getString("port") + "/"
                + config.getString("database") +
                "?useSSL=false",config.getString("username"),
                config.getString("password"));


    }
    public boolean isConnected(){
        return connection != null;
    }
    public void disconnect(){
        if(!isConnected()){
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
