package fr.rolan.core.commands.tabcompleter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class MoveTabCompleter implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> finallist = new ArrayList<String>();
		for(World world : Bukkit.getWorlds()) {
			list.add(world.getName());
		}
		for(String string : list) {
			if(string.startsWith(args[0])) {
				finallist.add(string);
			}
		}
		return finallist;
	}
	
}