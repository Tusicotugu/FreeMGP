package com.hurify1.tusicotugu.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import com.hurify1.tusicotugu.FreeMgpPlugin;

public class InventoryCloseListener implements Listener {
	
	private final FreeMgpPlugin plugin;
	
	public InventoryCloseListener(FreeMgpPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player)) return;
		Player player = (Player) event.getPlayer(); //Bukkit api is best
		plugin.getUserManager().getUser(player).ifPresent(user -> {
			if (user.getLastGeneratorLocation() != null) {
				Inventory inventory = event.getInventory();
				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					player.openInventory(inventory);
				}, 1);
			}
		});
	}
}
