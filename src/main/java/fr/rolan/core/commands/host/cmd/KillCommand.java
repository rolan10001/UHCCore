package fr.rolan.core.commands.host.cmd;

import static fr.rolan.api.game.GameSettings.*;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.rolan.api.commands.Commands;
import fr.rolan.api.events.KillCommandEvent;
import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;

public class KillCommand implements Commands {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length < 2)
			return true;
		if(Bukkit.getOfflinePlayer(args[1]) == null)
			return true;
		OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
		Bukkit.broadcastMessage("§8[§c§lInfo§8] §c"+target.getName()+" §7a été retiré de la partie !");
		PLAYERS_LIST.remove(target.getUniqueId());
		PLAYERS--;
		IUser user = APIPlugin.getInstance().getAPI().getUser(target.getUniqueId());
		user.setDied(true);
		user.setSpectator(true);
		user.setDiamondArmor(0);
		if(target.isOnline()) {
			Player p = target.getPlayer();
			for(ItemStack it : p.getInventory().getContents())
				 if(it != null && !it.getType().equals(Material.AIR))
					 p.getWorld().dropItem(p.getLocation(), it);
			for(ItemStack it : p.getInventory().getArmorContents())
				 if(it != null && !it.getType().equals(Material.AIR))
					 p.getWorld().dropItem(p.getLocation(), it);
			p.getInventory().clear();
			p.getInventory().setArmorContents(new ItemStack[] {new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR),new ItemStack(Material.AIR)});
			p.setGameMode(GameMode.SPECTATOR);
			p.setHealth(20);
			p.setMaxHealth(20);
		}
		Bukkit.getPluginManager().callEvent(new KillCommandEvent(sender, cmd, label, args));
		return false;
	}

}
