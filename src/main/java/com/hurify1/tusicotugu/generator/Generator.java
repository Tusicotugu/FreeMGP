package com.hurify1.tusicotugu.generator;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

public class Generator {
	
	private final Random random = new Random();
	private final Material material;
	private final Location location;
	private final UUID uuid;
	
	public Generator(Material material, Location location) {
		this.material = material;
		this.location = location;
		this.uuid = new UUID(this.random.nextLong(), this.random.nextLong());
	}
	
	public Generator(Material material, Location location, UUID uuid) {
		this.material = material;
		this.location = location;
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public Location getLocation() {
		return location;
	}
}
