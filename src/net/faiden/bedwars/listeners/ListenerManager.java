package net.faiden.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import net.faiden.bedwars.listeners.entity.EntityDamageListener;
import net.faiden.bedwars.listeners.player.PlayerDropItemListener;
import net.faiden.bedwars.listeners.player.PlayerInteractListener;
import net.faiden.bedwars.listeners.player.PlayerInventoryListener;
import net.faiden.bedwars.listeners.player.PlayerJoinListener;
import net.faiden.bedwars.listeners.player.PlayerMoveListener;
import net.faiden.bedwars.listeners.player.PlayerQuitListener;
import net.faiden.bedwars.listeners.world.BlocksListener;
import net.golema.api.patch.KnockbackFixerListener;

public class ListenerManager {
	
	private Plugin plugin;
	private PluginManager pluginManager;
	
	/**
	 * Enregistrement des listeners.
	 * 
	 * @param plugin
	 */
	public ListenerManager(Plugin plugin) {
		this.plugin = plugin;
		this.pluginManager = Bukkit.getPluginManager();
	}
	
	/*
	 * Gestion des événements.
	 */
	public void registerListener() {
		
		// Events liés » Entity.
		this.pluginManager.registerEvents(new EntityDamageListener(), plugin);
		
		// Events liés » Player.
		this.pluginManager.registerEvents(new PlayerJoinListener(), plugin);
		this.pluginManager.registerEvents(new PlayerQuitListener(), plugin);
		this.pluginManager.registerEvents(new PlayerMoveListener(), plugin);
		this.pluginManager.registerEvents(new PlayerInventoryListener(), plugin);
		this.pluginManager.registerEvents(new PlayerInteractListener(), plugin);
		this.pluginManager.registerEvents(new PlayerDropItemListener(), plugin);
		
		// Events liés » World.
		this.pluginManager.registerEvents(new BlocksListener(), plugin);
		
		// Events liés » Patch.
		this.pluginManager.registerEvents(new KnockbackFixerListener(), plugin);
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	public PluginManager getPluginManager() {
		return pluginManager;
	}
}
