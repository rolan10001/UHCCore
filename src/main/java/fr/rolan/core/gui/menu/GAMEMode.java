package fr.rolan.core.gui.menu;

import static fr.rolan.api.gui.GuiManager.*;

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

public class GAMEMode implements Listener {
	
	private final org.bukkit.inventory.Inventory GAMEMODE_MENU;
	
	public GAMEMode() {
		APIPlugin.getInstance().getAPI().getGuiManager().GAMEMODE_MENU = Bukkit.createInventory(null, 54, "§8» §eMode de Jeu");
		GAMEMODE_MENU = APIPlugin.getInstance().getAPI().getGuiManager().GAMEMODE_MENU;
		ItemStack uhc = new ItemStack(Material.GOLDEN_APPLE); ItemMeta uhcM = uhc.getItemMeta(); uhcM.setDisplayName("§aUHC"); uhc.setItemMeta(uhcM);
		GAMEMODE_MENU.setItem(13, uhc);
		GAMEMODE_MENU.setItem(49, getArrowBack());
		for(int i = 0; i < 54; i++) if(GAMEMODE_MENU.getItem(i) == null) GAMEMODE_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(GAMEMODE_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eMode de Jeu")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aUHC")) {
				APIPlugin.getInstance().getAPI().s.GAMEMODE = fr.rolan.api.game.enums.GAMEMode.UHC;
				APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(22, getGlass());
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
			}
		}
	}
	
}
