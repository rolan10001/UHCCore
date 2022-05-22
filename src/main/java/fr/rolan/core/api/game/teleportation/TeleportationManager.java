package fr.rolan.core.api.game.teleportation;

import static fr.rolan.api.game.GameSettings.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import fr.rolan.api.game.enums.GameState;
import fr.rolan.api.game.team.Teams;
import fr.rolan.api.game.teleportation.ITeleportationManager;
import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.listeners.teleportation.LobbyProtectListener;
import fr.rolan.core.listeners.teleportation.PlayerLobbyListener;
import fr.rolan.tools.NMSMethod;
import fr.rolan.tools.TPS;
import io.papermc.lib.PaperLib;

public class TeleportationManager extends BukkitRunnable implements ITeleportationManager {
	
	private final LobbyProtectListener lobbyProtectListener;
	private final PlayerLobbyListener playerLobbyListener;
	HashMap<Teams, Location> teamLocation = new HashMap<Teams, Location>();
	List<Block> barrierList = new ArrayList<Block>();
	int i = 0;
	
	public TeleportationManager() {
		this.lobbyProtectListener = new LobbyProtectListener();
		this.playerLobbyListener = new PlayerLobbyListener(APIPlugin.getInstance().getAPI().s);
		Bukkit.getPluginManager().registerEvents(lobbyProtectListener, APIPlugin.getInstance());
		Bukkit.getPluginManager().registerEvents(playerLobbyListener, APIPlugin.getInstance());
		APIPlugin.getInstance().getAPI().getStartedManager().unregisterListeners();
		APIPlugin.getInstance().getAPI().getGameManager().setGameState(GameState.TELEPORTATION);
		APIPlugin.getInstance().getAPI().getScoreboardManager().setScoreboard(APIPlugin.getInstance().getAPI().s.NAME, setGameScoreboard());
		runTaskTimer(APIPlugin.getInstance(), 20, 20);
	}

	@Override
	public void unregisterListeners() {
		HandlerList.unregisterAll(lobbyProtectListener);
		HandlerList.unregisterAll(playerLobbyListener);
	}
	
	@Override
	public Map<Integer, String> setGameScoreboard() {
		Map<Integer, String> map = new HashMap<>();
		map.put(0, "§8§l§m                    ");
		map.put(1, "§7» §cHost : §f%host%");
		map.put(2, "§7» §cJoueur(s) : §f%players%");
		map.put(3, "§f§8§l§m                    ");
		map.put(4, "§7» §cEpisode : §f%episode%");
		map.put(5, "§7» §cTimer : §f%timerH%:%timerM%:%timerS%");
		map.put(6, "§7» §cBorder : §f%border%");
		map.put(7, "§8§8§l§m                    ");
		map.put(8, "§7» §cCentre :§f%center% §f%center_distance%");
		map.put(9, "§7» §cTeam : §f%team%");
		map.put(10, "§7» §cKills : §f%kill%");
		map.put(11, "§r§8§l§m                    ");
		return map;
	}

