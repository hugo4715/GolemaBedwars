package net.faiden.bedwars.utils;

import tk.hugo4715.golema.timesaver.server.GameInfos;

public enum GameType {
	
	SOLO("Solo", 1, GameInfos.BEDWARSSOLO),
	TEAM_2V2("Team2V2", 2, GameInfos.BEDWARSTEAM2X2),
	TEAM_3V3("Team3V3", 3, GameInfos.BEDWARSTEAM3X3),
	NONE("None", -1, GameInfos.NONE);
	
	private String name;
	private Integer playerInTeam;
	private GameInfos gameInfos;

	private GameType(String name, Integer playerInTeam, GameInfos gameInfos) {
		this.name = name;
		this.playerInTeam = playerInTeam;
		this.gameInfos = gameInfos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPlayerInTeam() {
		return playerInTeam;
	}

	public void setPlayerInTeam(Integer playerInTeam) {
		this.playerInTeam = playerInTeam;
	}

	public GameInfos getGameInfos() {
		return gameInfos;
	}

	public void setGameInfos(GameInfos gameInfos) {
		this.gameInfos = gameInfos;
	}

	/**
	 * Récupérer le GameType par le nombre de Joueurs par Teams.
	 * 
	 * @param playerInTeam
	 */
	public static GameType getGameTypeByPlayerTeamCount(int playerInTeam) {
		for (GameType gameType : GameType.values()) {
			if (gameType.getPlayerInTeam() == playerInTeam)
				return gameType;
		}
		return GameType.NONE;
	}
}
