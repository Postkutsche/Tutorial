package de.postcrafter.tutorial.command;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import de.postcrafter.tutorial.Tutorial;

public class EconCommand implements CommandExecutor {

	private Economy econ;
	
	public EconCommand(Tutorial tutorial) {
		this.econ = tutorial.economy;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			Player p = (Player) cs;
			PlayerInventory pi = p.getInventory();
			int id;
			int amount;
			if (args.length != 2) {
				cs.sendMessage("§cFalsche Anzahl an Parametern!");
				return true;
			}
			try {
				id = Integer.valueOf(args[0]);
				amount = Integer.valueOf(args[1]); 
			} catch (NumberFormatException nfe) {
				cs.sendMessage("§cDu musst eine Zahl angeben!");
				return true;
			}
			if (label.equalsIgnoreCase("buy")) {
				if (pi.firstEmpty() == -1) {
					cs.sendMessage("§cDein Inventar ist voll!");
					return true;
				} else {
					int price = amount*10;
					if (this.econ.has(p.getName(), price)) {
						pi.addItem(new ItemStack(id, amount));
						this.econ.withdrawPlayer(p.getName(), price);
						p.updateInventory();
						return true;
					} else {
						cs.sendMessage("§cDu hast nicht genug " + this.econ.currencyNamePlural() + "!");
						return true;
					}
				}
			} else if (label.equalsIgnoreCase("sell")) {
				ItemStack item = new ItemStack(id, amount);
				if (pi.contains(item)) {
					pi.remove(item);
					this.econ.depositPlayer(p.getName(), amount*20);
					p.updateInventory();
					return true;
				} else {
					cs.sendMessage("Du hast nicht genug Item mit ID " + id);
					return true;
				}
			}
		}
		cs.sendMessage("§6Vaulttutorial");
		return true;
	}
}
