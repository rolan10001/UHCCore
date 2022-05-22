package fr.rolan.core.gui.menu.armor;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.core.APIPlugin;

public class Weapons implements Listener {
	
	public Weapons() {
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU = Bukkit.createInventory(null, 54, "§8» §eArmes");
		
		ItemStack diamond = new ItemStack(Material.DIAMOND_SWORD); ItemMeta diamondM = diamond.getItemMeta(); diamondM.setDisplayName("§bÉpée en Diamant"); diamondM.setLore(Arrays.asList("", "§8» §eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_DIAMOND_SWORD, "§8» §dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_DIAMOND_SWORD, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_DIAMOND_SWORD, "§8» §cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_DIAMOND_SWORD)); diamond.setItemMeta(diamondM);
		ItemStack iron = new ItemStack(Material.IRON_SWORD); ItemMeta ironM = iron.getItemMeta(); ironM.setDisplayName("§bÉpée en Fer"); ironM.setLore(Arrays.asList("", "§8» §eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_IRON_SWORD, "§8» §dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_IRON_SWORD, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_SWORD, "§8» §cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_IRON_SWORD)); iron.setItemMeta(ironM);
		ItemStack bow = new ItemStack(Material.BOW); ItemMeta bowM = bow.getItemMeta(); bowM.setDisplayName("§bArc"); bowM.setLore(Arrays.asList("", "§8» §ePower §6"+APIPlugin.getInstance().getAPI().s.POWER, "§8» §dPunch §5"+APIPlugin.getInstance().getAPI().s.PUNCH, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_BOW, "§8» §cFlame §4"+APIPlugin.getInstance().getAPI().s.FLAME, "§8» §7Infinity §8"+APIPlugin.getInstance().getAPI().s.INFINITY)); bow.setItemMeta(bowM);
		
		ItemStack it = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName("§1§bSharpness"); meta.setLore(Arrays.asList("", "§eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(12, it);
		meta.setDisplayName("§1§bKnockback"); meta.setLore(Arrays.asList("", "§dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(13, it);
		meta.setDisplayName("§1§bUnbreaking"); meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(14, it);
		meta.setDisplayName("§1§bFire Aspect"); meta.setLore(Arrays.asList("", "§cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(15, it);
		meta.setDisplayName("§2§bSharpness"); meta.setLore(Arrays.asList("", "§eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(21, it);
		meta.setDisplayName("§2§bKnockback"); meta.setLore(Arrays.asList("", "§dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(22, it);
		meta.setDisplayName("§2§bUnbreaking"); meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(23, it);
		meta.setDisplayName("§2§bFire Aspect"); meta.setLore(Arrays.asList("", "§cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(24, it);
		meta.setDisplayName("§bPower"); meta.setLore(Arrays.asList("", "§ePower §6"+APIPlugin.getInstance().getAPI().s.POWER, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(30, it);
		meta.setDisplayName("§bPunch"); meta.setLore(Arrays.asList("", "§dPunch §5"+APIPlugin.getInstance().getAPI().s.PUNCH, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(31, it);
		meta.setDisplayName("§bUnbreaking"); meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_BOW, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(32, it);
		meta.setDisplayName("§bFlame"); meta.setLore(Arrays.asList("", "§cFlame §4"+APIPlugin.getInstance().getAPI().s.FLAME, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(33, it);
		meta.setDisplayName("§bInfinity"); meta.setLore(Arrays.asList("", "§7Infinity §8"+APIPlugin.getInstance().getAPI().s.INFINITY, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(34, it);
		
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(10, diamond);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(19, iron);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(28, bow);
		APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eArmes")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bSharpness") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bFire Aspect") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bUnbreaking") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bKnockback") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bFire Aspect") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bPower") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bPunch") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bInfinity") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bFlame")) {
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				ItemMeta diamond = event.getInventory().getItem(10).getItemMeta();
				ItemMeta iron = event.getInventory().getItem(19).getItemMeta();
				ItemMeta bow = event.getInventory().getItem(28).getItemMeta();
				if(event.getClick().equals(ClickType.LEFT)) {
					if(event.getInventory().getItem(12).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.SHARPNESS_DIAMOND_SWORD < 5) {
						APIPlugin.getInstance().getAPI().s.SHARPNESS_DIAMOND_SWORD++;
						meta.setLore(Arrays.asList("", "§eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(13).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.KNOCKBACK_DIAMOND_SWORD < 2) {
						APIPlugin.getInstance().getAPI().s.KNOCKBACK_DIAMOND_SWORD++;
						meta.setLore(Arrays.asList("", "§dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(14).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_DIAMOND_SWORD < 3) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_DIAMOND_SWORD++;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(15).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_DIAMOND_SWORD < 2) {
						APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_DIAMOND_SWORD++;
						meta.setLore(Arrays.asList("", "§8» §cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(21).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.SHARPNESS_IRON_SWORD < 5) {
						APIPlugin.getInstance().getAPI().s.SHARPNESS_IRON_SWORD++;
						meta.setLore(Arrays.asList("", "§eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(22).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.KNOCKBACK_IRON_SWORD < 2) {
						APIPlugin.getInstance().getAPI().s.KNOCKBACK_IRON_SWORD++;
						meta.setLore(Arrays.asList("", "§dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(23).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_SWORD < 3) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_SWORD++;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(24).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_IRON_SWORD < 2) {
						APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_IRON_SWORD++;
						meta.setLore(Arrays.asList("", "§8» §cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(30).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.POWER < 5) {
						APIPlugin.getInstance().getAPI().s.POWER++;
						meta.setLore(Arrays.asList("", "§ePower §6"+APIPlugin.getInstance().getAPI().s.POWER, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(31).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PUNCH < 2) {
						APIPlugin.getInstance().getAPI().s.PUNCH++;
						meta.setLore(Arrays.asList("", "§dPunch §5"+APIPlugin.getInstance().getAPI().s.PUNCH, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(32).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_BOW < 3) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_BOW++;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_BOW, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(33).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.FLAME < 1) {
						APIPlugin.getInstance().getAPI().s.FLAME++;
						meta.setLore(Arrays.asList("", "§8» §cFlame §4"+APIPlugin.getInstance().getAPI().s.FLAME, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(34).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.INFINITY < 1) {
						APIPlugin.getInstance().getAPI().s.INFINITY++;
						meta.setLore(Arrays.asList("", "§8» §7Infinity §8"+APIPlugin.getInstance().getAPI().s.INFINITY, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}
				}else if(event.getClick().equals(ClickType.RIGHT)) {
					if(event.getInventory().getItem(12).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.SHARPNESS_DIAMOND_SWORD > 0) {
						APIPlugin.getInstance().getAPI().s.SHARPNESS_DIAMOND_SWORD--;
						meta.setLore(Arrays.asList("", "§eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(13).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.KNOCKBACK_DIAMOND_SWORD > 0) {
						APIPlugin.getInstance().getAPI().s.KNOCKBACK_DIAMOND_SWORD--;
						meta.setLore(Arrays.asList("", "§dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(14).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_DIAMOND_SWORD > 0) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_DIAMOND_SWORD--;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(15).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_DIAMOND_SWORD > 0) {
						APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_DIAMOND_SWORD--;
						meta.setLore(Arrays.asList("", "§8» §cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_DIAMOND_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(21).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.SHARPNESS_IRON_SWORD > 0) {
						APIPlugin.getInstance().getAPI().s.SHARPNESS_IRON_SWORD--;
						meta.setLore(Arrays.asList("", "§eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(22).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.KNOCKBACK_IRON_SWORD > 0) {
						APIPlugin.getInstance().getAPI().s.KNOCKBACK_IRON_SWORD--;
						meta.setLore(Arrays.asList("", "§dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(23).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_SWORD > 0) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_SWORD--;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(24).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_IRON_SWORD > 0) {
						APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_IRON_SWORD--;
						meta.setLore(Arrays.asList("", "§8» §cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_IRON_SWORD, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(30).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.POWER > 0) {
						APIPlugin.getInstance().getAPI().s.POWER--;
						meta.setLore(Arrays.asList("", "§ePower §6"+APIPlugin.getInstance().getAPI().s.POWER, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(31).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PUNCH > 0) {
						APIPlugin.getInstance().getAPI().s.PUNCH--;
						meta.setLore(Arrays.asList("", "§dPunch §5"+APIPlugin.getInstance().getAPI().s.PUNCH, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(32).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_BOW > 0) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_BOW--;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_BOW, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(33).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.FLAME > 0) {
						APIPlugin.getInstance().getAPI().s.FLAME--;
						meta.setLore(Arrays.asList("", "§8» §cFlame §4"+APIPlugin.getInstance().getAPI().s.FLAME, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(34).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.INFINITY > 0) {
						APIPlugin.getInstance().getAPI().s.INFINITY--;
						meta.setLore(Arrays.asList("", "§8» §7Infinity §8"+APIPlugin.getInstance().getAPI().s.INFINITY, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}
				}
				diamond.setLore(Arrays.asList("", "§8» §eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_DIAMOND_SWORD, "§8» §dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_DIAMOND_SWORD, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_DIAMOND_SWORD, "§8» §cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_DIAMOND_SWORD));
				iron.setLore(Arrays.asList("", "§8» §eSharpness §6"+APIPlugin.getInstance().getAPI().s.SHARPNESS_IRON_SWORD, "§8» §dKnockback §5"+APIPlugin.getInstance().getAPI().s.KNOCKBACK_IRON_SWORD, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_SWORD, "§8» §cFire Aspect §4"+APIPlugin.getInstance().getAPI().s.FIRE_ASPECT_IRON_SWORD));
				bow.setLore(Arrays.asList("", "§8» §ePower §6"+APIPlugin.getInstance().getAPI().s.POWER, "§8» §dPunch §5"+APIPlugin.getInstance().getAPI().s.PUNCH, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_BOW, "§8» §cFlame §4"+APIPlugin.getInstance().getAPI().s.FLAME, "§8» §7Infinity §8"+APIPlugin.getInstance().getAPI().s.INFINITY));
				event.getInventory().getItem(10).setItemMeta(diamond);
				event.getInventory().getItem(19).setItemMeta(iron);
				event.getInventory().getItem(28).setItemMeta(bow);
				event.getCurrentItem().setItemMeta(meta);
			}
		}
	}
}
