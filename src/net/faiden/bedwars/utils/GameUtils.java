package net.faiden.bedwars.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import net.faiden.bedwars.BedWars;

public class GameUtils {
	
	/**
	 * Récupérer le prefix de la partie.
	 * 
	 * @return
	 */
	public String getGamePrefix() {
		return ChatColor.GOLD + "" + ChatColor.BOLD + "BedWars" + ChatColor.WHITE + "│ ";
	}
	
	/**
	 * Récupérer le prefix des Teams.
	 * 
	 * @return
	 */
	public String getTeamPrefix() {
		return ChatColor.GOLD + "[Team] ";
	}
	
	/**
	 * Récupérer le minimum de Joueurs pour démarrer la partie.
	 * 
	 * @return
	 */
	public Integer getMinPlayer() {
		return 2;
	}
	
	/**
	 * Récupérer le World.
	 * 
	 * @return
	 */
	public World getWorld() {
		return Bukkit.getWorld("world");
	}

	/**
	 * Récupérer la location du Lobby.
	 * 
	 * @return
	 */
	public Location getLobbyLocation() {
		return new Location(Bukkit.getWorld("world"), BedWars.get().getMapConfig().get().getDouble("LobbyLocation.x"),
				BedWars.get().getMapConfig().get().getDouble("LobbyLocation.y"),
				BedWars.get().getMapConfig().get().getDouble("LobbyLocation.z"),
				(float) BedWars.get().getMapConfig().get().getDouble("LobbyLocation.yaw"),
				(float) BedWars.get().getMapConfig().get().getDouble("LobbyLocation.pitch"));
	}
	
	/**
	 * Récupérer la location du Centre.
	 * 
	 * @return
	 */
	public Location getCenterLocation() {
		return new Location(Bukkit.getWorld("world"), BedWars.get().getMapConfig().get().getDouble("CenterLocation.x"),
				BedWars.get().getMapConfig().get().getDouble("CenterLocation.y"),
				BedWars.get().getMapConfig().get().getDouble("CenterLocation.z"),
				(float) BedWars.get().getMapConfig().get().getDouble("CenterLocation.yaw"),
				(float) BedWars.get().getMapConfig().get().getDouble("CenterLocation.pitch"));
	}
}