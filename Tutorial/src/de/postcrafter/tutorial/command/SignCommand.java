package de.postcrafter.tutorial.command;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SignCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage("§cDu musst ein Spieler sein!");
			return true;
		}
		if (args.length >= 2) {
			try {
				int zeile = Integer.valueOf(args[0]);
				Player p = (Player) cs;
				Block b = p.getTargetBlock(null, 10);
				if (b == null) {
					cs.sendMessage("§cDu musst auf einen Block gucken!");
					return true;
				}
				BlockState bs = b.getState();
				if (bs instanceof Sign) {
					Sign sign = (Sign) bs;
					String text = "";
					for (int i = 1; i < args.length; i++) {
						text = text + args[i];
						if (i + 1 < args.length) {
							text = text + " ";
						}
					}
					try {
						sign.setLine(zeile, text);
						sign.update();
						cs.sendMessage("§7Der Text wurde zu §6" + sign.getLine(zeile) + " §7geändert!");
					} catch (ArrayIndexOutOfBoundsException exc) {
						cs.sendMessage("§cUngültige Zahl");
						return true;
					}
				} else {
					cs.sendMessage("§cDu musst auf ein Schild gucken!");
					return true;
				}
			} catch (NumberFormatException nfe) {
				cs.sendMessage("§cDu musst eine Zahl angeben!");
				return true;
			}
		} else {
			cs.sendMessage("§c/sign [Zeile] [Text]");
		}
		return true;
	}

}
