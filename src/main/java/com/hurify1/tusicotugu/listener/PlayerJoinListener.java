package com.hurify1.tusicotugu.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.hurify1.tusicotugu.FreeMgpPlugin;
import com.hurify1.tusicotugu.user.User;

public class PlayerJoinListener implements Listener {
	
	private final FreeMgpPlugin plugin;
	
	public PlayerJoinListener(FreeMgpPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.getUserManager().registerUser(new User(this.plugin, event.getPlayer()));
	}
}