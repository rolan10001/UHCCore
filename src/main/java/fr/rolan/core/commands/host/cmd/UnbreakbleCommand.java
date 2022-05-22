package fr.rolan.core.commands.host.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rolan.api.commands.Commands;

public class UnbreakbleCommand implements Commands {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player))
			return true;
		Player player = (Player)sender;
		if(player.getItemInHand() != null) {
			ItemMeta meta = player.getItemInHand().getItemMeta();
			meta.spigot().setUnbreakable(true);
			player.getItemInHand().setItemMeta(meta);
			player.sendMessage("§7§l▏ §aL'item est désormais incassable.");
		}
		return false;
	}

}