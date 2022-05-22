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

public class Régénération implements Listener {
	
	public Régénération() {
		APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU = Bukkit.createInventory(null, 54, "§8» §eRégénération");
		
		ItemStack a = new ItemStack(Material.POTION, 1, (byte) 1); PotionMeta A = (PotionMeta) a.getItemMeta(); A.setDisplayName("§bRégénération"); A.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.REGENERATION ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.REGENERATION_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.REGENERATION_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.REGENERATION_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.REGENERATION ? "§cdésactiver" : "§aactiver"))); A.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); a.setItemMeta(A);
		
		ItemStack level = new ItemStack(Material.GLOWSTONE_DUST); ItemMeta levelM = level.getItemMeta(); levelM.setDisplayName("§ePotion amplifiée"); levelM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.REGENERATION_AMPLIFIED ? "§aActiver" : "§cDésactiver"))); level.setItemMeta(levelM);
		ItemStack lengthen = new ItemStack(Material.REDSTONE); ItemMeta lengthenM = lengthen.getItemMeta(); lengthenM.setDisplayName("§ePotion allongée"); lengthenM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.REGENERATION_LENGTHEN ? "§aActiver" : "§cDésactiver"))); lengthen.setItemMeta(lengthenM);
		ItemStack splash = new ItemStack(Material.SULPHUR); ItemMeta splashM = splash.getItemMeta(); splashM.setDisplayName("§ePotion jetable"); splashM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.REGENERATION_SPLASH ? "§aActiver" : "§cDésactiver"))); splash.setItemMeta(splashM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU.setItem(19, a);
		APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU.setItem(22, level);
		APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU.setItem(23, lengthen);
		APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU.setItem(24, splash);
		APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eRégénération")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bRégénération")) {
				if(APIPlugin.getInstance().getAPI().s.REGENERATION) {APIPlugin.getInstance().getAPI().s.REGENERATION = false;}else {APIPlugin.getInstance().getAPI().s.REGENERATION = true;}
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotion amplifiée")) {
				if(APIPlugin.getInstance().getAPI().s.REGENERATION_AMPLIFIED) {APIPlugin.getInstance().getAPI().s.REGENERATION_AMPLIFIED = false;}else {APIPlugin.getInstance().getAPI().s.REGENERATION_AMPLIFIED = true;}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.REGENERATION_AMPLIFIED ? "§aActiver" : "§cDésactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotion allongée")) {
				if(APIPlugin.getInstance().getAPI().s.REGENERATION_LENGTHEN) {APIPlugin.getInstance().getAPI().s.REGENERATION_LENGTHEN = false;}else {APIPlugin.getInstance().getAPI().s.REGENERATION_LENGTHEN = true;}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.REGENERATION_LENGTHEN ? "§aActiver" : "§cDésactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotion jetable")) {
				if(APIPlugin.getInstance().getAPI().s.REGENERATION_SPLASH) {APIPlugin.getInstance().getAPI().s.REGENERATION_SPLASH = false;}else {APIPlugin.getInstance().getAPI().s.REGENERATION_SPLASH = true;}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.REGENERATION_SPLASH ? "§aActiver" : "§cDésactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}
			ItemMeta meta = APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU.getItem(19).getItemMeta();
			meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.REGENERATION ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.REGENERATION_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.REGENERATION_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.REGENERATION_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.REGENERATION ? "§cdésactiver" : "§aactiver")));
			APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU.getItem(19).setItemMeta(meta);
			APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.getItem(10).setItemMeta(meta);
		}
	}
}
