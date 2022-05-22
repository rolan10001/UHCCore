package fr.rolan.core.api.game;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.rolan.api.UHCAPI;
import fr.rolan.api.events.ActionBarEvent;
import fr.rolan.api.game.IGameManager;
import fr.rolan.api.game.Settings;
import fr.rolan.api.game.enums.GameState;
import fr.rolan.api.player.IUser;
import fr.rolan.tools.NMSMethod;
import io.papermc.lib.PaperLib;

public class GameManager implements IGameManager {
	
	private GameState state = GameState.LOBBY;
	private Settings configuration;
	
	public GameManager() {
		this.configuration = new Settings();
	}
	
	@Override
	public void setGameState(GameState state) {
		this.state = state;
	}

	@Override
	public boolean isState(GameState state) {
		return this.state.equals(state);
	}
	
	@Override
	public Settings getConfig() {
		return configuration;
	}
	
	@Override
	public void setConfig(Settings configuration) {
		this.configuration = (Settings) configuration;
	}

	@Override
	public int midDistance(Player player) {
		World world = player.getWorld();
		Location location = player.getLocation();
		location.setY(world.getSpawnLocation().getY());
		int distance = (int)location.distance(world.getSpawnLocation());
		return distance / 300 * 300;
	}

	@Override
	public int playerDistance(Player player, Location loc) {
		Location location = player.getLocation();
		if(!loc.getWorld().getName().equals(location.getWorld().getName()))
			return Integer.MAX_VALUE;
		int distance = (int)location.distance(loc);
		return distance;
	}

	@Override
	public String updateArrow(Player player, Location target) {
		String arrow;
		Location location = player.getLocation();
		if(!target.getWorld().getName().equals(location.getWorld().getName()))
			return "?";
		location.setY(target.getY());
		Vector dirToMiddle = target.toVector().subtract(player.getEyeLocation().toVector()).normalize();
		Vector playerDirection = player.getEyeLocation().getDirection();
		double angle = dirToMiddle.angle(playerDirection);
		double det = dirToMiddle.getX() * playerDirection.getZ() - dirToMiddle.getZ() * playerDirection.getX();
		angle *= Math.signum(det);
		if(angle > -0.39269908169872414D && angle < 0.39269908169872414D)
			arrow = "⬆";
		else if(angle > -1.1780972450961724D && angle < -0.39269908169872414D)
			arrow = "⬈";
		else if(angle < 1.1780972450961724D && angle > 0.39269908169872414D)
			arrow = "⬉";
		else if(angle > 1.1780972450961724D && angle < 1.9634954084936207D)
			arrow = "⬅";
		else if(angle < -1.1780972450961724D && angle > -1.9634954084936207D)
			arrow = "➡";
		else if(angle < -1.9634954084936207D && angle > -2.748893571891069D)
			arrow = "⬊";
		else if(angle > 1.9634954084936207D && angle < 2.748893571891069D)
			arrow = "⬋";
		else
			arrow = "⬇";
		return " §l" + arrow;
	}

	@Override
	public void transportion(UUID playerUUID, String message) {
		Player player = Bukkit.getPlayer(playerUUID);
		if(player != null) {
			World world = player.getWorld();
			WorldBorder border = world.getWorldBorder();
			int x;
			int z;
			Random r = new Random();
			int tp = r.nextInt(4);
			if(tp == 0) {
				x = (int) (r.nextInt((int) border.getSize() /4)+border.getSize() /4 - 25);
				z = (int) (r.nextInt((int) border.getSize() /4)+border.getSize() /4 - 25);
			}else if(tp == 1) {
				x = (int) (r.nextInt((int) border.getSize() /4)-border.getSize() /4 + 25);
				z = (int) (r.nextInt((int) border.getSize() /4)-border.getSize() /4 + 25);
			}else if(tp == 2) {
				x = (int) (r.nextInt((int) border.getSize() /4)+border.getSize() /4 - 25);
				z = (int) (r.nextInt((int) border.getSize() /4)-border.getSize() /4 + 25);
			}else {
				x = (int) (r.nextInt((int) border.getSize() /4)-border.getSize() /4 + 25);
				z = (int) (r.nextInt((int) border.getSize() /4)+border.getSize() /4 - 25);
			}
			x+=world.getSpawnLocation().getBlockX();
			z+=world.getSpawnLocation().getBlockZ();
			player.setFoodLevel(20);
			player.setSaturation(20.0F);
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage(message);
			PaperLib.teleportAsync(player, new Location(world, x, world.getHighestBlockYAt(x, z)+1, z));
		}
	}

	@Override
	public String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
		float percent = (float) current / max;

        int progressBars = (int) ((int) totalBars * percent);

        int leftOver = (totalBars - progressBars);

        StringBuilder sb = new StringBuilder();
        sb.append(completedColor);
        for (int i = 0; i < progressBars; i++)
            sb.append(symbol);
        sb.append(notCompletedColor);
        for (int i = 0; i < leftOver; i++)
            sb.append(symbol);
        return sb.toString();
	}
	
	public void actionBar(Player player) {
		UUID playerUUID = player.getUniqueId();
		IUser user = UHCAPI.get().getUser(player);
		if(user.isDied() || user.isSpectator())
			return;
		ActionBarEvent actionBarEvent = new ActionBarEvent(playerUUID, "");
		Bukkit.getPluginManager().callEvent(actionBarEvent);
		NMSMethod.sendActionbar(player, actionBarEvent.getActionBar());
	}
}
