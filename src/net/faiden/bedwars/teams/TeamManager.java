package net.faiden.bedwars.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import net.faiden.bedwars.BedWars;
import net.golema.database.support.locations.Cuboid;

public class TeamManager {

	private Map<TeamInfos, List<Player>> playerTeamList = new HashMap<TeamInfos, List<Player>>();

	/**
	 * Ajouter un Joueur dans une Team.
	 * 
	 * @param player
	 * @param teamInfos
	 */
	public void addPlayerTeam(Player player, TeamInfos teamInfos) {
		if (playerTeamList.get(teamInfos) == null)
			playerTeamList.put(teamInfos, new ArrayList<Player>());
		if (!(teamIsFull(teamInfos))) {
			removePlayerAllTeam(player);
			playerTeamList.get(teamInfos).add(player);
		}
	}

	/**
	 * Supprimer le Joueur d'une Team.
	 * 
	 * @param player
	 * @param teamInfos
	 */
	public void removePlayerTeam(Player player, TeamInfos teamInfos) {
		if (playerTeamList.get(teamInfos) != null)
			if (playerTeamList.get(teamInfos).contains(player))
				playerTeamList.get(teamInfos).remove(player);
	}

	/**
	 * Supprimer le Joueur de toute les Teams.
	 * 
	 * @param player
	 */
	public void removePlayerAllTeam(Player player) {
		for (TeamInfos teamInfos : TeamInfos.values()) {
			if (playerTeamList.get(teamInfos) != null)
				if (playerTeamList.get(teamInfos).contains(player))
					playerTeamList.get(teamInfos).remove(player);
		}
	}

	/**
	 * Ajouter le Joueur dans une Team Aléatoire.
	 * 
	 * @param player
	 */
	public void addPlayerInRandomTeam(Player player) {
		if(!playerHaveTeam(player)) {
			for (TeamInfos teamInfos : TeamInfos.values()) {
				if (!(teamIsFull(teamInfos)))
					addPlayerTeam(player, teamInfos);
			}
		}
	}

	/**
	 * Vérifier si le Joueur est dans une Team.
	 * 
	 * @param player
	 * @param teamInfos
	 * @return
	 */
	public boolean isPlayerInTeam(Player player, TeamInfos teamInfos) {
		if (playerTeamList.get(teamInfos) != null)
			return playerTeamList.get(teamInfos).contains(player);
		return false;
	}

	/**
	 * Vérifier si le Joueur possède une Team.
	 * 
	 * @param player
	 * @return
	 */
	public boolean playerHaveTeam(Player player) {
		for (TeamInfos teamInfos : TeamInfos.values()) {
			if (playerTeamList.get(teamInfos) != null)
				if (playerTeamList.get(teamInfos).contains(player))
					return true;
		}
		return false;
	}

	/**
	 * Savoir si une Team est pleine.
	 * 
	 * @return
	 */
	public boolean teamIsFull(TeamInfos teamInfos) {
		if (playerTeamList.get(teamInfos) != null)
			return playerTeamList.get(teamInfos).size() >= getPlayerTeamLimit();
		return false;
	}

	/**
	 * Récupérer la Team d'un Joueur.
	 * 
	 * @param player
	 * @return
	 */
	public TeamInfos getPlayerTeam(Player player) {
		for (TeamInfos teamInfos : TeamInfos.values()) {
			if (playerTeamList.get(teamInfos) != null)
				if (playerTeamList.get(teamInfos).contains(player))
					return teamInfos;
		}
		return null;
	}

