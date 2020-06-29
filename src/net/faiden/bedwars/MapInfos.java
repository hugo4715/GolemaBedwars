package net.faiden.bedwars;

public enum MapInfos {

	ALOIPIA("Aloipia", "aloipia.yml");

	private String mapName, configName;

	/**
	 * Gestion des maps.
	 * 
	 * @param mapName
	 * @param configName
	 */
	private MapInfos(String mapName, String configName) {
		this.mapName = mapName;
		this.configName = configName;
	}

	public String getMapName() {
		return mapName;
	}

	public String getConfigName() {
		return configName;
	}
}