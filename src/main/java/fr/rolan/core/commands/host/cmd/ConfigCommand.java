package fr.rolan.core.commands.host.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.rolan.api.commands.Commands;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.gui.Menu;

public class ConfigCommand implements Commands {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) 
			return true;
		
		Player player = (Player)sender;
		
		if(APIPlugin.getInstance().getAPI().getGuiManager().MENU == null)
			new Menu();
		player.openInventory(APIPlugin.getInstance().getAPI().getGuiManager().MENU);
		
		return false;
	}

}
