package fr.rolan.core.api.game.scenario;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.rolan.core.APIPlugin;

public class Timber implements Listener {
	
	public Timber() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if(block.getType().equals(Material.LOG) || block.getType().equals(Material.LOG_2)) {
			final Player p = event.getPlayer();
			Material mat = event.getBlock().getType();
			if (mat == Material.LOG || mat == Material.LOG_2) {
				final List<Block> bList = new ArrayList<>();
				final List<ItemStack> finalItems = new ArrayList<>();
				bList.add(event.getBlock());
				(new BukkitRunnable() {
					public void run() {
						for (int i = 0; i < bList.size(); i++) {
							Block block = bList.get(i);
							if (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
								List<ItemStack> items = new ArrayList<>(block.getDrops());
								block.setType(Material.AIR);
								for (ItemStack item : items)
									finalItems.add(item); 
							}  byte b; int j; BlockFace[] arrayOfBlockFace;
							for (j = (arrayOfBlockFace = BlockFace.values()).length, b = 0; b < j; ) { BlockFace face = arrayOfBlockFace[b];
							if (block.getRelative(face).getType() == Material.LOG || 
									block.getRelative(face).getType() == Material.LOG_2)
								bList.add(block.getRelative(face)); 
							b++; }
							bList.remove(block);
						} 
						if (bList.size() == 0) {
							for (ItemStack item : finalItems) {
								p.getInventory().addItem(new ItemStack[] { item });
							}  cancel();
						} 
					}
				}).runTaskTimer((Plugin)APIPlugin.getInstance(), 1L, 1L);
			} 
		}
	}
}
