package fr.rolan.core.gui.menu.settings.world.generation.biome;

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
import fr.rolan.tools.generation.Biome;

public class BiomeInMiddle implements Listener {
	
	public BiomeInMiddle() {
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU = Bukkit.createInventory(null, 36, "§8» §eBiome au 00");
		
		ItemStack ocean = new ItemStack(Material.WATER_BUCKET); ItemMeta oceanM = ocean.getItemMeta(); oceanM.setDisplayName("§bOcean"); oceanM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); ocean.setItemMeta(oceanM);
		ItemStack plains = new ItemStack(Material.GRASS); ItemMeta plainsM = plains.getItemMeta(); plainsM.setDisplayName("§aPlains"); plainsM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); plains.setItemMeta(plainsM);
		ItemStack desert = new ItemStack(Material.SAND); ItemMeta desertM = desert.getItemMeta(); desertM.setDisplayName("§eDesert"); desertM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); desert.setItemMeta(desertM);
		ItemStack extremehills = new ItemStack(Material.STONE); ItemMeta extremehillsM = extremehills.getItemMeta(); extremehillsM.setDisplayName("§9Extreme Hills"); extremehillsM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); extremehills.setItemMeta(extremehillsM);
		ItemStack forest = new ItemStack(Material.LEAVES); ItemMeta forestM = forest.getItemMeta(); forestM.setDisplayName("§2Forest"); forestM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); forest.setItemMeta(forestM);
		ItemStack taiga = new ItemStack(Material.LEAVES, 1, (byte) 1); ItemMeta taigaM = taiga.getItemMeta(); taigaM.setDisplayName("§2Taiga"); taigaM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); taiga.setItemMeta(taigaM);
		ItemStack swampland = new ItemStack(Material.RED_ROSE, 1, (byte) 1); ItemMeta swamplandM = swampland.getItemMeta(); swamplandM.setDisplayName("§2Swampland"); swamplandM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); swampland.setItemMeta(swamplandM);
		ItemStack river = new ItemStack(Material.LOG, 1, (byte) 2); ItemMeta riverM = river.getItemMeta(); riverM.setDisplayName("§2Birch Forest"); riverM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); river.setItemMeta(riverM);
		ItemStack ice = new ItemStack(Material.SNOW_BALL); ItemMeta iceM = ice.getItemMeta(); iceM.setDisplayName("§bIce"); iceM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); ice.setItemMeta(iceM);
		ItemStack mushroom = new ItemStack(Material.RED_MUSHROOM); ItemMeta mushroomM = mushroom.getItemMeta(); mushroomM.setDisplayName("§5Mushroom"); mushroomM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); mushroom.setItemMeta(mushroomM);
		ItemStack jungle = new ItemStack(Material.LEAVES, 1, (byte) 3); ItemMeta jungleM = jungle.getItemMeta(); jungleM.setDisplayName("§2Jungle"); jungleM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); jungle.setItemMeta(jungleM);
		ItemStack roofed = new ItemStack(Material.LOG_2, 1, (byte) 1); ItemMeta roofedM = roofed.getItemMeta(); roofedM.setDisplayName("§2Roofed Forest"); roofedM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); roofed.setItemMeta(roofedM);
		ItemStack savana = new ItemStack(Material.LOG_2); ItemMeta savanaM = savana.getItemMeta(); savanaM.setDisplayName("§6Savanna"); savanaM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); savana.setItemMeta(savanaM);
		ItemStack mesa = new ItemStack(Material.STAINED_CLAY); ItemMeta mesaM = mesa.getItemMeta(); mesaM.setDisplayName("§6Mesa"); mesaM.setLore(Arrays.asList("", "§7Choisissez un biome", "", "§8» §eClic gauche pour choisir celui-ci")); mesa.setItemMeta(mesaM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(10, ocean);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(11, plains);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(12, desert);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(13, extremehills);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(14, forest);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(15, taiga);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(16, swampland);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(19, river);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(20, ice);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(21, mushroom);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(22, jungle);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(23, roofed);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(24, savana);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(25, mesa);
		
		for(int i = 0; i < 36; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eBiome au 00")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && !event.getCurrentItem().getItemMeta().getDisplayName().equals("§7")) {
			player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU);
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bOcean")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.OCEAN;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aPlains")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.PLAINS;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eDesert")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.DESERT;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§9Extreme Hills")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.EXTREME_HILLS;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Forest")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.FOREST;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Taiga")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.TAIGA;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Birch Forest")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.BIRCH_FOREST;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bIce")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.ICE_PLAINS;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§5Mushroom")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.MUSHROOM_ISLAND;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Jungle")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.JUNGLE;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Roofed Forest")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.ROOFED_FOREST;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§6Savanna")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.SAVANNA;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§6Mesa")) {
				APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE = Biome.MESA;
			}
			ItemMeta meta = APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.getItem(4).getItemMeta();
			meta.setLore(Arrays.asList("", "§7Configurer le biome présent au 00", "", APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE.getName(), "", "§8» §eClic gauche pour configurer"));
			APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.getItem(4).setItemMeta(meta);
		}
	}
}
