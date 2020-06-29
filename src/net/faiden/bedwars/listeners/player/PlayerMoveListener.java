package net.faiden.bedwars.listeners.player;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.faiden.bedwars.BedWars;
import net.faiden.bedwars.GamePlayer;
import net.golema.database.support.GameStatus;
import net.golema.database.support.builder.TitleBuilder;
import net.golema.database.support.particle.ParticleEffect;

public class PlayerMoveListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		GamePlayer gamePlayer = GamePlayer.get(player);
		switch (GameStatus.getStatus()) {
		case LOBBY:

			// Gestion de la chute dans le Lobby.
			if(player.getLocation().getBlockY() < 60) {
				player.teleport(BedWars.get().getGameUtils().getLobbyLocation());
				new TitleBuilder(ChatColor.GOLD + "BedWars", ChatColor.YELLOW + "Pourquoi vouloir s'enfuir ?").send(player);
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
				return;
			}
			
			// Animation des vitres dans le Lobby.
			if (gamePlayer.getTeamInfos() != null) {
				Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
				if ((block.getType().equals(Material.STAINED_GLASS))
						|| (block.getType().equals(Material.STAINED_GLASS))) {
					block.setType(Material.STAINED_GLASS, true);
					block.setData((byte) gamePlayer.getTeamInfos().getIdBlockColor());	
					ParticleEffect.FLAME.display(player.getVelocity(), 0.6f, player.getLocation(), player);
				}
			}

			break;
		default:
			break;
		}
	}
}