package de.postcrafter.tutorial.region;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import de.postcrafter.tutorial.Tutorial;

public class RegionCommand implements CommandExecutor {

	private HashMap<String, Location> locs1 = new HashMap<String, Location>();
	private HashMap<String, Location> locs2 = new HashMap<String, Location>();
	
	private Tutorial plugin;
	
	public RegionCommand(Tutorial plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage("§cDu musst ein Spieler sein!");
			return true;
		}
		Player player = (Player) cs;
		Location loc = player.getLocation();
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("pos1")) {
				this.locs1.put(player.getName(), loc);
				cs.sendMessage("§7Punkt 1 gesetzt.");
			} else if (args[0].equalsIgnoreCase("pos2")) {
				this.locs2.put(player.getName(), loc);
				cs.sendMessage("§7Punkt 2 gesetzt.");
			} else if (args[0].equalsIgnoreCase("create")) {
				if (args.length == 2) {
					Location loc1 = this.locs1.get(player.getName());
					Location loc2 = this.locs2.get(player.getName());
					if (this.checkPoints(player)) {
						String name = args[1].toLowerCase();
						if (this.plugin.regions.containsKey(name)) {
							cs.sendMessage("§cDie Region " + name + " existiert schon.");
							return true;
						}
						this.plugin.regions.put(name, new Region(loc1, loc2));
						cs.sendMessage("§7Region §6" + name + " §7erstellt.");
					}
				} else {
					cs.sendMessage("§c/region create [name]");
				}
			} else if (args[0].equalsIgnoreCase("delete")) {
				if (args.length == 2) {
					String name = args[1].toLowerCase();
					if (this.plugin.regions.containsKey(name)) {
						this.plugin.regions.remove(name);
						cs.sendMessage("§7Die Region §6" + name + " §7wurde entfernt.");
					} else {
						cs.sendMessage("§cDie Region " + name + " existiert nicht.");
					}
				} else {
					cs.sendMessage("§c/region delete [name]");
				}
			} else if (args[0].equalsIgnoreCase("set")) {
				if (args.length == 2) {
					Location loc1 = this.locs1.get(player.getName());
					Location loc2 = this.locs2.get(player.getName());
					if (this.checkPoints(player)) {
						try {
							String[] a = args[1].split(":");
							int id = Integer.valueOf(a[0]);
							int damage = 0;
							if (a.length == 2) {
								damage = Integer.valueOf(a[1]);
							}
							Material mat = Material.getMaterial(id);
							if (mat == null) {
								cs.sendMessage("§cKein Item mit der ID " + id + " gefunden.");
								return true;
							}
							Region rg = new Region(loc1, loc2);
							rg.set(mat, (byte) damage);
							cs.sendMessage("§7Auswahl erfolgreich zu ID §6" + id + ":" + damage + "§7 gesetzt.");
						} catch (NumberFormatException nfe) {
							cs.sendMessage("§cGib eine Zahl an!");
							String name = "hi";
							StringUtils.capitalize(name.toLowerCase().replace("_", " "));
						}
					}
				}
			}
		}
		return false;
	}

	public boolean checkPoints(Player player) {
		Location loc1 = this.locs1.get(player.getName());
		Location loc2 = this.locs2.get(player.getName());
		if (loc1 == null || loc2 == null) {
			player.sendMessage("§cSetze zuerst 2 Punkte.");
			return false;
		}
		if (loc1.getWorld() != loc2.getWorld()) {
			player.sendMessage("§cDie Punkte müssen in der gleichen Welt sein");
			return false;
		}
		return true;
	}
	
}
