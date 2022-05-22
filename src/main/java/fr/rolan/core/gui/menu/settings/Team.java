package fr.rolan.core.gui.menu.settings;

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
import fr.rolan.api.game.team.Teams;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.gui.BannerTeam;

public class Team implements Listener {
	
	public Team() {
		APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU = Bukkit.createInventory(null, 54, "§8» §eTeam");
		
		APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU.setItem(13, setMetaInItem(new ItemStack(Material.BANNER, 1, (byte) 1), "§eTeam", Arrays.asList("", " §3"+APIPlugin.getInstance().getAPI().s.TEAM_SIZE+" §béquipes", "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")));
		APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU.setItem(22, setMetaInItem(new ItemStack(Material.BEACON), "§eNombre de joueur par équipe", Arrays.asList("", " §aTO§2§l"+APIPlugin.getInstance().getAPI().s.TEAM_PLAYERS_SIZE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer")));
		APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU.setItem(30, setMetaInItem(new ItemStack(Material.IRON_SWORD), "§eFriendly Fire", Arrays.asList("", (APIPlugin.getInstance().getAPI().s.TEAM_FRIENDLY_FIRE ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§7§e/§cdésactiver")));
		APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU.setItem(32, setMetaInItem(new ItemStack(Material.COMPASS), "§eDirection équipier", Arrays.asList("", (APIPlugin.getInstance().getAPI().s.TEAM_DIRECTION ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§7§e/§cdésactiver")));
		APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eTeam")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eTeam")) {
				if(event.getClick().equals(ClickType.LEFT) && APIPlugin.getInstance().getAPI().s.TEAM_SIZE < 15) {
					APIPlugin.getInstance().getAPI().s.TEAM_SIZE++;
					if(APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU != null)
						BannerTeam.updateInventory();
				}else if(event.getClick().equals(ClickType.RIGHT) && APIPlugin.getInstance().getAPI().s.TEAM_SIZE > 2) {
					APIPlugin.getInstance().getAPI().s.TEAM_SIZE--;
					if(APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU != null)
						BannerTeam.updateInventory();
				}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", " §3"+APIPlugin.getInstance().getAPI().s.TEAM_SIZE+" §béquipes", "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eNombre de joueur par équipe")) {
				if(event.getClick().equals(ClickType.LEFT)) {
					APIPlugin.getInstance().getAPI().s.TEAM_PLAYERS_SIZE++;
				}else if(event.getClick().equals(ClickType.RIGHT) && APIPlugin.getInstance().getAPI().s.TEAM_PLAYERS_SIZE > 2) {
					APIPlugin.getInstance().getAPI().s.TEAM_PLAYERS_SIZE--;
				}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", " §aTO§2§l"+APIPlugin.getInstance().getAPI().s.TEAM_PLAYERS_SIZE, "", "§8» §eClic gauche pour ajouter", "§8» §eClic droit pour retirer"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eFriendly Fire")) {
				APIPlugin.getInstance().getAPI().s.TEAM_FRIENDLY_FIRE = APIPlugin.getInstance().getAPI().s.TEAM_FRIENDLY_FIRE ? false:true;
				for(Teams team : Teams.values())
					team.setFriendlyfire(APIPlugin.getInstance().getAPI().s.TEAM_FRIENDLY_FIRE);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.TEAM_FRIENDLY_FIRE ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§7§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eDirection équipier")) {
				APIPlugin.getInstance().getAPI().s.TEAM_DIRECTION = APIPlugin.getInstance().getAPI().s.TEAM_DIRECTION ? false:true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.TEAM_DIRECTION ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§7§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}
		}
	}
}
