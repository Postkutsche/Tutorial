package de.postcrafter.tutorial.listener;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import de.postcrafter.tutorial.SignSaver;

public class SignListener implements Listener {

	@EventHandler
	public void onSign(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("[SignSaver]")) {
			SignSaver save = SignSaver.get(e.getBlock());
			if (save != null) {
				for (int i = 0; i < 4; i++) {
					e.setLine(i, save.getLine(i));
				}
			}
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		BlockState bs = e.getBlock().getState();
		if (bs instanceof Sign) {
			SignSaver.create((Sign) bs);
			e.getPlayer().sendMessage("§6SignSave §7erstellt.");
		}
	}
}
