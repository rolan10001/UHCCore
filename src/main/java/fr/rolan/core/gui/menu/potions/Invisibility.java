package fr.rolan.core.gui.menu.potions;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.core.APIPlugin;

public class Invisibility implements Listener {
	
	public Invisibility() {
		APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU = Bukkit.createInventory(null, 54, "§8» §eInvisibilité");
		
		ItemStack m = new ItemStack(Material.POTION, 1, (byte) 14); PotionMeta M = (PotionMeta) m.getItemMeta(); M.setDisplayName("§bInvisibilité"); M.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.INVISIBILITY ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY ? "§cdésactiver" : "§aactiver"))); M.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); m.setItemMeta(M);
		
		ItemStack level = new ItemStack(Material.GLOWSTONE_DUST); ItemMeta levelM = level.getItemMeta(); levelM.setDisplayName("§ePotion amplifiée"); levelM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.INVISIBILITY_AMPLIFIED ? "§aActiver" : "§cDésactiver"))); level.setItemMeta(levelM);
		ItemStack lengthen = new ItemStack(Material.REDSTONE); ItemMeta lengthenM = lengthen.getItemMeta(); lengthenM.setDisplayName("§ePotion allongée"); lengthenM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.INVISIBILITY_LENGTHEN ? "§aActiver" : "§cDésactiver"))); lengthen.setItemMeta(lengthenM);
		ItemStack splash = new ItemStack(Material.SULPHUR); ItemMeta splashM = splash.getItemMeta(); splashM.setDisplayName("§ePotion jetable"); splashM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.INVISIBILITY_SPLASH ? "§aActiver" : "§cDésactiver"))); splash.setItemMeta(splashM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU.setItem(19, m);
		APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU.setItem(22, level);
		APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU.setItem(23, lengthen);
		APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU.setItem(24, splash);
		APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU.setItem(49, getArrowBack());
		
		for(int z = 0; z < 54; z++) if(APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU.getItem(z) == null) APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU.setItem(z, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eInvisibilité")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bInvisibilité")) {
				if(APIPlugin.getInstance().getAPI().s.INVISIBILITY) {APIPlugin.getInstance().getAPI().s.INVISIBILITY = false;}else {APIPlugin.getInstance().getAPI().s.INVISIBILITY = true;}
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotion amplifiée")) {
				if(APIPlugin.getInstance().getAPI().s.INVISIBILITY_AMPLIFIED) {APIPlugin.getInstance().getAPI().s.INVISIBILITY_AMPLIFIED = false;}else {APIPlugin.getInstance().getAPI().s.INVISIBILITY_AMPLIFIED = true;}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.INVISIBILITY_AMPLIFIED ? "§aActiver" : "§cDésactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotion allongée")) {
				if(APIPlugin.getInstance().getAPI().s.INVISIBILITY_LENGTHEN) {APIPlugin.getInstance().getAPI().s.INVISIBILITY_LENGTHEN = false;}else {APIPlugin.getInstance().getAPI().s.INVISIBILITY_LENGTHEN = true;}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.INVISIBILITY_LENGTHEN ? "§aActiver" : "§cDésactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotion jetable")) {
				if(APIPlugin.getInstance().getAPI().s.INVISIBILITY_SPLASH) {APIPlugin.getInstance().getAPI().s.INVISIBILITY_SPLASH = false;}else {APIPlugin.getInstance().getAPI().s.INVISIBILITY_SPLASH = true;}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.INVISIBILITY_SPLASH ? "§aActiver" : "§cDésactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}
			ItemMeta meta = APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU.getItem(19).getItemMeta();
			meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.INVISIBILITY ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY ? "§cdésactiver" : "§aactiver")));
			APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU.getItem(19).setItemMeta(meta);
			APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.getItem(34).setItemMeta(meta);
		}
	}
}
