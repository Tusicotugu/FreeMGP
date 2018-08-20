package com.hurify1.tusicotugu;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.hurify1.tusicotugu.generator.GeneratorManager;
import com.hurify1.tusicotugu.generator.GeneratorStorage;
import com.hurify1.tusicotugu.listener.BlockBreakListener;
import com.hurify1.tusicotugu.listener.BlockPlaceListener;
import com.hurify1.tusicotugu.listener.InventoryClickListener;
import com.hurify1.tusicotugu.listener.InventoryCloseListener;
import com.hurify1.tusicotugu.listener.PlayerJoinListener;
import com.hurify1.tusicotugu.listener.PlayerQuitListener;
import com.hurify1.tusicotugu.user.User;
import com.hurify1.tusicotugu.user.UserManager;

public class FreeMgpPlugin extends JavaPlugin {
	
	private UserManager userManager;
	private GeneratorManager generatorManager;
	private GeneratorStorage storageManager;
	private ItemStack generatorItem;
	private ItemStack stoneItem;
	private ItemStack obsidianItem;
	
	@Override
	public void onEnable() {
		//New objects
		this.userManager = new UserManager();
		this.generatorManager = new GeneratorManager();
		this.storageManager = new GeneratorStorage();
		//Generator item
		generatorItem = new ItemStack(Material.ENDER_STONE);
		ItemMeta generatorMeta = generatorItem.getItemMeta();
		generatorMeta.setDisplayName(ChatColor.GREEN + "Generator");
		generatorMeta.setLore(Arrays.asList(ChatColor.GRAY + "Postaw i wybierz co generator ma robic!"));
		generatorItem.setItemMeta(generatorMeta);
		//Lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Kliknij, aby wybrac");
		//Stone item
		stoneItem = new ItemStack(Material.STONE);
		ItemMeta stoneMeta = stoneItem.getItemMeta();
		stoneMeta.setDisplayName(ChatColor.GREEN + "Kamien");
		stoneMeta.setLore(lore);
		stoneItem.setItemMeta(stoneMeta);
		//Obsidian item
		obsidianItem = new ItemStack(Material.OBSIDIAN);
		ItemMeta obisidianMeta = obsidianItem.getItemMeta();
		obisidianMeta.setDisplayName(ChatColor.GREEN + "Obsydian");
		obisidianMeta.setLore(lore);
		obsidianItem.setItemMeta(obisidianMeta);
		//Listener
		PluginManager pluginManager = this.getServer().getPluginManager();
		for (Listener listener : new Listener[] {
			new BlockPlaceListener(this),
			new InventoryClickListener(this),
			new InventoryCloseListener(this),
			new BlockBreakListener(this),
			new PlayerJoinListener(this),
			new PlayerQuitListener(this),
		}) {
			pluginManager.registerEvents(listener, this);
		}
		//Generator recipe
		NamespacedKey key = new NamespacedKey(this, NamespacedKey.BUKKIT);
		ShapedRecipe generatorCraft = new ShapedRecipe(key, getGeneratorItem());
		generatorCraft.shape(new String[]{"sss", "sgs", "sss"}).setIngredient('s', Material.STONE).setIngredient('g', Material.EMERALD);
		Bukkit.getServer().addRecipe(generatorCraft);
		//Register user after reload
		for (Player player : Bukkit.getOnlinePlayers()) {
			getUserManager().registerUser(new User(this, player));
		}
		//Storage load
		getStorageManager().load(this);
	}
	
	public UserManager getUserManager() {
		return this.userManager;
	}
	
	public GeneratorManager getGeneratorManager() {
		return this.generatorManager;
	}
	
	public GeneratorStorage getStorageManager() {
		return this.storageManager;
	}
	
	public ItemStack getGeneratorItem() {
		return generatorItem;
	}
	
	public ItemStack getStoneItem() {
		return stoneItem;
	}
	
	public ItemStack getObsidianItem() {
		return obsidianItem;
	}
}
