package net.faiden.bedwars;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.faiden.bedwars.teams.TeamInfos;
import net.faiden.bedwars.utils.TextureHeadInfos;
import net.golema.api.builder.titles.ActionBarBuilder;
import net.golema.database.golemaplayer.GolemaPlayer;
import net.golema.database.support.GameStatus;
import net.golema.database.support.builder.items.heads.CustomSkull;

public class GamePlayer {

	private Player player;
	private GolemaPlayer golemaPlayer;

	private TeamInfos teamInfos;

	/**
	 * Construction du GamePlayer.
	 * 
	 * @param player
	 */
	public GamePlayer(Player player) {
		this.player = player;
		this.golemaPlayer = GolemaPlayer.getGolemaPlayer(player);
	}

	/**
	 * Récupérer l'itemstack de sélection d'équipe.
	 * 
	 * @return
	 */
	public ItemStack getTeamItemSelect() {
		ItemStack itemStack = CustomSkull.getCustomSkull(getTextureHeadTeam().getLink());
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Sélecteur d'Équipe");
		itemMeta.setLore(Arrays.asList("§7Sélectionner une équipe."));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	/**
	 * Setup le Joueur dans une Team durant le Lobby.
	 * 
	 * @param teamInfos
	 */
	public void setupPlayerInTeam(TeamInfos teamInfos) {
		if (GameStatus.isStatus(GameStatus.LOBBY)) {
			this.teamInfos = teamInfos;
			player.getInventory().setItem(4, getTeamItemSelect());
			player.getInventory().setHelmet(CustomSkull.getCustomSkull(teamInfos.getTextureHeadInfos().getLink()));
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			new ActionBarBuilder(ChatColor.WHITE + "Bienvenue dans l'équipe : " + teamInfos.getChatColor()
					+ teamInfos.getTeamName() + ChatColor.WHITE + ".").sendTo(player);
			player.sendMessage(
					BedWars.get().getGameUtils().getTeamPrefix() + ChatColor.WHITE + "Vous avez rejoins l'équipe : "
							+ teamInfos.getChatColor() + teamInfos.getTeamName() + ChatColor.WHITE + ".");
		}
	}

	/**
	 * Récupérer la texture de la tête TeamInfos.
	 * 
	 * @return
	 */
	public TextureHeadInfos getTextureHeadTeam() {
		if (teamInfos == null)
			return TextureHeadInfos.BARRIER;
		return teamInfos.getTextureHeadInfos();
	}

	public Player getPlayer() {
		return player;
	}

	public GolemaPlayer getGolemaPlayer() {
		return golemaPlayer;
	}

	public TeamInfos getTeamInfos() {
		return teamInfos;
	}

	public void setTeamInfos(TeamInfos teamInfos) {
		this.teamInfos = teamInfos;
	}

	/**
	 * Récupérer un GamePlayer.
	 * 
	 * @param player
	 * @return
	 */
	public static GamePlayer get(Player player) {
		if (BedWars.get().getGamePlayerMap().get(player) == null)
			BedWars.get().getGamePlayerMap().put(player, new GamePlayer(player));
		return BedWars.get().getGamePlayerMap().get(player);
	}
}