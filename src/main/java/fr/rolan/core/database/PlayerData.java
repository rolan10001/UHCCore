package fr.rolan.core.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.mysql.jdbc.PreparedStatement;

import fr.rolan.api.database.IPlayerData;
import fr.rolan.core.APIPlugin;

public class PlayerData implements IPlayerData {
	
private UUID uuid;
	
	public PlayerData(UUID uuid) {
		this.uuid = uuid;
	}
	
	public void addKill(int power) {
		try {
			PreparedStatement preparedstatement = (PreparedStatement) APIPlugin.getInstance().getAPI().getDataBase().getConnection().clientPrepareStatement("UPDATE arena SET kills = kills + ? WHERE uuid_player = ?");
			preparedstatement.setInt(1, power);
			preparedstatement.setString(2, uuid.toString());
			preparedstatement.executeUpdate();
			preparedstatement.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getKill() {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) APIPlugin.getInstance().getAPI().getDataBase().getConnection().prepareStatement("SELECT kills FROM arena WHERE uuid_player = ?");
			preparedStatement.setString(1, uuid.toString());
			ResultSet rs = preparedStatement.executeQuery();
			int kills = 0;
			
			while(rs.next()) {
				kills = rs.getInt("kills");
			}
			preparedStatement.close();
			
			return kills;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void addDeath(int power) {
		try {
			PreparedStatement preparedstatement = (PreparedStatement) APIPlugin.getInstance().getAPI().getDataBase().getConnection().clientPrepareStatement("UPDATE arena SET death = death + ? WHERE uuid_player = ?");
			preparedstatement.setInt(1, power);
			preparedstatement.setString(2, uuid.toString());
			preparedstatement.executeUpdate();
			preparedstatement.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getDeath() {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) APIPlugin.getInstance().getAPI().getDataBase().getConnection().prepareStatement("SELECT death FROM arena WHERE uuid_player = ?");
			preparedStatement.setString(1, uuid.toString());
			ResultSet rs = preparedStatement.executeQuery();
			int death = 0;
			
			while(rs.next()) {
				death = rs.getInt("death");
			}
			preparedStatement.close();
			
			return death;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void addKillStreak(int power) {
		try {
			PreparedStatement preparedstatement = (PreparedStatement) APIPlugin.getInstance().getAPI().getDataBase().getConnection().clientPrepareStatement("UPDATE arena SET killstreak = killstreak + ? WHERE uuid_player = ?");
			preparedstatement.setInt(1, power);
			preparedstatement.setString(2, uuid.toString());
			preparedstatement.executeUpdate();
			preparedstatement.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getKillStreak() {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) APIPlugin.getInstance().getAPI().getDataBase().getConnection().prepareStatement("SELECT killstreak FROM arena WHERE uuid_player = ?");
			preparedStatement.setString(1, uuid.toString());
			ResultSet rs = preparedStatement.executeQuery();
			int death = 0;
			
			while(rs.next()) {
				death = rs.getInt("killstreak");
			}
			preparedStatement.close();
			
			return death;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
