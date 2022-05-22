package fr.rolan.core.commands;

import java.util.Arrays;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rolan.api.game.enums.GameState;
import fr.rolan.core.APIPlugin;

public class ConfirmCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player) || !APIPlugin.getInstance().getAPI().getGameManager().isState(GameState.LOBBY)) {
			return true;
		}
		Player player = (Player)sender;
		if(args.length == 0) return true;
		
		if(args[0].equalsIgnoreCase("start") && player.hasPermission("host.use")) {
			APIPlugin.getInstance().getAPI().getStuffManager().setStuffStart(player.getInventory().getContents());
			APIPlugin.getInstance().getAPI().getStuffManager().setStuffArmorStart(player.getInventory().getArmorContents());
		}else if(args[0].equalsIgnoreCase("death") && player.hasPermission("host.use")) {
			APIPlugin.getInstance().getAPI().getStuffManager().setStuffDeath(player.getInventory().getContents());
			APIPlugin.getInstance().getAPI().getStuffManager().setStuffArmorDeath(player.getInventory().getArmorContents());
		}/*else if(args[0].equalsIgnoreCase("kit")) {
			ArenaSettings.ARENA_KIT.put(player.getUniqueId(), player.getInventory().getContents());
			Main.INSTANCE.getConfig().set("kit."+player.getUniqueId(), player.getInventory().getContents());
			Main.INSTANCE.saveConfig();
		}*/
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] {null, null, null, null});
		player.setGameMode(GameMode.ADVENTURE);
		if(player.hasPermission("host.use")) {
			ItemStack it = new ItemStack(Material.COMMAND);
			ItemMeta meta = it.getItemMeta();
			meta.setDisplayName("§a§lConfigurez la partie");
			meta.setLore(Arrays.asList("", "§7Accéder au menu de configuration.", "", "§8§l» §eClic droit pour ouvrir le menu"));
			meta.addEnchant(Enchantment.DURABILITY, 5, false);
			it.setItemMeta(meta);
			player.getInventory().setItem(0, it);
		}
		if(APIPlugin.getInstance().getAPI().s.TEAM) {
			ItemStack team = new ItemStack(Material.BANNER, 1, (byte) 15);
			ItemMeta teamM = team.getItemMeta();
			teamM.setDisplayName("§eTeam");
			team.setItemMeta(teamM);
			player.getInventory().setItem(4, team);
		}
		ItemStack a = new ItemStack(Material.GOLD_SWORD);
		ItemMeta am = a.getItemMeta();
		am.setDisplayName("§bArena");
		am.setLore(Arrays.asList("", "§7Se téléporter à l'Arena.", "", "§8§l» §eClic droit pour se téléporter"));
		am.addEnchant(Enchantment.DURABILITY, 5, false);
		a.setItemMeta(am);
		player.getInventory().setItem(8, a);
		
		return true;
	}
	
}