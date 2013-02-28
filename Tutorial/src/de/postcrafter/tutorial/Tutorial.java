package de.postcrafter.tutorial;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.postcrafter.tutorial.command.AFKCommand;
import de.postcrafter.tutorial.command.CallbackCommand;
import de.postcrafter.tutorial.command.EconCommand;
import de.postcrafter.tutorial.command.GeschenkCommand;
import de.postcrafter.tutorial.command.HealCommand;
import de.postcrafter.tutorial.command.MetaCommand;
import de.postcrafter.tutorial.command.SignCommand;
import de.postcrafter.tutorial.command.TutorialCommand;
import de.postcrafter.tutorial.listener.AFKListener;
import de.postcrafter.tutorial.listener.ChatListener;
import de.postcrafter.tutorial.listener.DamageListener;
import de.postcrafter.tutorial.listener.FlyListener;
import de.postcrafter.tutorial.listener.JoinListener;
import de.postcrafter.tutorial.listener.SignListener;
import de.postcrafter.tutorial.region.Region;
import de.postcrafter.tutorial.region.RegionCommand;
import de.postcrafter.tutorial.region.RegionListener;

public class Tutorial extends JavaPlugin {
		
	private static Tutorial plugin;
	
	private int messageTask;
    public static Economy economy = null;

	public HashMap<String, Region> regions = new HashMap<String, Region>();
	
	public static Tutorial getTutorial() {
		return Tutorial.plugin;
	}
	
	@Override
	public void onDisable() {
//		Bukkit.getScheduler().cancelTask(this.messageTask);
		System.out.println("Tutorial beendet!");
		
		File file = new File("plugins/Tutorials", "regions.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		for (String region : this.regions.keySet()) {
			cfg.set(region, this.regions.get(region).serialize());
		}
		try {
			cfg.save(file);
			System.out.println("Saved " + this.regions.size() + " regions.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEnable() {
		Tutorial.plugin = this;
		
		this.setupEconomy();
		
		System.out.println("Starte Tutorial...");

		this.getCommand("tutorial").setExecutor(new TutorialCommand());
		this.getCommand("heal").setExecutor(new HealCommand());
		this.getCommand("geschenk").setExecutor(new GeschenkCommand());
		this.getCommand("callback").setExecutor(new CallbackCommand(this));
		this.getCommand("sign").setExecutor(new SignCommand());
		this.getCommand("econ").setExecutor(new EconCommand(this));
		this.getCommand("meta").setExecutor(new MetaCommand());
		this.getCommand("region").setExecutor(new RegionCommand(this));
		this.getCommand("afk").setExecutor(new AFKCommand());
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new JoinListener(this), this);
		pm.registerEvents(new DamageListener(), this);
		pm.registerEvents(new FlyListener(), this);
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new RegionListener(this), this);
		pm.registerEvents(new AFKListener(), this);
		pm.registerEvents(new SignListener(), this);
		
		this.getConfig().addDefault("info.motd", new String[] {
			"§7Willkommen auf dem §6Tutorialserver!",
			"§7Hier wird es viele Erklärungen geben"
		});
		this.getConfig().addDefault("messages", new String[] {
			"§1Das ist blau",
			"§4Das ist rot",
			"§6und das orange"
		});
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		File file = new File("plugins/Tutorials", "regions.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		for (String region : cfg.getConfigurationSection("").getKeys(false)) {
			String world = cfg.getString(region + ".world");
			World w = Bukkit.getWorld(world);
			if (w != null) {
				int minX = cfg.getInt(region + ".minX");
				int minY = cfg.getInt(region + ".minY");
				int minZ = cfg.getInt(region + ".minZ");

				int maxX = cfg.getInt(region + ".maxX");
				int maxY = cfg.getInt(region + ".maxY");
				int maxZ = cfg.getInt(region + ".maxZ");
				
				this.regions.put(region.toLowerCase(), new Region(new Location(w, minX, minY, minZ), new Location(w, maxX, maxY, maxZ)));
			}
		}
//		this.messageTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new MessageRunnable(this.getConfig().getStringList("messages")), 0L, 5*20L);
		
		ItemStack item0 = new ItemStack(Material.DIAMOND_BLOCK);
		ItemMeta meta0 = item0.getItemMeta();
		meta0.setDisplayName("§b§lDER §bDiamantblock");
		item0.setItemMeta(meta0);
		ShapelessRecipe recipe0 = new ShapelessRecipe(item0);
		recipe0.addIngredient(9, Material.DIAMOND_BLOCK);
		Bukkit.addRecipe(recipe0);
		
		ItemStack item1 = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta1 = item1.getItemMeta();
		meta1.setDisplayName("§6§oFeuerschwert");
		meta1.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
		item1.setItemMeta(meta1);
		ShapedRecipe recipe1 = new ShapedRecipe(item1);
		recipe1.shape("OBX","OXB","SOO");
		recipe1.setIngredient('X', Material.DIAMOND);
		recipe1.setIngredient('S', Material.STICK);
		recipe1.setIngredient('B', Material.BLAZE_POWDER);
		Bukkit.addRecipe(recipe1);
		
		ItemStack item2 = new ItemStack(Material.BAKED_POTATO);
		ItemMeta meta2 = item2.getItemMeta();
		meta2.setDisplayName("Verbrannte Kartoffel");
		item2.setItemMeta(meta2);
		FurnaceRecipe recipe2 = new FurnaceRecipe(item2, Material.BAKED_POTATO);
		Bukkit.addRecipe(recipe2);
	}
	
	private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
}
