package fr.rolan.core.commands;

import static fr.rolan.api.gui.GuiManager.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.rolan.core.APIPlugin;

public class UHCCommand implements CommandExecutor, Listener {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player))
			return true;
		
		Player player = (Player)sender;
		Inventory inv = Bukkit.createInventory(null, 18, "§eRègles");
		for(int i = 0; i < 18; i++)
			inv.setItem(i, getGlass());
		inv.setItem(0, setMetaInItem(new ItemStack(Material.SIGN), "§8-=-=- §6§lInformation Générale §8-=-=-", Arrays.asList("", "§7Date de création §8» §e6 Février 2021", "", 
				"§7Fondateurs §8» §3Jacko_2 §7& §3Louki_", "§7Développement §8» §crolan10001", "", "§fBienvenue sur DxD UHC, c'est un mode de", "§fJeu inspiré de l'animé High School DxD. Le",
				"§fserveur tourne avec un plugin UHC qui est", "§fen constant développement.", "", "§7Discord §8» §9discord.dxduhc.com", "§7Website §8» §dwww.dxduhc.com")));
		List<String> rules = new ArrayList<String>(Arrays.asList("", "§7Slots §8» §a"+APIPlugin.getInstance().getAPI().s.SLOT, 
				"§7Taille d'équipe §8» §a"+(!APIPlugin.getInstance().getAPI().s.TEAM ? "FFA" : "To"+APIPlugin.getInstance().getAPI().s.TEAM_PLAYERS_SIZE), "§7Scénarios §8» "));
		if(APIPlugin.getInstance().getAPI().s.CUTCLEAN) rules.add(" §8● §aCutClean");
		if(APIPlugin.getInstance().getAPI().s.HASTEY_BOYS) rules.add(" §8● §aHasteyBoys");
		if(APIPlugin.getInstance().getAPI().s.HASTEY_BABIES) rules.add(" §8● §aHasteyBabies");
		if(APIPlugin.getInstance().getAPI().s.FIRELESS) rules.add(" §8● §aFireLess");
		if(APIPlugin.getInstance().getAPI().s.SAFEMINERS) rules.add(" §8● §aSafeMiners");
		if(APIPlugin.getInstance().getAPI().s.RODLESS) rules.add(" §8● §aRodLess");
		if(APIPlugin.getInstance().getAPI().s.NO_EGG_NO_SNOW) rules.add(" §8● §aNoEggNoSnow");
		if(APIPlugin.getInstance().getAPI().s.TIMBER) rules.add(" §8● §aTimber");
		if(APIPlugin.getInstance().getAPI().s.FINALHEAL) rules.add(" §8● §aFinalHeal");
		if(APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT) rules.add(" §8● §aDiamond Limit");
		if(APIPlugin.getInstance().getAPI().s.CATEYES) rules.add(" §8● §aCatEyes");
		if(APIPlugin.getInstance().getAPI().s.SUPER_HEROES) rules.add(" §8● §aSuper Heroes");
		if(APIPlugin.getInstance().getAPI().s.GONEFISHING) rules.add(" §8● §aGoneFishing");
		if(APIPlugin.getInstance().getAPI().s.XENOPHOBIA) rules.add(" §8● §aXenophobia");
		inv.setItem(2, setMetaInItem(new ItemStack(Material.PAPER), "§8-=-=- §6§lRègles §8-=-=-", rules));
		inv.setItem(4, setMetaInItem(new ItemStack(Material.FLINT), "§8-=-=- §6§lDrops §8-=-=-", Arrays.asList("", "§7Pomme §8» §6"+APIPlugin.getInstance().getAPI().s.APPLE_DROP+"%", "§7Silex §8» §6"+APIPlugin.getInstance().getAPI().s.FLINT_DROP+"%", 
				"§7Ender Pearl §8» §6"+APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP+"%", "", "§7Shears §8» §aActivé", "", "§eLes pommes dropent sur tous les arbres.")));
		inv.setItem(6, setMetaInItem(new ItemStack(Material.GOLDEN_APPLE), "§8-=-=- §6§lInformation Soin §8-=-=-", Arrays.asList("", "§7Absorption §8» §e2❤", "", 
				"§7Absorption §8» "+(APIPlugin.getInstance().getAPI().s.ABSORPTION ? "§aActivé":"§cDésactivé"), "§7Notch Apple §8» §cDésactivé", "", "§7Golden Head §8» §cDésactivé", "§7Heads Heal §8» §64❤")));
		inv.setItem(8, setMetaInItem(new ItemStack(Material.BEACON), "§8-=-=- §6§lParamètres §8-=-=-", Arrays.asList("", "§7Pv dans le TAB §8» "+(APIPlugin.getInstance().getAPI().s.PV_IN_TAB ? "§aActivé":"§cDésactivé"),
				"§7Pv sur la tête §8» "+(APIPlugin.getInstance().getAPI().s.PV_ON_HEAD ? "§aActivé":"§cDésactivé"), "§7Coordonnées §8» "+(APIPlugin.getInstance().getAPI().s.F3 ? "§aActivé":"§cDésactivé"), "§7Auto-Revive §8» "+(APIPlugin.getInstance().getAPI().s.AUTORESPAWN ? "§aActivé":"§cDésactivé"),
				"§7Bug F5 §8» §aActivé")));
		inv.setItem(10, setMetaInItem(new ItemStack(Material.GRASS), "§8-=-=- §6§lParamètres Monde §8-=-=-", Arrays.asList("", "§7Difficulté §8» "+
				(APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.PEACEFUL) ? "§bPaisible": APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.EASY) ? "§aFacile" : APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.NORMAL) ? "§eNormal" : "§cDifficile"),
				"", "§7Nether §8» "+(APIPlugin.getInstance().getAPI().s.NETHER ? "§aActivé":"§cDésactivé"), "§7Chevaux §8» "+(APIPlugin.getInstance().getAPI().s.HORSE ? "§aActivé":"§cDésactivé"), "§7Cave Boost §8» "+(APIPlugin.getInstance().getAPI().s.CAVE_BOOST ? "§aActivé":"§cDésactivé"),
				"§7Middle Biome §8» "+APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE.getName())));
		inv.setItem(12, setMetaInItem(new ItemStack(Material.BARRIER), "§8-=-=- §6§lBordure §8-=-=-", Arrays.asList("", "§7Taille §8» §e"+APIPlugin.getInstance().getAPI().s.BORDER_SIZE+" +/-",
				"§7Taille Finale §8» §6"+APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE+" +/-", "§7Vitesse §8» §b"+new DecimalFormat("0.0").format(APIPlugin.getInstance().getAPI().s.BORDER_SPEED)+"blocs/s", 
				"§7Durée Estimée §8» §3"+new DecimalFormat("00").format(((int)((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)/60))+"min "+
				new DecimalFormat("00").format(((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)%60)+"s")));
		DecimalFormat format = new DecimalFormat("##00");
		List<String> timers = new ArrayList<String>(Arrays.asList("", "§7PvP §8» §c"+format.format((APIPlugin.getInstance().getAPI().s.PVP_TIMER/60))+"min "+format.format((APIPlugin.getInstance().getAPI().s.PVP_TIMER%60))+"s", 
				"§7Bordure §8» §b"+format.format((APIPlugin.getInstance().getAPI().s.BORDER_TIMER/60))+"min "+format.format((APIPlugin.getInstance().getAPI().s.BORDER_TIMER%60))+"s",
				"§7Invincibilité §8» §e"+format.format((APIPlugin.getInstance().getAPI().s.INVINCIBILITY_TIMER/60))+"min "+format.format((APIPlugin.getInstance().getAPI().s.INVINCIBILITY_TIMER%60))+"s"));
		if(APIPlugin.getInstance().getAPI().s.FINALHEAL) {
			timers.add("");
			timers.add("§7FinalHeal §8» §d"+new DecimalFormat("00").format((APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER/60))+"min "+new DecimalFormat("00").format((APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER%60))+"s");
		}
		/*if(isMode(ModeType.DXDUHC)) {
			timers.add("");
			timers.add("§7Rôles §8» §b"+new DecimalFormat("00").format((TIMER_ROLES/60))+"min "+new DecimalFormat("00").format((TIMER_ROLES%60))+"s");
			timers.add("§7Liste Démons §8» §c"+new DecimalFormat("00").format((TIMER_LIST/60))+"min "+new DecimalFormat("00").format((TIMER_LIST%60))+"s");
			if(DEMON_ERRANT)
				timers.add("§7Démon Errant §8» §4"+new DecimalFormat("00").format((MOMENT_DEMON_ERRANT/60))+"min "+new DecimalFormat("00").format((MOMENT_DEMON_ERRANT%60))+"s");
		}*/
		inv.setItem(14, setMetaInItem(new ItemStack(Material.WATCH), "§8-=-=- §6§lTimers §8-=-=-", timers));
		inv.setItem(16, setMetaInItem(new ItemStack(Material.DIAMOND_CHESTPLATE), "§8-=-=- §6§lArmure §8-=-=-", Arrays.asList("", "§7Limite de pièce §8» §3"+APIPlugin.getInstance().getAPI().s.ARMOR)));
		player.openInventory(inv);
		
		return false;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§eRègles")) return;
		event.setCancelled(true);
	}
}
