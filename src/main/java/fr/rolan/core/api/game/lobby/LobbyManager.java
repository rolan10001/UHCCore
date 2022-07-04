package fr.rolan.core.api.game.lobby;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import fr.rolan.api.game.enums.GameState;
import fr.rolan.api.game.lobby.ILobbyManager;
import fr.rolan.api.game.lobby.arena.IArenaManager;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.api.game.lobby.arena.ArenaManager;
import fr.rolan.core.listeners.lobby.LobbyProtectListener;
import fr.rolan.core.listeners.lobby.PlayerLobbyListener;
import fr.rolan.tools.NMSMethod;
import fr.rolan.tools.TPS;
import io.papermc.lib.PaperLib;

public class LobbyManager extends BukkitRunnable implements ILobbyManager {
	
	private final ArenaManager arenaManager;
	private final LobbyProtectListener lobbyProtectListener;
	private final PlayerLobbyListener playerLobbyListener;
	
	public LobbyManager() {
		this.arenaManager = new ArenaManager();
		this.lobbyProtectListener = new LobbyProtectListener();
		this.playerLobbyListener = new PlayerLobbyListener();
		Bukkit.getPluginManager().registerEvents(lobbyProtectListener, APIPlugin.getInstance());
		Bukkit.getPluginManager().registerEvents(playerLobbyListener, APIPlugin.getInstance());
		Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () -> APIPlugin.getInstance().getAPI().getGameManager().setGameState(GameState.LOBBY), 1L);
		runTaskTimerAsynchronously(APIPlugin.getInstance(), 20, 20);
	}
	
	@Override
	public IArenaManager getArenaManager() {
		return arenaManager;
	}
	
	@Override
	public void setPlayerLobby(Player player) {
		player.setGameMode(GameMode.ADVENTURE);
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.updateInventory();
		player.setMaxHealth(20);
		player.setHealth(20);
		player.setFoodLevel(25);
		PaperLib.teleportAsync(player, Bukkit.getWorld("Lobby").getSpawnLocation());
		for(PotionEffect potion : player.getActivePotionEffects())
			player.removePotionEffect(potion.getType());
		if(player.hasPermission("host.use")) {
			ItemStack it = new ItemStack(Material.COMMAND);
			ItemMeta meta = it.getItemMeta();
			meta.setDisplayName("§a§lConfigurez la partie");
			meta.setLore(Arrays.asList("", "§7Accéder au menu de configuration.", "", "§8§l» §eClic droit pour ouvrir le menu"));
			meta.addEnchant(Enchantment.DURABILITY, 5, false);
			it.setItemMeta(meta);
			player.getInventory().setItem(0, it);
		}
		if(APIPlugin.getInstance().getAPI().s.TEAM) {
			ItemStack team = new ItemStack(Material.BANNER, 1, (byte) 15);
			ItemMeta teamM = team.getItemMeta();
			teamM.setDisplayName("§eTeam");
			team.setItemMeta(teamM);
			player.getInventory().setItem(4, team);
		}
		ItemStack it = new ItemStack(Material.GOLD_SWORD);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName("§bArena");
		meta.setLore(Arrays.asList("", "§7Se téléporter à l'Arena.", "", "§8§l» §eClic droit pour se téléporter"));
		meta.addEnchant(Enchantment.DURABILITY, 5, false);
		it.setItemMeta(meta);
		player.getInventory().setItem(8, it);
	}
	
	@Override
	public void unregisterListeners() {
		HandlerList.unregisterAll(lobbyProtectListener);
		HandlerList.unregisterAll(playerLobbyListener);
	}
	
	@Override
	public Map<Integer, String> setLobbyScoreboard() {
		Map<Integer, String> map = new HashMap<>();
		map.put(0, "§8§l§m                    ");
		map.put(1, "§7» §cHost : §f%host%");
		map.put(2, "§7» §cJoueur(s) : §f%players%§c/§f%slot%");
		map.put(3, "§8§8§l§m                    ");
		map.put(4, "§7» §cJeu : %gamemode%");
		map.put(5, "§7» §cTeam : §f%team%");
		map.put(6, "§r§8§l§m                    ");
		return map;
	}

	@Override
	public void run() {
		if(!APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.LOBBY))
			cancel();
		Bukkit.getOnlinePlayers().forEach(player -> NMSMethod.setTablistHeaderFooter(player, "\n§6§l» §e§l"+(APIPlugin.getInstance().getAPI().s.NAME.equalsIgnoreCase("DxD UHC") ? "DxD §c§lUHC" : APIPlugin.getInstance().getAPI().s.NAME)+" §6§l«\n\n  §e§lPing: §r"+NMSMethod.getPingColor(player)+"§r  §l▏§r  §e§lTPS: §a"+TPS.getTPS()+"§r  §l▏§r  §e§lJoueurs: §a"+Bukkit.getOnlinePlayers().size()+"  \n", "\n§eCe plugin est open source\n\n§c§lgithub.com/rolan10001\n"));
	}
	
}
