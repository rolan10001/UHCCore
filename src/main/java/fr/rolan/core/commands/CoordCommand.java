package fr.rolan.core.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.rolan.api.UHCAPI;
import fr.rolan.api.game.enums.GameState;
import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;

public class CoordCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		IUser user = UHCAPI.get().getUser(player);
		if(!APIPlugin.getInstance().getAPI().s.TEAM)
			return true;
		if(UHCAPI.get().getGameManager().isState(GameState.LOBBY) || UHCAPI.get().getGameManager().isState(GameState.STARTED) || UHCAPI.get().getGameManager().isState(GameState.TELEPORTATION))
			return true;
		if(user.isDied() || user.isSpectator())
			return true;
		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();
		for(UUID uuid : user.getTeam().getPlayers())
			if(Bukkit.getPlayer(uuid) != null)
				Bukkit.getPlayer(uuid).sendMessage("§d§lTeam §7§l▏ §"+Integer.toHexString(user.getTeam().getColor())+player.getName()+" §8» §cX: §f" + x +" §cY: §f" + y + " §cZ: §f" + z);
		return false;
	}
}
