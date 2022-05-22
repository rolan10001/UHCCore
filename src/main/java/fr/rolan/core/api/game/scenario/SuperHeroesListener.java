package fr.rolan.core.api.game.scenario;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.rolan.api.game.scenario.superheroes.SuperHeroesType;
import fr.rolan.core.APIPlugin;

public class SuperHeroesListener implements Listener {
	
	public SuperHeroesListener() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player && event.getCause().equals(DamageCause.FALL) && APIPlugin.getInstance().getAPI().getUser((Player) event.getEntity()).getSuperHeroes() != null && APIPlugin.getInstance().getAPI().getUser((Player) event.getEntity()).getSuperHeroes().getType().equals(SuperHeroesType.JUMP))
			event.setCancelled(true);
	}
}
