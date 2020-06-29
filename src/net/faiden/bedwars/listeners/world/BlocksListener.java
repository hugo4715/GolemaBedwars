package net.faiden.bedwars.listeners.world;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import net.faiden.bedwars.BedWars;
import net.faiden.bedwars.GamePlayer;
import net.faiden.bedwars.teams.TeamInfos;
import net.golema.api.builder.HologramBuilder;
import net.golema.database.support.GameStatus;
import net.golema.database.support.builder.TitleBuilder;

public class BlocksListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {

		// Gestion des variables.
		Player player = event.getPlayer();
		GamePlayer gamePlayer = GamePlayer.get(player);
		Block block = event.getBlock();
		if (block == null)
			return;

		// Gestion des Status de la partie.
		switch (GameStatus.getStatus()) {
		case LOBBY:
			event.setCancelled(true);
			break;
		case GAME:

			// Détection d'une TNT.
			if (block.getType().equals(Material.TNT)) {

				// Vérifier si il la pose dans sa Zone.
				if (BedWars.get().getTeamManager().getBlockTeam(block).equals(gamePlayer.getTeamInfos())) {
					event.setCancelled(true);
					player.sendMessage(BedWars.get().getGameUtils().getGamePrefix() + ChatColor.RED
							+ "Vous ne pouvez pas poser de TNT si proche de votre base.");
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
					return;
				}

				// Explosion de la TnT.
				event.setCancelled(true);
				TNTPrimed tntPrimed = (TNTPrimed) BedWars.get().getGameUtils().getWorld()
						.spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);
				tntPrimed.setFuseTicks(40);
				HologramBuilder hologramBuilder;
				hologramBuilder = new HologramBuilder().editLocation(block.getLocation()).editMessage(ChatColor.DARK_RED
						+ "✹ " + ChatColor.RED + "" + ChatColor.BOLD + "EXPLOSION" + ChatColor.DARK_RED + " ✹")
						.withStay(40);
				hologramBuilder.sendToPlayers(Bukkit.getOnlinePlayers());
				tntPrimed.teleport(block.getLocation());
				tntPrimed.setMetadata("placed", new FixedMetadataValue(BedWars.get(), player.getName()));
				return;
			}
			break;
		default:
			break;
		}

	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		// Gestion des variables.
		Player player = event.getPlayer();
		GamePlayer gamePlayer = GamePlayer.get(player);
		Block block = event.getBlock();
		if (block == null)
			return;

		// Gestion des Status de la partie.
		switch (GameStatus.getStatus()) {
		case LOBBY:
			event.setCancelled(true);
			break;
		case GAME:

			// Destruction du lit.
			if (block.getType().equals(Material.BED_BLOCK)) {

				// Vérifier si le lit appartient au Joueur.
				if (BedWars.get().getTeamManager().getBlockTeam(block).equals(gamePlayer.getTeamInfos())) {
					event.setCancelled(true);
					player.sendMessage(BedWars.get().getGameUtils().getGamePrefix() + ChatColor.RED
							+ "Vous ne pouvez pas détruire votre lit.");
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
					return;
				}

				// Destruction du lit ennemie.
				TeamInfos teamInfos = BedWars.get().getTeamManager().getBlockTeam(block);
				if (teamInfos == null)
					return;
				if((BedWars.get().getTeamManager().getPlayerTeamList(teamInfos).isEmpty())
						|| (BedWars.get().getTeamManager().getPlayerTeamList(teamInfos).size() == 0))
					return;
				makeAnimationBedDestruction(player, teamInfos, block);
				return;
			}

			break;
		default:
			break;
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntity() instanceof TNTPrimed) {
			List<Block> blocksList = event.blockList();
			for (Block block : blocksList) {
				if ((block != null) && (block.getType().equals(Material.BED_BLOCK))) {
					TeamInfos teamInfos = BedWars.get().getTeamManager().getBlockTeam(block);
					Player player = Bukkit.getPlayer(event.getEntity().getMetadata("placed").get(0).asString());
					if (teamInfos == null)
						return;
					if((BedWars.get().getTeamManager().getPlayerTeamList(teamInfos).isEmpty())
							|| (BedWars.get().getTeamManager().getPlayerTeamList(teamInfos).size() == 0))
						return;
					if (player == null)
						return;
					makeAnimationBedDestruction(player, teamInfos, block);
					return;
				}
			}
		}
	}

	/**
	 * Effet lors de la destruction d'un lit.
	 * 
	 * @param player
	 * @param teamInfos
	 * @param block
	 */
	public void makeAnimationBedDestruction(Player player, TeamInfos teamInfos, Block block) {

		// Paramètres de la destruction du lit.
		BedWars.get().getGameUtils().getWorld().strikeLightningEffect(block.getLocation());
		GamePlayer gamePlayer = GamePlayer.get(player);
		TeamInfos playerTeam = gamePlayer.getTeamInfos();
		teamInfos.setBed(false);

		// Envoyer un message à l'équipe 'victime'.
		BedWars.get().getTeamManager().getPlayerTeamList(teamInfos).forEach(playersInTeam -> {
			new TitleBuilder(ChatColor.DARK_RED
						+ "✹ " + ChatColor.RED + "" + ChatColor.BOLD + "DESTRUCTION" + ChatColor.DARK_RED + " ✹",
						ChatColor.YELLOW + "Vous n'avez plus de lit, attention à la mort.").send(playersInTeam);
			playersInTeam.playSound(playersInTeam.getLocation(), Sound.WITHER_DEATH, 1.0f, 1.0f);
			
		});
		
		// Annonce de la destruction de lit.
		Bukkit.broadcastMessage(BedWars.get().getGameUtils().getGamePrefix() + playerTeam.getChatColor()
				+ player.getName() + " " + ChatColor.GRAY + "vient de détruire le lit de l'équipe "
				+ teamInfos.getChatColor() + "" + ChatColor.BOLD + teamInfos.getTeamName() + ChatColor.GRAY + ".");
		
		// Paramètres du Joueur.
		player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1.0f, 1.0f);
	}
}