package fr.rolan.core.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import fr.rolan.api.database.IDataBaseManager;

public class DataBaseManager implements IDataBaseManager {
	
	private final String urlBase;
	private final String host;
	private final String database;
	private final String userName;
	private final String password;
	private Connection connection;
	
	public DataBaseManager(String urlBase, String host, String database, String userName, String password) {
		this.urlBase = urlBase;
		this.host = host;
		this.database = database;
		this.userName = userName;
		this.password = password;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void createAccount(UUID uuid) {
		if(!hasAccount(uuid)) {
			try {
				PreparedStatement preparedstatement = (PreparedStatement) connection.clientPrepareStatement("INSERT INTO arena (uuid_player, pseudo_player, death, kills, killstreak) VALUES (?, ?, ?, ?, ?)");
				preparedstatement.setString(1, uuid.toString());
				preparedstatement.setString(2, Bukkit.getPlayer(uuid).getName());
				preparedstatement.setInt(3, 0);
				preparedstatement.setInt(4, 0);
				preparedstatement.setInt(5, 0);
				preparedstatement.execute();
				preparedstatement.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean hasAccount(UUID uuid) {
		try {
			PreparedStatement preparedstatement = (PreparedStatement) connection.prepareStatement("SELECT uuid_player FROM arena WHERE uuid_player = ?");
			preparedstatement.setString(1, uuid.toString());
			ResultSet rs = preparedstatement.executeQuery();
			
			while(rs.next()) {
				return true;
			}
			return false;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void connexion() {
		if(!isOnline()) {
			try {
				connection = (Connection) DriverManager.getConnection(this.urlBase + this.host + "/" + this.database + "?AutoReconnect=true", this.userName, this.password);
				System.out.println("[DatabaseManager] Succefully connected.");
				return;
			}catch(SQLException e) {
				e.printStackTrace();
			}
			for(Player p : Bukkit.getOnlinePlayers()) {createAccount(p.getUniqueId());}
		}
	}
	
	public void deconnexion() {
		if(isOnline()) {
			try {
				connection.close();
				System.out.println("[DatabaseManager] Succefully disconnected.");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isOnline() {
		try {
			if((connection == null) || (connection.isClosed())) {
				return false;
			}
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}