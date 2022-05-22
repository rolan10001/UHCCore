package fr.rolan.core.api.game.scenario;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ShapedRecipe;

import fr.rolan.core.APIPlugin;

public class RodLess implements Listener {
	
	public RodLess() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent ev) {
		if (ev.getRecipe() instanceof ShapedRecipe) {
			ShapedRecipe r = (ShapedRecipe)ev.getRecipe();
			if(r.getResult().getType() == Material.FISHING_ROD) {
				ev.setCancelled(true);
			}
		}
	}
}
