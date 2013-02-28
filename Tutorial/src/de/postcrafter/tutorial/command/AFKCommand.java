package de.postcrafter.tutorial.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.postcrafter.tutorial.event.AFKToggleEvent;

public class AFKCommand implements CommandExecutor {

	private List<String> afk = new ArrayList<String>();
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage("§cDu musst ein Spieler sein!");
			return true;
		}
		boolean wasAFK = this.afk.contains(cs.getName());
		boolean toAFK = !wasAFK;
		
		String message = null;
		if (args.length > 0) {
			message = args[0];
			for (int i = 1; i < args.length; i++) {
				message += " " + args[i];
			}
		}
		
		AFKToggleEvent event = new AFKToggleEvent((Player) cs, wasAFK, toAFK, message);
		Bukkit.getPluginManager().callEvent(event);
		
		if (event.wasAFK() != event.toAFK()) {
			Player p = event.getPlayer();
			if (event.toAFK()) {
				this.afk.add(p.getName());
				Bukkit.broadcastMessage("§6" + p.getName() + "§7 ist jetzt afk" + (event.getMessage() != null ? " (§6" + event.getMessage() + "§7)." : "."));
			} else {
				this.afk.remove(p.getName());
				Bukkit.broadcastMessage("§6" + p.getName() + "§7 ist jetzt nicht mehr afk.");
			}
		}
		return false;
	}

}
