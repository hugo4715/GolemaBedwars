package net.faiden.bedwars;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.faiden.bedwars.listeners.ListenerManager;
import net.faiden.bedwars.teams.TeamManager;
import net.faiden.bedwars.utils.GameType;
import net.faiden.bedwars.utils.GameUtils;
import net.golema.database.support.GameStatus;
import net.golema.database.support.configs.FileManager;
import net.golema.database.support.configs.FileManager.Config;
import net.golema.database.support.world.WorldManager;
import tk.hugo4715.golema.timesaver.TimeSaverAPI;
import tk.hugo4715.golema.timesaver.server.ServerStatus;
import tk.hugo4715.golema.timesaver.server.ServerType;

public class BedWars extends JavaPlugin {
	
	private Map<Player, GamePlayer> gamePlayerMap = new HashMap<Player, GamePlayer>();
	
	// Gestion des cartes.
	private FileManager fileManager;
	private Config mapConfig;
	private MapInfos mapInfos;
	private List<MapInfos> mapInfosList = new ArrayList<MapInfos>();
	
	// Gestion des utilitaires.
	private GameType gameType;
	private GameUtils gameUtils;
	private TeamManager teamManager;
	
	private static BedWars instance;
	
	@Override
	public void onLoad() {
		
		// Définition de l'instance du Plugin.
		instance = this;
		
		// Gestion de la Carte et des Configurations.
		Bukkit.unloadWorld("world", false);
		for(MapInfos mapInfos : MapInfos.values()) { mapInfosList.add(mapInfos); }
		this.mapInfos = mapInfosList.get(new Random().nextInt(mapInfosList.size()));
		fileManager = new FileManager(instance);
		mapConfig = fileManager.getConfig("maps/" + mapInfos.getConfigName());
		mapConfig.copyDefaults(true).save();
		WorldManager.deleteWorld(new File("world"));
		File from = new File("maps/" + mapInfos.getMapName());
		File to = new File("world");
		try {
			WorldManager.copyFolder(from, to); 
		} catch (Exception e) {
			System.err.println("Erreur: Le serveur n'arrive pas à copier la Map : " + mapInfos.getMapName());
			Bukkit.getServer().shutdown();
			return;
		}
		this.saveDefaultConfig();
		
		super.onLoad();
	}
	
	
	@Override
	public void onEnable() {
		
		// Settings de la partie.
		GameStatus.setStatus(GameStatus.LOBBY);
		new ListenerManager(instance).registerListener();
		
		// Chargement des utilitaires.
		this.gameType = GameType.getGameTypeByPlayerTeamCount(getConfig().getInt("playerPerTeam"));
		this.gameUtils = new GameUtils();
		this.teamManager = new TeamManager();
		
		// Gestion du TimerSaver.
		Bukkit.getScheduler().runTaskLater(instance, new Runnable() {
			@Override
			public void run() {
				TimeSaverAPI.setServerMap(mapInfos.getMapName());
				TimeSaverAPI.setServerStatus(ServerStatus.ALLOW);
				TimeSaverAPI.setServerGame(gameType.getGameInfos());
				TimeSaverAPI.setServerType(ServerType.GAME);
				TimeSaverAPI.setJoinable(true);
			}
		}, 40L);
		
		super.onEnable();
	}
	
	
	@Override
	public void onDisable() {}
	
	/**
	 * Récupérer la HashMap des 'GamePlayer'.
	 * 
	 * @return
	 */
	public Map<Player, GamePlayer> getGamePlayerMap() {
		return gamePlayerMap;
	}
	
	/**
	 * Gestion de la carte.
	 * 
	 * @return
	 */
	public MapInfos getMapInfos() {
		return mapInfos;
	}
	
	/**
	 * Gestion de la config.
	 * 
	 * @return
	 */
	public FileManager getFileManager() {
		return fileManager;
	}
	
	/**
	 * Récupérer la Configuration de la map.
	 * 
	 * @return
	 */
	public Config getMapConfig() {
		return mapConfig;
	}
	
	/**
	 * Récupérer les utilitaires de la partie.
	 * 
	 * @return
	 */
	public GameUtils getGameUtils() {
		return gameUtils;
	}
	
	/**
	 * Récupérer le Manager de Teams.
	 * 
	 * @return
	 */
	public TeamManager getTeamManager() {
		return teamManager;
	}
	
	/**
	 * Récupérer le Type de la Game.
	 * 
	 * @return
	 */
	public GameType getGameType() {
		return gameType;
	}
	
	/**
	 * Instance du plugin.
	 * 
	 * @return
	 */
	public static BedWars get() {
		return instance;
	}
} 