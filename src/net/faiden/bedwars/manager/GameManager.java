package net.faiden.bedwars.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.faiden.bedwars.BedWars;
import net.faiden.bedwars.GamePlayer;
import net.faiden.bedwars.teams.TeamInfos;
import net.faiden.bedwars.teams.TeamManager;
import net.faiden.bedwars.utils.GameType;
import net.golema.api.utils.PlayerUtils;
import net.golema.database.golemaplayer.GolemaPlayer;
import net.golema.database.support.GameStatus;
import net.golema.database.support.boards.TeamsTagsManager;
import net.golema.database.support.builder.TitleBuilder;
import tk.hugo4715.golema.timesaver.TimeSaverAPI;

public class GameManager {

	/**
	 * Démarrage de la partie.
	 */
	public GameManager() {

		// Paramètres de la partie.
		TimeSaverAPI.setJoinable(false);
		GameStatus.setStatus(GameStatus.GAME);

		// Paramètres des Joueurs.
		for (Player playerOnline : Bukkit.getOnlinePlayers()) {

			// Variables du Joueur.
			GolemaPlayer golemaPlayer = GolemaPlayer.getGolemaPlayer(playerOnline);
			GamePlayer gamePlayer = GamePlayer.get(playerOnline);

			// Téléportation du Joueur.
			TeamManager teamManager = BedWars.get().getTeamManager();
			teamManager.addPlayerInRandomTeam(playerOnline);
			gamePlayer.setTeamInfos(teamManager.getPlayerTeam(playerOnline));
			TeamInfos teamInfos = gamePlayer.getTeamInfos();
			playerOnline.teleport(teamManager.getTeamLocation(teamInfos));

			// Chargement du Joueur.
			PlayerUtils.clearInventory(playerOnline);
			playerOnline.setGameMode(GameMode.SURVIVAL);
			playerOnline.setMaxHealth(20.0d);
			playerOnline.setHealth(playerOnline.getMaxHealth());
			playerOnline.setFoodLevel(20);
			playerOnline.playSound(playerOnline.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);

			// Design du Joueur.
			TeamsTagsManager.setNameTag(playerOnline, teamInfos.getTeamName(), teamInfos.getChatColor() + ""
					+ ChatColor.BOLD + "[" + teamInfos.getTeamName().charAt(0) + "] " + teamInfos.getChatColor());
			new TitleBuilder(ChatColor.GOLD + "BedWars", ChatColor.YELLOW + "Let's go, bonne chance !")
					.send(playerOnline);

			// Message de démarrage de la partie.
			playerOnline.sendMessage("");
			golemaPlayer.sendCenteredMessage(
					ChatColor.GOLD + "■ " + ChatColor.YELLOW + "" + ChatColor.BOLD + "BedWars" + ChatColor.GOLD + " ■");
			playerOnline.sendMessage("");
		}

		// Paramètres du Jeu.
		this.sendTeamWarningMessage();
	}

	/**
	 * Envoyer les messages d'alerte de Teams.
	 */
	public void sendTeamWarningMessage() {
		if (BedWars.get().getGameType().equals(GameType.SOLO)) {
			Bukkit.broadcastMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "[" + ChatColor.DARK_RED + ""
					+ ChatColor.BOLD + "!" + ChatColor.WHITE + "" + ChatColor.BOLD + "] " + ChatColor.RED
					+ "Les teams ne sont pas autorisées.");
		} else {
			Bukkit.broadcastMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "[" + ChatColor.DARK_RED + "" + ChatColor.BOLD
					+ "!" + ChatColor.WHITE + "" + ChatColor.BOLD + "] " + ChatColor.RED
					+ "Le cross-team n'est pas autorisées.");
		}
	}
}