package net.faiden.bedwars.runnables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.faiden.bedwars.BedWars;
import net.faiden.bedwars.manager.GameManager;
import net.golema.database.support.builder.TitleBuilder;

public class LobbyRunnable extends BukkitRunnable {

	public static Integer lobbyTimer = 30;
	public static boolean isStarted = false;

	@Override
	public void run() {

		// Sécurité si le 'minPlayer' n'est plus respecté.
		if ((Bukkit.getOnlinePlayers().size() < BedWars.get().getGameUtils().getMinPlayer())) {
			Bukkit.broadcastMessage(BedWars.get().getGameUtils().getGamePrefix() + ChatColor.RED
					+ "Il n'y a pas assez de joueurs pour jouer.");
			lobbyTimer = 30;
			isStarted = false;
			this.cancel();
			return;
		}

		// Accélérer le démarrage de la partie.
		if ((Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) && (lobbyTimer >= 10)) {
			lobbyTimer = 5;
		}

		// Démarrage de la Partie.
		if (lobbyTimer == 0) {
			new GameManager();
			this.cancel();
			return;
		}

		// Envoie du Message pour Alerter le Timer.
		if ((lobbyTimer == 120) || (lobbyTimer == 90) || (lobbyTimer == 60) || (lobbyTimer == 30) || (lobbyTimer == 15)
				|| (lobbyTimer == 10) || ((lobbyTimer <= 5) && (lobbyTimer != 0))) {
			Bukkit.broadcastMessage(
					BedWars.get().getGameUtils().getGamePrefix() + " " + ChatColor.YELLOW + "Début de la partie dans "
							+ ChatColor.GOLD + lobbyTimer + getSeconds(lobbyTimer) + ChatColor.YELLOW + ".");
			for(Player playerOnline : Bukkit.getOnlinePlayers()) {
				playerOnline.playSound(playerOnline.getLocation(), Sound.NOTE_PLING, 2.0f, 1.0f);
				new TitleBuilder("", ChatColor.YELLOW + "Début de la partie dans " + ChatColor.GOLD + lobbyTimer
						+ getSeconds(lobbyTimer) + ChatColor.YELLOW + ".").send(playerOnline);
			}
		}

		// Gestion de l'évolution du Timer.
		for(Player playerOnline : Bukkit.getOnlinePlayers())
			playerOnline.setFoodLevel(lobbyTimer);
		lobbyTimer--;
	}

	/**
	 * Récupérer la String du temps.
	 * 
	 * @param timer
	 * @return
	 */
	private String getSeconds(Integer timer) {
		return timer == 1 ? " seconde" : " secondes";
	}
}