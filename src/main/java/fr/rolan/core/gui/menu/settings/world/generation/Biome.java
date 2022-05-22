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
import fr.rolan.core.gui.menu.settings.world.generation.biome.BiomeInMiddle;

public class Biome implements Listener {
	
	public Biome() {
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU = Bukkit.createInventory(null, 54, "§8» §eBiome");
		
		ItemStack it = new ItemStack(Material.COMPASS); ItemMeta meta = it.getItemMeta(); meta.setDisplayName("§eBiome au 00"); meta.setLore(Arrays.asList("", "§7Configurer le biome présent au 00", "", APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE.getName(), "", "§8» §eClic gauche pour configurer")); it.setItemMeta(meta);
		ItemStack ocean = new ItemStack(Material.WATER_BUCKET); ItemMeta oceanM = ocean.getItemMeta(); oceanM.setDisplayName("§bOcean"); oceanM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.OCEAN ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); ocean.setItemMeta(oceanM);
		ItemStack plains = new ItemStack(Material.GRASS); ItemMeta plainsM = plains.getItemMeta(); plainsM.setDisplayName("§aPlains"); plainsM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.PLAINS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); plains.setItemMeta(plainsM);
		ItemStack desert = new ItemStack(Material.SAND); ItemMeta desertM = desert.getItemMeta(); desertM.setDisplayName("§eDesert"); desertM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.DESERT ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); desert.setItemMeta(desertM);
		ItemStack extremehills = new ItemStack(Material.STONE); ItemMeta extremehillsM = extremehills.getItemMeta(); extremehillsM.setDisplayName("§9Extreme Hills"); extremehillsM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.EXTREME_HILLS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); extremehills.setItemMeta(extremehillsM);
		ItemStack forest = new ItemStack(Material.LEAVES); ItemMeta forestM = forest.getItemMeta(); forestM.setDisplayName("§2Forest"); forestM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.FOREST ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); forest.setItemMeta(forestM);
		ItemStack taiga = new ItemStack(Material.LEAVES, 1, (byte) 1); ItemMeta taigaM = taiga.getItemMeta(); taigaM.setDisplayName("§2Taiga"); taigaM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.TAIGA ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); taiga.setItemMeta(taigaM);
		ItemStack swampland = new ItemStack(Material.RED_ROSE, 1, (byte) 1); ItemMeta swamplandM = swampland.getItemMeta(); swamplandM.setDisplayName("§2Swampland"); swamplandM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.SWAMPLAND ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); swampland.setItemMeta(swamplandM);
		ItemStack river = new ItemStack(Material.WATER_BUCKET); ItemMeta riverM = river.getItemMeta(); riverM.setDisplayName("§bRiver"); riverM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.RIVER ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); river.setItemMeta(riverM);
		ItemStack ice = new ItemStack(Material.SNOW_BALL); ItemMeta iceM = ice.getItemMeta(); iceM.setDisplayName("§bIce"); iceM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.ICE ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); ice.setItemMeta(iceM);
		ItemStack mushroom = new ItemStack(Material.RED_MUSHROOM); ItemMeta mushroomM = mushroom.getItemMeta(); mushroomM.setDisplayName("§5Mushroom"); mushroomM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.MUSHROOM ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); mushroom.setItemMeta(mushroomM);
		ItemStack jungle = new ItemStack(Material.LEAVES, 1, (byte) 3); ItemMeta jungleM = jungle.getItemMeta(); jungleM.setDisplayName("§2Jungle"); jungleM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.JUNGLE ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); jungle.setItemMeta(jungleM);
		ItemStack roofed = new ItemStack(Material.LOG_2, 1, (byte) 1); ItemMeta roofedM = roofed.getItemMeta(); roofedM.setDisplayName("§2Roofed Forest"); roofedM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.ROOFED_FOREST ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); roofed.setItemMeta(roofedM);
		ItemStack savana = new ItemStack(Material.LOG_2); ItemMeta savanaM = savana.getItemMeta(); savanaM.setDisplayName("§6Savanna"); savanaM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.SAVANNA ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); savana.setItemMeta(savanaM);
		ItemStack mesa = new ItemStack(Material.STAINED_CLAY); ItemMeta mesaM = mesa.getItemMeta(); mesaM.setDisplayName("§6Mesa"); mesaM.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.MESA ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver")); mesa.setItemMeta(mesaM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(4, it);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(19, ocean);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(20, plains);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(21, desert);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(22, extremehills);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(23, forest);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(24, taiga);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(25, swampland);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(28, river);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(29, ice);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(30, mushroom);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(31, jungle);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(32, roofed);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(33, savana);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(34, mesa);
		APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eBiome")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eBiome au 00")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU == null) new BiomeInMiddle();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().BIOME_IN_MIDDLE_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bOcean")) {
				APIPlugin.getInstance().getAPI().s.OCEAN = APIPlugin.getInstance().getAPI().s.OCEAN ? false:true;
				fr.rolan.tools.generation.Biome.OCEAN.setActived(APIPlugin.getInstance().getAPI().s.OCEAN);
				fr.rolan.tools.generation.Biome.BEACH.setActived(APIPlugin.getInstance().getAPI().s.OCEAN);
				fr.rolan.tools.generation.Biome.DEEP_OCEAN.setActived(APIPlugin.getInstance().getAPI().s.OCEAN);
				fr.rolan.tools.generation.Biome.STONE_BEACH.setActived(APIPlugin.getInstance().getAPI().s.OCEAN);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.OCEAN ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aPlains")) {
				APIPlugin.getInstance().getAPI().s.PLAINS = APIPlugin.getInstance().getAPI().s.PLAINS ? false:true;
				fr.rolan.tools.generation.Biome.PLAINS.setActived(APIPlugin.getInstance().getAPI().s.PLAINS);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.PLAINS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eDesert")) {
				APIPlugin.getInstance().getAPI().s.DESERT = APIPlugin.getInstance().getAPI().s.DESERT ? false:true;
				fr.rolan.tools.generation.Biome.DESERT.setActived(APIPlugin.getInstance().getAPI().s.DESERT);
				fr.rolan.tools.generation.Biome.DESERT_HILLS.setActived(APIPlugin.getInstance().getAPI().s.DESERT);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.DESERT ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§9Extreme Hills")) {
				APIPlugin.getInstance().getAPI().s.EXTREME_HILLS = APIPlugin.getInstance().getAPI().s.EXTREME_HILLS ? false:true;
				fr.rolan.tools.generation.Biome.EXTREME_HILLS.setActived(APIPlugin.getInstance().getAPI().s.EXTREME_HILLS);
				fr.rolan.tools.generation.Biome.SMALL_MOUNTAINS.setActived(APIPlugin.getInstance().getAPI().s.EXTREME_HILLS);
				fr.rolan.tools.generation.Biome.EXTREME_HILLS_PLUS.setActived(APIPlugin.getInstance().getAPI().s.EXTREME_HILLS);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.EXTREME_HILLS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Forest")) {
				APIPlugin.getInstance().getAPI().s.FOREST = APIPlugin.getInstance().getAPI().s.FOREST ? false:true;
				fr.rolan.tools.generation.Biome.FOREST.setActived(APIPlugin.getInstance().getAPI().s.FOREST);
				fr.rolan.tools.generation.Biome.FOREST_HILLS.setActived(APIPlugin.getInstance().getAPI().s.FOREST);
				fr.rolan.tools.generation.Biome.BIRCH_FOREST.setActived(APIPlugin.getInstance().getAPI().s.FOREST);
				fr.rolan.tools.generation.Biome.BIRCH_FOREST_HILLS.setActived(APIPlugin.getInstance().getAPI().s.FOREST);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.FOREST ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Taiga")) {
				APIPlugin.getInstance().getAPI().s.TAIGA = APIPlugin.getInstance().getAPI().s.TAIGA ? false:true;
				fr.rolan.tools.generation.Biome.TAIGA.setActived(APIPlugin.getInstance().getAPI().s.TAIGA);
				fr.rolan.tools.generation.Biome.TAIGA_HILLS.setActived(APIPlugin.getInstance().getAPI().s.TAIGA);
				fr.rolan.tools.generation.Biome.MEGA_TAIGA_HILLS.setActived(APIPlugin.getInstance().getAPI().s.TAIGA);
				fr.rolan.tools.generation.Biome.MEGA_TAIGA.setActived(APIPlugin.getInstance().getAPI().s.TAIGA);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.TAIGA ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Swampland")) {
				APIPlugin.getInstance().getAPI().s.SWAMPLAND = APIPlugin.getInstance().getAPI().s.SWAMPLAND ? false:true;
				fr.rolan.tools.generation.Biome.SWAMPLAND.setActived(APIPlugin.getInstance().getAPI().s.SWAMPLAND);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.SWAMPLAND ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bRiver")) {
				APIPlugin.getInstance().getAPI().s.RIVER = APIPlugin.getInstance().getAPI().s.RIVER ? false:true;
				fr.rolan.tools.generation.Biome.RIVER.setActived(APIPlugin.getInstance().getAPI().s.RIVER);
				fr.rolan.tools.generation.Biome.FROZEN_RIVER.setActived(APIPlugin.getInstance().getAPI().s.RIVER ? APIPlugin.getInstance().getAPI().s.ICE : false);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.RIVER ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bIce")) {
				APIPlugin.getInstance().getAPI().s.ICE = APIPlugin.getInstance().getAPI().s.ICE ? false:true;
				fr.rolan.tools.generation.Biome.ICE_MOUNTAINS.setActived(APIPlugin.getInstance().getAPI().s.ICE ? APIPlugin.getInstance().getAPI().s.EXTREME_HILLS : false);
				fr.rolan.tools.generation.Biome.ICE_PLAINS.setActived(APIPlugin.getInstance().getAPI().s.ICE);
				fr.rolan.tools.generation.Biome.FROZEN_OCEAN.setActived(APIPlugin.getInstance().getAPI().s.ICE ? APIPlugin.getInstance().getAPI().s.OCEAN : false);
				fr.rolan.tools.generation.Biome.FROZEN_RIVER.setActived(APIPlugin.getInstance().getAPI().s.ICE ? APIPlugin.getInstance().getAPI().s.RIVER : false);
				fr.rolan.tools.generation.Biome.COLD_BEACH.setActived(APIPlugin.getInstance().getAPI().s.ICE ? APIPlugin.getInstance().getAPI().s.OCEAN : false);
				fr.rolan.tools.generation.Biome.COLD_TAIGA.setActived(APIPlugin.getInstance().getAPI().s.ICE ? APIPlugin.getInstance().getAPI().s.TAIGA : false);
				fr.rolan.tools.generation.Biome.COLD_TAIGA_HILLS.setActived(APIPlugin.getInstance().getAPI().s.ICE ? APIPlugin.getInstance().getAPI().s.TAIGA : false);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.ICE ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§5Mushroom")) {
				APIPlugin.getInstance().getAPI().s.MUSHROOM = APIPlugin.getInstance().getAPI().s.MUSHROOM ? false:true;
				fr.rolan.tools.generation.Biome.MUSHROOM_ISLAND.setActived(APIPlugin.getInstance().getAPI().s.MUSHROOM);
				fr.rolan.tools.generation.Biome.MUSHROOM_SHORE.setActived(APIPlugin.getInstance().getAPI().s.MUSHROOM);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.MUSHROOM ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Jungle")) {
				APIPlugin.getInstance().getAPI().s.JUNGLE = APIPlugin.getInstance().getAPI().s.JUNGLE ? false:true;
				fr.rolan.tools.generation.Biome.JUNGLE.setActived(APIPlugin.getInstance().getAPI().s.JUNGLE);
				fr.rolan.tools.generation.Biome.JUNGLE_EDGE.setActived(APIPlugin.getInstance().getAPI().s.JUNGLE);
				fr.rolan.tools.generation.Biome.JUNGLE_HILLS.setActived(APIPlugin.getInstance().getAPI().s.JUNGLE ? APIPlugin.getInstance().getAPI().s.EXTREME_HILLS : false);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.JUNGLE ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Roofed Forest")) {
				APIPlugin.getInstance().getAPI().s.ROOFED_FOREST = APIPlugin.getInstance().getAPI().s.ROOFED_FOREST ? false:true;
				fr.rolan.tools.generation.Biome.ROOFED_FOREST.setActived(APIPlugin.getInstance().getAPI().s.ROOFED_FOREST);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.ROOFED_FOREST ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§6Savanna")) {
				APIPlugin.getInstance().getAPI().s.SAVANNA = APIPlugin.getInstance().getAPI().s.SAVANNA ? false:true;
				fr.rolan.tools.generation.Biome.SAVANNA.setActived(APIPlugin.getInstance().getAPI().s.SAVANNA);
				fr.rolan.tools.generation.Biome.SAVANNA_PLATEAU.setActived(APIPlugin.getInstance().getAPI().s.SAVANNA);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.SAVANNA ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§6Mesa")) {
				APIPlugin.getInstance().getAPI().s.MESA = APIPlugin.getInstance().getAPI().s.MESA ? false:true;
				fr.rolan.tools.generation.Biome.MESA.setActived(APIPlugin.getInstance().getAPI().s.MESA);
				fr.rolan.tools.generation.Biome.MESA_PLATEAU.setActived(APIPlugin.getInstance().getAPI().s.MESA);
				fr.rolan.tools.generation.Biome.MESA_PLATEAU_F.setActived(APIPlugin.getInstance().getAPI().s.MESA);
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non ce biome", "", (APIPlugin.getInstance().getAPI().s.MESA ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}
		}
	}
}
