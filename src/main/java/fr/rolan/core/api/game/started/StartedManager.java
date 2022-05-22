package fr.rolan.core.api.game.started;

import static fr.rolan.api.gui.GuiManager.*;
import static fr.rolan.api.game.GameSettings.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.rolan.api.events.StartedManagerConstructorEvent;
import fr.rolan.api.game.enums.GAMEMode;
import fr.rolan.api.game.enums.GameState;
import fr.rolan.api.game.started.IStartedManager;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.api.game.scenario.CutClean;
import fr.rolan.core.api.game.scenario.DiamondLimit;
import fr.rolan.core.api.game.scenario.FireLess;
import fr.rolan.core.api.game.scenario.HasteyBabies;
import fr.rolan.core.api.game.scenario.HasteyBoys;
import fr.rolan.core.api.game.scenario.NoEggNoSnow;
import fr.rolan.core.api.game.scenario.RodLess;
import fr.rolan.core.api.game.scenario.SafeMiners;
import fr.rolan.core.api.game.scenario.SuperHeroesListener;
import fr.rolan.core.api.game.scenario.Timber;
import fr.rolan.core.listeners.started.LobbyProtectListener;
import fr.rolan.core.listeners.started.PlayerLobbyListener;
import fr.rolan.tools.NMSMethod;
import fr.rolan.tools.TPS;
import fr.rolan.tools.scoreboard.ScoreboardHealth;

public class StartedManager extends BukkitRunnable implements IStartedManager {
	
	private final LobbyProtectListener lobbyProtectListener;
	private final PlayerLobbyListener playerLobbyListener;
	private Class<?> clazz;
	private Object obj;
	
	public StartedManager() {
		this.lobbyProtectListener = new LobbyProtectListener();
		this.playerLobbyListener = new PlayerLobbyListener();
		Bukkit.getPluginManager().registerEvents(lobbyProtectListener, APIPlugin.getInstance());
		Bukkit.getPluginManager().registerEvents(playerLobbyListener, APIPlugin.getInstance());
		APIPlugin.getInstance().getAPI().getLobbyManager().unregisterListeners();
		APIPlugin.getInstance().getAPI().getGameManager().setGameState(GameState.STARTED);
		runTaskTimer(APIPlugin.getInstance(), 20, 20);
	}
	
	@Override
	public void unregisterListeners() {
		HandlerList.unregisterAll(lobbyProtectListener);
		HandlerList.unregisterAll(playerLobbyListener);
	}
	
