package fr.rolan.core.gui.menu.settings.world.generation;

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
import fr.rolan.tools.pregeneration.PregenerationTask;

public class Prégénération implements Listener {
	
	public Prégénération() {
		APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU = Bukkit.createInventory(null, 9, "§8» §ePrégénération");
		
		ItemStack pregen = new ItemStack(Material.BARRIER); ItemMeta pregenM = pregen.getItemMeta(); pregenM.setDisplayName("§ePrégénération"); pregenM.setLore(Arrays.asList("", "§7Configurer la taille de prégénération", "", "§e"+APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE+" +/-", "", "§8» §eClic gauche pour lancer la prégénération")); pregen.setItemMeta(pregenM);
		ItemStack purple = new ItemStack(Material.BANNER, 1, (byte) 5);BannerMeta purpleM = (BannerMeta) purple.getItemMeta();purpleM.setDisplayName("§1§5- 100");purpleM.setBaseColor(DyeColor.MAGENTA);purpleM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.MAGENTA, PatternType.BORDER), new Pattern(DyeColor.MAGENTA, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.MAGENTA, PatternType.STRIPE_TOP)));purpleM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);purple.setItemMeta(purpleM);
		ItemStack cyan = new ItemStack(Material.BANNER, 1, (byte) 12);BannerMeta cyanM = (BannerMeta) cyan.getItemMeta();cyanM.setDisplayName("§1§b- 50");cyanM.setBaseColor(DyeColor.CYAN);cyanM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.CYAN, PatternType.BORDER), new Pattern(DyeColor.CYAN, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.CYAN, PatternType.STRIPE_TOP)));cyanM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);cyan.setItemMeta(cyanM);
		ItemStack green = new ItemStack(Material.BANNER, 1, (byte) 2);BannerMeta greenM = (BannerMeta) green.getItemMeta();greenM.setDisplayName("§1§2- 1");greenM.setBaseColor(DyeColor.GREEN);greenM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.GREEN, PatternType.BORDER), new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP)));greenM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);green.setItemMeta(greenM);
		ItemStack red = new ItemStack(Material.BANNER, 1, (byte) 1);BannerMeta redM = (BannerMeta) red.getItemMeta();redM.setDisplayName("§1§c+ 100");redM.setBaseColor(DyeColor.RED);redM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.RED, PatternType.BORDER), new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.RED, PatternType.STRIPE_TOP)));redM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); red.setItemMeta(redM);
		ItemStack orange = new ItemStack(Material.BANNER, 1, (byte) 14);BannerMeta orangeM = (BannerMeta) orange.getItemMeta();orangeM.setDisplayName("§1§6+ 50");orangeM.setBaseColor(DyeColor.ORANGE);orangeM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.ORANGE, PatternType.BORDER), new Pattern(DyeColor.ORANGE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.ORANGE, PatternType.STRIPE_TOP)));orangeM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); orange.setItemMeta(orangeM);
		ItemStack yellow = new ItemStack(Material.BANNER, 1, (byte) 11);BannerMeta yellowM = (BannerMeta) yellow.getItemMeta();yellowM.setDisplayName("§1§e+ 1");yellowM.setBaseColor(DyeColor.YELLOW);yellowM.setPatterns(Arrays.asList(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.YELLOW, PatternType.BORDER), new Pattern(DyeColor.YELLOW, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.YELLOW, PatternType.STRIPE_TOP)));yellowM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); yellow.setItemMeta(yellowM);
		
		APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.setItem(0, getArrowBack());
		APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.setItem(1, purple);
		APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.setItem(2, cyan);
		APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.setItem(3, green);
		APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.setItem(4, pregen);
		APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.setItem(5, yellow);
		APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.setItem(6, orange);
		APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.setItem(7, red);
		for(int i = 0; i < 9; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().PREGENERATION_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §ePrégénération")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().GENERATION_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§1")) {
				ItemMeta meta = event.getInventory().getItem(4).getItemMeta();
				if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§5- 100") && APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE-100>0) {
					APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE-=100;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§b- 50") && APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE-50>0) {
					APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE-=50;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§2- 1") && APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE-1>0) {
					APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE-=1;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§c+ 100")) {
					APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE+=100;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§6+ 50")) {
					APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE+=50;
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().endsWith("§e+ 1")) {
					APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE+=1;
				}
				meta.setLore(Arrays.asList("", "§7Configurer la taille de prégénération", "", "§e"+APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE+" +/-", "", "§8» §eClic gauche pour lancer la prégénération"));
				event.getInventory().getItem(4).setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§ePrégénération")) {
				try {
					new PregenerationTask(Bukkit.getWorld("Host"), APIPlugin.getInstance().getAPI().s.PREGENERATION_VALUE);
				}catch(Exception e) {
					player.sendMessage("§7§l▏ §cUne erreur est survenue.");
				}
			}
		}
	}
}
