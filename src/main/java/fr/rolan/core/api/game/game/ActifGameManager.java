package fr.rolan.core.api.game.game;

import static fr.rolan.api.gui.GuiManager.*;
import static fr.rolan.api.game.GameSettings.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.rolan.api.UHCAPI;
import fr.rolan.api.events.BorderEvent;
import fr.rolan.api.events.DayEvent;
import fr.rolan.api.events.EpisodeEvent;
import fr.rolan.api.events.NoPvPEvent;
import fr.rolan.api.events.MinuteEvent;
import fr.rolan.api.events.NightEvent;
import fr.rolan.api.events.PvPEvent;
import fr.rolan.api.game.enums.GameState;
import fr.rolan.api.game.game.IActifGameManager;
import fr.rolan.api.game.scenario.superheroes.SuperHeroes;
import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.listeners.game.border.Border;
import fr.rolan.core.listeners.game.invincibility.Invincibility;
import fr.rolan.core.listeners.game.nopvp.NoPvP;
import fr.rolan.core.listeners.game.pvp.PvP;
import fr.rolan.tools.NMSMethod;
import fr.rolan.tools.TPS;

public class ActifGameManager extends BukkitRunnable implements IActifGameManager {
	
	private HashMap<UUID, Inventory> INVENTORIES = new HashMap<UUID, Inventory>();
	private HashMap<UUID, Location> deathLocation = new HashMap<UUID, Location>();
	private final Invincibility invincibility;
	private NoPvP nopvp;
	private PvP pvp;
	private Border border;
	
	public ActifGameManager() {
		this.invincibility = new Invincibility();
		APIPlugin.getInstance().getAPI().getTeleportationManager().unregisterListeners();
		APIPlugin.getInstance().getAPI().getGameManager().setGameState(GameState.INVINCIBILITY);
		runTaskTimer(APIPlugin.getInstance(), 20, 20);
		Bukkit.getScheduler().runTaskTimer(APIPlugin.getInstance(), () -> Bukkit.getOnlinePlayers().forEach(players -> UHCAPI.get().getGameManager().actionBar(players)), 5, 5);
	}

