package fr.rolan.core.commands.host.cmd;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.rolan.api.commands.Commands;
import io.papermc.lib.PaperLib;

public class TpCommand implements Commands {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length < 2)
			return true;
		
		Player player = Bukkit.getPlayer(args[1]);
		if(player == null) {
			sender.sendMessage("§7§l▏ §cJoueur introuvable.");
			return true;
		}
		
		World world = player.getWorld();
		WorldBorder border = world.getWorldBorder();
		int x;
		int z;
		Random r = new Random();
		int tp = r.nextInt(4);
		if(tp == 0) {
			x = (int) (r.nextInt((int) border.getSize() /4)+border.getSize() /4 - 25);
			z = (int) (r.nextInt((int) border.getSize() /4)+border.getSize() /4 - 25);
		}else if(tp == 1) {
			x = (int) (r.nextInt((int) border.getSize() /4)-border.getSize() /4 + 25);
			z = (int) (r.nextInt((int) border.getSize() /4)-border.getSize() /4 + 25);
		}else if(tp == 2) {
			x = (int) (r.nextInt((int) border.getSize() /4)+border.getSize() /4 - 25);
			z = (int) (r.nextInt((int) border.getSize() /4)-border.getSize() /4 + 25);
		}else {
			x = (int) (r.nextInt((int) border.getSize() /4)-border.getSize() /4 + 25);
			z = (int) (r.nextInt((int) border.getSize() /4)+border.getSize() /4 - 25);
		}
		x+=world.getSpawnLocation().getBlockX();
		z+=world.getSpawnLocation().getBlockZ();
		PaperLib.teleportAsync(player, new Location(world, x, world.getHighestBlockYAt(x, z)+1, z));
		player.sendMessage("§8[§eUHC§8] §7Vous avez été téléporté aléatoirement.");
		sender.sendMessage("§8[§eUHC§8] §7Vous avez téléporté aléatoirement §c"+player.getName()+"§7.");
		
		return false;
	}

}