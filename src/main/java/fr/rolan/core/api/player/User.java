package fr.rolan.core.api.player;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import fr.rolan.api.database.IPlayerData;
import fr.rolan.api.game.scenario.superheroes.SuperHeroes;
import fr.rolan.api.game.team.Teams;
import fr.rolan.api.player.IUser;
import fr.rolan.core.database.PlayerData;

public class User implements IUser {
	private final UUID uuid;
	private boolean arena = false;
	private int killstreak = 0;
	private int armor = 0;
	private Teams team = Teams.DEFAULT;
	private int diamondlimit = 0;
	private boolean died = false;
	private boolean spectator = false;
	private SuperHeroes superheroes;
	private ItemStack[] deathStuff;
	private ItemStack[] deathArmorStuff;
	private Location deathLoc;
	private int armorDeath;
	
	public User(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getUUID() {
		return uuid;
	}

	public boolean isInArena() {
		return arena;
	}

	public void setInArena(boolean arena) {
		this.arena = arena;
	}

	public int getKillStreak() {
		return killstreak;
	}

	public void setKillStreak(int killstreak) {
		this.killstreak = killstreak;
	}
	
	public IPlayerData getPlayerData() {
		return new PlayerData(uuid);
	}
	
	public int getKill() {
		return new PlayerData(uuid).getKill();
	}
	
	public int getDeath() {
		return new PlayerData(uuid).getDeath();
	}
	
	public void addKill(int kill) {
		new PlayerData(uuid).addKill(kill);
	}
	
	public void addDeath(int death) {
		new PlayerData(uuid).addDeath(death);
	}
	
	public void addHighestKillStreak(int kill) {
		new PlayerData(uuid).addKillStreak(kill);
	}
	
	public int getHighestKillStreak() {
		return new PlayerData(uuid).getKillStreak();
	}

	public int getDiamondArmor() {
		return armor;
	}

	public void setDiamondArmor(int armor) {
		this.armor = armor;
		setArmorDeath(armor);
	}

	public Teams getTeam() {
		return team;
	}

	public void setTeam(Teams team) {
		this.team = team;
	}

	public int getDiamondlimit() {
		return diamondlimit;
	}

	public void addDiamondlimit(int diamondlimit) {
		this.diamondlimit+=diamondlimit;
	}

	public boolean isDied() {
		return died;
	}

	public void setDied(boolean died) {
		this.died = died;
	}

	public boolean isSpectator() {
		return spectator;
	}

	public void setSpectator(boolean spectator) {
		this.spectator = spectator;
	}

	public SuperHeroes getSuperHeroes() {
		return superheroes;
	}

	public void setSuperHeroes(SuperHeroes superheroes) {
		this.superheroes = superheroes;
	}

	@Override
	public ItemStack[] getDeathStuff() {
		return deathStuff;
	}

	@Override
	public ItemStack[] getDeathArmorStuff() {
		return deathArmorStuff;
	}

	@Override
	public void setDeathStuff(ItemStack[] it) {
		this.deathStuff = it;
	}

	@Override
	public void setDeathArmorStuff(ItemStack[] it) {
		this.deathArmorStuff = it;
	}
	
	@Override
	public Location getDeathLoc() {
		return deathLoc;
	}

	@Override
	public void setDeathLoc(Location deathLoc) {
		this.deathLoc = deathLoc;
	}

	@Override
	public int getArmorDeath() {
		return armorDeath;
	}

	@Override
	public void setArmorDeath(int armorDeath) {
		this.armorDeath = armorDeath;
	}
}