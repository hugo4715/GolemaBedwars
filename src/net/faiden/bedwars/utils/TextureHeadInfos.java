package net.faiden.bedwars.utils;

public enum TextureHeadInfos {
	
	WOOL_RED("http://textures.minecraft.net/texture/86d35a963d5987894b6bc214e328b39cd2382426ff9c8e082b0b6a6e044d3a3"),
	WOOL_BLUE("http://textures.minecraft.net/texture/3f3e406291174d24cdf0f953f8a174a82bb3489dce8f679a443ef1aae0169061"),
	WOOL_GREEN("http://textures.minecraft.net/texture/d67470a0c18f6851e914353719e795877d29b3252f7e6bd4a1b865765bd74feb"),
	WOOL_YELLOW("http://textures.minecraft.net/texture/27bbd0b2911c96b5d87b2df76691a51b8b12c6fefd523146d8ac5ef1b8ee"),
	WOOL_AQUA("http://textures.minecraft.net/texture/f1af46febd45c0f4d81e8fa1b66b275d89e272b2ad55c978553a99c733e1ff"),
	WOOL_PINK("http://textures.minecraft.net/texture/6becfb3879936b899e420bfcd3a74f8a1bf9dd54c58ec7fb9f81d9a5d988e"),
	WOOL_ORANGE("http://textures.minecraft.net/texture/cbf7797a24a6af875f5c8271c5b8c425e19f372a415e0552fc247763f2859d1"),
	WOOL_WHITE("http://textures.minecraft.net/texture/3faf4c29f1e7405f4680c5c2b03ef9384f1aecfe2986ad50138c605fefff2f15"),
	DICE("http://textures.minecraft.net/texture/51e12ad9759a3d368e5d9696ed124f733406c4f7162bac4fa38a98218b7d7c6"),
	BARRIER("http://textures.minecraft.net/texture/3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025");
	
	private String link;
	
	/**
	 * Texture des têtes.
	 * 
	 * @param link
	 */
	private TextureHeadInfos(String link) {
		this.link = link;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
}