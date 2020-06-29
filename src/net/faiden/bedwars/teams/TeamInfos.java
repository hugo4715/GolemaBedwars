package net.faiden.bedwars.teams;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import net.faiden.bedwars.utils.TextureHeadInfos;

public enum TeamInfos {
	
	RED("Rouge", ChatColor.RED, (short) 14, Color.RED, true, 0, 0, 0, 0, 0, 0, 0, 0, 20, TextureHeadInfos.WOOL_RED),
	BLUE("Bleu", ChatColor.BLUE, (short) 11, Color.BLUE, true, 0, 0, 0, 0, 0, 0, 0, 0, 21, TextureHeadInfos.WOOL_BLUE),
	GREEN("Vert", ChatColor.GREEN, (short) 5, Color.GREEN, true, 0, 0, 0, 0, 0, 0, 0, 0, 22, TextureHeadInfos.WOOL_GREEN),
	YELLOW("Jaune", ChatColor.YELLOW, (short) 4, Color.YELLOW, true, 0, 0, 0, 0, 0, 0, 0, 0, 23, TextureHeadInfos.WOOL_YELLOW),
	AQUA("Cyan", ChatColor.AQUA, (short) 3, Color.AQUA, true, 0, 0, 0, 0, 0, 0, 0, 0, 24, TextureHeadInfos.WOOL_AQUA),
	PINK("Rose", ChatColor.LIGHT_PURPLE, (short) 6, Color.FUCHSIA, true, 0, 0, 0, 0, 0, 0, 0, 0, 30, TextureHeadInfos.WOOL_PINK),
	ORANGE("Orange", ChatColor.GOLD, (short) 1, Color.ORANGE, true, 0, 0, 0, 0, 0, 0, 0, 0, 31, TextureHeadInfos.WOOL_ORANGE),
	WHITE("Blanche", ChatColor.WHITE, (short) 0, Color.WHITE, true, 0, 0, 0, 0, 0, 0, 0, 0, 32, TextureHeadInfos.WOOL_WHITE);
	
	// Team Information.
	private String teamName;
	private ChatColor chatColor;
	private short idBlockColor;
	private Color color;
	private boolean bed;
	private int forgeLevel, hasteLevel, sharpnessLevel, protectionLevel, trapLevel, miningFatigueLevel, regenLevel,
			dragonLevel;

	// Team Menu Information.
	private int slotInventory;
	private TextureHeadInfos textureHeadInfos;

	/**
	 * Construction du TeamInfos.
	 * 
	 * @param teamName
	 * @param chatColor
	 * @param idBlockColor
	 * @param color
	 * @param bed
	 * @param forgeLevel
	 * @param hasteLevel
	 * @param sharpnessLevel
	 * @param protectionLevel
	 * @param trapLevel
	 * @param miningFatigueLevel
	 * @param regenLevel
	 * @param dragonLevel
	 * @param slotInventory
	 * @param headLink
	 */
	private TeamInfos(String teamName, ChatColor chatColor, short idBlockColor, Color color, boolean bed, int forgeLevel, int hasteLevel,
			int sharpnessLevel, int protectionLevel, int trapLevel, int miningFatigueLevel, int regenLevel,
			int dragonLevel, int slotInventory, TextureHeadInfos textureHeadInfos) {
		this.teamName = teamName;
		this.chatColor = chatColor;
		this.idBlockColor = idBlockColor;
		this.color = color;
		this.bed = bed;
		this.forgeLevel = forgeLevel;
		this.hasteLevel = hasteLevel;
		this.sharpnessLevel = sharpnessLevel;
		this.protectionLevel = protectionLevel;
		this.trapLevel = trapLevel;
		this.miningFatigueLevel = miningFatigueLevel;
		this.regenLevel = regenLevel;
		this.dragonLevel = dragonLevel;
		this.slotInventory = slotInventory;
		this.textureHeadInfos = textureHeadInfos;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public ChatColor getChatColor() {
		return chatColor;
	}

	public void setChatColor(ChatColor chatColor) {
		this.chatColor = chatColor;
	}
	
	public short getIdBlockColor() {
		return idBlockColor;
	}
	
	public void setIdBlockColor(short idBlockColor) {
		this.idBlockColor = idBlockColor;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isBed() {
		return bed;
	}

	public void setBed(boolean bed) {
		this.bed = bed;
	}

	public int getForgeLevel() {
		return forgeLevel;
	}

	public void setForgeLevel(int forgeLevel) {
		this.forgeLevel = forgeLevel;
	}

	public int getHasteLevel() {
		return hasteLevel;
	}

	public void setHasteLevel(int hasteLevel) {
		this.hasteLevel = hasteLevel;
	}

	public int getSharpnessLevel() {
		return sharpnessLevel;
	}

	public void setSharpnessLevel(int sharpnessLevel) {
		this.sharpnessLevel = sharpnessLevel;
	}

	public int getProtectionLevel() {
		return protectionLevel;
	}

	public void setProtectionLevel(int protectionLevel) {
		this.protectionLevel = protectionLevel;
	}

	public int getTrapLevel() {
		return trapLevel;
	}

	public void setTrapLevel(int trapLevel) {
		this.trapLevel = trapLevel;
	}

	public int getMiningFatigueLevel() {
		return miningFatigueLevel;
	}

	public void setMiningFatigueLevel(int miningFatigueLevel) {
		this.miningFatigueLevel = miningFatigueLevel;
	}

	public int getRegenLevel() {
		return regenLevel;
	}

	public void setRegenLevel(int regenLevel) {
		this.regenLevel = regenLevel;
	}

	public int getDragonLevel() {
		return dragonLevel;
	}

	public void setDragonLevel(int dragonLevel) {
		this.dragonLevel = dragonLevel;
	}

	public int getSlotInventory() {
		return slotInventory;
	}

	public void setSlotInventory(int slotInventory) {
		this.slotInventory = slotInventory;
	}
	
	public TextureHeadInfos getTextureHeadInfos() {
		return textureHeadInfos;
	}
	
	public void setTextureHeadInfos(TextureHeadInfos textureHeadInfos) {
		this.textureHeadInfos = textureHeadInfos;
	}

	/**
	 * Récupérer une TeamInfo par son Slot.
	 * 
	 * @param slot
	 * @return
	 */
	public static TeamInfos getTeamBySlot(int slot) {
		for (TeamInfos teamInfos : TeamInfos.values()) {
			if (teamInfos.getSlotInventory() == slot)
				return teamInfos;
		}
		return null;
	}

	/**
	 * Récupérer une TeamInfo par son Nom.
	 * 
	 * @param name
	 * @return
	 */
	public static TeamInfos getTeamByName(String name) {
		for (TeamInfos teamInfos : TeamInfos.values()) {
			if (teamInfos.getTeamName().equalsIgnoreCase(name))
				return teamInfos;
		}
		return null;
	}
}