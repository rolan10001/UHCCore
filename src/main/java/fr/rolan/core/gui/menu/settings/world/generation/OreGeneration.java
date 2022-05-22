package fr.rolan.core.gui.menu.settings.world.generation;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.gui.menu.settings.world.generation.ore.Coal;
import fr.rolan.core.gui.menu.settings.world.generation.ore.Diamond;
import fr.rolan.core.gui.menu.settings.world.generation.ore.Gold;
import fr.rolan.core.gui.menu.settings.world.generation.ore.Iron;
import fr.rolan.core.gui.menu.settings.world.generation.ore.Lapis;
import fr.rolan.core.gui.menu.settings.world.generation.ore.Redstone;

public class OreGeneration implements Listener {
	
	public OreGeneration() {
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU = Bukkit.createInventory(null, 9, "§8» §eGénération des minerais");
		
		ItemStack diamond = new ItemStack(Material.DIAMOND_ORE); ItemMeta diamondM = diamond.getItemMeta(); diamondM.setDisplayName("§eMinerai de Diamant"); diamondM.setLore(Arrays.asList("", "§7Configurer la génération du Diamant", "", "§8» §eClic gauche pour ouvrir le menu")); diamond.setItemMeta(diamondM);
		ItemStack gold = new ItemStack(Material.GOLD_ORE); ItemMeta goldM = gold.getItemMeta(); goldM.setDisplayName("§eMinerai d'Or"); goldM.setLore(Arrays.asList("", "§7Configurer la génération d'Or", "", "§8» §eClic gauche pour ouvrir le menu")); gold.setItemMeta(goldM);
		ItemStack iron = new ItemStack(Material.IRON_ORE); ItemMeta ironM = iron.getItemMeta(); ironM.setDisplayName("§eMinerai de Fer"); ironM.setLore(Arrays.asList("", "§7Configurer la génération de Fer", "", "§8» §eClic gauche pour ouvrir le menu")); iron.setItemMeta(ironM);
		ItemStack coal = new ItemStack(Material.COAL_ORE); ItemMeta coalM = coal.getItemMeta(); coalM.setDisplayName("§eMinerai de Charbon"); coalM.setLore(Arrays.asList("", "§7Configurer la génération de Charbon", "", "§8» §eClic gauche pour ouvrir le menu")); coal.setItemMeta(coalM);
		ItemStack redstone = new ItemStack(Material.REDSTONE_ORE); ItemMeta redstoneM = redstone.getItemMeta(); redstoneM.setDisplayName("§eMinerai de Redstone"); redstoneM.setLore(Arrays.asList("", "§7Configurer la génération de Redstone", "", "§8» §eClic gauche pour ouvrir le menu")); redstone.setItemMeta(redstoneM);
		ItemStack lapis = new ItemStack(Material.LAPIS_ORE); ItemMeta lapisM = lapis.getItemMeta(); lapisM.setDisplayName("§eMinerai de Lapis-Lazuli"); lapisM.setLore(Arrays.asList("", "§7Configurer la génération de Lapis-Lazuli", "", "§8» §eClic gauche pour ouvrir le menu")); lapis.setItemMeta(lapisM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU.setItem(1, coal);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU.setItem(2, iron);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU.setItem(3, diamond);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU.setItem(4, getArrowBack());
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU.setItem(5, gold);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU.setItem(6, lapis);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU.setItem(7, redstone);
		for(int i = 0; i < 9; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eGénération des minerais")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eMinerai de Diamant")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().DIAMOND_MENU == null) new Diamond();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().DIAMOND_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eMinerai d'Or")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().GOLD_MENU == null) new Gold();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().GOLD_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eMinerai de Fer")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().IRON_MENU == null) new Iron();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().IRON_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eMinerai de Lapis-Lazuli")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().LAPIS_MENU == null) new Lapis();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().LAPIS_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eMinerai de Charbon")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().COAL_MENU == null) new Coal();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().COAL_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eMinerai de Redstone")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().REDSTONE_MENU == null) new Redstone();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().REDSTONE_MENU);
			}
		}
	}
}
