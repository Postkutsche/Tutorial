package de.postcrafter.tutorial.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.postcrafter.tutorial.Tutorial;

public class JoinListener implements Listener {

	private Tutorial plugin;
	
	public JoinListener(Tutorial plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.isOp()) {
			e.setJoinMessage("§6"+p.getName()+" §7hat den Server betreten.");
		} else {
			e.setJoinMessage(null);
		}
		this.plugin.reloadConfig();
		for (String msg : this.plugin.getConfig().getStringList("info.motd")) {
			p.sendMessage(msg);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (p.isOp()) {
			e.setQuitMessage("§6"+p.getName()+" §7hat den Server verlassen.");
		} else {
			e.setQuitMessage(null);
		}
	}
	
}
