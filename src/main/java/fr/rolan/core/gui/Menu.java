package fr.rolan.core.gui;

import static fr.rolan.api.gui.GuiManager.*;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rolan.api.events.MenuConstructorEvent;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.gui.menu.Armor;
import fr.rolan.core.gui.menu.Border;
import fr.rolan.core.gui.menu.Configurations;
import fr.rolan.core.gui.menu.GAMEMode;
import fr.rolan.core.gui.menu.Potions;
import fr.rolan.core.gui.menu.Scenario;
import fr.rolan.core.gui.menu.Timer;
import fr.rolan.tools.AnvilInventory;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;

public class Menu implements Listener {
	
	private boolean inAnvil = false;
	private UUID playerInAnvil = null;
	
	public Menu() {
		APIPlugin.getInstance().getAPI().getGuiManager().MENU = Bukkit.createInventory(null, 54, "§8» §eConfiguration");
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(4, setMetaInItem(new ItemStack(Material.NETHER_STAR), "§bMode de Jeu", Arrays.asList("", "§7Séléctionner un mode de Jeu", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(10, setMetaInItem(new ItemStack(Material.WATCH), "§bTimer", Arrays.asList("", "§7Configurer les différents timers de la partie", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(12, setMetaInItem(new ItemStack(Material.BEACON), "§bParamètres", Arrays.asList("", "§7Gérer les différents paramètres", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(13, setMetaInItem(new ItemStack(Material.PAPER), "§bSlot", Arrays.asList("", "§7Gérer le nombre de slot", "§e"+APIPlugin.getInstance().getAPI().s.SLOT+" slots", "", "§8» §eClic gauche pour ajouter un slot", "§8» §eClic droit pour retirer un slot")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(14, setMetaInItem(new ItemStack(Material.BARRIER), "§bBordure", Arrays.asList("", "§7Configurer les différents paramètres de la bordure", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(16, setMetaInItem(new ItemStack(Material.CHEST), "§bInventaire", Arrays.asList("", "§7Gérer l'inventaire de départ et de mort", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(31, setMetaInItem(new ItemStack(Material.DIAMOND_SWORD), "§bScénario", Arrays.asList("", "§7Configurer les scénarios dans la partie", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(37, setMetaInItem(new ItemStack(Material.FURNACE), "§bConfiguration", Arrays.asList("", "§7Accéder aux configurations sauvegardées", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(39, setMetaInItem(new ItemStack(Material.POTION), "§bPotions", Arrays.asList("", "§7Configurer les potions", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(41, setMetaInItem(new ItemStack(Material.DIAMOND_CHESTPLATE), "§bArmure & Enchantement", Arrays.asList("", "§7Configurer les armures et enchantements", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(43, setMetaInItem(new ItemStack(Material.NAME_TAG), "§bNom de l'Host", Arrays.asList("", "§7Donner un nom à l'Host", "", "§8» §eClic gauche pour ouvrir le menu")));
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(45, getArrowBack());
		APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(49, setMetaInItem(new ItemStack(Material.SLIME_BALL), "§aLancer la partie", null));
		for(int i = 0; i < 54; i++) if(APIPlugin.getInstance().getAPI().getGuiManager().MENU.getItem(i) == null) APIPlugin.getInstance().getAPI().getGuiManager().MENU.setItem(i, getGlass());
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		Bukkit.getPluginManager().callEvent(new MenuConstructorEvent(APIPlugin.getInstance().getAPI().getGuiManager().MENU));
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null) return;
		if(event.getInventory().getName().equals("§8» §eConfiguration")) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
					player.closeInventory();
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bMode de Jeu")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().GAMEMODE_MENU == null) new GAMEMode();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().GAMEMODE_MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bArmure & Enchantement")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU == null) new Armor();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().ARMOR_MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bPotions")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU == null) new Potions();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().POTION_MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bTimer")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().TIMER_MENU == null) new Timer();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().TIMER_MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bBordure")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU == null) new Border();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().BORDER_MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bInventaire")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().INVENTORY_MENU == null) new fr.rolan.core.gui.menu.Inventory();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().INVENTORY_MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bParamètres")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU == null) new fr.rolan.core.gui.menu.Settings();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().SETTINGS_MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bScénario")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU == null) new Scenario();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().SCENARIO_MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bConfiguration")) {
					if(APIPlugin.getInstance().getAPI().getGuiManager().CONFIGURATION_MENU == null) new Configurations();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().CONFIGURATION_MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aLancer la partie")) {
					APIPlugin.getInstance().getAPI().NewStartedManager();
					event.getInventory().setItem(49, setMetaInItem(new ItemStack(Material.MAGMA_CREAM), "§cStopper la partie", null));
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cStopper la partie")) {
					APIPlugin.getInstance().getAPI().NewLobbyManager();
					APIPlugin.getInstance().getAPI().getStartedManager().unregisterListeners();
					event.getInventory().setItem(49, setMetaInItem(new ItemStack(Material.SLIME_BALL), "§aLancer la partie", null));
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bSlot")) {
					if(event.getClick().equals(ClickType.LEFT)) {
						APIPlugin.getInstance().getAPI().s.SLOT++;
					}else if(event.getClick().equals(ClickType.RIGHT)) {
						if(Bukkit.getOnlinePlayers().size() == APIPlugin.getInstance().getAPI().s.SLOT)
							return;
						if(APIPlugin.getInstance().getAPI().s.SLOT == 2)
							return;
						APIPlugin.getInstance().getAPI().s.SLOT--;
					}
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					meta.setLore(Arrays.asList("", "§7Gérer le nombre de slot", "§e"+APIPlugin.getInstance().getAPI().s.SLOT+" slots", "", "§8» §eClic gauche pour ajouter un slot", "§8» §eClic droit pour retirer un slot"));
					event.getCurrentItem().setItemMeta(meta);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bNom de l'Host")) {
					EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
			        AnvilInventory fakeAnvil = new AnvilInventory(entityPlayer);
			        int containerId = entityPlayer.nextContainerCounter();
			        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage("§8» §7Nom de l'§eHost", new Object[]{}), 0));
			        entityPlayer.activeContainer = fakeAnvil;
			        entityPlayer.activeContainer.windowId = containerId;
			        entityPlayer.activeContainer.addSlotListener(entityPlayer);
			        entityPlayer.activeContainer = fakeAnvil;
			        entityPlayer.activeContainer.windowId = containerId;
			        Inventory inv = fakeAnvil.getBukkitView().getTopInventory();
			        ItemStack it = new ItemStack(Material.NAME_TAG);
			        ItemMeta meta = it.getItemMeta();
			        meta.setDisplayName(APIPlugin.getInstance().getAPI().s.NAME);
			        meta.setLore(Arrays.asList("§7Modifier le nom de l'Host"));
			        it.setItemMeta(meta);
			        inv.setItem(0, it);
			        inAnvil = true;
			        playerInAnvil = player.getUniqueId();
				}
			}
		}else if(event.getInventory() instanceof org.bukkit.inventory.AnvilInventory && inAnvil && playerInAnvil != null && playerInAnvil.equals(event.getWhoClicked().getUniqueId())) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			InventoryView view = event.getView();
			int rawSlot = event.getRawSlot();
			if(rawSlot == view.convertSlot(rawSlot)) {
				if(rawSlot == 2 && event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().hasDisplayName()) {
					APIPlugin.getInstance().getAPI().s.NAME = event.getCurrentItem().getItemMeta().getDisplayName();
					for(Player players : Bukkit.getOnlinePlayers())
						if(!APIPlugin.getInstance().getAPI().getUser(players).isInArena())
							APIPlugin.getInstance().getAPI().getScoreboardManager().setPlayerTitle(players.getUniqueId(), APIPlugin.getInstance().getAPI().s.NAME);
					event.getInventory().clear();
					player.closeInventory();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
					inAnvil = false;
					playerInAnvil = null;
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void InventoryClose(InventoryCloseEvent event) {
		if(event.getInventory() instanceof org.bukkit.inventory.AnvilInventory && inAnvil && playerInAnvil != null && playerInAnvil.equals(event.getPlayer().getUniqueId())) {
			inAnvil = false;
			playerInAnvil = null;
		}
	}
}
