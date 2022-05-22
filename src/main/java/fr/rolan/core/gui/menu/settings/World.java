package fr.rolan.core.gui.menu.settings;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
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
import fr.rolan.core.gui.menu.settings.world.Drop;
import fr.rolan.core.gui.menu.settings.world.Generation;

public class World implements Listener {
	
	public World() {
		APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU = Bukkit.createInventory(null, 54, "§8» §eConfiguration Monde");
		
		ItemStack gen = new ItemStack(Material.DIRT); ItemMeta genM = gen.getItemMeta(); genM.setDisplayName("§eGénération"); genM.setLore(Arrays.asList("", "§7Configurer les paramètres de génération du monde", "", "§8» §eClic gauche pour ouvrir le menu")); gen.setItemMeta(genM);
		ItemStack difficulty = new ItemStack(Material.ROTTEN_FLESH); ItemMeta difficultyM = difficulty.getItemMeta(); difficultyM.setDisplayName("§eDifficulté"); difficultyM.setLore(Arrays.asList("", "§7Séléctionner la difficulté du monde", "", (APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.PEACEFUL) ? "§bPaisible": APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.EASY) ? "§aFacile" : APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.NORMAL) ? "§eNormal" : "§cDifficile"), "", "§8» §eClic gauche pour §aaugmenter", "§8» §eClic droit pour §cdiminuer")); difficulty.setItemMeta(difficultyM);
		ItemStack horse = new ItemStack(Material.SADDLE); ItemMeta horseM = horse.getItemMeta(); horseM.setDisplayName("§eChevaux"); horseM.setLore(Arrays.asList("", "§7Activer ou non les chevaux", "", (APIPlugin.getInstance().getAPI().s.HORSE ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver")); horse.setItemMeta(horseM);
		ItemStack drop = new ItemStack(Material.APPLE); ItemMeta dropM = drop.getItemMeta(); dropM.setDisplayName("§eDrop"); dropM.setLore(Arrays.asList("", "§7Configurer les pourcentages de drop", "", "§8» §eClic gauche pour ouvrir le menu")); drop.setItemMeta(dropM);
		ItemStack nether = new ItemStack(Material.NETHERRACK); ItemMeta netherM = nether.getItemMeta(); netherM.setDisplayName("§eNether"); netherM.setLore(Arrays.asList("", "§7Activer ou non le Nether", "", (APIPlugin.getInstance().getAPI().s.NETHER ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver")); nether.setItemMeta(netherM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU.setItem(13, gen);
		APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU.setItem(20, horse);
		APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU.setItem(24, nether);
		APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU.setItem(30, difficulty);
		APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU.setItem(32, drop);
		APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eConfiguration Monde")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eDifficulté")) {
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(event.getClick().equals(ClickType.LEFT)) {
					APIPlugin.getInstance().getAPI().s.DIFFICULTY = APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.PEACEFUL) ? Difficulty.EASY : APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.EASY) ? Difficulty.NORMAL : Difficulty.HARD;
				}else if(event.getClick().equals(ClickType.RIGHT)) {
					APIPlugin.getInstance().getAPI().s.DIFFICULTY = APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.HARD) ? Difficulty.NORMAL : APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.NORMAL) ? Difficulty.EASY : Difficulty.PEACEFUL;
				}
				meta.setLore(Arrays.asList("", "§7Séléctionner la difficulté du monde", "", (APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.PEACEFUL) ? "§bPaisible": APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.EASY) ? "§aFacile" : APIPlugin.getInstance().getAPI().s.DIFFICULTY.equals(Difficulty.NORMAL) ? "§eNormal" : "§cDifficile"), "", "§8» §eClic gauche pour §aaugmenter", "§8» §eClic droit pour §cdiminuer"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eChevaux")) {
				APIPlugin.getInstance().getAPI().s.HORSE = APIPlugin.getInstance().getAPI().s.HORSE ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non les chevaux", "", (APIPlugin.getInstance().getAPI().s.HORSE ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eNether")) {
				APIPlugin.getInstance().getAPI().s.NETHER = APIPlugin.getInstance().getAPI().s.NETHER ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				meta.setLore(Arrays.asList("", "§7Activer ou non le Nether", "", (APIPlugin.getInstance().getAPI().s.NETHER ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cdésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eDrop")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU == null) new Drop();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eGénération")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU == null) new Generation();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU);
			}
		}
	}
}
