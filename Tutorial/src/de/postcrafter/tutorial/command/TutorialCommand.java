package de.postcrafter.tutorial.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TutorialCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("tut")) {
			cs.sendMessage("§6Du Faulpelz, verwende den langen Befehl!");
			return true;
		}
		if (args.length == 0) {
			cs.sendMessage("§bWillkommen beim Tutorial!");
		} else if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("start")) {
				cs.sendMessage("§bDas Tutorial wird gestartet!");
			} else {
				cs.sendMessage("§cUngültiges Argument, gültige Argumente sind: start");
			}
		}
		return true;
	}
	
}
