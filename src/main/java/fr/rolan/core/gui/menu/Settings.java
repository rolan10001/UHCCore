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
import fr.rolan.api.game.team.Teams;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.gui.menu.settings.Team;
import fr.rolan.core.gui.menu.settings.World;

public class Settings implements Listener {
	
	public Settings() {
		APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU = Bukkit.createInventory(null, 54, "§8» §eParamètres");
		
		ItemStack team = new ItemStack(Material.BANNER, 1, (byte) 1); ItemMeta teamM = team.getItemMeta(); teamM.setDisplayName("§eTeam"); teamM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.TEAM ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§7§e/§cdésactiver", "§8» §eClic droit pour ouvrir le menu")); team.setItemMeta(teamM);
		ItemStack world = new ItemStack(Material.GRASS); ItemMeta worldM = world.getItemMeta(); worldM.setDisplayName("§eConfiguration Monde"); worldM.setLore(Arrays.asList("", "§7Configurer les paramètres du monde", "", "§8» §eClic gauche pour ouvrir le menu")); world.setItemMeta(worldM);
		ItemStack absorption = new ItemStack(Material.GOLDEN_APPLE); ItemMeta absorptionM = absorption.getItemMeta(); absorptionM.setDisplayName("§eAbsorption"); absorptionM.setLore(Arrays.asList("", "§7Activer ou non l'absorption", "", (APIPlugin.getInstance().getAPI().s.ABSORPTION ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver")); absorption.setItemMeta(absorptionM);
		ItemStack f3 = new ItemStack(Material.COMPASS); ItemMeta f3M = f3.getItemMeta(); f3M.setDisplayName("§eCoordonnées"); f3M.setLore(Arrays.asList("", "§7Activer ou non les coordonnées", "", (APIPlugin.getInstance().getAPI().s.F3 ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver")); f3.setItemMeta(f3M);
		ItemStack respawn = new ItemStack(Material.BEACON); ItemMeta respawnM = respawn.getItemMeta(); respawnM.setDisplayName("§eAuto-revive"); respawnM.setLore(Arrays.asList("", "§7Activer ou non l'auto-revive", "", (APIPlugin.getInstance().getAPI().s.AUTORESPAWN ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver")); respawn.setItemMeta(respawnM);
		ItemStack pv = new ItemStack(Material.INK_SACK, 1, (byte) 1); ItemMeta pvM = pv.getItemMeta(); pvM.setDisplayName("§eVie"); pvM.setLore(Arrays.asList("", "§7Activer ou non la vie dans le tab ou/et sur la tête", "", "§8» §7Dans le tab: "+(APIPlugin.getInstance().getAPI().s.PV_IN_TAB ? "§aActivé":"§cDésactivé"), "§8» §7Sur la tête: "+(APIPlugin.getInstance().getAPI().s.PV_ON_HEAD ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver §edans le tab", "§8» §eClic droit pour §aactiver§e/§cdésactiver §esur la tête")); pv.setItemMeta(pvM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU.setItem(11, absorption);
		APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU.setItem(13, team);
		APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU.setItem(15, f3);
		APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU.setItem(19, pv);
		APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU.setItem(25, respawn);
		APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU.setItem(31, world);
		APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eParamètres")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eTeam")) {
				ItemStack team = new ItemStack(Material.BANNER, 1, (byte) 15);
				ItemMeta teamM = team.getItemMeta();
				teamM.setDisplayName("§eTeam");
				team.setItemMeta(teamM);
				if(event.getClick().equals(ClickType.LEFT)) {
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					if(APIPlugin.getInstance().getAPI().s.TEAM) {
						APIPlugin.getInstance().getAPI().s.TEAM = false;
						for(Player players : Bukkit.getOnlinePlayers()) {
							players.getInventory().remove(team);
							Teams.DEFAULT.addPlayer(players.getUniqueId());
							APIPlugin.getInstance().getAPI().s.TEAM_FRIENDLY_FIRE = true;
							for(Teams teams : Teams.values())
								teams.setFriendlyfire(APIPlugin.getInstance().getAPI().s.TEAM_FRIENDLY_FIRE);
						}
					}else {
						APIPlugin.getInstance().getAPI().s.TEAM=true;
						for(Player players : Bukkit.getOnlinePlayers()) {if(!APIPlugin.getInstance().getAPI().getUser(players).isInArena()) {players.getInventory().setItem(4, team);}}
					}
					meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.TEAM ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§7§e/§cdésactiver", "§8» §eClic droit pour ouvrir le menu"));
					event.getCurrentItem().setItemMeta(meta);
				}else if(event.getClick().equals(ClickType.RIGHT)) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU == null) new Team();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().TEAM_MENU);
				}
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eConfiguration Monde")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU == null) new World();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eAbsorption")) {
				APIPlugin.getInstance().getAPI().s.ABSORPTION = APIPlugin.getInstance().getAPI().s.ABSORPTION ? false: true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non l'absorption", "", (APIPlugin.getInstance().getAPI().s.ABSORPTION ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eCoordonnées")) {
				APIPlugin.getInstance().getAPI().s.F3 = APIPlugin.getInstance().getAPI().s.F3 ? false: true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non les coordonnées", "", (APIPlugin.getInstance().getAPI().s.F3 ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eAuto-revive")) {
				APIPlugin.getInstance().getAPI().s.AUTORESPAWN = APIPlugin.getInstance().getAPI().s.AUTORESPAWN ? false: true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non l'auto-revive", "", (APIPlugin.getInstance().getAPI().s.AUTORESPAWN ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eVie")) {
				if(event.getClick().equals(ClickType.LEFT)) {
					APIPlugin.getInstance().getAPI().s.PV_IN_TAB = APIPlugin.getInstance().getAPI().s.PV_IN_TAB ? false: true;
				}else if(event.getClick().equals(ClickType.RIGHT)) {
					APIPlugin.getInstance().getAPI().s.PV_ON_HEAD = APIPlugin.getInstance().getAPI().s.PV_ON_HEAD ? false: true;
				}
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non la vie dans le tab ou/et sur la tête", "", "§8» §7Dans le tab: "+(APIPlugin.getInstance().getAPI().s.PV_IN_TAB ? "§aActivé":"§cDésactivé"), "§8» §7Sur la tête: "+(APIPlugin.getInstance().getAPI().s.PV_ON_HEAD ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver §edans le tab", "§8» §eClic droit pour §aactiver§e/§cdésactiver §esur la tête"));
				event.getCurrentItem().setItemMeta(meta);
			}
		}
	}
}
