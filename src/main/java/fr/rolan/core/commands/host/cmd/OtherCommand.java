package fr.rolan.core.commands.host.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.rolan.api.commands.Commands;
import fr.rolan.api.events.HostCommandEvent;

public class OtherCommand implements Commands {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Bukkit.getPluginManager().callEvent(new HostCommandEvent(sender, cmd, label, args));
		
		return false;
	}

}
