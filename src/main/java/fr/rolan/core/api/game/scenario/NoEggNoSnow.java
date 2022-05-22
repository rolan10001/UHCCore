package fr.rolan.core.api.game.scenario;

import org.bukkit.Bukkit;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.rolan.core.APIPlugin;

public class NoEggNoSnow implements Listener {
	
	public NoEggNoSnow() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		if(entity instanceof Player && (damager instanceof Snowball || damager instanceof Egg) && ((Projectile) event.getDamager()).getShooter() instanceof Player) 
			event.setCancelled(true);
	}
}
