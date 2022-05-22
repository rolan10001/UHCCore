package fr.rolan.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.papermc.lib.PaperLib;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MoveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player)sender;
		if(args.length == 1) {
			try {
				PaperLib.teleportAsync(player, new Location(Bukkit.getWorld(args[0]), Bukkit.getWorld(args[0]).getSpawnLocation().getX(), Bukkit.getWorld(args[0]).getSpawnLocation().getY(), Bukkit.getWorld(args[0]).getSpawnLocation().getZ()));	
			}catch(Exception e) {
				player.sendMessage("§7§l▏ §cMonde inconnu.");
				return true;
			}
			if(args[0].equalsIgnoreCase("Evaluation")) {
				player.sendMessage("§7Vous pouvez téléporter aux lieux suivants:");
				TextComponent evaluation = new TextComponent("§8§l» §aJeu de l'Evaluation");
				evaluation.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/tp "+player.getName()+" 9855 107 10001"));
				TextComponent jump = new TextComponent("§8§l» §eJump");
				jump.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/tp "+player.getName()+" 9561.5 37.5 9479.5"));
				TextComponent labyrinthe = new TextComponent("§8§l» §6Labyrinthe");
				labyrinthe.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/tp "+player.getName()+" 8944.5 105.5 9201.5"));
				TextComponent dropper = new TextComponent("§8§l» §bDropper");
				dropper.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/tp "+player.getName()+" 10300.5 256 10019.5"));
				TextComponent arene = new TextComponent("§8§l» §cArène");
				arene.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/tp "+player.getName()+" 7900.0 22 8082.0"));
				TextComponent arc = new TextComponent("§8§l» §7Tir à l'Arc");
				arc.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/tp "+player.getName()+" 8474.5 36 8538.5"));
				player.spigot().sendMessage(evaluation);
				player.spigot().sendMessage(jump);
				player.spigot().sendMessage(labyrinthe);
				player.spigot().sendMessage(dropper);
				player.spigot().sendMessage(arene);
				player.spigot().sendMessage(arc);
			}
		}else {
			player.sendMessage("§e/move §f<worldname>");
		}
		
		return true;
	}

}