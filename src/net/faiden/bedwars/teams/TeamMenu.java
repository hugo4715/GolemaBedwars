package net.faiden.bedwars.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.faiden.bedwars.BedWars;
import net.faiden.bedwars.GamePlayer;
import net.golema.api.builder.virtual.VirtualMenu;
import net.golema.database.support.builder.items.ItemBuilder;
import net.golema.database.support.builder.items.heads.CustomSkull;

public class TeamMenu extends VirtualMenu {

	private Map<Player, Inventory> playerInventoryMap = new HashMap<Player, Inventory>();

	/**
	 * Création du menu d'équipe.
	 * 
	 * @param player
	 */
	public TeamMenu(Player player) {
		super(player, "Choix d'équipes", 6);

		// Appliquer les bordures au menu.
		for (Integer slots : getBorderSlot())
			this.menuInventory.setItem(slots, new ItemBuilder().type(Material.STAINED_GLASS_PANE).name("§a").build());

		// Icon pour rejoindre une équipe aléatoire / fermer le menu.
		this.menuInventory.setItem(49,
				new ItemBuilder().type(Material.ARMOR_STAND).name(ChatColor.WHITE + "Aléatoire").build());
		this.menuInventory.setItem(53,
				new ItemBuilder().type(Material.WOOD_DOOR).name(ChatColor.RED + "Fermer").build());

		// Gestion des items des équipes.
		for (TeamInfos teamInfos : TeamInfos.values()) {
			ItemStack itemStack = CustomSkull.getCustomSkull(teamInfos.getTextureHeadInfos().getLink());
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemStack.setAmount(BedWars.get().getTeamManager().getTeamPlayerCount(teamInfos));
			itemMeta.setDisplayName(teamInfos.getChatColor() + teamInfos.getTeamName());
			List<String> lores = new ArrayList<>();
			lores.add("");
			lores.add(ChatColor.GRAY + "Etat : " + this.getTeamState(teamInfos));
			lores.add("");
			lores.add(ChatColor.GRAY + "Membres de l'équipe:");
			for (Player playerTeam : BedWars.get().getTeamManager().getPlayerTeamList(teamInfos))
				lores.add(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "» " + teamInfos.getChatColor()
						+ playerTeam.getName());
			for (int staySlot = 0; staySlot != ((int) (BedWars.get().getTeamManager().getPlayerTeamLimit()
					- BedWars.get().getTeamManager().getTeamPlayerCount(teamInfos))); staySlot++)
				lores.add(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "» " + ChatColor.GRAY + "[Disponible]");
			lores.add("");
			lores.add(ChatColor.GOLD + "" + ChatColor.BOLD + "➸ " + ChatColor.YELLOW + "Rejoindre l'équipe.");
			itemMeta.setLore(lores);
			itemStack.setItemMeta(itemMeta);
			this.menuInventory.setItem(teamInfos.getSlotInventory(), itemStack);
		}

		// Paramètres du Menu et Ouverture.
		playerInventoryMap.put(player, menuInventory);
		open();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		GamePlayer gamePlayer = GamePlayer.get(player);
		if (event.getInventory() == null)
			return;
		if (event.getClickedInventory() == null)
			return;
		if (event.getCurrentItem() == null)
			return;
		if (event.getCurrentItem().getType().equals(Material.AIR))
			return;
		if (!(event.getInventory().equals(playerInventoryMap.get(player))))
			return;
		if (event.getInventory().equals(playerInventoryMap.get((Player) event.getWhoClicked()))) {
			event.setCancelled(true);
			switch (event.getCurrentItem().getType()) {
			case ARMOR_STAND:

				// Si le Joueur possède déjà une équipe.
				if (BedWars.get().getTeamManager().playerHaveTeam(player)) {
					player.sendMessage(BedWars.get().getGameUtils().getTeamPrefix() + ChatColor.RED
							+ "Vous êtes déjà dans une équipe.");
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
					player.closeInventory();
					return;
				}

				// Ajouter le Joueur dans une équipe.
				BedWars.get().getTeamManager().addPlayerInRandomTeam(player);
				gamePlayer.setupPlayerInTeam(BedWars.get().getTeamManager().getPlayerTeam(player));
				player.closeInventory();
				break;
			case SKULL_ITEM:
				TeamInfos teamInfos = TeamInfos.getTeamBySlot(event.getSlot());

				// Le Joueur est déjà dans cette équipe.
				if (BedWars.get().getTeamManager().isPlayerInTeam(player, teamInfos)) {
					player.sendMessage(BedWars.get().getGameUtils().getTeamPrefix() + ChatColor.RED
							+ "Vous êtes déjà dans cette équipe.");
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
					player.closeInventory();
					return;
				}

				// Vérifier si la Team est pleine.
				if (BedWars.get().getTeamManager().teamIsFull(teamInfos)) {
					player.sendMessage(
							BedWars.get().getGameUtils().getTeamPrefix() + ChatColor.RED + "Cette équipe est pleine.");
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
					player.closeInventory();
					return;
				}

				// Ajouter le Joueur dans une équipe.
				BedWars.get().getTeamManager().addPlayerTeam(player, teamInfos);
				gamePlayer.setupPlayerInTeam(teamInfos);
				player.closeInventory();
				break;
			case WOOD_DOOR:
				player.closeInventory();
				break;
			default:
				break;
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if (event.getInventory() == null)
			return;
		if (playerInventoryMap.get(player) == null)
			return;
		if (playerInventoryMap.get(player).equals(event.getInventory()))
			playerInventoryMap.remove(player);
	}

	/**
	 * Retourner le State de la Team.
	 * 
	 * @param teamInfos
	 * @return
	 */
	public String getTeamState(TeamInfos teamInfos) {
		if (BedWars.get().getTeamManager().teamIsFull(teamInfos))
			return ChatColor.RED + "Complète";
		return ChatColor.GREEN + "Disponible";
	}

	/**
	 * Récupérer les slots du contour du menu.
	 * 
	 * @return
	 */
	public List<Integer> getBorderSlot() {
		List<Integer> slotsList = new ArrayList<Integer>();
		for (int i = 0; i != 10; i++)
			slotsList.add(i);
		slotsList.add(17);
		slotsList.add(18);
		slotsList.add(26);
		slotsList.add(27);
		slotsList.add(35);
		slotsList.add(36);
		for (int i = 44; i != 53; i++)
			if (i != 49)
				slotsList.add(i);
		return slotsList;
	}
}