package fr.rolan.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player)sender;
		if(args.length == 0) {
			player.sendMessage("§e/gamemode§r <mode> <joueur>");
			return true;
		}
		if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
			if(args.length >= 2) {
				if(args[1].equals("@a")) {
					for(Player players : Bukkit.getOnlinePlayers()) {players.setGameMode(GameMode.SURVIVAL); players.sendMessage("Le gamemode a été mis à jour.");}
				}else if(Bukkit.getPlayer(args[1]) != null) {
					Bukkit.getPlayer(args[1]).setGameMode(GameMode.SURVIVAL);
					Bukkit.getPlayer(args[1]).sendMessage("Le gamemode a été mis à jour.");
					player.sendMessage("Le gamemode de "+args[1]+" a été mis à jour.");
				}
				return true;
			}
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage("Le gamemode a été mis à jour.");
		}else if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
			if(args.length >= 2) {
				if(args[1].equals("@a")) {
					for(Player players : Bukkit.getOnlinePlayers()) {players.setGameMode(GameMode.CREATIVE); players.sendMessage("Le gamemode a été mis à jour.");}
				}else if(Bukkit.getPlayer(args[1]) != null) {
					Bukkit.getPlayer(args[1]).setGameMode(GameMode.CREATIVE);
					Bukkit.getPlayer(args[1]).sendMessage("Le gamemode a été mis à jour.");
					player.sendMessage("Le gamemode de "+args[1]+" a été mis à jour.");
				}
				return true;
			}
			player.setGameMode(GameMode.CREATIVE);
			player.sendMessage("Le gamemode a été mis à jour.");
		}else if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
			if(args.length >= 2) {
				if(args[1].equals("@a")) {
					for(Player players : Bukkit.getOnlinePlayers()) {players.setGameMode(GameMode.ADVENTURE); players.sendMessage("Le gamemode a été mis à jour.");}
				}else if(Bukkit.getPlayer(args[1]) != null) {
					Bukkit.getPlayer(args[1]).setGameMode(GameMode.ADVENTURE);
					Bukkit.getPlayer(args[1]).sendMessage("Le gamemode a été mis à jour.");
					player.sendMessage("Le gamemode de "+args[1]+" a été mis à jour.");
				}
				return true;
			}
			player.setGameMode(GameMode.ADVENTURE);
			player.sendMessage("Le gamemode a été mis à jour.");
		}else if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator")) {
			if(args.length >= 2) {
				if(args[1].equals("@a")) {
					for(Player players : Bukkit.getOnlinePlayers()) {players.setGameMode(GameMode.SPECTATOR); players.sendMessage("Le gamemode a été mis à jour.");}
				}else if(Bukkit.getPlayer(args[1]) != null) {
					Bukkit.getPlayer(args[1]).setGameMode(GameMode.SPECTATOR);
					Bukkit.getPlayer(args[1]).sendMessage("Le gamemode a été mis à jour.");
					player.sendMessage("Le gamemode de "+args[1]+" a été mis à jour.");
				}
				return true;
			}
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("Le gamemode a été mis à jour.");
		}
		
		return true;
	}
}