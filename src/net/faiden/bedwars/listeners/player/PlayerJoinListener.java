package net.faiden.bedwars.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.faiden.bedwars.BedWars;
import net.faiden.bedwars.GamePlayer;
import net.faiden.bedwars.runnables.LobbyRunnable;
import net.golema.api.utils.PlayerUtils;
import net.golema.database.support.GameStatus;
import net.golema.database.support.builder.items.ItemBuilder;

public class PlayerJoinListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {

		// Gestion des variables.
		Player player = event.getPlayer();
		GamePlayer gamePlayer = GamePlayer.get(player);
		event.setJoinMessage(null);

		// La partie est déjà lancée.
		if (!(GameStatus.isStatus(GameStatus.LOBBY))) {
			// Option de mise en spectator.
			return;
		}

		// Paramètres des joueurs.
		player.teleport(BedWars.get().getGameUtils().getLobbyLocation());
		player.setGameMode(GameMode.ADVENTURE);

		// Paramètres de l'inventaire du joueur.
		PlayerUtils.clearInventory(player);
		player.getInventory().setItem(4, gamePlayer.getTeamItemSelect());
		player.getInventory().setItem(8, new ItemBuilder().type(Material.BED)
				.name(ChatColor.RED + "" + ChatColor.BOLD + "Retourner au hub").build());
		
		// Timer de démarrage.
		if ((Bukkit.getOnlinePlayers().size() >= BedWars.get().getGameUtils().getMinPlayer())) {
			new LobbyRunnable().runTaskTimer(BedWars.get(), 0L, 20L);
			LobbyRunnable.isStarted = true;
		}
	}
}