	/**
	 * Récupérer la Location de la Team.
	 * 
	 * @param teamInfos
	 * @return
	 */
	public Location getTeamLocation(TeamInfos teamInfos) {
		return new Location(BedWars.get().getGameUtils().getWorld(),
				BedWars.get().getMapConfig().get().getDouble("TeamSpawnLocation." + teamInfos.getTeamName() + ".x"),
				BedWars.get().getMapConfig().get().getDouble("TeamSpawnLocation." + teamInfos.getTeamName() + ".y"),
				BedWars.get().getMapConfig().get().getDouble("TeamSpawnLocation." + teamInfos.getTeamName() + ".z"),
				(float) BedWars.get().getMapConfig().get()
						.getDouble("TeamSpawnLocation." + teamInfos.getTeamName() + ".yaw"),
				(float) BedWars.get().getMapConfig().get()
						.getDouble("TeamSpawnLocation." + teamInfos.getTeamName() + ".pitch"));
	}

	/**
	 * Récupérer la Location de la Forge de la Team.
	 * 
	 * @param teamInfos
	 * @return
	 */
	public Location getForgeTeamLocation(TeamInfos teamInfos) {
		return new Location(BedWars.get().getGameUtils().getWorld(),
				BedWars.get().getMapConfig().get().getDouble("TeamForgeLocation." + teamInfos.getTeamName() + ".x"),
				BedWars.get().getMapConfig().get().getDouble("TeamForgeLocation." + teamInfos.getTeamName() + ".y"),
				BedWars.get().getMapConfig().get().getDouble("TeamForgeLocation." + teamInfos.getTeamName() + ".z"));
	}

	/**
	 * Récupérer la zone d'une Team.
	 * 
	 * @param teamInfos
	 * @return
	 */
	public Cuboid getTeamCuboid(TeamInfos teamInfos) {
		return new Cuboid(new Location(BedWars.get().getGameUtils().getWorld(),
				BedWars.get().getMapConfig().get().getDouble("TeamZoneCuboid." + teamInfos.getTeamName() + ".min.x"),
				BedWars.get().getMapConfig().get().getDouble("TeamZoneCuboid." + teamInfos.getTeamName() + ".min.y"),
				BedWars.get().getMapConfig().get().getDouble("TeamZoneCuboid." + teamInfos.getTeamName() + ".min.z")),
				new Location(BedWars.get().getGameUtils().getWorld(),
						BedWars.get().getMapConfig().get()
								.getDouble("TeamZoneCuboid." + teamInfos.getTeamName() + ".max.x"),
						BedWars.get().getMapConfig().get()
								.getDouble("TeamZoneCuboid." + teamInfos.getTeamName() + ".max.y"),
						BedWars.get().getMapConfig().get()
								.getDouble("TeamZoneCuboid." + teamInfos.getTeamName() + ".max.z")));
	}
	
	/**
	 * Récupérer le lieu d'un Bloc.
	 * 
	 * @param block
	 * @return
	 */
	public TeamInfos getBlockTeam(Block block) {
		for(TeamInfos teamInfos : TeamInfos.values()) {
			if(getTeamCuboid(teamInfos).IsArena(block.getLocation()))
				return teamInfos;
		}
		return null;
	}
	
	/**
	 * Récupérer le nombre de Joueur dans une équipe.
	 * 
	 * @param teamInfos
	 * @return
	 */
	public Integer getTeamPlayerCount(TeamInfos teamInfos) {
		if (playerTeamList.get(teamInfos) != null)
			return playerTeamList.get(teamInfos).size();
		return 0;
	}

	/**
	 * Récupérer les Joueurs dans une Team.
	 * 
	 * @param teamInfos
	 * @return
	 */
	public List<Player> getPlayerTeamList(TeamInfos teamInfos) {
		if (playerTeamList.get(teamInfos) != null)
			return playerTeamList.get(teamInfos);
		return new ArrayList<Player>();
	}

	/**
	 * Récupérer le maximum de Joueurs par Team.
	 * 
	 * @return
	 */
	public Integer getPlayerTeamLimit() {
		return BedWars.get().getGameType().getPlayerInTeam();
	}

	/**
	 * Gestion de la Map des Teams.
	 * 
	 * @return
	 */
	public Map<TeamInfos, List<Player>> getPlayerTeamList() {
		return playerTeamList;
	}
}