package fr.rolan.core.gui.menu.scenario;

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

public class DiamondLimit implements Listener {
	
	public DiamondLimit() {
		APIPlugin.getInstance().getAPI().getGuiManager().DIAMONDLIMIT_MENU = Bukkit.createInventory(null, 9, "§8» §eDiamondLimit");
		
		ItemStack diamond = new ItemStack(Material.DIAMOND); ItemMeta diamondM = diamond.getItemMeta(); diamondM.setDisplayName("§eDiamondLimit"); diamondM.setLore(Arrays.asList("", "§7Configurer le nombre de diamant maximum", "", "§3"+APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT_SIZE+" §bdiamant"+(APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT_SIZE <= 1 ? "" : "s")+" maximum", "", "§8» §eClic gauche pour §aajouter", "§8» §eClic droit pour §cretirer")); diamond.setItemMeta(diamondM);
		ItemStack gold = new ItemStack(Material.GOLD_INGOT); ItemMeta goldM = gold.getItemMeta(); goldM.setDisplayName("§eGold"); goldM.setLore(Arrays.asList("", "§7Activer ou non le drop d'or lorsque la diamondlimit est atteint", "", (APIPlugin.getInstance().getAPI().s.DROP_GOLD_AFTER_LIMIT ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver")); gold.setItemMeta(goldM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().DIAMONDLIMIT_MENU.setItem(0, getArrowBack());
		APIPlugin.getInstance().getAPI().getGuiManager().DIAMONDLIMIT_MENU.setItem(4, diamond);
		APIPlugin.getInstance().getAPI().getGuiManager().DIAMONDLIMIT_MENU.setItem(8, gold);
		for(int i = 0; i < 9; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().DIAMONDLIMIT_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().DIAMONDLIMIT_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().DIAMONDLIMIT_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eDiamondLimit")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eDiamondLimit")) {
				if(event.getClick().equals(ClickType.LEFT)) {
					APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT_SIZE++;
				}else if(event.getClick().equals(ClickType.RIGHT) && APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT_SIZE-1 > 0) {
					APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT_SIZE--;
				}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Configurer le nombre de diamant maximum", "", "§3"+APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT_SIZE+" §bdiamant"+(APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT_SIZE <= 1 ? "" : "s")+" maximum", "", "§8» §eClic gauche pour §aajouter", "§8» §eClic droit pour §cretirer"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eGold")) {
				APIPlugin.getInstance().getAPI().s.DROP_GOLD_AFTER_LIMIT = APIPlugin.getInstance().getAPI().s.DROP_GOLD_AFTER_LIMIT ? false:true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non le drop d'or lorsque la diamondlimit est atteint", "", (APIPlugin.getInstance().getAPI().s.DROP_GOLD_AFTER_LIMIT ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}
		}
	}
}
