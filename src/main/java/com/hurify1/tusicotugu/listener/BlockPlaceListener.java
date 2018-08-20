
package com.hurify1.tusicotugu.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.hurify1.tusicotugu.FreeMgpPlugin;

public class BlockPlaceListener implements Listener {

	private final FreeMgpPlugin plugin;

	public BlockPlaceListener(FreeMgpPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Location location = event.getBlock().getLocation();
		location.setY(location.getY() + 1);
		if (!location.getBlock().getType().equals(Material.AIR)) {
			if (!player.getInventory().getItemInMainHand().isSimilar(plugin.getGeneratorItem())) return;
			player.sendTitle(ChatColor.DARK_GREEN + "*" + ChatColor.DARK_GREEN + " GENERATOR " + ChatColor.DARK_GREEN + "*", ChatColor.GRAY + "Nad " + ChatColor.GREEN + "generatorem " + ChatColor.GRAY + "nie moze stac blok", 5, 20, 5);
			event.setCancelled(true);
			return;
		}
		if (!player.getInventory().getItemInMainHand().isSimilar(plugin.getGeneratorItem())) return;
		Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.GREEN + "Generator");
		inventory.setItem(1, plugin.getStoneItem());
		inventory.setItem(3, plugin.getObsidianItem());
		player.openInventory(inventory);
		plugin.getUserManager().getUser(player).ifPresent(user -> {
			user.setLastGeneratorLocation(event.getBlock().getLocation());
		});
	}
}