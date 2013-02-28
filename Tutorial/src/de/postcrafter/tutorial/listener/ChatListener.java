package de.postcrafter.tutorial.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (e.getPlayer().hasPermission("chat.color")) {
			e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			e.getPlayer().setDisplayName("§6"+e.getPlayer().getName());
		}
		e.setFormat("%1$s: §f%2$s");
	}
	
}
