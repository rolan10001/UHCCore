package fr.rolan.core.commands.host.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.rolan.api.commands.Commands;

public class Effect implements Commands {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		player.getWorld().playEffect(player.getLocation(), org.bukkit.Effect.valueOf(args[1]), 1000000);
		return false;
	}

}