package de.postcrafter.tutorial.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.postcrafter.tutorial.event.AFKToggleEvent;

public class AFKListener implements Listener {

	@EventHandler
	public void onAFK(AFKToggleEvent e) {
		e.setMessage("Der böse Listener hat zugeschlagen!");
	}
	
}
