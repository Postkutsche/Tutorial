package de.postcrafter.tutorial.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.postcrafter.tutorial.Tutorial;

public class CallbackCommand implements CommandExecutor {

	private Tutorial plugin;
	
	public CallbackCommand(Tutorial instance) {
		this.plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage("§cDu musst ein Spieler sein.");
			return true;
		}
		final Player p = (Player) cs;
		final Location loc = p.getLocation();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
			@Override
			public void run() {
				p.teleport(loc);
				p.sendMessage("§7Du wurdest teleportiert.");
			}
		}, 10*20L);
		p.sendMessage("§7Du wirst gleich teleportiert.");
		return false;
	}

}
