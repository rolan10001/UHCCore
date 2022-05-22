package fr.rolan.core.listeners.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.gui.BannerTeam;
import io.papermc.lib.PaperLib;

public class PlayerLobbyListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		IUser user = APIPlugin.getInstance().getAPI().getUser(player);
		event.setJoinMessage("§a+ §8» §7"+player.getName()+" §8[§e"+Bukkit.getOnlinePlayers().size()+"§8/§e99§8]");
		if(user.isInArena())
			return;
		APIPlugin.getInstance().getAPI().getLobbyManager().setPlayerLobby(player);
		APIPlugin.getInstance().getAPI().getScoreboardManager().setPlayerScoreboard(player.getUniqueId(), APIPlugin.getInstance().getAPI().s.NAME, APIPlugin.getInstance().getAPI().getLobbyManager().setLobbyScoreboard());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage("§c- §8» §7"+player.getName()+" §8[§e"+(Bukkit.getOnlinePlayers().size()-1)+"§8/§e99§8]");
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		IUser user = APIPlugin.getInstance().getAPI().getUser(player);
		if(user.isInArena())
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
		Player player = (Player) event.getEntity();
		IUser user = APIPlugin.getInstance().getAPI().getUser(player);
		if(user.isInArena())
			return;
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
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName()) {
				if(event.getItem().getItemMeta().getDisplayName().equals("§bArena")) {
					Bukkit.dispatchCommand(event.getPlayer(), "arena");
				}else if(event.getItem().getItemMeta().getDisplayName().equals("§a§lConfigurez la partie")) {
					if(APIPlugin.getInstance().getAPI().s.HOST == null)
						APIPlugin.getInstance().getAPI().s.HOST = event.getPlayer().getUniqueId();
					event.setCancelled(true);
					Bukkit.dispatchCommand(event.getPlayer(), "h config");
				}else if(event.getItem().getItemMeta().getDisplayName().equals("§eTeam")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU == null) new BannerTeam(APIPlugin.getInstance().getAPI().s);
					event.getPlayer().openInventory(APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU);
				}
			}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		IUser user = APIPlugin.getInstance().getAPI().getUser(player);
		if(user.isInArena())
			return;
		if(player.getLocation().getY() > 0)
			return;
		PaperLib.teleportAsync(player, Bukkit.getWorld("Lobby").getSpawnLocation());
	}
}
