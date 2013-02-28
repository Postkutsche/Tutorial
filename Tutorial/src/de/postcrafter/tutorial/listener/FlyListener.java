package de.postcrafter.tutorial.listener;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class FlyListener implements Listener {

	private HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.STICK) {
			Long time = System.currentTimeMillis();
			if (this.cooldowns.containsKey(p.getName())) {
				Long lastUsage = this.cooldowns.get(p.getName());
				if (lastUsage + 10*1000 > time) {
					p.sendMessage("§cDu darfst noch nicht wieder hochfliegen.");
					return;				
				}
			}
			p.setVelocity(p.getVelocity().setY(5.0D));
			this.cooldowns.put(p.getName(), time);
		}
	}
}
