package com.hurify1.tusicotugu.user;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.hurify1.tusicotugu.FreeMgpPlugin;

public class User {
	private final Reference<Player> bukkit;
	private final String username;
	private final UUID uniqueId;
	private Location lastGeneratorLocation;

	public User(FreeMgpPlugin plugin, Player bukkit) {
		this.bukkit = new WeakReference<Player>(bukkit);
		username = bukkit.getName();
		uniqueId = bukkit.getUniqueId();
	}	
	
	public Location getLastGeneratorLocation() {
		return lastGeneratorLocation;
	}

	public void setLastGeneratorLocation(Location lastGeneratorLocation) {
		this.lastGeneratorLocation = lastGeneratorLocation;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(getUniqueId());
	}

	public Location getLocation() {
		return getPlayer().getLocation();
	}

	public Optional<Player> getBukkit() {
		return Optional.ofNullable((Player) bukkit.get());
	}

	public String getUsername() {
		return username;
	}

	public UUID getUniqueId() {
		return uniqueId;
	}	
}
