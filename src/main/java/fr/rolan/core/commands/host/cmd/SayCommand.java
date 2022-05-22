package fr.rolan.core.commands.host.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.rolan.api.commands.Commands;
import fr.rolan.core.APIPlugin;

public class SayCommand implements Commands {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) 
			return true;
		
		Player player = (Player)sender;
		StringBuilder bc = new StringBuilder();
		for(String part : args) {
			if(!part.equals(args[0])) {
				bc.append(part + " ");
			}
		}
		Bukkit.broadcastMessage("§8§m------------------------------");
		Bukkit.broadcastMessage((APIPlugin.getInstance().getAPI().getPermissions().isAdmin(player.getUniqueId()) ? "§c§lADMIN ▏ §c" : APIPlugin.getInstance().getAPI().getPermissions().isStaff(player.getUniqueId()) ? "§a§lSTAFF ▏ §a" : "§e§lHOST ▏ §e")+player.getName()+" §8§l▶ §e§l"+bc.toString());
		Bukkit.broadcastMessage("§8§m------------------------------");
		return false;
	}
}