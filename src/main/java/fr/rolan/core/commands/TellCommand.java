package fr.rolan.core.commands;

import java.util.HashMap;
import java.util.UUID;

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

public class TellCommand implements CommandExecutor {
	
	private HashMap<UUID, UUID> lastMSG = new HashMap<UUID, UUID>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return true;
		
		Player player = (Player) sender;
		
		if(cmd.getName().equals("tell")) {
			if(args.length == 0) {
				player.sendMessage("§e/message§r <username> <message...>");
				return true;
			}else if(args.length == 1) {
				player.sendMessage("§e/message§r <username> <message...>");
				return true;
			}else if(args.length >= 2) {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					StringBuilder bc = new StringBuilder();
					for(String part : args) {
						if(!part.equals(args[0])) {
							bc.append(part + " ");
						}
					}
					player.sendMessage("§6Envoyé à §7"+target.getName()+"§8: §e"+bc.toString());
					TextComponent msg = new TextComponent("§6De §7"+player.getName()+"§8: §e"+bc.toString());
					msg.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§8§l » §eCliquez pour répondre à §7"+player.getName()+"§e.").create()));
					msg.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/r "));
					target.spigot().sendMessage(msg);
					lastMSG.put(player.getUniqueId(), target.getUniqueId());
					lastMSG.put(target.getUniqueId(), player.getUniqueId());
				}else player.sendMessage("§7§l▏ §cCe joueur n'existe pas ou n'est pas connecté.");
			}
		}else {
			if(args.length == 0) {
				player.sendMessage("§e/reply§r <message...>");
				return true;
			}else if(args.length >= 1) {
				if(lastMSG.containsKey(player.getUniqueId())) {
					if(Bukkit.getOfflinePlayer(lastMSG.get(player.getUniqueId())).isOnline()) {
						Player target = Bukkit.getPlayer(lastMSG.get(player.getUniqueId()));
						StringBuilder bc = new StringBuilder();
						for(String part : args) {
							bc.append(part + " ");
						}
						player.sendMessage("§6Envoyé à §7"+target.getName()+"§8: §e"+bc.toString());
						TextComponent msg = new TextComponent("§6De §7"+player.getName()+"§8: §e"+bc.toString());
						msg.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§8§l » §eCliquez pour répondre à §7"+player.getName()+"§e.").create()));
						msg.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/r "));
						target.spigot().sendMessage(msg);
						lastMSG.put(player.getUniqueId(), target.getUniqueId());
						lastMSG.put(target.getUniqueId(), player.getUniqueId());
					}else player.sendMessage("§7§l▏ §cCe joueur n'existe pas ou n'est pas connecté.");
				}else player.sendMessage("§7§l▏ §cVous n'avez personne à qui répondre.");
			}
		}
		
		return true;
	}

}