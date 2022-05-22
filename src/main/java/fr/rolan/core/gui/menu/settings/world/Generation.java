package fr.rolan.core.gui.menu.settings.world;

import static fr.rolan.api.gui.GuiManager.*;
import static fr.rolan.api.game.GameSettings.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.gui.menu.settings.world.generation.OreGeneration;
import fr.rolan.core.gui.menu.settings.world.generation.Prégénération;
import fr.rolan.tools.pregeneration.PregenerationTask;
import io.papermc.lib.PaperLib;
import net.minecraft.server.v1_8_R3.BiomeBase;

public class Generation implements Listener {
	
	public Generation() {
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU = Bukkit.createInventory(null, 54, "§8» §eGénération");
		
		ItemStack generate = new ItemStack(Material.GRASS); ItemMeta generateM = generate.getItemMeta(); generateM.setDisplayName("§eGénérer"); generateM.setLore(Arrays.asList("", "§7Commencer la génération du monde", "", "§7Prégénérer le monde: "+(APIPlugin.getInstance().getAPI().s.PREGENERATION ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour générer", "§8» §eClic droit pour §aactiver§e/§cdésactiver §ela pregen")); generate.setItemMeta(generateM);
		ItemStack pregen = new ItemStack(Material.BARRIER); ItemMeta pregenM = pregen.getItemMeta(); pregenM.setDisplayName("§ePrégénération"); pregenM.setLore(Arrays.asList("", "§7Configurer la prégénération du monde", "", "§8» §eClic gauche pour ouvrir le menu")); pregen.setItemMeta(pregenM);
		ItemStack ore = new ItemStack(Material.DIAMOND_ORE); ItemMeta oreM = ore.getItemMeta(); oreM.setDisplayName("§eGénération Minerai"); oreM.setLore(Arrays.asList("", "§7Configurer la génération des minerais dans le monde", "", "§8» §eClic gauche pour ouvrir le menu")); ore.setItemMeta(oreM);
		ItemStack biome = new ItemStack(Material.DEAD_BUSH); ItemMeta biomeM = biome.getItemMeta(); biomeM.setDisplayName("§eBiome"); biomeM.setLore(Arrays.asList("", "§7Gérer les biomes du monde", "", "§8» §eClic gauche pour ouvrir le menu")); biome.setItemMeta(biomeM);
		ItemStack cave = new ItemStack(Material.STONE); ItemMeta caveM = cave.getItemMeta(); caveM.setDisplayName("§eCave Boost"); caveM.setLore(Arrays.asList("", "§7Booster le spawn des grottes" ,"", (APIPlugin.getInstance().getAPI().s.CAVE_BOOST?"§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver")); cave.setItemMeta(caveM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU.setItem(4, generate);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU.setItem(13, pregen);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU.setItem(20, ore);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU.setItem(24, biome);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU.setItem(31, cave);
		APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU.setItem(49, getArrowBack());
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eGénération")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eGénérer")) {
				if(event.getClick().equals(ClickType.LEFT)) {
					for(Player players : Bukkit.getOnlinePlayers()) {if(players.getWorld().getName().equals("Host")) PaperLib.teleportAsync(players, Bukkit.getWorld("Lobby").getSpawnLocation());}
					try {
						Bukkit.unloadWorld("Host", false);
						FileUtils.deleteDirectory(new File("Host"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					player.sendMessage("§7§l▏ §aGénération du monde.");
					List<fr.rolan.tools.generation.Biome> biomeList = new ArrayList<fr.rolan.tools.generation.Biome>();
					//faire gaffe avec les pre config l'enum ne met pas à jour les boolean pour savoir si le biome est activé
					for(fr.rolan.tools.generation.Biome biome : fr.rolan.tools.generation.Biome.values()) {if(biome.isActived() && !biome.getName().endsWith("River") && !biome.getName().startsWith("§5Mushroom")) {biomeList.add(biome);}}
					try {
						Field biomesField = BiomeBase.class.getDeclaredField("biomes");
						biomesField.setAccessible(true);
					
						Field modifiersField = Field.class.getDeclaredField("modifiers");
						modifiersField.setAccessible(true);
						modifiersField.setInt(biomesField, biomesField.getModifiers() & ~Modifier.FINAL);
						BiomeBase[] biomes = (BiomeBase[])biomesField.get((Object)null);
						
						Field craftBiomesField = CraftBlock.class.getDeclaredField("BIOME_MAPPING");
						craftBiomesField.setAccessible(true);

						modifiersField.setInt(craftBiomesField, craftBiomesField.getModifiers() & ~Modifier.FINAL);
						Biome[] BIOME_MAPPING = updateBiomeMapping(new Biome[BiomeBase.getBiomes().length]);
						
						if(!APIPlugin.getInstance().getAPI().s.ROOFED_FOREST) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.ROOFED_FOREST.id] = biome;
							BIOME_MAPPING[BiomeBase.ROOFED_FOREST.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.PLAINS) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.PLAINS.id] = biome;
							BIOME_MAPPING[BiomeBase.PLAINS.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.SWAMPLAND) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.SWAMPLAND.id] = biome;
							BIOME_MAPPING[BiomeBase.SWAMPLAND.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.RIVER) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.RIVER.id] = biome;
						}
						if(!APIPlugin.getInstance().getAPI().s.OCEAN) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.OCEAN.id] = biome;
							biomes[BiomeBase.BEACH.id] = biome;
							biomes[BiomeBase.STONE_BEACH.id] = biome;
							biomes[BiomeBase.DEEP_OCEAN.id] = biome;
						}
						if(!APIPlugin.getInstance().getAPI().s.DESERT) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.DESERT.id] = biome;
							biomes[BiomeBase.DESERT_HILLS.id] = biome;
							BIOME_MAPPING[BiomeBase.DESERT.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.EXTREME_HILLS) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.EXTREME_HILLS.id] = biome;
							biomes[BiomeBase.EXTREME_HILLS_PLUS.id] = biome;
							biomes[BiomeBase.SMALL_MOUNTAINS.id] = biome;
							BIOME_MAPPING[BiomeBase.EXTREME_HILLS.id + 128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.EXTREME_HILLS_PLUS.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.FOREST) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.FOREST.id] = biome;
							biomes[BiomeBase.FOREST_HILLS.id] = biome;
							biomes[BiomeBase.BIRCH_FOREST.id] = biome;
							biomes[BiomeBase.BIRCH_FOREST_HILLS.id] = biome;
							BIOME_MAPPING[BiomeBase.FOREST.id + 128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.BIRCH_FOREST.id + 128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.BIRCH_FOREST_HILLS.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.TAIGA) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.TAIGA.id] = biome;
							biomes[BiomeBase.TAIGA_HILLS.id] = biome;
							biomes[BiomeBase.MEGA_TAIGA_HILLS.id] = biome;
							biomes[BiomeBase.MEGA_TAIGA.id] = biome;
							BIOME_MAPPING[BiomeBase.TAIGA.id + 128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.MEGA_TAIGA.id + 128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.MEGA_TAIGA_HILLS.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.ICE) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.FROZEN_OCEAN.id] = biome;
							biomes[BiomeBase.FROZEN_RIVER.id] = biome;
							biomes[BiomeBase.ICE_PLAINS.id] = biome;
							biomes[BiomeBase.ICE_MOUNTAINS.id] = biome;
							biomes[BiomeBase.COLD_BEACH.id] = biome;
							biomes[BiomeBase.COLD_TAIGA.id] = biome;
							biomes[BiomeBase.COLD_TAIGA_HILLS.id] = biome;
							BIOME_MAPPING[BiomeBase.ICE_PLAINS.id + 128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.COLD_TAIGA.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.MUSHROOM) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.MUSHROOM_ISLAND.id] = biome;
							biomes[BiomeBase.MUSHROOM_SHORE.id] = biome;
						}
						if(!APIPlugin.getInstance().getAPI().s.JUNGLE) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.JUNGLE.id] = biome;
							biomes[BiomeBase.JUNGLE_EDGE.id] = biome;
							biomes[BiomeBase.JUNGLE_HILLS.id] = biome;
							BIOME_MAPPING[BiomeBase.JUNGLE.id + 128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.JUNGLE_EDGE.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.SAVANNA) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.SAVANNA.id] = biome;
							biomes[BiomeBase.SAVANNA_PLATEAU.id] = biome;
							BIOME_MAPPING[BiomeBase.SAVANNA.id + 128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.SAVANNA_PLATEAU.id + 128] = CraftBlock.biomeBaseToBiome(biome);
						}
						if(!APIPlugin.getInstance().getAPI().s.MESA) {
							BiomeBase biome = biomeList.get(new Random().nextInt(biomeList.size())).getBiome();
							biomes[BiomeBase.MESA.id] = biome;
							biomes[BiomeBase.MESA_PLATEAU.id] = biome;
							biomes[BiomeBase.MESA_PLATEAU_F.id] = biome;
							BIOME_MAPPING[BiomeBase.MESA.id+128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.MESA_PLATEAU_F.id+128] = CraftBlock.biomeBaseToBiome(biome);
							BIOME_MAPPING[BiomeBase.MESA_PLATEAU.id+128] = CraftBlock.biomeBaseToBiome(biome);
						}
						biomesField.set((Object)null, biomes);
						craftBiomesField.set(null, BIOME_MAPPING);
						/*Field maxHeightDiamond = CustomWorldSettingsFinal.class.getDeclaredField("av");
						maxHeightDiamond.setAccessible(true);
						modifiersField.setInt(maxHeightDiamond, maxHeightDiamond.getModifiers() & ~Modifier.FINAL);
						System.out.println(maxHeightDiamond.get(null));
						maxHeightDiamond.set(null, APIPlugin.getInstance().getAPI().s.MAX_HEIGHT_DIAMOND_ORE);*/
					}catch(NoSuchFieldException|IllegalAccessException e) {
						e.printStackTrace();
					}//faire une histoire de groupe pour que les biomes modifiés soient logiques
					new BukkitRunnable() {
						
						@Override
						public void run() {
							new WorldCreator("Host").type(WorldType.NORMAL).generateStructures(true).createWorld();
							try {
								Location loc = fr.rolan.tools.generation.Biome.findBiome(Bukkit.getWorld("Host"), APIPlugin.getInstance().getAPI().s.BIOME_IN_MIDDLE);
								Bukkit.getWorld("Host").setSpawnLocation(loc.getBlockX(), loc.getWorld().getHighestBlockYAt(loc), loc.getBlockZ());
								player.sendMessage("§7§l▏ §aGénération du monde terminée.");
								BORDER = Bukkit.getWorld("Host").getWorldBorder();
								BORDER.setCenter(loc);
								if(APIPlugin.getInstance().getAPI().s.PREGENERATION) new PregenerationTask(Bukkit.getWorld("Host"), APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE);
							}catch(Exception e) {
								player.sendMessage("§7§l▏ §cUne erreur est survenue, veuillez générer un nouveau monde.");
							}
						}
					}.runTaskLater(APIPlugin.getInstance(), 20);
				}else if(event.getClick().equals(ClickType.RIGHT)) {
					APIPlugin.getInstance().getAPI().s.PREGENERATION = APIPlugin.getInstance().getAPI().s.PREGENERATION ? false:true;
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					meta.setLore(Arrays.asList("", "§7Commencer la génération du monde", "", "§7Prégénérer le monde: "+(APIPlugin.getInstance().getAPI().s.PREGENERATION ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour générer", "§8» §eClic droit pour §aactiver§e/§cdésactiver §e la pregen"));
					event.getCurrentItem().setItemMeta(meta);
				}
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eGénération Minerai")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU == null) new OreGeneration();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_ORE_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePrégénération")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU == null) new Prégénération();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eBiome")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU == null) new fr.rolan.core.gui.menu.settings.world.generation.Biome();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().BIOME_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eCave Boost")) {
				APIPlugin.getInstance().getAPI().s.CAVE_BOOST = APIPlugin.getInstance().getAPI().s.CAVE_BOOST ? false:true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Booster le spawn des grottes" ,"", (APIPlugin.getInstance().getAPI().s.CAVE_BOOST?"§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}
		}
	}
	
	private Biome[] updateBiomeMapping(Biome[] BIOME_MAPPING) {
		BIOME_MAPPING[BiomeBase.OCEAN.id] = Biome.OCEAN;
        BIOME_MAPPING[BiomeBase.PLAINS.id] = Biome.PLAINS;
        BIOME_MAPPING[BiomeBase.DESERT.id] = Biome.DESERT;
        BIOME_MAPPING[BiomeBase.EXTREME_HILLS.id] = Biome.EXTREME_HILLS;
        BIOME_MAPPING[BiomeBase.FOREST.id] = Biome.FOREST;
        BIOME_MAPPING[BiomeBase.TAIGA.id] = Biome.TAIGA;
        BIOME_MAPPING[BiomeBase.SWAMPLAND.id] = Biome.SWAMPLAND;
        BIOME_MAPPING[BiomeBase.RIVER.id] = Biome.RIVER;
        BIOME_MAPPING[BiomeBase.HELL.id] = Biome.HELL;
        BIOME_MAPPING[BiomeBase.SKY.id] = Biome.SKY;
        BIOME_MAPPING[BiomeBase.FROZEN_OCEAN.id] = Biome.FROZEN_OCEAN;
        BIOME_MAPPING[BiomeBase.FROZEN_RIVER.id] = Biome.FROZEN_RIVER;
        BIOME_MAPPING[BiomeBase.ICE_PLAINS.id] = Biome.ICE_PLAINS;
        BIOME_MAPPING[BiomeBase.ICE_MOUNTAINS.id] = Biome.ICE_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.MUSHROOM_ISLAND.id] = Biome.MUSHROOM_ISLAND;
        BIOME_MAPPING[BiomeBase.MUSHROOM_SHORE.id] = Biome.MUSHROOM_SHORE;
        BIOME_MAPPING[BiomeBase.BEACH.id] = Biome.BEACH;
        BIOME_MAPPING[BiomeBase.DESERT_HILLS.id] = Biome.DESERT_HILLS;
        BIOME_MAPPING[BiomeBase.FOREST_HILLS.id] = Biome.FOREST_HILLS;
        BIOME_MAPPING[BiomeBase.TAIGA_HILLS.id] = Biome.TAIGA_HILLS;
        BIOME_MAPPING[BiomeBase.SMALL_MOUNTAINS.id] = Biome.SMALL_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.JUNGLE.id] = Biome.JUNGLE;
        BIOME_MAPPING[BiomeBase.JUNGLE_HILLS.id] = Biome.JUNGLE_HILLS;
        BIOME_MAPPING[BiomeBase.JUNGLE_EDGE.id] = Biome.JUNGLE_EDGE;
        BIOME_MAPPING[BiomeBase.DEEP_OCEAN.id] = Biome.DEEP_OCEAN;
        BIOME_MAPPING[BiomeBase.STONE_BEACH.id] = Biome.STONE_BEACH;
        BIOME_MAPPING[BiomeBase.COLD_BEACH.id] = Biome.COLD_BEACH;
        BIOME_MAPPING[BiomeBase.BIRCH_FOREST.id] = Biome.BIRCH_FOREST;
        BIOME_MAPPING[BiomeBase.BIRCH_FOREST_HILLS.id] = Biome.BIRCH_FOREST_HILLS;
        BIOME_MAPPING[BiomeBase.ROOFED_FOREST.id] = Biome.ROOFED_FOREST;
        BIOME_MAPPING[BiomeBase.COLD_TAIGA.id] = Biome.COLD_TAIGA;
        BIOME_MAPPING[BiomeBase.COLD_TAIGA_HILLS.id] = Biome.COLD_TAIGA_HILLS;
        BIOME_MAPPING[BiomeBase.MEGA_TAIGA.id] = Biome.MEGA_TAIGA;
        BIOME_MAPPING[BiomeBase.MEGA_TAIGA_HILLS.id] = Biome.MEGA_TAIGA_HILLS;
        BIOME_MAPPING[BiomeBase.EXTREME_HILLS_PLUS.id] = Biome.EXTREME_HILLS_PLUS;
        BIOME_MAPPING[BiomeBase.SAVANNA.id] = Biome.SAVANNA;
        BIOME_MAPPING[BiomeBase.SAVANNA_PLATEAU.id] = Biome.SAVANNA_PLATEAU;
        BIOME_MAPPING[BiomeBase.MESA.id] = Biome.MESA;
        BIOME_MAPPING[BiomeBase.MESA_PLATEAU_F.id] = Biome.MESA_PLATEAU_FOREST;
        BIOME_MAPPING[BiomeBase.MESA_PLATEAU.id] = Biome.MESA_PLATEAU;

        // Extended Biomes
        BIOME_MAPPING[BiomeBase.PLAINS.id + 128] = Biome.SUNFLOWER_PLAINS;
        BIOME_MAPPING[BiomeBase.DESERT.id + 128] = Biome.DESERT_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.FOREST.id + 128] = Biome.FLOWER_FOREST;
        BIOME_MAPPING[BiomeBase.TAIGA.id + 128] = Biome.TAIGA_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.SWAMPLAND.id + 128] = Biome.SWAMPLAND_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.ICE_PLAINS.id + 128] = Biome.ICE_PLAINS_SPIKES;
        BIOME_MAPPING[BiomeBase.JUNGLE.id + 128] = Biome.JUNGLE_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.JUNGLE_EDGE.id + 128] = Biome.JUNGLE_EDGE_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.COLD_TAIGA.id + 128] = Biome.COLD_TAIGA_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.SAVANNA.id + 128] = Biome.SAVANNA_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.SAVANNA_PLATEAU.id + 128] = Biome.SAVANNA_PLATEAU_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.MESA.id + 128] = Biome.MESA_BRYCE;
        BIOME_MAPPING[BiomeBase.MESA_PLATEAU_F.id + 128] = Biome.MESA_PLATEAU_FOREST_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.MESA_PLATEAU.id + 128] = Biome.MESA_PLATEAU_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.BIRCH_FOREST.id + 128] = Biome.BIRCH_FOREST_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.BIRCH_FOREST_HILLS.id + 128] = Biome.BIRCH_FOREST_HILLS_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.ROOFED_FOREST.id + 128] = Biome.ROOFED_FOREST_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.MEGA_TAIGA.id + 128] = Biome.MEGA_SPRUCE_TAIGA;
        BIOME_MAPPING[BiomeBase.EXTREME_HILLS.id + 128] = Biome.EXTREME_HILLS_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.EXTREME_HILLS_PLUS.id + 128] = Biome.EXTREME_HILLS_PLUS_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.MEGA_TAIGA_HILLS.id + 128] = Biome.MEGA_SPRUCE_TAIGA_HILLS;
		return BIOME_MAPPING;
	}
}
