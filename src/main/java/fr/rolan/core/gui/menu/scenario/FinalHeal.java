package fr.rolan.core.gui.menu.scenario;

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

public class FinalHeal implements Listener {
	
	public FinalHeal() {
		APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU = Bukkit.createInventory(null, 9, "§8» §eFinalHeal");
		
		DecimalFormat format = new DecimalFormat("00");
		ItemStack finalheal = new ItemStack(Material.BARRIER); ItemMeta finalhealM = finalheal.getItemMeta(); finalhealM.setDisplayName("§dFinalHeal"); finalhealM.setLore(Arrays.asList("", "§7Temps avant activation du scénario", "", "§d"+format.format((APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER/60))+"min "+format.format((APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER%60))+"s")); finalheal.setItemMeta(finalhealM);
		ItemStack purple = new ItemStack(Material.BANNER, 1, (byte) 5);BannerMeta purpleM = (BannerMeta) purple.getItemMeta();purpleM.setDisplayName("§1§5- 5min");purpleM.setBaseColor(DyeColor.MAGENTA);purpleM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.MAGENTA, PatternType.BORDER), new Pattern(DyeColor.MAGENTA, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.MAGENTA, PatternType.STRIPE_TOP)));purpleM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);purple.setItemMeta(purpleM);
		ItemStack cyan = new ItemStack(Material.BANNER, 1, (byte) 12);BannerMeta cyanM = (BannerMeta) cyan.getItemMeta();cyanM.setDisplayName("§1§b- 1min");cyanM.setBaseColor(DyeColor.CYAN);cyanM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.CYAN, PatternType.BORDER), new Pattern(DyeColor.CYAN, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.CYAN, PatternType.STRIPE_TOP)));cyanM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);cyan.setItemMeta(cyanM);
		ItemStack green = new ItemStack(Material.BANNER, 1, (byte) 2);BannerMeta greenM = (BannerMeta) green.getItemMeta();greenM.setDisplayName("§1§2- 30s");greenM.setBaseColor(DyeColor.GREEN);greenM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.GREEN, PatternType.BORDER), new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP)));greenM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);green.setItemMeta(greenM);
		ItemStack red = new ItemStack(Material.BANNER, 1, (byte) 1);BannerMeta redM = (BannerMeta) red.getItemMeta();redM.setDisplayName("§1§c+ 5min");redM.setBaseColor(DyeColor.RED);redM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.RED, PatternType.BORDER), new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.RED, PatternType.STRIPE_TOP)));redM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); red.setItemMeta(redM);
		ItemStack orange = new ItemStack(Material.BANNER, 1, (byte) 14);BannerMeta orangeM = (BannerMeta) orange.getItemMeta();orangeM.setDisplayName("§1§6+ 1min");orangeM.setBaseColor(DyeColor.ORANGE);orangeM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.ORANGE, PatternType.BORDER), new Pattern(DyeColor.ORANGE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.ORANGE, PatternType.STRIPE_TOP)));orangeM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); orange.setItemMeta(orangeM);
		ItemStack yellow = new ItemStack(Material.BANNER, 1, (byte) 11);BannerMeta yellowM = (BannerMeta) yellow.getItemMeta();yellowM.setDisplayName("§1§e+ 30s");yellowM.setBaseColor(DyeColor.YELLOW);yellowM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.YELLOW, PatternType.BORDER), new Pattern(DyeColor.YELLOW, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.YELLOW, PatternType.STRIPE_TOP)));yellowM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); yellow.setItemMeta(yellowM);
		APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.setItem(1, purple);
		APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.setItem(2, cyan);
		APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.setItem(3, green);
		APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.setItem(4, finalheal);
		APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.setItem(5, yellow);
		APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.setItem(6, orange);
		APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.setItem(7, red);
		
		APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.setItem(0, getArrowBack());
		for(int i = 0; i < 9; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().FINALHEAL_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §eFinalHeal")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§1")) {
				ItemMeta meta = event.getInventory().getItem(4).getItemMeta();
				if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§5- 5min") && APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER-300>0) {
					APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER-=300;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§b- 1min") && APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER-60>0) {
					APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER-=60;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§2- 30s") && APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER-30>0) {
					APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER-=30;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§c+ 5min")) {
					APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER+=300;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§6+ 1min")) {
					APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER+=60;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§e+ 30s")) {
					APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER+=30;
				}
				meta.setLore(Arrays.asList("", "§7Temps avant activation du scénario", "", "§d"+new DecimalFormat("00").format((APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER/60))+"min "+new DecimalFormat("00").format((APIPlugin.getInstance().getAPI().s.FINALHEAL_TIMER%60))+"s"));
				event.getInventory().getItem(4).setItemMeta(meta);
			}
		}
	}
}
