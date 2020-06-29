package net.faiden.bedwars.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.golema.database.support.GameStatus;

public class PlayerInventoryListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		switch (GameStatus.getStatus()) {
		case LOBBY:
			event.setCancelled(true);
			break;
		default:
			break;
		}
	}
}