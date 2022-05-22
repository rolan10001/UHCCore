package fr.rolan.core.api.game.team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.rolan.api.game.team.ITeam;
import fr.rolan.api.game.team.Teams;
import fr.rolan.api.player.IUser;
import fr.rolan.api.scoreboard.IScoreboardTeam;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.api.scoreboard.ScoreboardTeam;

public class Team implements ITeam {
	
	private final List<IScoreboardTeam> teams = new ArrayList<IScoreboardTeam>();
	
	public Team() {
		updateTeam();
	}
	
	@Override
	public List<IScoreboardTeam> getTeams() {
		return teams;
	}

	@Override
	public Teams getTeam(String name) {
		for(Teams t : Teams.values()) {
			if(t.getName().equals(name)) {
				return t;
			}
		}
		return Teams.DEFAULT;
	}

	@Override
	public IScoreboardTeam getScoreboardTeam(String name) {
		for(IScoreboardTeam teams : teams) {
			if(teams.getName().equals(name)) {
				return teams;
			}
		}
		return null;
	}

	@Override
	public void updatePlayer(Player player) {
		IUser user = APIPlugin.getInstance().getAPI().getUser(player);
		user.getTeam().addPlayer(player.getUniqueId());
		for(Teams t : Teams.values()) {
			IScoreboardTeam team = new ScoreboardTeam(t.getName(), t.getPrefix(), t.getSuffix(), t.getColor());
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(team.createTeam());
			for(UUID uuid : t.getPlayers()) {
				if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
					try {((CraftPlayer) player).getHandle().playerConnection.sendPacket(team.updateTeam());}catch(Exception e) {}
					try{((CraftPlayer) player).getHandle().playerConnection.sendPacket(team.addOrRemovePlayer(3, Bukkit.getPlayer(uuid).getName()));}catch(Exception e) {}
				}
			}
		}
	}

	@Override
	public void updateTeam() {
		for(Teams t : Teams.values()) {
			IScoreboardTeam team = new ScoreboardTeam(t.getName(), t.getPrefix(), t.getSuffix(), t.getColor());
			for(Player players : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) players).getHandle().playerConnection.sendPacket(team.createTeam());
			}
			for(Player players : Bukkit.getOnlinePlayers()) {
				for(UUID uuid : t.getPlayers()) {
					if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
						try {((CraftPlayer) players).getHandle().playerConnection.sendPacket(team.updateTeam());}catch(Exception e) {}
						try{((CraftPlayer) players).getHandle().playerConnection.sendPacket(team.addOrRemovePlayer(3, Bukkit.getPlayer(uuid).getName()));}catch(Exception e) {}
					}
				}
			}
			teams.add(team);
		}
	}

}
