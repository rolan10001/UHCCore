package fr.rolan.core.api.game.scenario;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import fr.rolan.core.APIPlugin;

public class CutClean implements Listener {
	
	public CutClean() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if(player.getGameMode().equals(GameMode.CREATIVE)) return;
		
		if(block.getType() == Material.IRON_ORE) {
			Location location = block.getLocation().add(0.5, 0.5, 0.5);
		    World world = location.getWorld();
			event.setCancelled(true);
			block.getWorld().spawn(location, ExperienceOrb.class).setExperience(2);
			block.setType(Material.AIR);
			world.dropItem(location, new ItemStack(Material.IRON_INGOT));
		}else if(block.getType() == Material.GOLD_ORE) {
			Location location = block.getLocation().add(0.5, 0.5, 0.5);
		    World world = location.getWorld();
			event.setCancelled(true);
			block.getWorld().spawn(location, ExperienceOrb.class).setExperience(4);
			block.setType(Material.AIR);
			world.dropItem(location, new ItemStack(Material.GOLD_INGOT));
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if(entity.getType() == EntityType.COW) {
			for(int i = 0; i < event.getDrops().size(); i++) {
				if(event.getDrops().get(i).getType().equals(Material.RAW_BEEF)) {
					event.getDrops().add(new ItemStack(Material.COOKED_BEEF, event.getDrops().get(i).getAmount()));
					event.getDrops().remove(i);
					i--;
				}
			}
		}else if(entity.getType() == EntityType.PIG) {
			for(int i = 0; i < event.getDrops().size(); i++) {
				if(event.getDrops().get(i).getType().equals(Material.PORK)) {
					event.getDrops().add(new ItemStack(Material.GRILLED_PORK, event.getDrops().get(i).getAmount()));
					event.getDrops().remove(i);
					i--;
				}
			}
		}else if(entity.getType() == EntityType.SHEEP) {
			for(int i = 0; i < event.getDrops().size(); i++) {
				if(event.getDrops().get(i).getType().equals(Material.MUTTON)) {
					event.getDrops().add(new ItemStack(Material.COOKED_MUTTON, event.getDrops().get(i).getAmount()));
					event.getDrops().remove(i);
					i--;
				}
			}
		}else if(entity.getType() == EntityType.CHICKEN) {
			for(int i = 0; i < event.getDrops().size(); i++) {
				if(event.getDrops().get(i).getType().equals(Material.RAW_CHICKEN)) {
					event.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, event.getDrops().get(i).getAmount()));
					event.getDrops().remove(i);
					i--;
				}
			}
		}else if(entity.getType() == EntityType.RABBIT) {
			for(int i = 0; i < event.getDrops().size(); i++) {
				if(event.getDrops().get(i).getType().equals(Material.RABBIT)) {
					event.getDrops().add(new ItemStack(Material.COOKED_RABBIT, event.getDrops().get(i).getAmount()));
					event.getDrops().remove(i);
					i--;
				}
			}
		}
	}
}
