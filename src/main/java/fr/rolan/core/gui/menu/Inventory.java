package fr.rolan.core.gui.menu;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.core.APIPlugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Inventory implements Listener {
	
	public Inventory() {
		APIPlugin.getInstance().getAPI().getGuiManager().INVENTORY_MENU = Bukkit.createInventory(null, 54, "§8» §eInventaire");
		
		ItemStack start = new ItemStack(Material.CHEST); ItemMeta startM = start.getItemMeta(); startM.setDisplayName("§eInventaire de départ"); startM.setLore(Arrays.asList("", "§7Configurer l'inventaire de départ", "", "§8» §eClic gauche pour configurer")); start.setItemMeta(startM);
		ItemStack death = new ItemStack(Material.ENDER_CHEST); ItemMeta deathM = death.getItemMeta(); deathM.setDisplayName("§eInventaire de mort"); deathM.setLore(Arrays.asList("", "§7Configurer l'inventaire de mort", "", "§8» §eClic gauche pour configurer")); death.setItemMeta(deathM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().INVENTORY_MENU.setItem(20, start);
		APIPlugin.getInstance().getAPI().getGuiManager().INVENTORY_MENU.setItem(24, death);
		APIPlugin.getInstance().getAPI().getGuiManager().INVENTORY_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().INVENTORY_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().INVENTORY_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().INVENTORY_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eInventaire")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eInventaire de départ")) {
				player.closeInventory();
				player.getInventory().clear();
				player.getInventory().setArmorContents(new ItemStack[] {null, null, null, null});
				if(APIPlugin.getInstance().getAPI().getStuffManager().getStuffStart() != null)
					player.getInventory().setContents(APIPlugin.getInstance().getAPI().getStuffManager().getStuffStart());
				if(APIPlugin.getInstance().getAPI().getStuffManager().getStuffArmorStart() != null)
					player.getInventory().setArmorContents(APIPlugin.getInstance().getAPI().getStuffManager().getStuffArmorStart());
				player.setGameMode(GameMode.CREATIVE);
				TextComponent text = new TextComponent("§7§l▏ §7Faites l'inventaire de départ, §apuis §acliquez §asur §ace §amessage §apour §aconfirmer.");
				text.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/confirm start"));
				player.spigot().sendMessage(text);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eInventaire de mort")) {
				player.closeInventory();
				player.getInventory().clear();
				player.getInventory().setArmorContents(new ItemStack[] {null, null, null, null});
				if(APIPlugin.getInstance().getAPI().getStuffManager().getStuffDeath() != null)
					player.getInventory().setContents(APIPlugin.getInstance().getAPI().getStuffManager().getStuffDeath());
				if(APIPlugin.getInstance().getAPI().getStuffManager().getStuffArmorDeath() != null)
					player.getInventory().setArmorContents(APIPlugin.getInstance().getAPI().getStuffManager().getStuffArmorDeath());
				player.setGameMode(GameMode.CREATIVE);
				TextComponent text = new TextComponent("§7§l▏ §7Faites l'inventaire de mort, §apuis §acliquez §asur §ace §amessage §apour §aconfirmer.");
				text.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/confirm death"));
				player.spigot().sendMessage(text);
			}
		}
	}
}
