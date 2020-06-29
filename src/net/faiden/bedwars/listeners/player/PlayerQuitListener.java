package net.faiden.bedwars.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.faiden.bedwars.GamePlayer;

public class PlayerQuitListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		// Gestion des variables.
		Player player = event.getPlayer();
		@SuppressWarnings("unused")
		GamePlayer gamePlayer = GamePlayer.get(player);
		event.setQuitMessage(null);
	}
}