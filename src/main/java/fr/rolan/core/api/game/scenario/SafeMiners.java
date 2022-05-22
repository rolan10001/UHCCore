package fr.rolan.core.api.game.scenario;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.rolan.core.APIPlugin;

public class SafeMiners implements Listener {
	
	public SafeMiners() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		if(player.getLocation().getY() > 32.0)
			return;
		if(event.getCause().equals(DamageCause.FIRE) || event.getCause().equals(DamageCause.FIRE_TICK) || event.getCause().equals(DamageCause.LAVA)) {
			event.setCancelled(true);
		}else {
			event.setDamage(event.getDamage()-event.getDamage()/100*80);
		}
	}
}
