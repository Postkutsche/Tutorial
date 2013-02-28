package de.postcrafter.tutorial.region;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.postcrafter.tutorial.Tutorial;

public class RegionListener implements Listener {

	private Tutorial plugin;
	
	public RegionListener(Tutorial plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Location loc1 = e.getFrom();
		Location loc2 = e.getTo();
		for (String region : this.plugin.regions.keySet()) {
			Region rg = this.plugin.regions.get(region);
			if (rg.isInside(loc2) && !rg.isInside(loc1)) {
				e.getPlayer().sendMessage("§7Willkommen in Region §6" + region + "§7.");
			} else if (rg.isInside(loc1) && !rg.isInside(loc2)) {
				e.getPlayer().sendMessage("§7Du verlässt nun Region §6" + region + "§7.");
			}
		}
	}
}
