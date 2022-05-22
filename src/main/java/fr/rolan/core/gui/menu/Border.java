package fr.rolan.core.gui.menu;

import static fr.rolan.api.gui.GuiManager.*;

import java.text.DecimalFormat;
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

public class Border implements Listener {
	
	public Border() {
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU = Bukkit.createInventory(null, 54, "§8» §eBordure");
		
		DecimalFormat format = new DecimalFormat("00");
		ItemStack border = new ItemStack(Material.BARRIER); ItemMeta borderM = border.getItemMeta(); borderM.setDisplayName("§eBordure"); borderM.setLore(Arrays.asList("", "§7Taille de la bordure", "", "§e"+APIPlugin.getInstance().getAPI().s.BORDER_SIZE+" +/-")); border.setItemMeta(borderM);
		ItemStack finale = new ItemStack(Material.BARRIER); ItemMeta finaleM = finale.getItemMeta(); finaleM.setDisplayName("§6Bordure Finale"); finaleM.setLore(Arrays.asList("", "§7Taille de la bordure finale", "", "§6"+APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE+" +/-")); finale.setItemMeta(finaleM);
		ItemStack it = new ItemStack(Material.BARRIER); ItemMeta meta = it.getItemMeta(); meta.setDisplayName("§bVitesse de réduction"); meta.setLore(Arrays.asList("", "§7Vitesse de réduction de la bordure", "", "§b"+new DecimalFormat("##0.0").format(APIPlugin.getInstance().getAPI().s.BORDER_SPEED)+"blocs/s", "§3"+format.format(((int)((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)/60))+"min "+format.format(((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)%60)+"s")); it.setItemMeta(meta);
		
		ItemStack purple = new ItemStack(Material.BANNER, 1, (byte) 5);BannerMeta purpleM = (BannerMeta) purple.getItemMeta();purpleM.setDisplayName("§1§5- 100");purpleM.setBaseColor(DyeColor.MAGENTA);purpleM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.MAGENTA, PatternType.BORDER), new Pattern(DyeColor.MAGENTA, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.MAGENTA, PatternType.STRIPE_TOP)));purpleM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);purple.setItemMeta(purpleM);
		ItemStack cyan = new ItemStack(Material.BANNER, 1, (byte) 12);BannerMeta cyanM = (BannerMeta) cyan.getItemMeta();cyanM.setDisplayName("§1§b- 50");cyanM.setBaseColor(DyeColor.CYAN);cyanM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.CYAN, PatternType.BORDER), new Pattern(DyeColor.CYAN, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.CYAN, PatternType.STRIPE_TOP)));cyanM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);cyan.setItemMeta(cyanM);
		ItemStack green = new ItemStack(Material.BANNER, 1, (byte) 2);BannerMeta greenM = (BannerMeta) green.getItemMeta();greenM.setDisplayName("§1§2- 1");greenM.setBaseColor(DyeColor.GREEN);greenM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.GREEN, PatternType.BORDER), new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP)));greenM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);green.setItemMeta(greenM);
		ItemStack red = new ItemStack(Material.BANNER, 1, (byte) 1);BannerMeta redM = (BannerMeta) red.getItemMeta();redM.setDisplayName("§1§c+ 100");redM.setBaseColor(DyeColor.RED);redM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.RED, PatternType.BORDER), new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.RED, PatternType.STRIPE_TOP)));redM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); red.setItemMeta(redM);
		ItemStack orange = new ItemStack(Material.BANNER, 1, (byte) 14);BannerMeta orangeM = (BannerMeta) orange.getItemMeta();orangeM.setDisplayName("§1§6+ 50");orangeM.setBaseColor(DyeColor.ORANGE);orangeM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.ORANGE, PatternType.BORDER), new Pattern(DyeColor.ORANGE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.ORANGE, PatternType.STRIPE_TOP)));orangeM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); orange.setItemMeta(orangeM);
		ItemStack yellow = new ItemStack(Material.BANNER, 1, (byte) 11);BannerMeta yellowM = (BannerMeta) yellow.getItemMeta();yellowM.setDisplayName("§1§e+ 1");yellowM.setBaseColor(DyeColor.YELLOW);yellowM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.YELLOW, PatternType.BORDER), new Pattern(DyeColor.YELLOW, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.YELLOW, PatternType.STRIPE_TOP)));yellowM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); yellow.setItemMeta(yellowM);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(10, purple);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(11, cyan);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(12, green);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(13, border);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(14, yellow);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(15, orange);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(16, red);
		purpleM.setDisplayName("§2§5- 100"); purple.setItemMeta(purpleM);
		cyanM.setDisplayName("§2§b- 50"); cyan.setItemMeta(cyanM);
		greenM.setDisplayName("§2§2- 1"); green.setItemMeta(greenM);
		redM.setDisplayName("§2§c+ 100"); red.setItemMeta(redM);
		orangeM.setDisplayName("§2§6+ 50"); orange.setItemMeta(orangeM);
		yellowM.setDisplayName("§2§e+ 1"); yellow.setItemMeta(yellowM);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(19, purple);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(20, cyan);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(21, green);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(22, finale);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(23, yellow);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(24, orange);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(25, red);
		purpleM.setDisplayName("§3§5- 1"); purple.setItemMeta(purpleM);
		cyanM.setDisplayName("§3§b- 0.5"); cyan.setItemMeta(cyanM);
		greenM.setDisplayName("§3§2- 0.1"); green.setItemMeta(greenM);
		redM.setDisplayName("§3§c+ 1"); red.setItemMeta(redM);
		orangeM.setDisplayName("§3§6+ 0.5"); orange.setItemMeta(orangeM);
		yellowM.setDisplayName("§3§e+ 0.1"); yellow.setItemMeta(yellowM);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(28, purple);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(29, cyan);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(30, green);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(31, it);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(32, yellow);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(33, orange);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(34, red);
		APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(49, getArrowBack());
		
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eBordure")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§1")) {
				ItemMeta meta = event.getInventory().getItem(13).getItemMeta();
				if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§5- 100") && APIPlugin.getInstance().getAPI().s.BORDER_SIZE-100>0) {
					APIPlugin.getInstance().getAPI().s.BORDER_SIZE-=100;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§b- 50") && APIPlugin.getInstance().getAPI().s.BORDER_SIZE-50>0) {
					APIPlugin.getInstance().getAPI().s.BORDER_SIZE-=50;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§2- 1") && APIPlugin.getInstance().getAPI().s.BORDER_SIZE-1>0) {
					APIPlugin.getInstance().getAPI().s.BORDER_SIZE-=1;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§c+ 100")) {
					APIPlugin.getInstance().getAPI().s.BORDER_SIZE+=100;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§6+ 50")) {
					APIPlugin.getInstance().getAPI().s.BORDER_SIZE+=50;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§e+ 1")) {
					APIPlugin.getInstance().getAPI().s.BORDER_SIZE+=1;
				}
				meta.setLore(Arrays.asList("", "§7Taille de la bordure", "", "§e"+APIPlugin.getInstance().getAPI().s.BORDER_SIZE+" +/-"));
				event.getInventory().getItem(13).setItemMeta(meta);
				meta = event.getInventory().getItem(31).getItemMeta();
				meta.setLore(Arrays.asList("", "§7Vitesse de réduction de la bordure", "", "§b"+new DecimalFormat("0.0").format(APIPlugin.getInstance().getAPI().s.BORDER_SPEED)+"blocs/s", "§3"+new DecimalFormat("00").format(((int)((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)/60))+"min "+new DecimalFormat("00").format(((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)%60)+"s"));
				event.getInventory().getItem(31).setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§2")) {
				ItemMeta meta = event.getInventory().getItem(22).getItemMeta();
				if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§5- 100") && APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE-100>0) {
					APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE-=100;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§b- 50") && APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE-50>0) {
					APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE-=50;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§2- 1") && APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE-1>0) {
					APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE-=1;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§c+ 100")) {
					APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE+=100;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§6+ 50")) {
					APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE+=50;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§e+ 1")) {
					APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE+=1;
				}
				meta.setLore(Arrays.asList("", "§7Taille de la bordure finale", "", "§6"+APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE+" +/-"));
				event.getInventory().getItem(22).setItemMeta(meta);
				meta = event.getInventory().getItem(31).getItemMeta();
				meta.setLore(Arrays.asList("", "§7Vitesse de réduction de la bordure", "", "§b"+new DecimalFormat("0.0").format(APIPlugin.getInstance().getAPI().s.BORDER_SPEED)+"blocs/s", "§3"+new DecimalFormat("00").format(((int)((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)/60))+"min "+new DecimalFormat("00").format(((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)%60)+"s"));
				event.getInventory().getItem(31).setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§3")) {
				ItemMeta meta = event.getInventory().getItem(31).getItemMeta();
				if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§5- 1") && APIPlugin.getInstance().getAPI().s.BORDER_SPEED-1>0) {
					APIPlugin.getInstance().getAPI().s.BORDER_SPEED-=1;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§b- 0.5") && APIPlugin.getInstance().getAPI().s.BORDER_SPEED-0.5>0) {
					APIPlugin.getInstance().getAPI().s.BORDER_SPEED-=0.5;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§2- 0.1") && APIPlugin.getInstance().getAPI().s.BORDER_SPEED-0.1>0) {
					APIPlugin.getInstance().getAPI().s.BORDER_SPEED-=0.1;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§c+ 1")) {
					APIPlugin.getInstance().getAPI().s.BORDER_SPEED+=1;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§6+ 0.5")) {
					APIPlugin.getInstance().getAPI().s.BORDER_SPEED+=0.5;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§e+ 0.1")) {
					APIPlugin.getInstance().getAPI().s.BORDER_SPEED+=0.1;
				}
				meta.setLore(Arrays.asList("", "§7Vitesse de réduction de la bordure", "", "§b"+new DecimalFormat("0.0").format(APIPlugin.getInstance().getAPI().s.BORDER_SPEED)+"blocs/s", "§3"+new DecimalFormat("00").format(((int)((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)/60))+"min "+new DecimalFormat("00").format(((double) (APIPlugin.getInstance().getAPI().s.BORDER_SIZE-APIPlugin.getInstance().getAPI().s.BORDER_FINAL_SIZE)/APIPlugin.getInstance().getAPI().s.BORDER_SPEED)%60)+"s"));
				event.getInventory().getItem(31).setItemMeta(meta);
			}
		}
	}
}