	@Override
	public void run() {
		if(i >= PLAYERS_LIST.size()) {
			cancel();
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if(!APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.TELEPORTATION)) cancel();
					for(Player player : Bukkit.getOnlinePlayers()) {
						IUser user = APIPlugin.getInstance().getAPI().getUser(player);
						DecimalFormat format = new DecimalFormat("00");
						NMSMethod.setTablistHeaderFooter(player, "\n§6§l» §e§l"+(APIPlugin.getInstance().getAPI().s.NAME.equalsIgnoreCase("DxD UHC") ? "DxD §c§lUHC" : APIPlugin.getInstance().getAPI().s.NAME)+" §6§l«\n\n  §e§lPing: §r"+NMSMethod.getPingColor(player)+"§r  §l▏§r  §e§lTPS: §a"+TPS.getTPS()+"§r  §l▏§r  §e§lJoueurs: §a"+Bukkit.getOnlinePlayers().size()+"  \n", "\n§7Timer: §e"+format.format(HEURE)+":"+format.format(MINUTE)+":"+format.format(SECOND)+"\n§7Kills: §e"+user.getKillStreak()+"\n");
						NMSMethod.sendActionbar(player, "§8§l» §7Stabilisation des TPS en cours...");
					}
				}
			}.runTaskTimer(APIPlugin.getInstance(), 20, 20);
			new BukkitRunnable() {
				
				@Override
				public void run() {
					for(Block bloc : barrierList)
						bloc.breakNaturally();
					if(APIPlugin.getInstance().getAPI().s.XENOPHOBIA) {
						new BukkitRunnable() {
							
							@Override
							public void run() {
								Bukkit.getOnlinePlayers().forEach(player -> NMSMethod.disguiseInPNJ(player));
							}
						}.runTaskTimer(APIPlugin.getInstance(), 300, 20);
					}
					APIPlugin.getInstance().getAPI().NewActifGameManager();
				}
			}.runTaskLater(APIPlugin.getInstance(), 200);
			return;
		}
		if(!Bukkit.getOfflinePlayer(PLAYERS_LIST.get(i)).isOnline()) {
			PLAYERS_LIST.remove(i);
		}else {
			Player player = Bukkit.getPlayer(PLAYERS_LIST.get(i));
			PLAYERS++;
			IUser user = APIPlugin.getInstance().getAPI().getUser(player);
			DecimalFormat format = new DecimalFormat("00");
			NMSMethod.setTablistHeaderFooter(player, "\n§6§l» §e§l"+(APIPlugin.getInstance().getAPI().s.NAME.equalsIgnoreCase("DxD UHC") ? "DxD §c§lUHC" : APIPlugin.getInstance().getAPI().s.NAME)+" §6§l«\n\n  §e§lPing: §r"+NMSMethod.getPingColor(player)+"§r  §l▏§r  §e§lTPS: §a"+TPS.getTPS()+"§r  §l▏§r  §e§lJoueurs: §a"+Bukkit.getOnlinePlayers().size()+"  \n", "\n§7Timer: §e"+format.format(HEURE)+":"+format.format(MINUTE)+":"+format.format(SECOND)+"\n§7Kills: §e"+user.getKillStreak()+"\n");
			player.setGameMode(GameMode.SURVIVAL);
			player.getInventory().clear();
			player.getInventory().setArmorContents(new ItemStack[] {new ItemStack(Material.AIR), new ItemStack(Material.AIR),
					new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
			try{player.getInventory().setContents(APIPlugin.getInstance().getAPI().getStuffManager().getStuffStart());}catch(NullPointerException e) {}
			try{player.getInventory().setArmorContents(APIPlugin.getInstance().getAPI().getStuffManager().getStuffArmorStart());}catch(NullPointerException e) {}
			player.updateInventory();
			for(PotionEffect e : player.getActivePotionEffects()) player.removePotionEffect(e.getType());
	        player.setLevel(0);
	        player.setExp(0);
	        player.setMaxHealth(20);
	        player.setHealth(20);
	        player.setFoodLevel(20);
	        player.setSaturation(20);
	        if(player.hasPermission("host.use"))
	        	player.setPlayerListName("§"+Integer.toHexString(user.getTeam().getColor())+player.getName());
	        if(APIPlugin.getInstance().getAPI().s.F3) {NMSMethod.enableF3(player);}else {NMSMethod.disableF3(player);}
			if(APIPlugin.getInstance().getAPI().s.TEAM) {
				if(!teamLocation.containsKey(user.getTeam())) {
					Random r = new Random();
			        int x;
			        int z;
			        int tp = r.nextInt(4);
					if(tp == 0) {
						x = (0 + (r.nextInt((int) BORDER.getSize() /2)) - 25);
						z = (0 + (r.nextInt((int) BORDER.getSize() /2)) - 25);
					}else if(tp == 1) {
						x = (0 - (r.nextInt((int) BORDER.getSize() /2)) + 25);
						z = (0 - (r.nextInt((int) BORDER.getSize() /2)) + 25);
					}else if(tp == 2) {
						x = (0 + (r.nextInt((int) BORDER.getSize() /2)) - 25);
						z = (0 - (r.nextInt((int) BORDER.getSize() /2)) + 25);
					}else {
						x = (0 - (r.nextInt((int) BORDER.getSize() /2)) + 25);
						z = (0 + (r.nextInt((int) BORDER.getSize() /2)) - 25);
					}
					x+=Bukkit.getWorld("Host").getSpawnLocation().getBlockX();
					z+=Bukkit.getWorld("Host").getSpawnLocation().getBlockZ();
					teamLocation.put(user.getTeam(), new Location(Bukkit.getWorld("Host"), x, 251, z));
					barrierList.add(new Location(Bukkit.getWorld("Host"), x, 250, z).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 250, z).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x, 250, z+1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 250, z+1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 250, z).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x, 250, z-1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 250, z-1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 250, z-1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 250, z+1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x+2, 252, z).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x+2, 252, z+1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x+2, 252, z-1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x-2, 252, z).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x-2, 252, z+1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x-2, 252, z-1).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x, 252, z+2).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 252, z+2).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 252, z+2).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x, 252, z-2).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 252, z-2).getBlock());
					barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 252, z-2).getBlock());
					new Location(Bukkit.getWorld("Host"), x, 250, z).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x+1, 250, z).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x, 250, z+1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x+1, 250, z+1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x-1, 250, z).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x, 250, z-1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x-1, 250, z-1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x+1, 250, z-1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x-1, 250, z+1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x+2, 252, z).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x+2, 252, z+1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x+2, 252, z-1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x-2, 252, z).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x-2, 252, z+1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x-2, 252, z-1).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x, 252, z+2).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x+1, 252, z+2).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x-1, 252, z+2).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x, 252, z-2).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x+1, 252, z-2).getBlock().setType(Material.BARRIER);
					new Location(Bukkit.getWorld("Host"), x-1, 252, z-2).getBlock().setType(Material.BARRIER);
				}
				PaperLib.teleportAsync(player, teamLocation.get(user.getTeam()));
			}else {
		        Random r = new Random();
		        int x;
		        int z;
		        int tp = r.nextInt(4);
				if(tp == 0) {
					x = (0 + (r.nextInt((int) BORDER.getSize() /2)) - 25);
					z = (0 + (r.nextInt((int) BORDER.getSize() /2)) - 25);
				}else if(tp == 1) {
					x = (0 - (r.nextInt((int) BORDER.getSize() /2)) + 25);
					z = (0 - (r.nextInt((int) BORDER.getSize() /2)) + 25);
				}else if(tp == 2) {
					x = (0 + (r.nextInt((int) BORDER.getSize() /2)) - 25);
					z = (0 - (r.nextInt((int) BORDER.getSize() /2)) + 25);
				}else {
					x = (0 - (r.nextInt((int) BORDER.getSize() /2)) + 25);
					z = (0 + (r.nextInt((int) BORDER.getSize() /2)) - 25);
				}
				x+=Bukkit.getWorld("Host").getSpawnLocation().getBlockX();
				z+=Bukkit.getWorld("Host").getSpawnLocation().getBlockZ();
				Location aléatoire = new Location(Bukkit.getWorld("Host"), x, 251, z);
				barrierList.add(new Location(Bukkit.getWorld("Host"), x, 250, z).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 250, z).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x, 250, z+1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 250, z+1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 250, z).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x, 250, z-1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 250, z-1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 250, z-1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 250, z+1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x+2, 252, z).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x+2, 252, z+1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x+2, 252, z-1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x-2, 252, z).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x-2, 252, z+1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x-2, 252, z-1).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x, 252, z+2).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 252, z+2).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 252, z+2).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x, 252, z-2).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x+1, 252, z-2).getBlock());
				barrierList.add(new Location(Bukkit.getWorld("Host"), x-1, 252, z-2).getBlock());
				new Location(Bukkit.getWorld("Host"), x, 250, z).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x+1, 250, z).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x, 250, z+1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x+1, 250, z+1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x-1, 250, z).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x, 250, z-1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x-1, 250, z-1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x+1, 250, z-1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x-1, 250, z+1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x+2, 252, z).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x+2, 252, z+1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x+2, 252, z-1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x-2, 252, z).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x-2, 252, z+1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x-2, 252, z-1).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x, 252, z+2).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x+1, 252, z+2).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x-1, 252, z+2).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x, 252, z-2).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x+1, 252, z-2).getBlock().setType(Material.BARRIER);
				new Location(Bukkit.getWorld("Host"), x-1, 252, z-2).getBlock().setType(Material.BARRIER);
				PaperLib.teleportAsync(player, aléatoire);
			}
			Bukkit.getOnlinePlayers().forEach(players -> NMSMethod.sendActionbar(players, "§8§l» §7Téléportation des joueurs : §e"+(i+1)+"§8/§e"+PLAYERS_LIST.size()));
			i++;
		}
	}
}
