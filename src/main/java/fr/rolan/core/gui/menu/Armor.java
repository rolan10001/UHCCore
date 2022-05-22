package fr.rolan.core.gui.menu;

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
import fr.rolan.core.gui.menu.armor.DiamondArmor;
import fr.rolan.core.gui.menu.armor.IronArmor;
import fr.rolan.core.gui.menu.armor.Weapons;

public class Armor implements Listener {
	
	public Armor() {
		APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU = Bukkit.createInventory(null, 54, "§8» §eArmure §7& §eEnchantement");
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE); ItemMeta chestplateM = chestplate.getItemMeta(); chestplateM.setDisplayName("§bLimite de pièce"); chestplateM.setLore(Arrays.asList("", " §3"+APIPlugin.getInstance().getAPI().s.ARMOR+" §bpièce"+(APIPlugin.getInstance().getAPI().s.ARMOR <= 1 ? "" : "s")+" en diamant", "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")); chestplate.setItemMeta(chestplateM);
		ItemStack leather = new ItemStack(Material.LEATHER_HELMET); ItemMeta leatherM = leather.getItemMeta(); leatherM.setDisplayName("§bArmure en Cuir"); leatherM.setLore(Arrays.asList("", "§7Configurer les enchantements de l'armure en Cuir", "", "§8» §eClic gauche pour ouvrir le menu")); leather.setItemMeta(leatherM);
		ItemStack gold = new ItemStack(Material.GOLD_HELMET); ItemMeta goldM = gold.getItemMeta(); goldM.setDisplayName("§bArmure en Or"); goldM.setLore(Arrays.asList("", "§7Configurer les enchantements de l'armure en Or", "", "§8» §eClic gauche pour ouvrir le menu")); gold.setItemMeta(goldM);
		ItemStack iron = new ItemStack(Material.IRON_HELMET); ItemMeta ironM = iron.getItemMeta(); ironM.setDisplayName("§bArmure en Fer"); ironM.setLore(Arrays.asList("", "§7Configurer les enchantements de l'armure en Fer", "", "§8» §eClic gauche pour ouvrir le menu")); iron.setItemMeta(ironM);
		ItemStack diamond = new ItemStack(Material.DIAMOND_HELMET); ItemMeta diamondM = diamond.getItemMeta(); diamondM.setDisplayName("§bArmure en Diamant"); diamondM.setLore(Arrays.asList("", "§7Configurer les enchantements de l'armure en Diamant", "", "§8» §eClic gauche pour ouvrir le menu")); diamond.setItemMeta(diamondM);
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD); ItemMeta swordM = sword.getItemMeta(); swordM.setDisplayName("§bArmes"); swordM.setLore(Arrays.asList("", "§7Configurer les enchantements des armes", "", "§8» §eClic gauche pour ouvrir le menu")); sword.setItemMeta(swordM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU.setItem(13, chestplate);
		APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU.setItem(19, iron);
		APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU.setItem(25, diamond);
		APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU.setItem(29, leather);
		APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU.setItem(31, sword);
		APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU.setItem(33, gold);
		APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	  
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eArmure §7& §eEnchantement")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bLimite de pièce")) {
				if(event.getClick().equals(ClickType.LEFT) && APIPlugin.getInstance().getAPI().s.ARMOR < 4) {
					APIPlugin.getInstance().getAPI().s.ARMOR++;
				}else if(event.getClick().equals(ClickType.RIGHT) && APIPlugin.getInstance().getAPI().s.ARMOR > 0) {
					APIPlugin.getInstance().getAPI().s.ARMOR--;
				}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", " §3"+APIPlugin.getInstance().getAPI().s.ARMOR+" §bpièce"+(APIPlugin.getInstance().getAPI().s.ARMOR <= 1 ? "" : "s")+" en diamant", "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bArmure en Fer")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU == null) {
					new IronArmor();
				}
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().IRON_ARMOR_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bArmure en Diamant")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().DIAMOND_ARMOR_MENU == null) {
					new DiamondArmor();
				}
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().DIAMOND_ARMOR_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bArmes")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU == null) {
					new Weapons();
				}
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().WEAPONS_MENU);
			}
		}
	}
}
