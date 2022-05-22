package fr.rolan.core.api.stuff;

import org.bukkit.inventory.ItemStack;

import fr.rolan.api.stuff.IStuffManager;

public class StuffManager implements IStuffManager {
	
	private ItemStack[] stuffStart = new ItemStack[] {};
	private ItemStack[] stuffArmorStart = new ItemStack[] {};
	private ItemStack[] stuffDeath = new ItemStack[] {};
	private ItemStack[] stuffArmorDeath = new ItemStack[] {};
	
	@Override
	public ItemStack[] getStuffStart() {
		return stuffStart;
	}
	
	@Override
	public void setStuffStart(ItemStack[] items) {
		this.stuffStart = items;
	}
	
	@Override
	public ItemStack[] getStuffArmorStart() {
		return stuffArmorStart;
	}
	
	@Override
	public void setStuffArmorStart(ItemStack[] items) {
		this.stuffArmorStart = items;
	}
	
	@Override
	public ItemStack[] getStuffDeath() {
		return stuffDeath;
	}
	
	@Override
	public void setStuffDeath(ItemStack[] items) {
		this.stuffDeath = items;
	}
	
	@Override
	public ItemStack[] getStuffArmorDeath() {
		return stuffArmorDeath;
	}
	
	@Override
	public void setStuffArmorDeath(ItemStack[] items) {
		this.stuffArmorDeath = items;
	}
}
