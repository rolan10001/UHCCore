package fr.rolan.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import fr.rolan.api.UHCAPI;
import fr.rolan.api.database.DataBaseInfo;
import fr.rolan.api.database.IDataBaseManager;
import fr.rolan.api.game.IGameManager;
import fr.rolan.api.game.Settings;
import fr.rolan.api.game.game.IActifGameManager;
import fr.rolan.api.game.lobby.ILobbyManager;
import fr.rolan.api.game.started.IStartedManager;
import fr.rolan.api.game.team.ITeam;
import fr.rolan.api.game.teleportation.ITeleportationManager;
import fr.rolan.api.gui.GuiManager;
import fr.rolan.api.jda.IJDA;
import fr.rolan.api.permissions.IPermissions;
import fr.rolan.api.player.IUser;
import fr.rolan.api.scoreboard.IScoreboardManager;
import fr.rolan.api.stuff.IStuffManager;
import fr.rolan.core.api.game.GameManager;
import fr.rolan.core.api.game.game.ActifGameManager;
import fr.rolan.core.api.game.lobby.LobbyManager;
import fr.rolan.core.api.game.started.StartedManager;
import fr.rolan.core.api.game.team.Team;
import fr.rolan.core.api.game.teleportation.TeleportationManager;
import fr.rolan.core.api.jda.JDA;
import fr.rolan.core.api.permissions.Permissions;
import fr.rolan.core.api.stuff.StuffManager;
import fr.rolan.core.database.DataBaseManager;
import fr.rolan.core.scoreboard.ScoreboardManager;

public class ApiImplementation extends UHCAPI {
	
	private final APIPlugin plugin;
	public Settings s;
	private GuiManager guiManager;
	private final List<Listener> guis = new ArrayList<Listener>();
	private final JDA jda;
	private final DataBaseManager database;
	private final Permissions permissions;
	private final Team team;
	public final ScoreboardManager scoreboardManager;
	private final GameManager gameManager;
	private final IStuffManager stuffManager;
	private LobbyManager lobbyManager;
	private StartedManager startedManager;
	private final List<Listener> scenarios = new ArrayList<>();
	private TeleportationManager teleportationManager;
	private ActifGameManager actifGameManager;
	
	public ApiImplementation(APIPlugin plugin, net.dv8tion.jda.api.JDA jda) {
		super(plugin);
		
		this.plugin = plugin;
		
		this.s = new Settings();
		this.guiManager = new GuiManager();
		
		this.jda = new JDA(jda);
		//this.guiMenu = new GuiMenuImpl();
		this.database = new DataBaseManager("jdbc:mysql://", "localhost", DataBaseInfo.NAME, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
		this.permissions = new Permissions();
		this.team = new Team();
		this.scoreboardManager = new ScoreboardManager();
		this.gameManager = new GameManager();
		this.stuffManager = new StuffManager();
		this.lobbyManager = new LobbyManager();
	}
	
	@Override
	public APIPlugin getPlugin() {
		return plugin;
	}

	@Override
	public IJDA getJDA() {
		return jda;
	}
	
	@Override
	public IDataBaseManager getDataBase() {
		return database;
	}
	
	@Override
	public IPermissions getPermissions() {
		return permissions;
	}
	
	@Override
	public ITeam getTeam() {
		return team;
	}
	
	@Override
	public IUser getUser(UUID uuid) {
		for(IUser user : plugin.getUsers())
			if(user.getUUID().equals(uuid))
				return user;
		return null;
	}
	
	@Override
	public IUser getUser(Player player) {
		for(IUser user : plugin.getUsers())
			if(user.getUUID().equals(player.getUniqueId()))
				return user;
		return null;
	}
	
	@Override
	public Collection<IUser> getUsers() {
		return plugin.getUsers();
	}
	
	@Override
	public IScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}
	
	@Override
	public IGameManager getGameManager() {
		return gameManager;
	}
	
	@Override
	public IStuffManager getStuffManager() {
		return stuffManager;
	}
	
	@Override
	public ILobbyManager getLobbyManager() {
		return lobbyManager;
	}
	
	@Override
	public void NewLobbyManager() {
		this.lobbyManager = new LobbyManager();
	}

	@Override
	public IStartedManager getStartedManager() {
		return startedManager;
	}

	@Override
	public void NewStartedManager() {
		this.startedManager = new StartedManager();
	}
	
	@Override
	public List<Listener> getScenarios() {
		return scenarios;
	}

	@Override
	public ITeleportationManager getTeleportationManager() {
		return teleportationManager;
	}

	@Override
	public void NewTeleportationManager() {
		this.teleportationManager = new TeleportationManager();
	}
	
	@Override
	public IActifGameManager getActifGameManager() {
		return actifGameManager;
	}
	
	@Override
	public void NewActifGameManager() {
		this.actifGameManager = new ActifGameManager();
	}

	@Override
	public Settings getSettings() {
		return s;
	}
	
	@Override
	public GuiManager getGuiManager() {
		return guiManager;
	}

	@Override
	public List<Listener> getGuis() {
		return guis;
	}
}
