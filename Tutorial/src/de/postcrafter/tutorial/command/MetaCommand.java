package de.postcrafter.tutorial.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class MetaCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage("§cDu musst ein Spieler sein!");
			return true;
		}
		if (args.length < 2) {
			cs.sendMessage("§cZu wenig Argumente.");
			return true;
		}
		Player p = (Player) cs;
		ItemStack item = p.getItemInHand();
		if (item == null) {
			cs.sendMessage("§cNimm ein Item in die Hand!");
			return true;
		}
		ItemMeta meta = item.getItemMeta();
		if (args[0].equalsIgnoreCase("name")) {
			String name = "";
			for (int i = 1; i < args.length; i++) {
				name += args[i];
				if (i + 1 < args.length) {
					name += " ";
				}
			}
			meta.setDisplayName(name);
		} else if (args[0].equalsIgnoreCase("lore")){
			List<String> lore = new ArrayList<String>();
			for (int i = 1; i < args.length; i++) {
				lore.add(args[i]);
			}
			meta.setLore(lore);
		} else if (args[0].equalsIgnoreCase("book")) {
			if (args.length >= 3) {
				if (meta instanceof BookMeta) {
					File file = new File("plugins/Tutorials/Books", args[2]+".yml");
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
					BookMeta bookMeta = (BookMeta) meta;
					if (args[1].equalsIgnoreCase("save")) {
						cfg.set("title", bookMeta.getTitle());
						cfg.set("author", bookMeta.getAuthor());
						cfg.set("pages", bookMeta.getPages());
						try {
							cfg.save(file);
							cs.sendMessage("§7Buch gespeichert als §6"+args[2]+".yml§7.");
						} catch (IOException e) {
							cs.sendMessage("§cEs ist ein Fehler aufgetreten.");
							e.printStackTrace();
						}
						return true;
					} else if (args[1].equalsIgnoreCase("load")) {
						if (!file.exists()) {
							cs.sendMessage("§cDer Speicherstand dieses Buches existiert nicht.");
							return true;
						}
						bookMeta.setTitle(cfg.getString("title"));
						bookMeta.setAuthor(cfg.getString("author"));
						bookMeta.setPages(cfg.getStringList("pages"));
					} else {
						cs.sendMessage("§cUngültiger Befehl! Nur save und load möglich!");
						return true;
					}
				} else {
					cs.sendMessage("§cNimm ein Buch in die Hand!");
					return true;
				}
			} else {
				cs.sendMessage("§cZu wenig Argumente.");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("skull")) {
			if (meta instanceof SkullMeta) {
				SkullMeta skull = (SkullMeta) meta;
				skull.setOwner(args[1]);
				skull.setDisplayName("§7Kopf von §6" + args[1]);
				cs.sendMessage("§7Du hälst jetzt den Kopf von §6" + args[1] + "§7 in der Hand.");
			} else {
				cs.sendMessage("§cNimm einen Spielerkopf in die Hand!");
				return true;
			}
		} else {
			cs.sendMessage("§cFalsche Angabe.");
			return true;
		}
		item.setItemMeta(meta);
		return false;
	}

}
