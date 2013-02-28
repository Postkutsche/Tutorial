package de.postcrafter.tutorial.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		Entity en0 = e.getDamager();
		if (en0 instanceof Player) {
			Entity en1 = e.getEntity();
			if (en1 instanceof Player) {
				Player p0 = (Player) en0;
				Player p1 = (Player) en1;
				String msg = "§6"+p1.getName()+"§7's Lebensanzeige: §4";
				for (int i = 0; i < p1.getHealth(); i++) {
					msg = msg + "|";
				}
				p0.sendMessage(msg);
			}
		}
	}
	
}
