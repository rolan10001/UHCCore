package fr.rolan.core.api.game.scenario;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import fr.rolan.api.game.Settings;
import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;

public class DiamondLimit implements Listener {
	
	private Settings s;
	
	public DiamondLimit(Settings s) {
		this.s = s;
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		IUser user = APIPlugin.getInstance().getAPI().getUser(player);
		if(block.getType().equals(Material.DIAMOND_ORE) && !player.getGameMode().equals(GameMode.CREATIVE)) {
			if(user.getDiamondlimit() < s.DIAMONDLIMIT_SIZE) {
				user.addDiamondlimit(1);
				player.sendMessage("§7§l▏ §bDiamondLimit: §l"+user.getDiamondlimit()+"§b/§l"+s.DIAMONDLIMIT_SIZE);
			}else {
				event.setCancelled(true);
				block.setType(Material.AIR);
				if(s.DROP_GOLD_AFTER_LIMIT) {
					block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(8);
					block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.GOLD_INGOT));
				}
				player.sendMessage("§7§l▏ §cDiamond Limit déjà atteinte.");
			}
		}
	}
}
