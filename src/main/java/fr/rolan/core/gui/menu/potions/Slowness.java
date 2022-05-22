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

public class Slowness implements Listener {
	
	public Slowness() {
		APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU = Bukkit.createInventory(null, 54, "§8» §eLenteur");
		
		ItemStack i = new ItemStack(Material.POTION, 1, (byte) 10); PotionMeta I = (PotionMeta) i.getItemMeta(); I.setDisplayName("§bLenteur"); I.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SLOWNESS ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.SLOWNESS_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.SLOWNESS_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.SLOWNESS_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.SLOWNESS ? "§cdésactiver" : "§aactiver"))); I.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); i.setItemMeta(I);
		
		ItemStack level = new ItemStack(Material.GLOWSTONE_DUST); ItemMeta levelM = level.getItemMeta(); levelM.setDisplayName("§ePotion amplifiée"); levelM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SLOWNESS_AMPLIFIED ? "§aActiver" : "§cDésactiver"))); level.setItemMeta(levelM);
		ItemStack lengthen = new ItemStack(Material.REDSTONE); ItemMeta lengthenM = lengthen.getItemMeta(); lengthenM.setDisplayName("§ePotion allongée"); lengthenM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SLOWNESS_LENGTHEN ? "§aActiver" : "§cDésactiver"))); lengthen.setItemMeta(lengthenM);
		ItemStack splash = new ItemStack(Material.SULPHUR); ItemMeta splashM = splash.getItemMeta(); splashM.setDisplayName("§ePotion jetable"); splashM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SLOWNESS_SPLASH ? "§aActiver" : "§cDésactiver"))); splash.setItemMeta(splashM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU.setItem(19, i);
		APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU.setItem(22, level);
		APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU.setItem(23, lengthen);
		APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU.setItem(24, splash);
		APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU.setItem(49, getArrowBack());
		
		for(int z = 0; z < 54; z++) if(APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU.getItem(z) == null) APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU.setItem(z, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eLenteur")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bLenteur")) {
				if(APIPlugin.getInstance().getAPI().s.SLOWNESS) {APIPlugin.getInstance().getAPI().s.SLOWNESS = false;}else {APIPlugin.getInstance().getAPI().s.SLOWNESS = true;}
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotion amplifiée")) {
				if(APIPlugin.getInstance().getAPI().s.SLOWNESS_AMPLIFIED) {APIPlugin.getInstance().getAPI().s.SLOWNESS_AMPLIFIED = false;}else {APIPlugin.getInstance().getAPI().s.SLOWNESS_AMPLIFIED = true;}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SLOWNESS_AMPLIFIED ? "§aActiver" : "§cDésactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotion allongée")) {
				if(APIPlugin.getInstance().getAPI().s.SLOWNESS_LENGTHEN) {APIPlugin.getInstance().getAPI().s.SLOWNESS_LENGTHEN = false;}else {APIPlugin.getInstance().getAPI().s.SLOWNESS_LENGTHEN = true;}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SLOWNESS_LENGTHEN ? "§aActiver" : "§cDésactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotion jetable")) {
				if(APIPlugin.getInstance().getAPI().s.SLOWNESS_SPLASH) {APIPlugin.getInstance().getAPI().s.SLOWNESS_SPLASH = false;}else {APIPlugin.getInstance().getAPI().s.SLOWNESS_SPLASH = true;}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SLOWNESS_SPLASH ? "§aActiver" : "§cDésactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}
			ItemMeta meta = APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU.getItem(19).getItemMeta();
			meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SLOWNESS ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.SLOWNESS_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.SLOWNESS_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.SLOWNESS_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.SLOWNESS ? "§cdésactiver" : "§aactiver")));
			APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU.getItem(19).setItemMeta(meta);
			APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.getItem(30).setItemMeta(meta);
		}
	}
}
