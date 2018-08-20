package com.hurify1.tusicotugu.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.hurify1.tusicotugu.FreeMgpPlugin;
import com.hurify1.tusicotugu.generator.GeneratorManager;

public class BlockBreakListener implements Listener {

	private final FreeMgpPlugin plugin;

	public BlockBreakListener(FreeMgpPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Material material = block.getType();
		Location location = block.getLocation();
		GeneratorManager generatorManager = plugin.getGeneratorManager();
		if (material.equals(Material.OBSIDIAN) || material.equals(Material.STONE)) {
			generatorManager.get(location).ifPresent(generator -> {
				if (generator == null) return;
				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					if (!generatorManager.getAll().contains(generator)) return;
					block.setType(generator.getMaterial());
				}, 20 * 2);
			});
		} else if (material.equals(Material.ENDER_STONE)) {
			location.setY(location.getY() + 1);
			generatorManager.get(location).ifPresent(generator -> {
				location.setY(location.getY() - 1);
				if (generator == null) return;
				location.getBlock().setType(Material.AIR);
				World world = location.getWorld();
				world.dropItem(location, plugin.getGeneratorItem());
				generatorManager.removeGenerator(generator);
				plugin.getStorageManager().remove(plugin, generator);
				event.getPlayer().sendTitle(ChatColor.DARK_GREEN + "*" + ChatColor.DARK_GREEN + " GENERATOR " + ChatColor.DARK_GREEN + "*", ChatColor.GRAY + "Zniszczyles " + ChatColor.GREEN + "generator", 5, 20, 5);
				event.setCancelled(true);
			});
		}
	}
}
