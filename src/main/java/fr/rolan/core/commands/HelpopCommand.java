package fr.rolan.core.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;

public class HelpopCommand implements CommandExecutor {
	
	private int numéro = 0;
	private HashMap<Integer, String> playernuméro = new HashMap<Integer, String>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		if(args.length == 0) {
			sender.sendMessage("§cLa commande est /helpop <message>");
		}else if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("rep") && player.hasPermission("host.use")) {
				StringBuilder bc = new StringBuilder();
				for(String part : args)
					if(part != args[0] && part != args[1])
						bc.append(part + " ");
				Bukkit.getPlayer(playernuméro.get(Integer.valueOf(args[1]))).sendMessage("§8§l[§3§lHELPOP§8§l] §r"+player.getName()+" §8§l» §r§7"+bc.toString());
			}else {
				numéro++;
				playernuméro.put(numéro, player.getName());
				StringBuilder bc = new StringBuilder();
				for(String part : args)
					bc.append(part + " ");
				for(Player players : Bukkit.getOnlinePlayers())
					if(players.hasPermission("host.use")) {
						players.sendMessage("§8§l[§3§lHELPOP§8§l] §r§7"+bc.toString());
						TextComponent pseudo = new TextComponent("§9[Voir pseudo]");
						TextComponent rep = new TextComponent(" §b[Répondre au joueur]");
						pseudo.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§9§l"+player.getName()).create()));
						rep.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/helpop rep "+numéro+" "));
						players.spigot().sendMessage(pseudo, rep);
					}
				player.sendMessage("§8§l[§3§lHELPOP§8§l] §7Votre message a bien été envoyé, un administrateur vous répondra d'ici peu.");
			}
		}
		return false;
	}

}