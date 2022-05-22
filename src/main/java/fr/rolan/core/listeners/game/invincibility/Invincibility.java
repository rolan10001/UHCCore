package fr.rolan.core.listeners.game.invincibility;

import java.text.DecimalFormat;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.rolan.api.UHCAPI;
import fr.rolan.api.events.ActionBarEvent;
import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;

public class Invincibility implements Listener {
	
	public Invincibility() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		APIPlugin.getInstance().getAPI().getScoreboardManager().setPlayerScoreboard(player.getUniqueId(), APIPlugin.getInstance().getAPI().s.NAME, APIPlugin.getInstance().getAPI().getTeleportationManager().setGameScoreboard());
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) event.setCancelled(true);
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		IUser user = APIPlugin.getInstance().getAPI().getUser(player);
		if(APIPlugin.getInstance().getAPI().s.TEAM) {
			if(event.getMessage().startsWith("!")) {
				for(Player players : Bukkit.getOnlinePlayers())
					players.sendMessage("§"+Integer.toHexString(user.getTeam().getColor())+player.getName()+" §8» §f"+(player.hasPermission("host.use") ? ChatColor.translateAlternateColorCodes('&', event.getMessage().substring(1)) : event.getMessage().substring(1)));
			}else {
				for(UUID uuid : user.getTeam().getPlayers())
					if(Bukkit.getPlayer(uuid) != null)
						Bukkit.getPlayer(uuid).sendMessage("§d§lTeam §7§l▏ §"+Integer.toHexString(user.getTeam().getColor())+player.getName()+" §8» §f"+(player.hasPermission("host.use") ? ChatColor.translateAlternateColorCodes('&', event.getMessage()) : event.getMessage()));
			}
		}else {
			for(Player players : Bukkit.getOnlinePlayers())
				players.sendMessage("§7"+player.getName()+" §8» §f"+(player.hasPermission("host.use") ? ChatColor.translateAlternateColorCodes('&', event.getMessage()) : event.getMessage()));
		}
	}
	
	@EventHandler
	public void onActionBar(ActionBarEvent event) {
		if(!APIPlugin.getInstance().getAPI().s.TEAM)
			return;
		if(!APIPlugin.getInstance().getAPI().s.TEAM_DIRECTION)
			return;
		IUser user = UHCAPI.get().getUser(event.getPlayerUUID());
		if(user.isDied() || user.isSpectator())
			return;
		if(user.getTeam().getPlayers().size() <= 1)
			return;
		StringBuilder stringBuilder = new StringBuilder();
		for(UUID uuid : user.getTeam().getPlayers())
			if(Bukkit.getPlayer(uuid) != null && !uuid.equals(event.getPlayerUUID()))
				stringBuilder.append((Bukkit.getPlayer(uuid).getHealth() <= 6.0D ? "§c" : Bukkit.getPlayer(uuid).getHealth() <= 10.0D ? "§6" : Bukkit.getPlayer(uuid).getHealth() <= 12.0D ? "§e" : "§a")+Bukkit.getPlayer(uuid).getName()+": §f"+(Bukkit.getPlayer(uuid).getWorld().getName().equals(Bukkit.getPlayer(event.getPlayerUUID()).getWorld().getName()) ? (UHCAPI.get().getGameManager().updateArrow(Bukkit.getPlayer(event.getPlayerUUID()), Bukkit.getPlayer(uuid).getLocation())+" §f"+new DecimalFormat("###########0.0").format(UHCAPI.get().getGameManager().playerDistance(Bukkit.getPlayer(event.getPlayerUUID()), Bukkit.getPlayer(uuid).getLocation()))) : "?")+"   ");
		event.setActionBar(stringBuilder.toString());
	}
}
