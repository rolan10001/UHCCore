package fr.rolan.core.commands.host.cmd;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.rolan.api.commands.Commands;
import fr.rolan.api.game.enums.GameState;
import fr.rolan.core.APIPlugin;

public class SelectHostCommand implements Commands {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("*"))
			return true;
		if(args.length != 2)
			return true;
		if(Bukkit.getOfflinePlayer(args[1]) == null)
			return true;
		if(APIPlugin.getInstance().getAPI().s.HOST != null && Bukkit.getOfflinePlayer(APIPlugin.getInstance().getAPI().s.HOST).isOnline()) {
			UUID uuid = APIPlugin.getInstance().getAPI().s.HOST;
			APIPlugin.getInstance().getAPI().s.HOST = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
			APIPlugin.getInstance().getAPI().getPermissions().updatePermissionsForPlayer(Bukkit.getPlayer(uuid));
			if(APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.LOBBY))
				APIPlugin.getInstance().getAPI().getLobbyManager().setPlayerLobby(Bukkit.getPlayer(uuid));
		}
		if(APIPlugin.getInstance().getAPI().s.HOST == null)
			APIPlugin.getInstance().getAPI().s.HOST = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
		Bukkit.broadcastMessage("§8[§eUHC§8] §c"+Bukkit.getOfflinePlayer(args[1]).getName()+" §fest désormais l'host de la partie.");
		if(Bukkit.getOfflinePlayer(args[1]).isOnline()) {
			APIPlugin.getInstance().getAPI().getPermissions().updatePermissionsForPlayer(Bukkit.getPlayer(args[1]));
			if(APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.LOBBY))
				APIPlugin.getInstance().getAPI().getLobbyManager().setPlayerLobby(Bukkit.getPlayer(args[1]));
		}
		return false;
	}

}
