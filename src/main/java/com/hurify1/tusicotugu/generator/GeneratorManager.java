package com.hurify1.tusicotugu.generator;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;

public class GeneratorManager {

	private final Map<Location, Generator> byLocation = new ConcurrentHashMap<>();
	
	public Collection<Generator> getAll() {
		return byLocation.values(); //return new ArrayList<Generator>(byLocation.values());
	} 
	
	public void addGenerator(Generator generator) {
		this.byLocation.put(generator.getLocation(), generator);
	}
	
	public void removeGenerator(Generator generator) {
		this.byLocation.remove(generator.getLocation());
	}
	
	public Optional<Generator> get(Location location) {
		return Optional.ofNullable(this.byLocation.get(location));
	}
}