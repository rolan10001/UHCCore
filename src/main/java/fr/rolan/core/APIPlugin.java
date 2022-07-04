package fr.rolan.core;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.rolan.api.database.DataBaseInfo;
import fr.rolan.api.game.GameSettings;
import fr.rolan.api.game.enums.GameState;
import fr.rolan.api.player.IUser;
import fr.rolan.core.api.player.User;
import fr.rolan.core.commands.ConfirmCommand;
import fr.rolan.core.commands.CoordCommand;
import fr.rolan.core.commands.GameModeCommand;
import fr.rolan.core.commands.HelpCommand;
import fr.rolan.core.commands.HelpopCommand;
import fr.rolan.core.commands.MoveCommand;
import fr.rolan.core.commands.TellCommand;
import fr.rolan.core.commands.UHCCommand;
import fr.rolan.core.commands.host.HostCommand;
import fr.rolan.core.commands.tabcompleter.GameModeTabCompleter;
import fr.rolan.core.commands.tabcompleter.MoveTabCompleter;
import fr.rolan.tools.armor.ArmorEquipEvent;
import fr.rolan.tools.armor.ArmorListener;
import fr.rolan.tools.pregeneration.EventChunkUnload;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class APIPlugin extends JavaPlugin implements Listener {
	
	private static APIPlugin instance;
	private ApiImplementation api;
	private final FileConfiguration botConfig = new YamlConfiguration();
	private final Map<UUID, IUser> users = new HashMap<UUID, IUser>();
	private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
	
	public static APIPlugin getInstance() {
		return instance;
	}
	
	public void log(String message) {
		getLogger().info(message);
	}
	
	public void log(Level level, String message) {
		getLogger().log(level, message);
	}
	
	public ApiImplementation getAPI() {
		return this.api;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		File botFile = new File(getDataFolder(), "bot.yml");
		if(!botFile.exists()) {
			botFile.getParentFile().mkdirs();
			saveResource("bot.yml", false);
		}
		try {
			botConfig.load(botFile);
		}catch(IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		this.saveDefaultConfig();
		
		Bukkit.setWhitelist(true);
		new WorldCreator("Host").createWorld();
		getCommand("host").setExecutor((CommandExecutor) new HostCommand());
		getCommand("gamemode").setExecutor(new GameModeCommand());
        getCommand("gamemode").setTabCompleter(new GameModeTabCompleter());
        TellCommand TellCommands = new TellCommand();
        getCommand("tell").setExecutor(TellCommands);
        getCommand("r").setExecutor(TellCommands);
        getCommand("helpop").setExecutor(new HelpopCommand());
        getCommand("move").setExecutor(new MoveCommand());
        getCommand("move").setTabCompleter(new MoveTabCompleter());
        getCommand("confirm").setExecutor(new ConfirmCommand());
        UHCCommand uhcCommand = new UHCCommand();
        Bukkit.getPluginManager().registerEvents(uhcCommand, this);
        getCommand("uhc").setExecutor(uhcCommand);
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("coord").setExecutor(new CoordCommand());
        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new EventChunkUnload(), this);
        
		try {
			this.api = new ApiImplementation(instance, JDABuilder.createDefault(DataBaseInfo.TOKEN).setActivity(Activity.watching("https://www.dxduhc.com")).setAutoReconnect(true).build().awaitReady());
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
		
		new ArmorListener(getConfig().getStringList("blocked"));
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			users.putIfAbsent(player.getUniqueId(), new User(player.getUniqueId()));
			getAPI().getTeam().updatePlayer(player);
			getAPI().scoreboardManager.onLogin(player);
			getAPI().getPermissions().updatePermissionsForPlayer(player);
			getAPI().getLobbyManager().setPlayerLobby(player);
		}
		getAPI().getScoreboardManager().setScoreboard(getAPI().s.NAME, getAPI().getLobbyManager().setLobbyScoreboard());
		
		log("#==========[WELCOME TO UHC API]==========#");
		log("# UHCAPI is now loading. Please read     #");
		log("# carefully all outputs coming from it.  #");
		log("#========================================#");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onDisable() {
		getAPI().scoreboardManager.onDisable();
		getAPI().getJDA().getJDA().getRegisteredListeners().forEach(listener -> getAPI().getJDA().getJDA().removeEventListener(listener));
	}
	
	public FileConfiguration getBotConfig() {
		return this.botConfig;
	}
	
	public void saveBotConfig() {
    	try {
			botConfig.save(new File(getDataFolder(), "bot.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }
 
    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

	public Collection<IUser> getUsers() {
		return users.values();
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if(player.hasPermission("host.use")) {
			event.setResult(Result.ALLOWED);
			return;
		}
		if(!player.isWhitelisted() && Bukkit.getServer().hasWhitelist()) {
			event.setResult(Result.KICK_WHITELIST);
			event.setKickMessage("§6§l» §e§l"+getAPI().getSettings().NAME+" §6§l«\n\n§c§lVous n'êtes pas whitelist sur le serveur.\n\n§7Une erreur ? Contactez l'host de la partie si ce n'est pas normal.");
		}else if(GameSettings.PLAYERS >= getAPI().s.SLOT) {
			event.setResult(Result.KICK_OTHER);
			event.setKickMessage("§6§l» §e§l"+getAPI().getSettings().NAME+" §6§l«\n\n§c§lLe serveur est full.\n\n§7Une erreur ? Contactez l'host de la partie si ce n'est pas normal.");
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		users.putIfAbsent(player.getUniqueId(), new User(player.getUniqueId()));
		getAPI().getTeam().updatePlayer(player);
		getAPI().scoreboardManager.onLogin(player);
		getAPI().getPermissions().updatePermissionsForPlayer(player);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		getAPI().scoreboardManager.onLogout(player);
	}
	
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		if(!(event.getEntity() instanceof Creature) && !(event.getEntity() instanceof Slime))
			return;
		if(!event.getEntity().getWorld().getName().equals("Lobby") && !event.getEntity().getWorld().getName().equals("Evaluation"))
			return;
		if(event.getEntity().getWorld().getName().equals("Evaluation") && event.getEntity().getLocation().getZ() < 8190 && event.getEntity().getLocation().getZ() > 8000 && event.getEntity().getLocation().getX() < 8010 && event.getEntity().getLocation().getX() > 7800)
			return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent event) {
		if(getAPI().getGameManager().isState(GameState.LOBBY)) return;
		Player player = event.getPlayer();
		IUser user = getAPI().getUser(player);
		if(event.getNewArmorPiece() != null && event.getNewArmorPiece().getType().name().startsWith("DIAMOND")) {
			if(user.getDiamondArmor() >= getAPI().s.ARMOR) {
				event.setCancelled(true);
			}else {
				user.setDiamondArmor(user.getDiamondArmor()+1);
			}
		}else if(event.getOldArmorPiece() != null && event.getOldArmorPiece().getType().name().startsWith("DIAMOND")) {
			user.setDiamondArmor(user.getDiamondArmor()-1);
		}
	}
	
	@EventHandler
	public void onBrew(BrewEvent event) {
		if(getAPI().getGameManager().isState(GameState.LOBBY)) return;
		if(!getAPI().s.ALLPOTION) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.LENGTHEN) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.SPLASH) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.AMPLIFIED) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.GHAST_TEAR) && !getAPI().s.REGENERATION) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.SUGAR) && !getAPI().s.SPEED) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.MAGMA_CREAM) && !getAPI().s.FIRE_RESISTANCE) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.SPIDER_EYE) && !getAPI().s.POISON) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.SPECKLED_MELON) && !getAPI().s.HEAL) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.GOLDEN_CARROT) && !getAPI().s.NIGHT_VISION) {event.setCancelled(true); return;}
		if(((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() == null) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() == null) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() == null)) && event.getContents().getIngredient().getType().equals(Material.FERMENTED_SPIDER_EYE) && !getAPI().s.WEAKNESS) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.BLAZE_POWDER) && !getAPI().s.STRENGTH) {event.setCancelled(true); return;}
		if(((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && (Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.SPEED) || Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.JUMP))) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && (Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.SPEED) || Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.JUMP))) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && (Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.SPEED) || Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.JUMP)))) && event.getContents().getIngredient().getType().equals(Material.FERMENTED_SPIDER_EYE) && !getAPI().s.SLOWNESS) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.RABBIT_FOOT) && !getAPI().s.JUMP) {event.setCancelled(true); return;}
		if(((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && (Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.INSTANT_HEAL) || Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.POISON))) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && (Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.INSTANT_HEAL) || Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.POISON))) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && (Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.INSTANT_HEAL) || Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.POISON)))) && event.getContents().getIngredient().getType().equals(Material.FERMENTED_SPIDER_EYE) && !getAPI().s.DAMAGE) {event.setCancelled(true); return;}
		if(event.getContents().getIngredient().getType().equals(Material.RAW_FISH) && !getAPI().s.WATER) {event.setCancelled(true); return;}
		if(((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.NIGHT_VISION)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.NIGHT_VISION)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.NIGHT_VISION))) && event.getContents().getIngredient().getType().equals(Material.FERMENTED_SPIDER_EYE) && !getAPI().s.INVISIBILITY) {event.setCancelled(true); return;}
		if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.REGEN)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.REGEN)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.REGEN))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.REGENERATION_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.REGENERATION_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.REGENERATION_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.SPEED)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.SPEED)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.SPEED))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.SPEED_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.SPEED_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.SPEED_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.FIRE_RESISTANCE)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.FIRE_RESISTANCE)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.FIRE_RESISTANCE))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.FIRE_RESISTANCE_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.FIRE_RESISTANCE_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.FIRE_RESISTANCE_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.POISON)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.POISON)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.POISON))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.POISON_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.POISON_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.POISON_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.INSTANT_HEAL)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.INSTANT_HEAL)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.INSTANT_HEAL))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.HEAL_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.HEAL_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.HEAL_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.NIGHT_VISION)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.NIGHT_VISION)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.NIGHT_VISION))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.NIGHT_VISION_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.NIGHT_VISION_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.NIGHT_VISION_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.STRENGTH)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.STRENGTH)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.STRENGTH))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.STRENGTH_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.STRENGTH_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.STRENGTH_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.SLOWNESS)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.SLOWNESS)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.SLOWNESS))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.SLOWNESS_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.SLOWNESS_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.SLOWNESS_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.JUMP)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.JUMP)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.JUMP))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.JUMP_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.JUMP_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.JUMP_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.INSTANT_DAMAGE)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.INSTANT_DAMAGE)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.INSTANT_DAMAGE))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.DAMAGE_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.DAMAGE_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.DAMAGE_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.WATER)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.WATER)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.WATER))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.WATER_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.WATER_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.WATER_AMPLIFIED) {event.setCancelled(true); return;}
		}else if((event.getContents().getItem(1) != null && event.getContents().getItem(1).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(1)).getType() != null && Potion.fromItemStack(event.getContents().getItem(1)).getType().equals(PotionType.INVISIBILITY)) || (event.getContents().getItem(2) != null && event.getContents().getItem(2).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(2)).getType() != null && Potion.fromItemStack(event.getContents().getItem(2)).getType().equals(PotionType.INVISIBILITY)) || (event.getContents().getItem(3) != null && event.getContents().getItem(3).getType().equals(Material.POTION) && Potion.fromItemStack(event.getContents().getItem(3)).getType() != null && Potion.fromItemStack(event.getContents().getItem(3)).getType().equals(PotionType.INVISIBILITY))) {
			if(event.getContents().getIngredient().getType().equals(Material.REDSTONE) && !getAPI().s.INVISIBILITY_LENGTHEN) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.SULPHUR) && !getAPI().s.INVISIBILITY_SPLASH) {event.setCancelled(true); return;}
			if(event.getContents().getIngredient().getType().equals(Material.GLOWSTONE_DUST) && !getAPI().s.INVISIBILITY_AMPLIFIED) {event.setCancelled(true); return;}
		}
	}
	
	@EventHandler
	public void onEnchant(EnchantItemEvent event) {
		if(getAPI().getGameManager().isState(GameState.LOBBY)) return;
		if(event.getItem().getType().name().startsWith("DIAMOND")) {
			if(event.getItem().getType().name().endsWith("HELMET")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_DIAMOND_HELMET) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
							if(getAPI().s.PROTECTION_DIAMOND_HELMET != 0) meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, getAPI().s.PROTECTION_DIAMOND_HELMET, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_PROJECTILE) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_DIAMOND_HELMET) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
							if(getAPI().s.PROJ_DIAMOND_HELMET != 0) meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, getAPI().s.PROJ_DIAMOND_HELMET, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_HELMET) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_DIAMOND_HELMET != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_DIAMOND_HELMET, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.THORNS) && event.getEnchantsToAdd().get(Enchantment.THORNS) > getAPI().s.THORNS_DIAMOND_HELMET) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.THORNS);
							if(getAPI().s.THORNS_DIAMOND_HELMET != 0) meta.addEnchant(Enchantment.THORNS, getAPI().s.THORNS_DIAMOND_HELMET, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
			}else if(event.getItem().getType().name().endsWith("CHESTPLATE")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_DIAMOND_CHESTPLATE) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
							if(getAPI().s.PROTECTION_DIAMOND_CHESTPLATE != 0) meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, getAPI().s.PROTECTION_DIAMOND_CHESTPLATE, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_PROJECTILE) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_DIAMOND_CHESTPLATE) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
							if(getAPI().s.PROJ_DIAMOND_CHESTPLATE != 0) meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, getAPI().s.PROJ_DIAMOND_CHESTPLATE, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_CHESTPLATE) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_DIAMOND_CHESTPLATE != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_DIAMOND_CHESTPLATE, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.THORNS) && event.getEnchantsToAdd().get(Enchantment.THORNS) > getAPI().s.THORNS_DIAMOND_CHESTPLATE) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.THORNS);
							if(getAPI().s.THORNS_DIAMOND_CHESTPLATE != 0) meta.addEnchant(Enchantment.THORNS, getAPI().s.THORNS_DIAMOND_CHESTPLATE, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
			}else if(event.getItem().getType().name().endsWith("LEGGINGS")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_DIAMOND_LEGGINGS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
							if(getAPI().s.PROTECTION_DIAMOND_LEGGINGS != 0) meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, getAPI().s.PROTECTION_DIAMOND_LEGGINGS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_PROJECTILE) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_DIAMOND_LEGGINGS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
							if(getAPI().s.PROJ_DIAMOND_LEGGINGS != 0) meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, getAPI().s.PROJ_DIAMOND_LEGGINGS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_LEGGINGS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_DIAMOND_LEGGINGS != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_DIAMOND_LEGGINGS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.THORNS) && event.getEnchantsToAdd().get(Enchantment.THORNS) > getAPI().s.THORNS_DIAMOND_LEGGINGS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.THORNS);
							if(getAPI().s.THORNS_DIAMOND_LEGGINGS != 0) meta.addEnchant(Enchantment.THORNS, getAPI().s.THORNS_DIAMOND_LEGGINGS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
			}else if(event.getItem().getType().name().endsWith("BOOTS")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_DIAMOND_BOOTS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
							if(getAPI().s.PROTECTION_DIAMOND_BOOTS != 0) meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, getAPI().s.PROTECTION_DIAMOND_BOOTS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_PROJECTILE) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_DIAMOND_BOOTS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
							if(getAPI().s.PROJ_DIAMOND_BOOTS != 0) meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, getAPI().s.PROJ_DIAMOND_BOOTS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_BOOTS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_DIAMOND_BOOTS != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_DIAMOND_BOOTS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.THORNS) && event.getEnchantsToAdd().get(Enchantment.THORNS) > getAPI().s.THORNS_DIAMOND_BOOTS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.THORNS);
							if(getAPI().s.THORNS_DIAMOND_BOOTS != 0) meta.addEnchant(Enchantment.THORNS, getAPI().s.THORNS_DIAMOND_BOOTS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
			}else if(event.getItem().getType().name().endsWith("SWORD")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.DAMAGE_ALL) && event.getEnchantsToAdd().get(Enchantment.DAMAGE_ALL) > getAPI().s.SHARPNESS_DIAMOND_SWORD) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DAMAGE_ALL);
							if(getAPI().s.SHARPNESS_DIAMOND_SWORD != 0) meta.addEnchant(Enchantment.DAMAGE_ALL, getAPI().s.SHARPNESS_DIAMOND_SWORD, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.KNOCKBACK) && event.getEnchantsToAdd().get(Enchantment.KNOCKBACK) > getAPI().s.KNOCKBACK_DIAMOND_SWORD) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.KNOCKBACK);
							if(getAPI().s.KNOCKBACK_DIAMOND_SWORD != 0) meta.addEnchant(Enchantment.KNOCKBACK, getAPI().s.KNOCKBACK_DIAMOND_SWORD, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_SWORD) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_DIAMOND_SWORD != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_DIAMOND_SWORD, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.FIRE_ASPECT) && event.getEnchantsToAdd().get(Enchantment.FIRE_ASPECT) > getAPI().s.FIRE_ASPECT_DIAMOND_SWORD) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.FIRE_ASPECT);
							if(getAPI().s.FIRE_ASPECT_DIAMOND_SWORD != 0) meta.addEnchant(Enchantment.FIRE_ASPECT, getAPI().s.FIRE_ASPECT_DIAMOND_SWORD, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 4);
				}
			}
		}else if(event.getItem().getType().name().startsWith("IRON")) {
			if(event.getItem().getType().name().endsWith("HELMET")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_IRON_HELMET) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
							if(getAPI().s.PROTECTION_IRON_HELMET != 0) meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, getAPI().s.PROTECTION_IRON_HELMET, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_PROJECTILE) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_IRON_HELMET) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
							if(getAPI().s.PROJ_IRON_HELMET != 0) meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, getAPI().s.PROJ_IRON_HELMET, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_HELMET) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_IRON_HELMET != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_IRON_HELMET, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.THORNS) && event.getEnchantsToAdd().get(Enchantment.THORNS) > getAPI().s.THORNS_IRON_HELMET) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.THORNS);
							if(getAPI().s.THORNS_IRON_HELMET != 0) meta.addEnchant(Enchantment.THORNS, getAPI().s.THORNS_IRON_HELMET, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
			}else if(event.getItem().getType().name().endsWith("CHESTPLATE")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_IRON_CHESTPLATE) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
							if(getAPI().s.PROTECTION_IRON_CHESTPLATE != 0) meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, getAPI().s.PROTECTION_IRON_CHESTPLATE, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_PROJECTILE) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_IRON_CHESTPLATE) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
							if(getAPI().s.PROJ_IRON_CHESTPLATE != 0) meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, getAPI().s.PROJ_IRON_CHESTPLATE, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_CHESTPLATE) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_IRON_CHESTPLATE != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_IRON_CHESTPLATE, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.THORNS) && event.getEnchantsToAdd().get(Enchantment.THORNS) > getAPI().s.THORNS_IRON_CHESTPLATE) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.THORNS);
							if(getAPI().s.THORNS_IRON_CHESTPLATE != 0) meta.addEnchant(Enchantment.THORNS, getAPI().s.THORNS_IRON_CHESTPLATE, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
			}else if(event.getItem().getType().name().endsWith("LEGGINGS")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_IRON_LEGGINGS) {
					new BukkitRunnable() {
						
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
							if(getAPI().s.PROTECTION_IRON_LEGGINGS != 0) meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, getAPI().s.PROTECTION_IRON_LEGGINGS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_PROJECTILE) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_IRON_LEGGINGS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
							if(getAPI().s.PROJ_IRON_LEGGINGS != 0) meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, getAPI().s.PROJ_IRON_LEGGINGS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_LEGGINGS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_IRON_LEGGINGS != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_IRON_LEGGINGS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.THORNS) && event.getEnchantsToAdd().get(Enchantment.THORNS) > getAPI().s.THORNS_IRON_LEGGINGS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.THORNS);
							if(getAPI().s.THORNS_IRON_LEGGINGS != 0) meta.addEnchant(Enchantment.THORNS, getAPI().s.THORNS_IRON_LEGGINGS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
			}else if(event.getItem().getType().name().endsWith("BOOTS")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_IRON_BOOTS) {
					new BukkitRunnable() {
						
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
							if(getAPI().s.PROTECTION_IRON_BOOTS != 0) meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, getAPI().s.PROTECTION_IRON_BOOTS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_PROJECTILE) && event.getEnchantsToAdd().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_IRON_BOOTS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
							if(getAPI().s.PROJ_IRON_BOOTS != 0) meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, getAPI().s.PROJ_IRON_BOOTS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_BOOTS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_IRON_BOOTS != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_IRON_BOOTS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.THORNS) && event.getEnchantsToAdd().get(Enchantment.THORNS) > getAPI().s.THORNS_IRON_BOOTS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.THORNS);
							if(getAPI().s.THORNS_IRON_BOOTS != 0) meta.addEnchant(Enchantment.THORNS, getAPI().s.THORNS_IRON_BOOTS, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
			}else if(event.getItem().getType().name().endsWith("SWORD")) {
				if(event.getEnchantsToAdd().containsKey(Enchantment.DAMAGE_ALL) && event.getEnchantsToAdd().get(Enchantment.DAMAGE_ALL) > getAPI().s.SHARPNESS_IRON_SWORD) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DAMAGE_ALL);
							if(getAPI().s.SHARPNESS_IRON_SWORD != 0) meta.addEnchant(Enchantment.DAMAGE_ALL, getAPI().s.SHARPNESS_IRON_SWORD, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 1);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.KNOCKBACK) && event.getEnchantsToAdd().get(Enchantment.KNOCKBACK) > getAPI().s.KNOCKBACK_IRON_SWORD) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.KNOCKBACK);
							if(getAPI().s.KNOCKBACK_IRON_SWORD != 0) meta.addEnchant(Enchantment.KNOCKBACK, getAPI().s.KNOCKBACK_IRON_SWORD, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 2);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_SWORD) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.DURABILITY);
							if(getAPI().s.UNBREAKING_IRON_SWORD != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_IRON_SWORD, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 3);
				}
				if(event.getEnchantsToAdd().containsKey(Enchantment.FIRE_ASPECT) && event.getEnchantsToAdd().get(Enchantment.FIRE_ASPECT) > getAPI().s.FIRE_ASPECT_IRON_SWORD) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ItemMeta meta = event.getItem().getItemMeta();
							meta.removeEnchant(Enchantment.FIRE_ASPECT);
							if(getAPI().s.FIRE_ASPECT_IRON_SWORD != 0) meta.addEnchant(Enchantment.FIRE_ASPECT, getAPI().s.FIRE_ASPECT_IRON_SWORD, true);
							event.getItem().setItemMeta(meta);
						}
					}.runTaskLater(this, 4);
				}
			}
		}else if(event.getItem().getType().name().equals("BOW")) {
			if(event.getEnchantsToAdd().containsKey(Enchantment.ARROW_DAMAGE) && event.getEnchantsToAdd().get(Enchantment.ARROW_DAMAGE) > getAPI().s.POWER) {
				new BukkitRunnable() {
					@Override
					public void run() {
						ItemMeta meta = event.getItem().getItemMeta();
						meta.removeEnchant(Enchantment.ARROW_DAMAGE);
						if(getAPI().s.POWER != 0) meta.addEnchant(Enchantment.ARROW_DAMAGE, getAPI().s.POWER, true);
						event.getItem().setItemMeta(meta);
					}
				}.runTaskLater(this, 1);
			}
			if(event.getEnchantsToAdd().containsKey(Enchantment.ARROW_KNOCKBACK) && event.getEnchantsToAdd().get(Enchantment.ARROW_KNOCKBACK) > getAPI().s.PUNCH) {
				new BukkitRunnable() {
					@Override
					public void run() {
						ItemMeta meta = event.getItem().getItemMeta();
						meta.removeEnchant(Enchantment.ARROW_KNOCKBACK);
						if(getAPI().s.PUNCH != 0) meta.addEnchant(Enchantment.ARROW_KNOCKBACK, getAPI().s.PUNCH, true);
						event.getItem().setItemMeta(meta);
					}
				}.runTaskLater(this, 2);
			}
			if(event.getEnchantsToAdd().containsKey(Enchantment.DURABILITY) && event.getEnchantsToAdd().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_BOW) {
				new BukkitRunnable() {
					@Override
					public void run() {
						ItemMeta meta = event.getItem().getItemMeta();
						meta.removeEnchant(Enchantment.DURABILITY);
						if(getAPI().s.UNBREAKING_BOW != 0) meta.addEnchant(Enchantment.DURABILITY, getAPI().s.UNBREAKING_BOW, true);
						event.getItem().setItemMeta(meta);
					}
				}.runTaskLater(this, 3);
			}
			if(event.getEnchantsToAdd().containsKey(Enchantment.ARROW_FIRE) && event.getEnchantsToAdd().get(Enchantment.ARROW_FIRE) > getAPI().s.FLAME) {
				new BukkitRunnable() {
					@Override
					public void run() {
						ItemMeta meta = event.getItem().getItemMeta();
						meta.removeEnchant(Enchantment.ARROW_FIRE);
						if(getAPI().s.FLAME != 0) meta.addEnchant(Enchantment.ARROW_FIRE, getAPI().s.FLAME, true);
						event.getItem().setItemMeta(meta);
					}
				}.runTaskLater(this, 4);
			}
			if(event.getEnchantsToAdd().containsKey(Enchantment.ARROW_INFINITE) && event.getEnchantsToAdd().get(Enchantment.ARROW_INFINITE) > getAPI().s.INFINITY) {
				new BukkitRunnable() {
					@Override
					public void run() {
						ItemMeta meta = event.getItem().getItemMeta();
						meta.removeEnchant(Enchantment.ARROW_INFINITE);
						if(getAPI().s.INFINITY != 0) meta.addEnchant(Enchantment.ARROW_INFINITE, getAPI().s.INFINITY, true);
						event.getItem().setItemMeta(meta);
					}
				}.runTaskLater(this, 5);
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(getAPI().getGameManager().isState(GameState.LOBBY) || !(event.getInventory() instanceof AnvilInventory) || event.getRawSlot() != 2) return;
		int level = ((Player) event.getWhoClicked()).getLevel();
		ItemStack it = event.getCurrentItem();
		if(it.getType().name().startsWith("DIAMOND")) {
			if(it.getType().name().endsWith("HELMET")) {
				if(it.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && it.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_DIAMOND_HELMET) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.PROTECTION_PROJECTILE) && it.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_DIAMOND_HELMET) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_HELMET) {
					event.setCancelled(true);
				}
			}else if(it.getType().name().endsWith("CHESTPLATE")) {
				if(it.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && it.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_DIAMOND_CHESTPLATE) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.PROTECTION_PROJECTILE) && it.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_DIAMOND_CHESTPLATE) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_CHESTPLATE) {
					event.setCancelled(true);
				}
			}else if(it.getType().name().endsWith("LEGGINGS")) {
				if(it.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && it.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_DIAMOND_LEGGINGS) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.PROTECTION_PROJECTILE) && it.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_DIAMOND_LEGGINGS) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_LEGGINGS) {
					event.setCancelled(true);
				}
			}else if(it.getType().name().endsWith("BOOTS")) {
				if(it.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && it.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_DIAMOND_BOOTS) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.PROTECTION_PROJECTILE) && it.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_DIAMOND_BOOTS) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_BOOTS) {
					event.setCancelled(true);
				}
			}else if(it.getType().name().endsWith("SWORD")) {
				if(it.getEnchantments().containsKey(Enchantment.DAMAGE_ALL) && it.getEnchantments().get(Enchantment.DAMAGE_ALL) > getAPI().s.SHARPNESS_DIAMOND_SWORD) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.KNOCKBACK) && it.getEnchantments().get(Enchantment.KNOCKBACK) > getAPI().s.KNOCKBACK_DIAMOND_SWORD) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_DIAMOND_SWORD) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.FIRE_ASPECT) && it.getEnchantments().get(Enchantment.FIRE_ASPECT) > getAPI().s.FIRE_ASPECT_DIAMOND_SWORD) {
					event.setCancelled(true);
				}
			}
		}else if(it.getType().name().startsWith("IRON")) {
			if(it.getType().name().endsWith("HELMET")) {
				if(it.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && it.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_IRON_HELMET) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.PROTECTION_PROJECTILE) && it.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_IRON_HELMET) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_HELMET) {
					event.setCancelled(true);
				}
			}else if(it.getType().name().endsWith("CHESTPLATE")) {
				if(it.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && it.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_IRON_CHESTPLATE) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.PROTECTION_PROJECTILE) && it.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_IRON_CHESTPLATE) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_CHESTPLATE) {
					event.setCancelled(true);
				}
			}else if(it.getType().name().endsWith("LEGGINGS")) {
				if(it.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && it.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_IRON_LEGGINGS) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.PROTECTION_PROJECTILE) && it.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_IRON_LEGGINGS) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_LEGGINGS) {
					event.setCancelled(true);
				}
			}else if(it.getType().name().endsWith("BOOTS")) {
				if(it.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && it.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) > getAPI().s.PROTECTION_IRON_BOOTS) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.PROTECTION_PROJECTILE) && it.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) > getAPI().s.PROJ_IRON_BOOTS) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_BOOTS) {
					event.setCancelled(true);
				}
			}else if(it.getType().name().endsWith("SWORD")) {
				if(it.getEnchantments().containsKey(Enchantment.DAMAGE_ALL) && it.getEnchantments().get(Enchantment.DAMAGE_ALL) > getAPI().s.SHARPNESS_IRON_SWORD) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.KNOCKBACK) && it.getEnchantments().get(Enchantment.KNOCKBACK) > getAPI().s.KNOCKBACK_IRON_SWORD) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_IRON_SWORD) {
					event.setCancelled(true);
				}else if(it.getEnchantments().containsKey(Enchantment.FIRE_ASPECT) && it.getEnchantments().get(Enchantment.FIRE_ASPECT) > getAPI().s.FIRE_ASPECT_IRON_SWORD) {
					event.setCancelled(true);
				}
			}
		}else if(it.getType().name().equals("BOW")) {
			if(it.getEnchantments().containsKey(Enchantment.ARROW_DAMAGE) && it.getEnchantments().get(Enchantment.ARROW_DAMAGE) > getAPI().s.POWER) {
				event.setCancelled(true);
			}else if(it.getEnchantments().containsKey(Enchantment.ARROW_KNOCKBACK) && it.getEnchantments().get(Enchantment.ARROW_KNOCKBACK) > getAPI().s.PUNCH) {
				event.setCancelled(true);
			}else if(it.getEnchantments().containsKey(Enchantment.DURABILITY) && it.getEnchantments().get(Enchantment.DURABILITY) > getAPI().s.UNBREAKING_BOW) {
				event.setCancelled(true);
			}else if(it.getEnchantments().containsKey(Enchantment.ARROW_FIRE) && it.getEnchantments().get(Enchantment.ARROW_FIRE) > getAPI().s.FLAME) {
				event.setCancelled(true);
			}else if(it.getEnchantments().containsKey(Enchantment.ARROW_INFINITE) && it.getEnchantments().get(Enchantment.ARROW_INFINITE) > getAPI().s.INFINITY) {
				event.setCancelled(true);
			}
		}
		if(event.isCancelled()) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					((Player) event.getWhoClicked()).setLevel(level);
				}
			}.runTaskLater(this, 1);
		}
	}
}
