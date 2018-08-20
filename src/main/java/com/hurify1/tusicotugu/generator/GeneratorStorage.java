package com.hurify1.tusicotugu.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.json.simple.JSONObject;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.hurify1.tusicotugu.FreeMgpPlugin;

public class GeneratorStorage {

	@SuppressWarnings("unchecked")
	public void save(FreeMgpPlugin plugin, Generator generator) {
		try {
			JSONObject obj = new JSONObject();
			File folder = new File(plugin.getDataFolder(), "generators");
			if (!folder.exists()) createFolder(plugin);
			File path = new File(folder, generator.getUuid() + ".json");
			Location location = generator.getLocation();
			obj.put("X", location.getX());
			obj.put("Y", location.getY());
			obj.put("Z", location.getZ());
			obj.put("Material", generator.getMaterial());
			obj.put("World", location.getWorld().getName().toString());
			FileWriter file = new FileWriter(path);
			file.write(obj.toJSONString());
			file.close();
		} catch (IOException exeption) {
			Bukkit.getLogger().log(Level.WARNING, ChatColor.stripColor(exeption.toString()));
		}
	}

	public void load(FreeMgpPlugin plugin) {
		File folder = new File(plugin.getDataFolder(), "generators");
		if (!folder.exists()) createFolder(plugin);
		File[] file = folder.listFiles();
		int i;
		for (i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				JsonParser parser = new JsonParser();
				try {
					JsonObject object = (JsonObject) parser.parse(new FileReader(file[i]));
					JsonObject jsonObject = object;
					World world = Bukkit.getWorld(jsonObject.get("World").toString().replaceAll("\"", ""));
					if (world != null) {
						Material material = Material.getMaterial(jsonObject.get("Material").toString().replaceAll("\"", ""));
						double x = Double.valueOf(jsonObject.get("X").toString());
						double y = Double.valueOf(jsonObject.get("Y").toString());
						double z = Double.valueOf(jsonObject.get("Z").toString());
						UUID uuid = UUID.fromString(file[i].getName().replaceAll(".json", ""));
						Location location = new Location(world, x, y, z);
						Generator generator = new Generator(material, location, uuid);
						plugin.getGeneratorManager().addGenerator(generator);
					}
				} catch (JsonIOException | JsonSyntaxException | FileNotFoundException exception) {
					Bukkit.getLogger().log(Level.WARNING, ChatColor.stripColor(exception.toString()));
				}
			}
		}
		Bukkit.getLogger().log(Level.INFO, "[FreeMCG] " + i + " generators loaded");
		
		Bukkit.getLogger().log(Level.INFO, "[FreeMCG] Folder /FreeMcg/generators/ was created!");
	}
	
	public void remove(FreeMgpPlugin plugin, Generator generator) {
		File folder = new File(plugin.getDataFolder(), "generators");
		if (!folder.exists()) createFolder(plugin);
		File path = new File(folder, generator.getUuid() + ".json");
		path.delete();
	}
	
	public void createFolder(FreeMgpPlugin plugin) {
		//Create /FreeMcg/generators/ folder
		File folder = new File(plugin.getDataFolder(), "generators");
		if (!folder.exists() && !folder.isDirectory()) {
			folder.mkdirs();
			Bukkit.getLogger().log(Level.INFO, "[FreeMCG] Folder /FreeMcg/generators/ was created!");
		}
	}
}
