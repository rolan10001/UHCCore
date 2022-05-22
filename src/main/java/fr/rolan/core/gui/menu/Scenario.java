package fr.rolan.core.gui.menu;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.gui.menu.scenario.DiamondLimit;
import fr.rolan.core.gui.menu.scenario.FinalHeal;

public class Scenario implements Listener {
	
	public Scenario() {
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU = Bukkit.createInventory(null, 54, "§8» §eScénario");
		
		ItemStack cutclean = new ItemStack(Material.IRON_ORE); ItemMeta cutcleanM = cutclean.getItemMeta(); cutcleanM.setDisplayName("§eCutclean"); if(APIPlugin.getInstance().getAPI().s.CUTCLEAN) {cutcleanM.addEnchant(Enchantment.DURABILITY, 5, false);} cutcleanM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.CUTCLEAN ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); cutclean.setItemMeta(cutcleanM);
		ItemStack hastey = new ItemStack(Material.DIAMOND_PICKAXE); ItemMeta hasteyM = hastey.getItemMeta(); hasteyM.setDisplayName("§eHasteyBoys"); if(APIPlugin.getInstance().getAPI().s.HASTEY_BOYS) {hasteyM.addEnchant(Enchantment.DURABILITY, 5, false);} hasteyM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.HASTEY_BOYS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); hastey.setItemMeta(hasteyM);
		ItemStack hasteybabies = new ItemStack(Material.WOOD_PICKAXE); ItemMeta hasteybabiesM = hasteybabies.getItemMeta(); hasteybabiesM.setDisplayName("§eHasteyBabies"); if(APIPlugin.getInstance().getAPI().s.HASTEY_BABIES) {hasteybabiesM.addEnchant(Enchantment.DURABILITY, 5, false);} hasteybabiesM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.HASTEY_BABIES ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); hasteybabies.setItemMeta(hasteybabiesM);
		ItemStack fireless = new ItemStack(Material.BLAZE_POWDER); ItemMeta firelessM = fireless.getItemMeta(); firelessM.setDisplayName("§eFireLess"); if(APIPlugin.getInstance().getAPI().s.FIRELESS) {firelessM.addEnchant(Enchantment.DURABILITY, 5, false);} firelessM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.FIRELESS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); fireless.setItemMeta(firelessM);
		ItemStack safeminers = new ItemStack(Material.IRON_PICKAXE); ItemMeta safeminersM = safeminers.getItemMeta(); safeminersM.setDisplayName("§eSafeMiners"); if(APIPlugin.getInstance().getAPI().s.SAFEMINERS) {safeminersM.addEnchant(Enchantment.DURABILITY, 5, false);} safeminersM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SAFEMINERS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); safeminers.setItemMeta(safeminersM);
		ItemStack rodless = new ItemStack(Material.FISHING_ROD); ItemMeta rodlessM = rodless.getItemMeta(); rodlessM.setDisplayName("§eRodLess"); if(APIPlugin.getInstance().getAPI().s.RODLESS) {rodlessM.addEnchant(Enchantment.DURABILITY, 5, false);} rodlessM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.RODLESS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); rodless.setItemMeta(rodlessM);
		ItemStack noeggnosnow = new ItemStack(Material.SNOW_BALL); ItemMeta noeggnosnowM = noeggnosnow.getItemMeta(); noeggnosnowM.setDisplayName("§eNoEggNoSnow"); if(APIPlugin.getInstance().getAPI().s.NO_EGG_NO_SNOW) {noeggnosnowM.addEnchant(Enchantment.DURABILITY, 5, false);} noeggnosnowM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.NO_EGG_NO_SNOW ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); noeggnosnow.setItemMeta(noeggnosnowM);
		ItemStack timber = new ItemStack(Material.LOG); ItemMeta timberM = timber.getItemMeta(); timberM.setDisplayName("§eTimber"); if(APIPlugin.getInstance().getAPI().s.TIMBER) {timberM.addEnchant(Enchantment.DURABILITY, 5, false);} timberM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.TIMBER ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); timber.setItemMeta(timberM);
		ItemStack finalheal = new ItemStack(Material.SUGAR); ItemMeta finalhealM = finalheal.getItemMeta(); finalhealM.setDisplayName("§eFinalHeal"); if(APIPlugin.getInstance().getAPI().s.FINALHEAL) {finalhealM.addEnchant(Enchantment.DURABILITY, 5, false);} finalhealM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.FINALHEAL ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver", "§8» §eClic droit pour ouvrir le menu")); finalheal.setItemMeta(finalhealM);
		ItemStack diamondlimit = new ItemStack(Material.DIAMOND); ItemMeta diamondlimitM = diamondlimit.getItemMeta(); diamondlimitM.setDisplayName("§eDiamondLimit"); if(APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT) {diamondlimitM.addEnchant(Enchantment.DURABILITY, 5, false);} diamondlimitM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver", "§8» §eClic droit pour ouvrir le menu")); diamondlimit.setItemMeta(diamondlimitM);
		ItemStack cateyes = new ItemStack(Material.EYE_OF_ENDER); ItemMeta cateyesM = cateyes.getItemMeta(); cateyesM.setDisplayName("§eCatEyes"); if(APIPlugin.getInstance().getAPI().s.CATEYES) {cateyesM.addEnchant(Enchantment.DURABILITY, 5, false);} cateyesM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.CATEYES ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); cateyes.setItemMeta(cateyesM);
		ItemStack superheros = new ItemStack(Material.NETHER_STAR); ItemMeta superherosM = superheros.getItemMeta(); superherosM.setDisplayName("§eSuper Heroes"); if(APIPlugin.getInstance().getAPI().s.SUPER_HEROES) {superherosM.addEnchant(Enchantment.DURABILITY, 5, false);} superherosM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SUPER_HEROES ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); superheros.setItemMeta(superherosM);
		ItemStack gonefishing = new ItemStack(Material.FISHING_ROD); ItemMeta gonefishingM = gonefishing.getItemMeta(); gonefishingM.setDisplayName("§eGoneFishing"); if(APIPlugin.getInstance().getAPI().s.GONEFISHING) {gonefishingM.addEnchant(Enchantment.DURABILITY, 5, false);} gonefishingM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.GONEFISHING ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); gonefishing.setItemMeta(gonefishingM);
		ItemStack xenophobia = new ItemStack(Material.EMERALD); ItemMeta xenophobiaM = xenophobia.getItemMeta(); xenophobiaM.setDisplayName("§eXenophobia"); if(APIPlugin.getInstance().getAPI().s.XENOPHOBIA) {xenophobiaM.addEnchant(Enchantment.DURABILITY, 5, false);} xenophobiaM.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.XENOPHOBIA ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver")); xenophobia.setItemMeta(xenophobiaM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(10, cutclean);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(11, hastey);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(12, hasteybabies);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(13, fireless);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(14, safeminers);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(15, rodless);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(16, noeggnosnow);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(19, timber);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(20, finalheal);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(21, diamondlimit);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(22, cateyes);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(23, superheros);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(24, gonefishing);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(25, xenophobia);
		APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(49, getArrowBack());
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eScénario")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eCutclean")) {
				APIPlugin.getInstance().getAPI().s.CUTCLEAN = APIPlugin.getInstance().getAPI().s.CUTCLEAN ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.CUTCLEAN) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.CUTCLEAN ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eHasteyBoys")) {
				APIPlugin.getInstance().getAPI().s.HASTEY_BOYS = APIPlugin.getInstance().getAPI().s.HASTEY_BOYS ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.HASTEY_BOYS) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.HASTEY_BOYS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eHasteyBabies")) {
				APIPlugin.getInstance().getAPI().s.HASTEY_BABIES = APIPlugin.getInstance().getAPI().s.HASTEY_BABIES ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.HASTEY_BABIES) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.HASTEY_BABIES ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eFireLess")) {
				APIPlugin.getInstance().getAPI().s.FIRELESS = APIPlugin.getInstance().getAPI().s.FIRELESS ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.FIRELESS) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.FIRELESS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eSafeMiners")) {
				APIPlugin.getInstance().getAPI().s.SAFEMINERS = APIPlugin.getInstance().getAPI().s.SAFEMINERS ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.SAFEMINERS) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SAFEMINERS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eRodLess")) {
				APIPlugin.getInstance().getAPI().s.RODLESS = APIPlugin.getInstance().getAPI().s.RODLESS ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.RODLESS) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.RODLESS ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eNoEggNoSnow")) {
				APIPlugin.getInstance().getAPI().s.NO_EGG_NO_SNOW = APIPlugin.getInstance().getAPI().s.NO_EGG_NO_SNOW ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.NO_EGG_NO_SNOW) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.NO_EGG_NO_SNOW ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eTimber")) {
				APIPlugin.getInstance().getAPI().s.TIMBER = APIPlugin.getInstance().getAPI().s.TIMBER ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.TIMBER) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.TIMBER ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eFinalHeal")) {
				if(event.getClick().equals(ClickType.LEFT)) {
					APIPlugin.getInstance().getAPI().s.FINALHEAL = APIPlugin.getInstance().getAPI().s.FINALHEAL ? false : true;
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					if(APIPlugin.getInstance().getAPI().s.FINALHEAL) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
					meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.FINALHEAL ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver", "§8» §eClic droit pour ouvrir le menu"));
					event.getCurrentItem().setItemMeta(meta);
				}else if(event.getClick().equals(ClickType.RIGHT)) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU == null) new FinalHeal();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU);
				}
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eDiamondLimit")) {
				if(event.getClick().equals(ClickType.LEFT)) {
					APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT = APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT ? false : true;
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					if(APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
					meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.DIAMONDLIMIT ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver", "§8» §eClic droit pour ouvrir le menu"));
					event.getCurrentItem().setItemMeta(meta);
				}else if(event.getClick().equals(ClickType.RIGHT)) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().DIAMONDLIMIT_MENU == null) new DiamondLimit();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().DIAMONDLIMIT_MENU);
				}
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eCatEyes")) {
				APIPlugin.getInstance().getAPI().s.CATEYES = APIPlugin.getInstance().getAPI().s.CATEYES ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.CATEYES) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.CATEYES ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eSuper Heroes")) {
				APIPlugin.getInstance().getAPI().s.SUPER_HEROES = APIPlugin.getInstance().getAPI().s.SUPER_HEROES ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.SUPER_HEROES) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SUPER_HEROES ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eGoneFishing")) {
				APIPlugin.getInstance().getAPI().s.GONEFISHING = APIPlugin.getInstance().getAPI().s.GONEFISHING ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.GONEFISHING) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.GONEFISHING ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eXenophobia")) {
				APIPlugin.getInstance().getAPI().s.XENOPHOBIA = APIPlugin.getInstance().getAPI().s.XENOPHOBIA ? false : true;
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.XENOPHOBIA) {meta.addEnchant(Enchantment.DURABILITY, 5, false);}else {meta.removeEnchant(Enchantment.DURABILITY);}
				meta.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.XENOPHOBIA ? "§aActivé":"§cDésactivé"), "", "§8» §eClic gauche pour §aactiver§e/§cDésactiver"));
				event.getCurrentItem().setItemMeta(meta);
			}
		}
	}
}
