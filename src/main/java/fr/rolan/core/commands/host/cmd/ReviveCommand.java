package fr.rolan.core.commands.host.cmd;

import static fr.rolan.api.game.GameSettings.*;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.rolan.api.commands.Commands;
import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;

public class ReviveCommand implements Commands {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length < 2)
			return true;
		if(Bukkit.getPlayer(args[1]) == null)
			return true;
		Player player = Bukkit.getPlayer(args[1]);
		IUser user = APIPlugin.getInstance().getAPI().getUser(player.getUniqueId());
		if(!user.isDied())
			return true;
		Bukkit.broadcastMessage("§8[§c§lInfo§8] §c"+player.getName()+" §7a été ressuscité !");
		PLAYERS++;
		PLAYERS_LIST.add(player.getUniqueId());
		user.setDied(false);
		user.setSpectator(false);
		user.setDiamondArmor(user.getArmorDeath());
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(user.getDeathLoc());
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*30, 255, false, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*30, 0, false, false));
		player.getInventory().setContents(user.getDeathStuff());
		player.getInventory().setArmorContents(user.getDeathArmorStuff());
		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.setSaturation(30);
		return false;
	}

}
