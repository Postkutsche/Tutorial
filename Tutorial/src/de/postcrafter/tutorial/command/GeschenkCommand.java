package de.postcrafter.tutorial.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GeschenkCommand implements CommandExecutor {

	File file = new File("plugins/Tutorials", "geschenk.yml");
	FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage("§cDu kannst kein Geschenk bekommen, du hast kein Inventar.");
			return true;
		}
		Player p = (Player) cs;
		List<String> list = this.cfg.getStringList("geschenke");
		if (list == null) {
			list = new ArrayList<String>();
		}
		if (list.contains(p.getName())) {
			p.sendMessage("§7Du hattest schon ein Geschenk.");
			return true;
		}
		PlayerInventory pi = p.getInventory();
		if (pi.firstEmpty() == -1) {
			p.sendMessage("§7Dein Inventar ist voll.");
			return true;
		}
		list.add(p.getName());
		this.cfg.set("geschenke", list);
		try {
			this.cfg.save(file);
			pi.addItem(new ItemStack(Material.DIAMOND));
			p.sendMessage("§/Hier hast du dein Geschenk.");
			p.updateInventory();
		} catch (IOException e) {
			e.printStackTrace();
			cs.sendMessage("§cEs ist ein Fehler aufgetreten!");
		}
		return false;
	}

}
