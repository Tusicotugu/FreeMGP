package com.hurify1.tusicotugu.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.hurify1.tusicotugu.FreeMgpPlugin;
import com.hurify1.tusicotugu.user.UserManager;

public class PlayerQuitListener implements Listener {

	private final FreeMgpPlugin plugin;

	public PlayerQuitListener(FreeMgpPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		UserManager userManager = plugin.getUserManager();
		userManager.getUser(event.getPlayer()).ifPresent(user -> userManager.unregisterUser(user));
	}
}