	@Override
	public void unregisterListeners() {
		if(APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.INVINCIBILITY)) {
			HandlerList.unregisterAll(invincibility);
		}else if(APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.NOPVP)) {
			HandlerList.unregisterAll(nopvp);
		}else if(APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.PVP)) {
			HandlerList.unregisterAll(pvp);
		}else if(APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.BORDER)) {
			HandlerList.unregisterAll(border);
		}
	}
	
	@Override
	public void newNoPvPState() {
		unregisterListeners();
		APIPlugin.getInstance().getAPI().getGameManager().setGameState(GameState.NOPVP);
		this.nopvp = new NoPvP();
		Bukkit.getPluginManager().callEvent(new NoPvPEvent());
		Bukkit.broadcastMessage("§8[§eUHC§8] §7Vous n'êtes plus invincible.");
	}

	@Override
	public void newPvPState() {
		unregisterListeners();
		APIPlugin.getInstance().getAPI().getGameManager().setGameState(GameState.PVP);
		this.pvp = new PvP();
		Bukkit.getPluginManager().callEvent(new PvPEvent());
		Bukkit.broadcastMessage("§8[§eUHC§8] §cLe PvP est désormais §aactivé §c!");
		Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.ENDERDRAGON_GROWL, 1F, 0F));
	}

	@Override
	public void newBorderState() {
		unregisterListeners();
		APIPlugin.getInstance().getAPI().getGameManager().setGameState(GameState.BORDER);
		this.border = new Border();
		Bukkit.getPluginManager().callEvent(new BorderEvent());
		BORDER.setWarningDistance(20);
		BORDER.setSize(APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE*2, (long) ((APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED));
		Bukkit.broadcastMessage("§8[§eUHC§8] §bLa réduction de la bordure est désormais §aactivée §7!");
		Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.ENDERDRAGON_GROWL, 1F, 0F));
	}
	
	@Override
	public HashMap<UUID, Inventory> getInventories() {
		return INVENTORIES;
	}
	
	@Override
	public HashMap<UUID, Location> getDeathLocation() {
		return deathLocation;
	}
	
	@Override
	public void run() {
		SECOND++;
		if(SECOND == 60) {
			MINUTE++;
			MINUTES++;
			SECOND = 0;
			Bukkit.getPluginManager().callEvent(new MinuteEvent());
			if(MINUTES%5 == 0 && MINUTES%10 != 0)
				Bukkit.getPluginManager().callEvent(new NightEvent());
			if(MINUTES%10 == 0)
				Bukkit.getPluginManager().callEvent(new DayEvent());
			if(MINUTES%20 == 0) {
				EPISODE++;
				Bukkit.broadcastMessage("§8[§eUHC§8] §7Fin de l'épisode §c"+EPISODE+"§7.");
				Bukkit.getPluginManager().callEvent(new EpisodeEvent());
			}
			if(MINUTE == 60) {
				HEURE++;
				MINUTE = 0;
			}
		}
		for(Player player : Bukkit.getOnlinePlayers()) {
			IUser user = APIPlugin.getInstance().getAPI().getUser(player);
			DecimalFormat format = new DecimalFormat("00");
			NMSMethod.setTablistHeaderFooter(player, "\n§6§l» §e§l"+(APIPlugin.getInstance().getAPI().s.NAME.equalsIgnoreCase("DxD UHC") ? "DxD §c§lUHC" : APIPlugin.getInstance().getAPI().s.NAME)+" §6§l«\n\n  §e§lPing: §r"+NMSMethod.getPingColor(player)+"§r  §l▏§r  §e§lTPS: §a"+TPS.getTPS()+"§r  §l▏§r  §e§lJoueurs: §a"+PLAYERS+"  \n", "\n§7Timer: §e"+format.format(HEURE)+":"+format.format(MINUTE)+":"+format.format(SECOND)+"\n§7Kills: §e"+user.getKillStreak()+"\n");
			if(APIPlugin.getInstance().getAPI().s.CATEYES)
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
			if(APIPlugin.getInstance().getAPI().s.PV_IN_TAB || APIPlugin.getInstance().getAPI().s.PV_ON_HEAD)
				HEALTH_SCOREBOARD.setObjectiveForPlayer(player);
		}
		for(UUID uuid : INVENTORIES.keySet())
			if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
				Inventory inv = INVENTORIES.get(uuid);
				Player target = Bukkit.getPlayer(uuid);
				for(int i = 0; i < 36; i++)
					inv.setItem(i, target.getInventory().getContents()[i] == null || target.getInventory().getContents()[i].getType().equals(Material.AIR) ? new ItemStack(Material.AIR) : target.getInventory().getContents()[i]);
				for(int i = 36; i < 45; i++)
					inv.setItem(i, getGlass());
				for(int i = 45; i < 49; i++)
					inv.setItem(i, target.getInventory().getArmorContents()[i-45] == null || target.getInventory().getArmorContents()[i-45].getType().equals(Material.AIR) ? new ItemStack(Material.AIR) : target.getInventory().getArmorContents()[i-45]);
				inv.setItem(49, getGlass());
				inv.setItem(50, setMetaInItem(new ItemStack(Material.GOLDEN_APPLE), "§eVie", Arrays.asList("§7Vie : §c"+new DecimalFormat("00.00").format(target.getHealth())+"❤", "§7Saturation : §e"+target.getFoodLevel(), "§7XP : §a"+target.getLevel())));
				List<String> list = new ArrayList<String>();
				for(PotionEffect effect : target.getActivePotionEffects())
					list.add("§b"+effect.getType().getName()+" "+effect.getAmplifier()+" : §7"+(effect.getDuration()/60)+"min"+(effect.getDuration()%60)+"s");
				inv.setItem(51, setMetaInItem(new ItemStack(Material.POTION, 1, (byte) 9), "§eEffets de potion", list));
				ItemStack it = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);SkullMeta meta = (SkullMeta) it.getItemMeta();meta.setOwner(target.getName());meta.setDisplayName("§eInformations");
				List<String> lore = new ArrayList<String>();
				lore.add("§7Kills : §c"+APIPlugin.getInstance().getAPI().getUser(target).getKillStreak());
				meta.setLore(lore);
				it.setItemMeta(meta);
				inv.setItem(52, it);
				inv.setItem(53, getGlass());
				INVENTORIES.replace(uuid, inv);
			}
		if(APIPlugin.getInstance().getAPI().s.SUPER_HEROES && HEURE == 0 && MINUTES == 0 && SECOND == 10)
			Bukkit.getOnlinePlayers().forEach(player -> new SuperHeroes(player));
		if(APIPlugin.getInstance().getAPI().s.INVINCIBILITY_TIMER/60 == MINUTES && APIPlugin.getInstance().getAPI().s.INVINCIBILITY_TIMER%60 == SECOND)
			newNoPvPState();
		if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == SECOND) {
			newPvPState();
		}else if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES+10 && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == SECOND) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7PvP activé dans §e10 §7minutes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == SECOND) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7PvP activé dans §e1 §7minute.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == 60-SECOND-30) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7PvP activé dans §e30 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == 60-SECOND-10) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7PvP activé dans §e10 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == 60-SECOND-5) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7PvP activé dans §e5 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == 60-SECOND-4) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7PvP activé dans §e4 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == 60-SECOND-3) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7PvP activé dans §e3 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == 60-SECOND-2) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7PvP activé dans §e2 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.PVP_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.PVP_TIMER%60 == 60-SECOND-1) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7PvP activé dans §e1 §7seconde.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}
		if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == SECOND) {
			newBorderState();
		}else if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES+10 && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == SECOND) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Réduction de la bordure dans §e10 §7minutes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == SECOND) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Réduction de la bordure dans §e1 §7minute.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == 60-SECOND-30) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Réduction de la bordure dans §e30 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == 60-SECOND-10) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Réduction de la bordure dans §e10 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == 60-SECOND-5) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Réduction de la bordure dans §e5 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == 60-SECOND-4) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Réduction de la bordure dans §e4 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == 60-SECOND-3) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Réduction de la bordure dans §e3 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == 60-SECOND-2) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Réduction de la bordure dans §e2 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}else if(APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60 == MINUTES+1 && APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60 == 60-SECOND-1) {
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Réduction de la bordure dans §e1 §7secondes.");
			Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.CLICK, 1.0F, 1.0F));
		}
		if(APIPlugin.getInstance().getAPI().s.FINALHEAL && APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER/60 == MINUTES && APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER%60 == SECOND) {
			Bukkit.getOnlinePlayers().forEach(player -> player.setHealth(player.getMaxHealth()));
			Bukkit.broadcastMessage("§8[§eUHC§8] §7Le scénario §dFinalHeal §7est §aactivé§7.");
		}
	}
}
