package de.postcrafter.tutorial.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		Player p = null;
		if (args.length == 0) {
			if (cs instanceof Player) {
				p = (Player) cs;
			} else {
				cs.sendMessage("§cDu kannst dich als Konsole nicht heilen.");
				return true;
			}
		} else {
			String name = args[0];
			p = Bukkit.getPlayer(name);
		}
		if (p == null) {
			cs.sendMessage("§Der Spieler ist nicht online!");
			return true;
		}
		if (p != cs) {
			if (!cs.hasPermission("heal.other")) {
				cs.sendMessage("§cDu kannst andere nicht heilen!");
				return true;
			}
			cs.sendMessage("§eDu hast " + p.getName() + " geheilt.");
			p.sendMessage("§eDu wurdest von " + cs.getName() + " geheilt.");
		} else {
			if (!cs.hasPermission("heal.me")) {
				cs.sendMessage("§cDu kannst dich nicht heilen!");
				return true;
			}
			p.sendMessage("§eDu hast dich geheilt.");
		}
		p.setHealth(20);
		return true;
	}

}
