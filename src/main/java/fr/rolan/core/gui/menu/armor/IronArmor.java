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

public class IronArmor implements Listener {
	
	public IronArmor() {
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU = Bukkit.createInventory(null, 54, "§8» §7Armure en §eFer");
		
		ItemStack helmet = new ItemStack(Material.IRON_HELMET); ItemMeta helmetM = helmet.getItemMeta(); helmetM.setDisplayName("§bCasque en Fer"); helmetM.setLore(Arrays.asList("", "§8» §eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_HELMET, "§8» §dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_HELMET, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_HELMET, "§8» §cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_HELMET)); helmet.setItemMeta(helmetM);
		ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE); ItemMeta chestplateM = chestplate.getItemMeta(); chestplateM.setDisplayName("§bPlastron en Fer"); chestplateM.setLore(Arrays.asList("", "§8» §eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_CHESTPLATE, "§8» §dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_CHESTPLATE, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_CHESTPLATE, "§8» §cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_CHESTPLATE)); chestplate.setItemMeta(chestplateM);
		ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS); ItemMeta leggingsM = leggings.getItemMeta(); leggingsM.setDisplayName("§bPantalon en Fer"); leggingsM.setLore(Arrays.asList("", "§8» §eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_LEGGINGS, "§8» §dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_LEGGINGS, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_LEGGINGS, "§8» §cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_LEGGINGS)); leggings.setItemMeta(leggingsM);
		ItemStack boots = new ItemStack(Material.IRON_BOOTS); ItemMeta bootsM = boots.getItemMeta(); bootsM.setDisplayName("§bBottes en Fer"); bootsM.setLore(Arrays.asList("", "§8» §eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_BOOTS, "§8» §dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_BOOTS, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_BOOTS, "§8» §cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_BOOTS)); boots.setItemMeta(bootsM);
		
		ItemStack it = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName("§1§bProtection"); meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(3, it);
		meta.setDisplayName("§1§bProjectile Protection"); meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(4, it);
		meta.setDisplayName("§1§bUnbreaking"); meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(5, it);
		meta.setDisplayName("§1§bThorns"); meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(6, it);
		meta.setDisplayName("§2§bProtection"); meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(12, it);
		meta.setDisplayName("§2§bProjectile Protection"); meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(13, it);
		meta.setDisplayName("§2§bUnbreaking"); meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(14, it);
		meta.setDisplayName("§2§bThorns"); meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(15, it);
		meta.setDisplayName("§3§bProtection"); meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(21, it);
		meta.setDisplayName("§3§bProjectile Protection"); meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(22, it);
		meta.setDisplayName("§3§bUnbreaking"); meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(23, it);
		meta.setDisplayName("§3§bThorns"); meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(24, it);
		meta.setDisplayName("§4§bProtection"); meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(30, it);
		meta.setDisplayName("§4§bProjectile Protection"); meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(31, it);
		meta.setDisplayName("§4§bUnbreaking"); meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(32, it);
		meta.setDisplayName("§4§bThorns"); meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); it.setItemMeta(meta);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(33, it);
		
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(1, helmet);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(10, chestplate);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(19, leggings);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(28, boots);
		APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §7Armure en §eFer")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bProtection") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bProjectile Protection") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bUnbreaking") || event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§bThorns")) {
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				ItemMeta helmet = event.getInventory().getItem(1).getItemMeta();
				ItemMeta chestplate = event.getInventory().getItem(10).getItemMeta();
				ItemMeta leggings = event.getInventory().getItem(19).getItemMeta();
				ItemMeta boots = event.getInventory().getItem(28).getItemMeta();
				if(event.getClick().equals(ClickType.LEFT)) {
					if(event.getInventory().getItem(3).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_HELMET < 4) {
						APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_HELMET++;
						meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(4).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROJ_IRON_HELMET < 4) {
						APIPlugin.getInstance().getAPI().s.PROJ_IRON_HELMET++;
						meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(5).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_HELMET < 3) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_HELMET++;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(6).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.THORNS_IRON_HELMET < 3) {
						APIPlugin.getInstance().getAPI().s.THORNS_IRON_HELMET++;
						meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(12).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_CHESTPLATE < 4) {
						APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_CHESTPLATE++;
						meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(13).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROJ_IRON_CHESTPLATE < 4) {
						APIPlugin.getInstance().getAPI().s.PROJ_IRON_CHESTPLATE++;
						meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(14).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_CHESTPLATE < 3) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_CHESTPLATE++;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(15).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.THORNS_IRON_CHESTPLATE < 3) {
						APIPlugin.getInstance().getAPI().s.THORNS_IRON_CHESTPLATE++;
						meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(21).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_LEGGINGS < 4) {
						APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_LEGGINGS++;
						meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(22).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROJ_IRON_LEGGINGS < 4) {
						APIPlugin.getInstance().getAPI().s.PROJ_IRON_LEGGINGS++;
						meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(23).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_LEGGINGS < 3) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_LEGGINGS++;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(24).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.THORNS_IRON_LEGGINGS < 3) {
						APIPlugin.getInstance().getAPI().s.THORNS_IRON_LEGGINGS++;
						meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(30).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_BOOTS < 4) {
						APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_BOOTS++;
						meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(31).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROJ_IRON_BOOTS < 4) {
						APIPlugin.getInstance().getAPI().s.PROJ_IRON_BOOTS++;
						meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(32).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_BOOTS < 3) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_BOOTS++;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(33).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.THORNS_IRON_BOOTS < 3) {
						APIPlugin.getInstance().getAPI().s.THORNS_IRON_BOOTS++;
						meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}
				}else if(event.getClick().equals(ClickType.RIGHT)) {
					if(event.getInventory().getItem(3).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_HELMET > 0) {
						APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_HELMET--;
						meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(4).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROJ_IRON_HELMET > 0) {
						APIPlugin.getInstance().getAPI().s.PROJ_IRON_HELMET--;
						meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(5).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_HELMET > 0) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_HELMET--;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(6).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.THORNS_IRON_HELMET > 0) {
						APIPlugin.getInstance().getAPI().s.THORNS_IRON_HELMET--;
						meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_HELMET, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(12).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_CHESTPLATE > 0) {
						APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_CHESTPLATE--;
						meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(13).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROJ_IRON_CHESTPLATE > 0) {
						APIPlugin.getInstance().getAPI().s.PROJ_IRON_CHESTPLATE--;
						meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(14).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_CHESTPLATE > 0) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_CHESTPLATE--;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(15).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.THORNS_IRON_CHESTPLATE > 0) {
						APIPlugin.getInstance().getAPI().s.THORNS_IRON_CHESTPLATE--;
						meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_CHESTPLATE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(21).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_LEGGINGS> 0) {
						APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_LEGGINGS--;
						meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(22).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROJ_IRON_LEGGINGS > 0) {
						APIPlugin.getInstance().getAPI().s.PROJ_IRON_LEGGINGS--;
						meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(23).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_LEGGINGS > 0) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_LEGGINGS--;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(24).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.THORNS_IRON_LEGGINGS > 0) {
						APIPlugin.getInstance().getAPI().s.THORNS_IRON_LEGGINGS--;
						meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_LEGGINGS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(30).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_BOOTS> 0) {
						APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_BOOTS--;
						meta.setLore(Arrays.asList("", "§eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(31).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.PROJ_IRON_BOOTS > 0) {
						APIPlugin.getInstance().getAPI().s.PROJ_IRON_BOOTS--;
						meta.setLore(Arrays.asList("", "§dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(32).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_BOOTS > 0) {
						APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_BOOTS--;
						meta.setLore(Arrays.asList("", "§aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}else if(event.getInventory().getItem(33).isSimilar(event.getCurrentItem()) && APIPlugin.getInstance().getAPI().s.THORNS_IRON_BOOTS > 0) {
						APIPlugin.getInstance().getAPI().s.THORNS_IRON_BOOTS--;
						meta.setLore(Arrays.asList("", "§cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_BOOTS, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
					}
				}
				helmet.setLore(Arrays.asList("", "§8» §eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_HELMET, "§8» §dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_HELMET, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_HELMET, "§8» §cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_HELMET));
				chestplate.setLore(Arrays.asList("", "§8» §eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_CHESTPLATE, "§8» §dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_CHESTPLATE, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_CHESTPLATE, "§8» §cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_CHESTPLATE));
				leggings.setLore(Arrays.asList("", "§8» §eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_LEGGINGS, "§8» §dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_LEGGINGS, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_LEGGINGS, "§8» §cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_LEGGINGS));
				boots.setLore(Arrays.asList("", "§8» §eProtection §6"+APIPlugin.getInstance().getAPI().s.PROTECTION_IRON_BOOTS, "§8» §dProjectile Protection §5"+APIPlugin.getInstance().getAPI().s.PROJ_IRON_BOOTS, "§8» §aUnbreaking §2"+APIPlugin.getInstance().getAPI().s.UNBREAKING_IRON_BOOTS, "§8» §cThorns §4"+APIPlugin.getInstance().getAPI().s.THORNS_IRON_BOOTS));
				event.getInventory().getItem(1).setItemMeta(helmet);
				event.getInventory().getItem(10).setItemMeta(chestplate);
				event.getInventory().getItem(19).setItemMeta(leggings);
				event.getInventory().getItem(28).setItemMeta(boots);
				event.getCurrentItem().setItemMeta(meta);
			}
		}
	}
}
