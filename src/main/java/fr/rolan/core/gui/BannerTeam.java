package fr.rolan.core.gui;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.rolan.api.UHCAPI;
import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.api.game.Settings;
import fr.rolan.api.game.team.Teams;
import fr.rolan.core.APIPlugin;

public class BannerTeam implements Listener {
	
	private static Settings s;
	
	public BannerTeam(Settings s) {
		BannerTeam.s = s;
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU = Bukkit.createInventory(null, 54, "§eTeams");
		List<String> list = new ArrayList<String>();
		int i = 0;
		int x = 9;
		for(Teams team : Teams.values())
			if(!team.equals(Teams.DEFAULT)) {
				for(UUID uuid : team.getPlayers())
					list.add("§8» §7"+Bukkit.getOfflinePlayer(uuid).getName());
				x++;
				if(x%9 == 0) {x++;}else if((x+1)%9 == 0) {x+=2;}
					
				APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(x, setMetaInItem(new ItemStack(Material.BANNER, 1, team.getBannercolor()), "§7Equipe "+team.getDisplayName(), list));
				list.clear();
				i++;
				if(i >= s.TEAM_SIZE)
					break;
			}
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(49, setMetaInItem(new ItemStack(Material.BANNER, 1, (byte) 7), "§7Quittez votre équipe", null));
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(0, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(1, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(7, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(8, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(9, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(17, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(36, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(44, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(45, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(46, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(52, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(53, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	public static void updateInventory() {
		List<String> list = new ArrayList<String>();
		int i = 0;
		int x = 9;
		for(Teams team : Teams.values())
			if(!team.equals(Teams.DEFAULT)) {
				for(UUID uuid : team.getPlayers())
					list.add("§8» §7"+Bukkit.getOfflinePlayer(uuid).getName());
				x++;
				if(x%9 == 0) {x++;}else if((x+1)%9 == 0) {x+=2;}
					
				APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(x, setMetaInItem(new ItemStack(Material.BANNER, 1, team.getBannercolor()), "§7Equipe "+team.getDisplayName(), list));
				list.clear();
				i++;
				if(i >= s.TEAM_SIZE)
					break;
			}
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(49, setMetaInItem(new ItemStack(Material.BANNER, 1, (byte) 7), "§7Quittez votre équipe", null));
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(0, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(1, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(7, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(8, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(9, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(17, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(36, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(44, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(45, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(46, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(52, getGlass());
		APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.setItem(53, getGlass());
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals(APIPlugin.getInstance().getAPI().getGuiManager().BANNER_TEAM_MENU.getName())) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.closeInventory();
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§7Equipe")) {
				for(Teams team : Teams.values())
					if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith(team.getDisplayName())) {
						if(team.getPlayers().size() < s.TEAM_PLAYERS_SIZE) {
							team.addPlayer(player.getUniqueId());
							updateInventory();
							UHCAPI.get().getPermissions().updatePermissionsForPlayer(player);
							break;
						}else {
							player.sendMessage("§7§l▏ §cErreur, cette équipe est pleine.");
							break;
						}
					}
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§7Quittez votre équipe")) {
				Teams.DEFAULT.addPlayer(player.getUniqueId());
				updateInventory();
				UHCAPI.get().getPermissions().updatePermissionsForPlayer(player);
			}
		}
	}
}
