package fr.jejeoh.practice.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class DBConnect {
	
    private String urlBase;
    private String host;
    private String database;
    private String username;
    private String password;


    public static Connection connection;

    public DBConnect(String urlBase, String host, String database, String username, String password) {
        this.urlBase = urlBase;
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;


    }
 public static Connection getConnection() {
        return connection;

    }
 public void createAccount(Player p) {
	 
	 int elo = 500;

        if (!hasAccont(p)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (uuid, player_name, elo) VALUES (?,?,?)");
                preparedStatement.setString(1, p.getUniqueId().toString());
                preparedStatement.setString(2, p.getName().toString());
                preparedStatement.setInt(3, elo);
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public boolean hasAccont(Player p) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM users WHERE uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 public void connexion() {
        if (!isOnline()) {
            try {
                connection = DriverManager.getConnection(this.urlBase + this.host + "/" + this.database, this.username, this.password);
                return;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deconnexion() {
        if (isOnline()) {
            try {
                connection.close();
                System.out.println("§c[Practice] Successfully disconnected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isOnline() {
        try {
            if ((connection == null) || (connection.isClosed())) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
