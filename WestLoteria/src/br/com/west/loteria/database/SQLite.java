package br.com.west.loteria.database;

import br.com.west.loteria.Main;
import org.bukkit.Bukkit;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {

    private Connection connection;
    private boolean active;

    public void openConnection() {
        try {
            File file = new File(Main.getInstance().getDataFolder(), "database.db");
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            active = true;

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao abrir conexão com a database, desligando plugin...");
            Main.getInstance().getPluginLoader().disablePlugin(Main.getInstance());
        }
    }


    public Connection getConnection() {
        if (connection == null || !active)
            openConnection();
        return connection;
    }

    public void disconnect() {
        if (connection != null && active) {
            try {
                connection.close();
                active = false;
            } catch (SQLException e) {
            }
        }
    }
}
