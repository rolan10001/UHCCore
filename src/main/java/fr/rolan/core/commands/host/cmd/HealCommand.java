package fr.rolan.core.commands.host.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.rolan.api.commands.Commands;

public class HealCommand implements Commands {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		for(Player players : Bukkit.getOnlinePlayers())
			players.setHealth(players.getMaxHealth());
		Bukkit.broadcastMessage("§8[§eUHC§8] §r§7Tous les joueurs ont été §dheal §7!");
		return false;
	}

}