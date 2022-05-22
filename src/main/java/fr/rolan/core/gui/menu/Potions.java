package fr.rolan.core.gui.menu;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.gui.menu.potions.Damage;
import fr.rolan.core.gui.menu.potions.FireResistance;
import fr.rolan.core.gui.menu.potions.Heal;
import fr.rolan.core.gui.menu.potions.Invisibility;
import fr.rolan.core.gui.menu.potions.Jump;
import fr.rolan.core.gui.menu.potions.NightVision;
import fr.rolan.core.gui.menu.potions.Poison;
import fr.rolan.core.gui.menu.potions.Régénération;
import fr.rolan.core.gui.menu.potions.Slowness;
import fr.rolan.core.gui.menu.potions.Speed;
import fr.rolan.core.gui.menu.potions.Strength;
import fr.rolan.core.gui.menu.potions.Water;
import fr.rolan.core.gui.menu.potions.Weakness;

public class Potions implements Listener {
	
	public Potions() {
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU = Bukkit.createInventory(null, 54, "§8» §7Configuration §ePotions");
		
		ItemStack force = new ItemStack(Material.BLAZE_POWDER); ItemMeta forceM = force.getItemMeta(); forceM.setDisplayName("§bPourcentage Force"); forceM.setLore(Arrays.asList("", "§c"+APIPlugin.getInstance().getAPI().s.STRENGTH_VALUE+"%", "", "§8» §eClic gauche pour §aajouter", "§8» §eClic droit pour §cretirer")); force.setItemMeta(forceM);
		ItemStack allpotion = new ItemStack(Material.POTION); ItemMeta allpotionM = allpotion.getItemMeta(); allpotionM.setDisplayName("§eToutes les potions"); allpotionM.setLore(Arrays.asList("", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.ALLPOTION ? "§cdésactiver" : "§aactiver"))); allpotion.setItemMeta(allpotionM);
		ItemStack level = new ItemStack(Material.GLOWSTONE_DUST); ItemMeta levelM = level.getItemMeta(); levelM.setDisplayName("§ePotions amplifiées"); levelM.setLore(Arrays.asList("", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.AMPLIFIED ? "§cdésactiver" : "§aactiver"))); level.setItemMeta(levelM);
		ItemStack lengthen = new ItemStack(Material.REDSTONE); ItemMeta lengthenM = lengthen.getItemMeta(); lengthenM.setDisplayName("§ePotions allongées"); lengthenM.setLore(Arrays.asList("", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.LENGTHEN ? "§cdésactiver" : "§aactiver"))); lengthen.setItemMeta(lengthenM);
		ItemStack splash = new ItemStack(Material.SULPHUR); ItemMeta splashM = splash.getItemMeta(); splashM.setDisplayName("§ePotions jetables"); splashM.setLore(Arrays.asList("", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.SPLASH ? "§cdésactiver" : "§aactiver"))); splash.setItemMeta(splashM);
		
		ItemStack a = new ItemStack(Material.POTION, 1, (byte) 1); PotionMeta A = (PotionMeta) a.getItemMeta(); A.setDisplayName("§bRégénération"); A.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.REGENERATION ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.REGENERATION_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.REGENERATION_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.REGENERATION_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); A.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); a.setItemMeta(A);
		ItemStack b = new ItemStack(Material.POTION, 1, (byte) 2); PotionMeta B = (PotionMeta) b.getItemMeta(); B.setDisplayName("§bSpeed"); B.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SPEED ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.SPEED_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.SPEED_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.SPEED_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); B.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); b.setItemMeta(B);
		ItemStack c = new ItemStack(Material.POTION, 1, (byte) 3); PotionMeta C = (PotionMeta) c.getItemMeta(); C.setDisplayName("§bRésistance au Feu"); C.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.FIRE_RESISTANCE ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.FIRE_RESISTANCE_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.FIRE_RESISTANCE_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.FIRE_RESISTANCE_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); C.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); c.setItemMeta(C);
		ItemStack d = new ItemStack(Material.POTION, 1, (byte) 4); PotionMeta D = (PotionMeta) d.getItemMeta(); D.setDisplayName("§bPoison"); D.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.POISON ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.POISON_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.POISON_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.POISON_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); D.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); d.setItemMeta(D);
		ItemStack e = new ItemStack(Material.POTION, 1, (byte) 5); PotionMeta E = (PotionMeta) e.getItemMeta(); E.setDisplayName("§bHeal"); E.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.HEAL ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.HEAL_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.HEAL_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.HEAL_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); E.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); e.setItemMeta(E);
		ItemStack f = new ItemStack(Material.POTION, 1, (byte) 6); PotionMeta F = (PotionMeta) f.getItemMeta(); F.setDisplayName("§bNight Vision"); F.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.NIGHT_VISION ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.NIGHT_VISION_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.NIGHT_VISION_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.NIGHT_VISION_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); F.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); f.setItemMeta(F);
		ItemStack g = new ItemStack(Material.POTION, 1, (byte) 8); PotionMeta G = (PotionMeta) g.getItemMeta(); G.setDisplayName("§bFaiblesse"); G.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.WEAKNESS ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.WEAKNESS_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.WEAKNESS_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.WEAKNESS_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); G.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); g.setItemMeta(G);
		ItemStack h = new ItemStack(Material.POTION, 1, (byte) 9); PotionMeta H = (PotionMeta) h.getItemMeta(); H.setDisplayName("§bForce"); H.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.STRENGTH ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.STRENGTH_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.STRENGTH_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.STRENGTH_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); H.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); h.setItemMeta(H);
		ItemStack i = new ItemStack(Material.POTION, 1, (byte) 10); PotionMeta I = (PotionMeta) i.getItemMeta(); I.setDisplayName("§bLenteur"); I.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.SLOWNESS ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.SLOWNESS_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.SLOWNESS_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.SLOWNESS_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); I.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); i.setItemMeta(I);
		ItemStack j = new ItemStack(Material.POTION, 1, (byte) 11); PotionMeta J = (PotionMeta) j.getItemMeta(); J.setDisplayName("§bSaut"); J.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.JUMP ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.JUMP_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.JUMP_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.JUMP_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); J.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); j.setItemMeta(J);
		ItemStack k = new ItemStack(Material.POTION, 1, (byte) 12); PotionMeta K = (PotionMeta) k.getItemMeta(); K.setDisplayName("§bDégats"); K.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.DAMAGE ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.DAMAGE_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.DAMAGE_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.DAMAGE_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); K.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); k.setItemMeta(K);
		ItemStack l = new ItemStack(Material.POTION, 1, (byte) 13); PotionMeta L = (PotionMeta) l.getItemMeta(); L.setDisplayName("§bRespiration"); L.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.WATER ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.WATER_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.WATER_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.WATER_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); L.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); l.setItemMeta(L);
		ItemStack m = new ItemStack(Material.POTION, 1, (byte) 14); PotionMeta M = (PotionMeta) m.getItemMeta(); M.setDisplayName("§bInvisibilité"); M.setLore(Arrays.asList("", (APIPlugin.getInstance().getAPI().s.INVISIBILITY ? "§aActivé" : "§cDésactivé"), "", "§8» §7Potions amplifiées: "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY_AMPLIFIED ? "§aActivé" : "§cDésactivé"), "§8» §7Potions allongées: "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY_LENGTHEN ? "§aActivé" : "§cDésactivé"), "§8» §7Potions jetables: "+(APIPlugin.getInstance().getAPI().s.INVISIBILITY_SPLASH ? "§aActivé" : "§cDésactivé"), "", "§8» §eClic gauche pour configurer")); M.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); m.setItemMeta(M);
		
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(13, force);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(10, a);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(16, b);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(19, c);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(20, d);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(24, e);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(25, f);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(28, g);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(29, h);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(30, i);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(31, j);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(32, k);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(33, l);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(34, m);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(47, allpotion);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(48, lengthen);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(49, getArrowBack());
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(50, level);
		APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(51, splash);
		
		for(int z = 0; z < 54; z++) if(APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.getItem(z) == null) APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU.setItem(z, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getInventory().getName().equals("§8» §7Configuration §ePotions")) return;
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bPourcentage Force")) {
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(event.getClick().equals(ClickType.LEFT)) {
					APIPlugin.getInstance().getAPI().s.STRENGTH_VALUE++;
				}else if(event.getClick().equals(ClickType.RIGHT) && APIPlugin.getInstance().getAPI().s.STRENGTH_VALUE > 0) {
					APIPlugin.getInstance().getAPI().s.STRENGTH_VALUE--;
				}
				meta.setLore(Arrays.asList("", "§c"+APIPlugin.getInstance().getAPI().s.STRENGTH_VALUE+"%", "", "§8» §eClic gauche pour §aajouter", "§8» §eClic droit pour §cretirer"));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eToutes les potions")) {
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.ALLPOTION) {APIPlugin.getInstance().getAPI().s.ALLPOTION = false;}else {APIPlugin.getInstance().getAPI().s.ALLPOTION = true;}
				meta.setLore(Arrays.asList("", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.ALLPOTION ? "§cdésactiver" : "§aactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotions amplifiées")) {
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.AMPLIFIED) {APIPlugin.getInstance().getAPI().s.AMPLIFIED = false;}else {APIPlugin.getInstance().getAPI().s.AMPLIFIED = true;}
				meta.setLore(Arrays.asList("", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.AMPLIFIED ? "§cdésactiver" : "§aactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotions allongées")) {
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.LENGTHEN) {APIPlugin.getInstance().getAPI().s.LENGTHEN = false;}else {APIPlugin.getInstance().getAPI().s.LENGTHEN = true;}
				meta.setLore(Arrays.asList("", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.LENGTHEN ? "§cdésactiver" : "§aactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§ePotions jetables")) {
				ItemMeta meta = event.getCurrentItem().getItemMeta();
				if(APIPlugin.getInstance().getAPI().s.SPLASH) {APIPlugin.getInstance().getAPI().s.SPLASH = false;}else {APIPlugin.getInstance().getAPI().s.SPLASH = true;}
				meta.setLore(Arrays.asList("", "§8» §eClic gauche pour "+(APIPlugin.getInstance().getAPI().s.SPLASH ? "§cdésactiver" : "§aactiver")));
				event.getCurrentItem().setItemMeta(meta);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bRégénération")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU == null) new Régénération();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().REGENERATION_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bSpeed")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().SPEED_MENU == null) new Speed();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().SPEED_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bRésistance au Feu")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().FIRE_RESISTANCE_MENU == null) new FireResistance();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().FIRE_RESISTANCE_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bPoison")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().POISON_MENU == null) new Poison();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().POISON_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bHeal")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().HEAL_MENU == null) new Heal();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().HEAL_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bNight Vision")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().NIGHT_VISION_MENU == null) new NightVision();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().NIGHT_VISION_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bFaiblesse")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().WEAKNESS_MENU == null) new Weakness();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().WEAKNESS_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bForce")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().STRENGTH_MENU == null) new Strength();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().STRENGTH_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bLenteur")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU == null) new Slowness();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().SLOWNESS_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bSaut")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().JUMP_MENU == null) new Jump();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().JUMP_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bDégats")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().DAMAGE_MENU == null) new Damage();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().DAMAGE_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bRespiration")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().WATER_MENU == null) new Water();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().WATER_MENU);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bInvisibilité")) {
				if(APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU == null) new Invisibility();
				player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().INVISIBILITY_MENU);
			}
		}
	}
}
