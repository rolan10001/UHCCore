package fr.rolan.core.listeners.teleportation;

import static fr.rolan.api.game.GameSettings.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.rolan.api.game.Settings;
import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;
import fr.rolan.tools.NMSMethod;
import io.papermc.lib.PaperLib;

public class PlayerLobbyListener implements Listener {
	
	private Settings s;
	
	public PlayerLobbyListener(Settings s) {
		this.s = s;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage("§a+ §8» §7"+player.getName()+" §8[§e"+Bukkit.getOnlinePlayers().size()+"§8/§e99§8]");
		if(PLAYERS_LIST.contains(player.getUniqueId())) {
			PLAYERS++;
			if(s.F3) {NMSMethod.enableF3(player);}else {NMSMethod.disableF3(player);}
			event.setJoinMessage("§a+ §8» §7"+player.getName()+" §8[§e"+PLAYERS_LIST.size()+"§8/§e99§8]");
		}else {
			IUser user = APIPlugin.getInstance().getAPI().getUser(player);
			user.setSpectator(true);
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("§7§l▏ §7La partie a déjà commencé, vous pouvez toujours la regarder en tant que spectateur.");
			event.setJoinMessage(null);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage("§c- §8» §7"+player.getName()+" §8[§e"+(Bukkit.getOnlinePlayers().size()-1)+"§8/§e99§8]");
		if(PLAYERS_LIST.contains(player.getUniqueId())) {
			event.setQuitMessage("§c- §8» §7"+player.getName()+" §8[§e"+(PLAYERS_LIST.size()-1)+"§8/§e99§8]");
			PLAYERS--;
		}else {
			event.setQuitMessage(null);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		IUser user = APIPlugin.getInstance().getAPI().getUser(player);
		for(Player players : Bukkit.getOnlinePlayers())
			players.sendMessage((APIPlugin.getInstance().getAPI().getPermissions().isAdmin(player.getUniqueId()) ? "§c§lADMIN ▏ " : APIPlugin.getInstance().getAPI().getPermissions().isStaff(player.getUniqueId()) ? "§a§lSTAFF ▏ " : player.hasPermission("host.use") ? "§e§lHOST ▏ " : "")+user.getTeam().getPrefix()+player.getName()+" §8§l» "+((player.hasPermission("host.use")) ? "§r" : "§7")+((event.getMessage().startsWith("&") && player.hasPermission("host.use")) ? ChatColor.translateAlternateColorCodes('&', event.getMessage()) : event.getMessage()));
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
		event.setFoodLevel(25);
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if(event.getItemDrop().getItemStack().hasItemMeta() && event.getItemDrop().getItemStack().getItemMeta().hasDisplayName())
			if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals("§bArena") || event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals("§a§lConfigurez la partie"))
				event.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null) return;
		
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName())
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bArena") || event.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lConfigurez la partie"))
				event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(player.getLocation().getY() > 0)
			return;
		PaperLib.teleportAsync(player, Bukkit.getWorld("Lobby").getSpawnLocation());
	}
}