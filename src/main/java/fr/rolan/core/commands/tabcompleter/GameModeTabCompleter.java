package fr.rolan.core.commands.tabcompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class GameModeTabCompleter implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 1) {
			ArrayList<String> list = new ArrayList<String>(Arrays.asList("survival", "creative", "spectator", "adventure"));
			ArrayList<String> finallist = new ArrayList<String>();
			for(String string : list) {
				if(string.startsWith(args[0])) {
					finallist.add(string);
				}
			}
			return finallist;
		}
		
		return null;
	}
	
}