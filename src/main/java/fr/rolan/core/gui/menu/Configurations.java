package fr.rolan.core.gui.menu;

import static fr.rolan.api.gui.GuiManager.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import fr.rolan.api.events.SaveConfigurationEvent;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.api.stuff.StuffManager;
import fr.rolan.core.utils.FileUtils;
import fr.rolan.core.utils.Serializer;
import fr.rolan.tools.AnvilInventory;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;

public class Configurations implements Listener {
	
	private boolean inAnvil = false;
	private UUID playerInAnvil = null;
	private final Inventory CONFIGURATION_MENU;
	
	public Configurations() {
		APIPlugin.getInstance().getAPI().getGuiManager().CONFIGURATION_MENU = Bukkit.createInventory(null, 54, "§8» §eConfigurations");
		CONFIGURATION_MENU = APIPlugin.getInstance().getAPI().getGuiManager().CONFIGURATION_MENU;
		updateInventory();
		updatePlayerConfiguration();
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
		APIPlugin.getInstance().getAPI().getGuis().add(this);
	}
	
	private void updateInventory() {
		for(int i = 0; i < 9; i++) CONFIGURATION_MENU.setItem(i, getGlass());
		for(int i = 9; i < 18; i++) CONFIGURATION_MENU.setItem(i, getGlass());
		for(int i = 45; i < 54; i++) CONFIGURATION_MENU.setItem(i, getGlass());
		CONFIGURATION_MENU.setItem(17, getGlass());CONFIGURATION_MENU.setItem(18, getGlass());CONFIGURATION_MENU.setItem(26, getGlass());CONFIGURATION_MENU.setItem(27, getGlass());
		CONFIGURATION_MENU.setItem(35, getGlass());CONFIGURATION_MENU.setItem(36, getGlass());CONFIGURATION_MENU.setItem(44, getGlass());
		CONFIGURATION_MENU.setItem(9, getArrowBack());
		CONFIGURATION_MENU.setItem(2, setMetaInItem(new ItemStack(Material.WORKBENCH), "§eVos configurations", null));
		CONFIGURATION_MENU.setItem(6, setMetaInItem(new ItemStack(Material.FURNACE), "§eConfigurations du serveur", null));
		CONFIGURATION_MENU.setItem(13, setMetaInItem(new ItemStack(Material.SLIME_BALL), "§aSauvegarder", Arrays.asList("", "§7Sauvegarder votre configuration actuelle", "", "§8» §eClic gauche pour sauvegarder")));
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == null) return;
		if(event.getInventory().getName().equals("§8» §eConfigurations")) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eConfigurations du serveur")) {
					event.getInventory().clear();
					updateInventory();
					updateServerConfiguration();
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eVos configurations")) {
					event.getInventory().clear();
					updateInventory();
					updatePlayerConfiguration();
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aSauvegarder")) {
					EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
			        AnvilInventory fakeAnvil = new AnvilInventory(entityPlayer);
			        int containerId = entityPlayer.nextContainerCounter();
			        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage("§eNom de la Configuration", new Object[]{}), 0));
			        entityPlayer.activeContainer = fakeAnvil;
			        entityPlayer.activeContainer.windowId = containerId;
			        entityPlayer.activeContainer.addSlotListener(entityPlayer);
			        entityPlayer.activeContainer = fakeAnvil;
			        entityPlayer.activeContainer.windowId = containerId;
			        Inventory inv = fakeAnvil.getBukkitView().getTopInventory();
			        ItemStack it = new ItemStack(Material.NAME_TAG);
			        ItemMeta meta = it.getItemMeta();
			        meta.setDisplayName(APIPlugin.getInstance().getAPI().s.NAME);
			        meta.setLore(Arrays.asList("§7Nom de la Configuration"));
			        it.setItemMeta(meta);
			        inv.setItem(0, it);
			        inAnvil = true;
			        playerInAnvil = player.getUniqueId();
				}else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§e")) {
					if(event.getClick().equals(ClickType.LEFT)) {
						load(event.getCurrentItem().getItemMeta().getDisplayName());
						player.closeInventory();
						APIPlugin.getInstance().getAPI().getScoreboardManager().setTitle(APIPlugin.getInstance().getAPI().s.NAME);
						for(Listener l : APIPlugin.getInstance().getAPI().getGuis())
							HandlerList.unregisterAll(l);
						APIPlugin.getInstance().getAPI().getGuis().clear();
						try {
							for(Field f : APIPlugin.getInstance().getAPI().getGuiManager().getClass().getDeclaredFields()) {
								f.setAccessible(true);
								f.set(APIPlugin.getInstance().getAPI().getGuiManager(), null);
							}
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}else if(event.getClick().equals(ClickType.RIGHT)) {
						erase(player, event.getCurrentItem().getItemMeta().getDisplayName());
						event.getInventory().clear();
						updateInventory();
						updatePlayerConfiguration();
					}
				}
			}
		}else if(event.getInventory() instanceof org.bukkit.inventory.AnvilInventory && inAnvil && playerInAnvil != null && playerInAnvil.equals(event.getWhoClicked().getUniqueId())) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			InventoryView view = event.getView();
			int rawSlot = event.getRawSlot();
			if(rawSlot == view.convertSlot(rawSlot)) {
				if(rawSlot == 2 && event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().hasDisplayName()) {
					try{saveConfig(player, event.getCurrentItem().getItemMeta().getDisplayName());}catch(Exception e) {e.printStackTrace();}
					Bukkit.getPluginManager().callEvent(new SaveConfigurationEvent(player, event.getCurrentItem().getItemMeta().getDisplayName()));
					event.getInventory().clear();
					player.closeInventory();
					player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().CONFIGURATION_MENU);
					updatePlayerConfiguration();
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
	
	private void updatePlayerConfiguration() {
		JsonParser parser = new JsonParser();
		File repertoire = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+APIPlugin.getInstance().getAPI().s.HOST.toString());
		if(!repertoire.exists())
			repertoire.mkdirs();
		for(File file : repertoire.listFiles()) {
			if(file.getName().endsWith(".json"))
				try {
					JsonObject obj = parser.parse(new FileReader(file)).getAsJsonObject();
					CONFIGURATION_MENU.addItem(setMetaInItem(new ItemStack(Material.WORKBENCH), "§e"+file.getName().substring(0, file.getName().length()-5), Arrays.asList(
							"§7» §cTitre de l'Host : §f"+obj.get("NAME").getAsString(),
							"§7» §cMode de Jeu : §f"+(obj.get("GAMEMODE").getAsString().equals("UHC") ? "UHC" : obj.get("MODE_NAME").getAsString()),
							"§7» §cSlots : §f"+obj.get("SLOT").getAsInt(),
							"",
							"§7» §cInvincibilité : §f"+new DecimalFormat("00").format(obj.get("INVINCIBILITY_TIMER").getAsInt()/60)+"min"+new DecimalFormat("00").format(obj.get("INVINCIBILITY_TIMER").getAsInt()%60),
							"§7» §cPvP : §f"+new DecimalFormat("00").format(obj.get("PVP_TIMER").getAsInt()/60)+"min"+new DecimalFormat("00").format(obj.get("PVP_TIMER").getAsInt()%60),
							"§7» §cBordure : §f"+new DecimalFormat("##00").format(obj.get("BORDER_TIMER").getAsInt()/60)+"min"+new DecimalFormat("00").format(obj.get("BORDER_TIMER").getAsInt()%60),
							"§7» §cTaille initial : §f"+new DecimalFormat("##00.0").format(obj.get("BORDER_SIZE").getAsDouble())+"+/-",
							"§7» §cTaille finale : §f"+new DecimalFormat("##00.0").format(obj.get("BORDER_FINAL_SIZE").getAsDouble())+"+/-",
							"§7» §cVitesse : §f"+new DecimalFormat("##0.0").format(obj.get("BORDER_SPEED").getAsDouble())+" bloc(s)/s",
							"",
							"§7» §fClic gauche pour §aappliquer la configuration",
							"§7» §fClic droit pour §csupprimer la configuration"
							)));
				} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
					e.printStackTrace();
				}
		}
	}
	
	private void updateServerConfiguration() {
		
	}
	
	private void load(String name) {
		File repertoire = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+APIPlugin.getInstance().getAPI().s.HOST.toString());
		File[] files = repertoire.listFiles();
		if(files == null)
			return;
		FileUtils.loadConfig(APIPlugin.getInstance().getAPI().s.HOST, name.substring(2));
	}
	
	private void saveConfig(Player player, String name) {
		File file = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+APIPlugin.getInstance().getAPI().s.HOST.toString()+File.separator+name+".json");
		File fileStuff = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+APIPlugin.getInstance().getAPI().s.HOST.toString()+File.separator+name+".yml");
		if(!new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs").exists())
			new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs").mkdirs();
		File repertoire = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+APIPlugin.getInstance().getAPI().s.HOST.toString());
		if(!repertoire.exists())
			repertoire.mkdirs();
		File[] files = repertoire.listFiles();
		if(files == null || files.length < 21) {
			FileUtils.save(file, Serializer.serialize(APIPlugin.getInstance().getAPI().s));
			FileUtils.saveStuff(fileStuff, (StuffManager) APIPlugin.getInstance().getAPI().getStuffManager());
			player.sendMessage("§7▏ §aConfiguration sauvegardée avec succès.");
		}else {
			player.sendMessage("§7▏ §cVous avez atteint la limite de sauvegarde de configuration.");
		}
	}
	
	private void erase(Player player, String name) {
		File repertoire = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+APIPlugin.getInstance().getAPI().s.HOST.toString());
		File[] files = repertoire.listFiles();
		if(files == null)
			return;
		File file = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+APIPlugin.getInstance().getAPI().s.HOST.toString()+File.separator+name.substring(2)+".json");
		File fileStuff = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+APIPlugin.getInstance().getAPI().s.HOST.toString()+File.separator+name.substring(2)+".yml");
		if(file.delete() && fileStuff.delete())
			player.sendMessage("§7▏ §aLa Configuration a été effacé avec succès.");
		else
			player.sendMessage("§7▏ §cEchec lors de l'effacement de la Configuration.");
	}
}
