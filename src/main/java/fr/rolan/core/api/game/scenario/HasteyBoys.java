package fr.rolan.core.api.game.scenario;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rolan.core.APIPlugin;

public class HasteyBoys implements Listener {
	
	public HasteyBoys() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent ev) {
		Player player = (Player) ev.getWhoClicked();
		
		if (ev.getRecipe() instanceof ShapedRecipe) {
			ShapedRecipe r = (ShapedRecipe)ev.getRecipe();
			if(r.getResult().getType() == Material.WOOD_PICKAXE || r.getResult().getType() == Material.WOOD_AXE || r.getResult().getType() == Material.WOOD_SPADE
					|| r.getResult().getType() == Material.STONE_PICKAXE || r.getResult().getType() == Material.STONE_AXE || r.getResult().getType() == Material.STONE_SPADE 
					|| r.getResult().getType() == Material.IRON_PICKAXE || r.getResult().getType() == Material.IRON_AXE || r.getResult().getType() == Material.IRON_SPADE
					|| r.getResult().getType() == Material.GOLD_PICKAXE || r.getResult().getType() == Material.GOLD_AXE || r.getResult().getType() == Material.GOLD_SPADE
					|| r.getResult().getType() == Material.DIAMOND_PICKAXE || r.getResult().getType() == Material.DIAMOND_AXE || r.getResult().getType() == Material.DIAMOND_SPADE) {
				ItemStack result = new ItemStack(r.getResult().getType());
				ItemMeta meta = result.getItemMeta();
				meta.addEnchant(Enchantment.DIG_SPEED, 3, true);
				meta.addEnchant(Enchantment.DURABILITY, 3, true);
				result.setItemMeta(meta);
				ev.getInventory().setResult(result);
				player.updateInventory();
			}
		}
	}
}
