package fr.rolan.core.commands.host.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.rolan.api.commands.Commands;
import fr.rolan.core.APIPlugin;

public class AddCommand implements Commands {
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length != 2)
			return true;
		if(Bukkit.getOfflinePlayer(args[1]) == null)
			return true;
		if(APIPlugin.getInstance().getAPI().s.COHOST.contains(Bukkit.getOfflinePlayer(args[1]).getUniqueId()))
			return true;
		if(APIPlugin.getInstance().getAPI().s.HOST.equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId()))
			return true;
		APIPlugin.getInstance().getAPI().s.COHOST.add(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
		Bukkit.broadcastMessage("§8[§eUHC§8] §c"+Bukkit.getOfflinePlayer(args[1]).getName()+" §fest désormais co-host de la partie.");
		if(Bukkit.getOfflinePlayer(args[1]).isOnline()) {
			APIPlugin.getInstance().getAPI().getPermissions().updatePermissionsForPlayer(Bukkit.getPlayer(args[1]));
			APIPlugin.getInstance().getAPI().getLobbyManager().setPlayerLobby(Bukkit.getPlayer(args[1]));
		}
		return false;
	}
}
