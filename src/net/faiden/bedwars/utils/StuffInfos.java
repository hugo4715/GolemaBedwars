package net.faiden.bedwars.utils;

public enum StuffInfos {
	
	DEFAULT("Défault"),
	CHAINMAIL("Maille"),
	IRON("Fer"),
	DIAMOND("Diamant");
	
	private String name;
	
	private StuffInfos(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}