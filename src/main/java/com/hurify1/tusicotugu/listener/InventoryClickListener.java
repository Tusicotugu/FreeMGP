package com.hurify1.tusicotugu.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.hurify1.tusicotugu.FreeMgpPlugin;
import com.hurify1.tusicotugu.generator.Generator;
import com.hurify1.tusicotugu.user.User;

public class InventoryClickListener implements Listener {

	private final FreeMgpPlugin plugin;
	
	public InventoryClickListener(FreeMgpPlugin plugin) {
		this.plugin = plugin;
	}
	
	private void createGenerator(Location location, Material material, User user, InventoryClickEvent event) {
		location.setY(location.getY() + 1);
		Generator generator = new Generator(material, location);
		plugin.getGeneratorManager().addGenerator(generator);
		plugin.getStorageManager().save(plugin, generator);
		location.getBlock().setType(material);
		user.setLastGeneratorLocation(null);
		event.setCancelled(true);
		user.getPlayer().closeInventory();
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if (event.getInventory() == null || event.getCurrentItem() == null) return;
			plugin.getUserManager().getUser(player).ifPresent(user -> {
				Location location = user.getLastGeneratorLocation();
				if (event.getCurrentItem().isSimilar(plugin.getStoneItem())) {
					createGenerator(location, Material.STONE, user, event);
				} else if (event.getCurrentItem().isSimilar(plugin.getObsidianItem())) {
					createGenerator(location, Material.OBSIDIAN, user, event);
				} else {
					return;
				}
				player.sendTitle(ChatColor.DARK_GREEN + "*" + ChatColor.DARK_GREEN + " GENERATOR " + ChatColor.DARK_GREEN + "*", ChatColor.GRAY + "Stworzyles " + ChatColor.GREEN + "generator", 5, 20, 5);
			});
		}
	}
}