	@Override
	public void setClassManager(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public Class<?> getClassManager() {
		return clazz;
	}
	
	@Override
	public Object getInstanceObjectManager() {
		return obj;
	}
	
	int i = 10;
	
	@Override
	public void run() {
		if(!APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.STARTED)) {
			cancel();
			Bukkit.getOnlinePlayers().forEach(player -> NMSMethod.sendTitle(player, 1, 20, 1, "§e§l"+(APIPlugin.getInstance().getAPI().s.NAME.equalsIgnoreCase("DxD UHC") ? "DxD §c§lUHC" : APIPlugin.getInstance().getAPI().s.NAME), "§cDépart annulé"));
			return;
		}else if(i <= 0) {
			cancel();
			Bukkit.getOnlinePlayers().forEach(player -> NMSMethod.sendTitle(player, 5, 20, 5, "§e§l"+(APIPlugin.getInstance().getAPI().s.NAME.equalsIgnoreCase("DxD UHC") ? "DxD §c§lUHC" : APIPlugin.getInstance().getAPI().s.NAME), "§bBonne chance !"));
			for(Player players : Bukkit.getOnlinePlayers()) PLAYERS_LIST.add(players.getUniqueId());
			Bukkit.getPluginManager().callEvent(new StartedManagerConstructorEvent());
			APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(45, getGlass());
			APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(49, getArrowBack());
			BORDER.setCenter(Bukkit.getWorld("Host").getSpawnLocation());
			BORDER.setSize(APIPlugin.getInstance().getAPI().s.BORDER_SIZE*2);
			Bukkit.getWorld("Host").setGameRuleValue("doFireTick", "false");
			Bukkit.getWorld("Host").setGameRuleValue("reducedDebugInfo", "true");
			Bukkit.getWorld("Host").setGameRuleValue("naturalRegeneration", "false");
			Bukkit.getWorld("Host").setGameRuleValue("randomTickSpeed", "3");
			Bukkit.getWorld("Host").setFullTime(0);
			Bukkit.getWorld("Host").setDifficulty(APIPlugin.getInstance().getAPI().s.DIFFICULTY);
			PLAYERS = 0;
			if(APIPlugin.getInstance().getAPI().s.CUTCLEAN)
				APIPlugin.getInstance().getAPI().getScenarios().add(new CutClean());
			if(APIPlugin.getInstance().getAPI().s.HASTEY_BOYS)
				APIPlugin.getInstance().getAPI().getScenarios().add(new HasteyBoys());
			if(APIPlugin.getInstance().getAPI().s.HASTEY_BABIES)
				APIPlugin.getInstance().getAPI().getScenarios().add(new HasteyBabies());
			if(APIPlugin.getInstance().getAPI().s.FIRELESS)
				APIPlugin.getInstance().getAPI().getScenarios().add(new FireLess());
			if(APIPlugin.getInstance().getAPI().s.RODLESS)
				APIPlugin.getInstance().getAPI().getScenarios().add(new RodLess());
			if(APIPlugin.getInstance().getAPI().s.NO_EGG_NO_SNOW)
				APIPlugin.getInstance().getAPI().getScenarios().add(new NoEggNoSnow());
			if(APIPlugin.getInstance().getAPI().s.TIMBER)
				APIPlugin.getInstance().getAPI().getScenarios().add(new Timber());
			if(APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT)
				APIPlugin.getInstance().getAPI().getScenarios().add(new DiamondLimit(APIPlugin.getInstance().getAPI().s));
			if(APIPlugin.getInstance().getAPI().s.SUPER_HEROES)
				APIPlugin.getInstance().getAPI().getScenarios().add(new SuperHeroesListener());
			if(APIPlugin.getInstance().getAPI().s.SAFEMINERS)
				APIPlugin.getInstance().getAPI().getScenarios().add(new SafeMiners());
			if(APIPlugin.getInstance().getAPI().s.PV_IN_TAB || APIPlugin.getInstance().getAPI().s.PV_ON_HEAD) HEALTH_SCOREBOARD = new ScoreboardHealth(APIPlugin.getInstance().getAPI().s, APIPlugin.getInstance().getAPI().s.PV_IN_TAB, APIPlugin.getInstance().getAPI().s.PV_ON_HEAD);
			if(APIPlugin.getInstance().getAPI().s.GONEFISHING) {ItemStack rod = new ItemStack(Material.FISHING_ROD);ItemMeta rodM = rod.getItemMeta();rodM.spigot().setUnbreakable(true);rodM.addEnchant(Enchantment.LURE, 3, true);rodM.addEnchant(Enchantment.LUCK, 254, true);rod.setItemMeta(rodM);Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().addItem(rod));}
			if(APIPlugin.getInstance().getAPI().s.GAMEMODE.equals(GAMEMode.UHC)) {
				APIPlugin.getInstance().getAPI().NewTeleportationManager();
			}else {
				try {
					obj = clazz.newInstance();
				} catch(InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		for(Player players : Bukkit.getOnlinePlayers()) {
			NMSMethod.setTablistHeaderFooter(players, "\n§6§l» §e§l"+(APIPlugin.getInstance().getAPI().s.NAME.equalsIgnoreCase("DxD UHC") ? "DxD §c§lUHC" : APIPlugin.getInstance().getAPI().s.NAME)+" §6§l«\n\n  §e§lPing: §r"+NMSMethod.getPingColor(players)+"§r  §l▏§r  §e§lTPS: §a"+TPS.getTPS()+"§r  §l▏§r  §e§lJoueurs: §a"+Bukkit.getOnlinePlayers().size()+"  \n", "\n§eToutes les informations sur §bDxD UHC\n\n§c§lwww.dxduhc.com\n");
			NMSMethod.sendTitle(players, 1, 20, 1, "§e§l"+(APIPlugin.getInstance().getAPI().s.NAME.equalsIgnoreCase("DxD UHC") ? "DxD §c§lUHC" : APIPlugin.getInstance().getAPI().s.NAME), "§6Lancement dans §c"+i+"§6s");
			players.playSound(players.getLocation(), "random.orb", 0.5F, (float) (0.5+i/10.0));
		}
		i--;
	}
}
