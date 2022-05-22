package fr.rolan.core.gui.menu.settings.world;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.core.APIPlugin;

public class Drop implements Listener {
	
	public Drop() {
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU = Bukkit.createInventory(null, 54, "§8» §eDrop");
		ItemStack apple = new ItemStack(Material.APPLE); ItemMeta appleM = apple.getItemMeta(); appleM.setDisplayName("§cPomme"); appleM.setLore(Arrays.asList("", "§7Pourcentage de chance de drop de pomme", "", "§c"+APIPlugin.getInstance().getAPI().s.APPLE_DROP+"%")); apple.setItemMeta(appleM);
		ItemStack flint = new ItemStack(Material.FLINT); ItemMeta flintM = flint.getItemMeta(); flintM.setDisplayName("§8Silex"); flintM.setLore(Arrays.asList("", "§7Pourcentage de chance de drop de silex", "", "§8"+APIPlugin.getInstance().getAPI().s.FLINT_DROP+"%")); flint.setItemMeta(flintM);
		ItemStack it = new ItemStack(Material.ENDER_PEARL); ItemMeta meta = it.getItemMeta(); meta.setDisplayName("§eEnder Pearl"); meta.setLore(Arrays.asList("", "§7Pourcentage de chance de drop de Ender Pearl", "", "§e"+APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP+"%")); it.setItemMeta(meta);
		
		ItemStack purple = new ItemStack(Material.BANNER, 1, (byte) 5);BannerMeta purpleM = (BannerMeta) purple.getItemMeta();purpleM.setDisplayName("§1§5- 10%");purpleM.setBaseColor(DyeColor.MAGENTA);purpleM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.MAGENTA, PatternType.BORDER), new Pattern(DyeColor.MAGENTA, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.MAGENTA, PatternType.STRIPE_TOP)));purpleM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);purple.setItemMeta(purpleM);
		ItemStack cyan = new ItemStack(Material.BANNER, 1, (byte) 12);BannerMeta cyanM = (BannerMeta) cyan.getItemMeta();cyanM.setDisplayName("§1§b- 5%");cyanM.setBaseColor(DyeColor.CYAN);cyanM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.CYAN, PatternType.BORDER), new Pattern(DyeColor.CYAN, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.CYAN, PatternType.STRIPE_TOP)));cyanM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);cyan.setItemMeta(cyanM);
		ItemStack green = new ItemStack(Material.BANNER, 1, (byte) 2);BannerMeta greenM = (BannerMeta) green.getItemMeta();greenM.setDisplayName("§1§2- 1%");greenM.setBaseColor(DyeColor.GREEN);greenM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.GREEN, PatternType.BORDER), new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP)));greenM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);green.setItemMeta(greenM);
		ItemStack red = new ItemStack(Material.BANNER, 1, (byte) 1);BannerMeta redM = (BannerMeta) red.getItemMeta();redM.setDisplayName("§1§c+ 10%");redM.setBaseColor(DyeColor.RED);redM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.RED, PatternType.BORDER), new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.RED, PatternType.STRIPE_TOP)));redM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); red.setItemMeta(redM);
		ItemStack orange = new ItemStack(Material.BANNER, 1, (byte) 14);BannerMeta orangeM = (BannerMeta) orange.getItemMeta();orangeM.setDisplayName("§1§6+ 5%");orangeM.setBaseColor(DyeColor.ORANGE);orangeM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.ORANGE, PatternType.BORDER), new Pattern(DyeColor.ORANGE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.ORANGE, PatternType.STRIPE_TOP)));orangeM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); orange.setItemMeta(orangeM);
		ItemStack yellow = new ItemStack(Material.BANNER, 1, (byte) 11);BannerMeta yellowM = (BannerMeta) yellow.getItemMeta();yellowM.setDisplayName("§1§e+ 1%");yellowM.setBaseColor(DyeColor.YELLOW);yellowM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.YELLOW, PatternType.BORDER), new Pattern(DyeColor.YELLOW, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.YELLOW, PatternType.STRIPE_TOP)));yellowM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); yellow.setItemMeta(yellowM);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(10, purple);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(11, cyan);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(12, green);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(13, apple);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(14, yellow);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(15, orange);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(16, red);
		purpleM.setDisplayName("§2§5- 10%"); purple.setItemMeta(purpleM);
		cyanM.setDisplayName("§2§b- 5%"); cyan.setItemMeta(cyanM);
		greenM.setDisplayName("§2§2- 1%"); green.setItemMeta(greenM);
		redM.setDisplayName("§2§c+ 10%"); red.setItemMeta(redM);
		orangeM.setDisplayName("§2§6+ 5%"); orange.setItemMeta(orangeM);
		yellowM.setDisplayName("§2§e+ 1%"); yellow.setItemMeta(yellowM);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(19, purple);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(20, cyan);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(21, green);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(22, flint);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(23, yellow);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(24, orange);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(25, red);
		purpleM.setDisplayName("§3§5- 10%"); purple.setItemMeta(purpleM);
		cyanM.setDisplayName("§3§b- 5%"); cyan.setItemMeta(cyanM);
		greenM.setDisplayName("§3§2- 1%"); green.setItemMeta(greenM);
		redM.setDisplayName("§3§c+ 10%"); red.setItemMeta(redM);
		orangeM.setDisplayName("§3§6+ 5%"); orange.setItemMeta(orangeM);
		yellowM.setDisplayName("§3§e+ 1%"); yellow.setItemMeta(yellowM);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(28, purple);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(29, cyan);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(30, green);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(31, it);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(32, yellow);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(33, orange);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(34, red);
		APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().DROP_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eDrop")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().WORLD_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§1")) {
				ItemMeta meta = event.getInventory().getItem(13).getItemMeta();
				if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§5- 10%") && APIPlugin.getInstance().getAPI().s.APPLE_DROP-10>0) {
					APIPlugin.getInstance().getAPI().s.APPLE_DROP-=10;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§b- 5%") && APIPlugin.getInstance().getAPI().s.APPLE_DROP-5>0) {
					APIPlugin.getInstance().getAPI().s.APPLE_DROP-=5;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§2- 1%") && APIPlugin.getInstance().getAPI().s.APPLE_DROP-1>0) {
					APIPlugin.getInstance().getAPI().s.APPLE_DROP-=1;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§c+ 10%")) {
					APIPlugin.getInstance().getAPI().s.APPLE_DROP+=10;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§6+ 5%")) {
					APIPlugin.getInstance().getAPI().s.APPLE_DROP+=5;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§e+ 1%")) {
					APIPlugin.getInstance().getAPI().s.APPLE_DROP+=1;
				}
				meta.setLore(Arrays.asList("", "§7Pourcentage de chance de drop de pomme", "", "§c"+APIPlugin.getInstance().getAPI().s.APPLE_DROP+"%"));
				event.getInventory().getItem(13).setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§2")) {
				ItemMeta meta = event.getInventory().getItem(22).getItemMeta();
				if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§5- 10%") && APIPlugin.getInstance().getAPI().s.FLINT_DROP-10>0) {
					APIPlugin.getInstance().getAPI().s.FLINT_DROP-=10;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§b- 5%") && APIPlugin.getInstance().getAPI().s.FLINT_DROP-5>0) {
					APIPlugin.getInstance().getAPI().s.FLINT_DROP-=5;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§2- 1%") && APIPlugin.getInstance().getAPI().s.FLINT_DROP-1>0) {
					APIPlugin.getInstance().getAPI().s.FLINT_DROP-=1;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§c+ 10%")) {
					APIPlugin.getInstance().getAPI().s.FLINT_DROP+=10;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§6+ 5%")) {
					APIPlugin.getInstance().getAPI().s.FLINT_DROP+=5;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§e+ 1%")) {
					APIPlugin.getInstance().getAPI().s.FLINT_DROP+=1;
				}
				meta.setLore(Arrays.asList("", "§7Pourcentage de chance de drop de silex", "", "§8"+APIPlugin.getInstance().getAPI().s.FLINT_DROP+"%"));
				event.getInventory().getItem(22).setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§3")) {
				ItemMeta meta = event.getInventory().getItem(31).getItemMeta();
				if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§5- 10%") && APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP-10>-1) {
					APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP-=10;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§b- 5%") && APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP-5>-1) {
					APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP-=5;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§2- 1%") && APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP-1>-1) {
					APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP-=1;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§c+ 10%")) {
					APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP+=10;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§6+ 5%")) {
					APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP+=5;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§e+ 1%")) {
					APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP+=1;
				}
				meta.setLore(Arrays.asList("", "§7Pourcentage de chance de drop de Ender Pearl", "", "§e"+APIPlugin.getInstance().getAPI().s.ENDER_PEARL_DROP+"%"));
				event.getInventory().getItem(31).setItemMeta(meta);
			}
		}
	}
}
