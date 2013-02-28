package de.postcrafter.tutorial;

import java.util.List;

import org.bukkit.Bukkit;

public class MessageRunnable implements Runnable {

	private int counter = 0;
	private List<String> messages;
	
	public MessageRunnable(List<String> messages) {
		this.messages = messages;
	}
	
	@Override
	public void run() {
		if (this.counter < this.messages.size()) {
			Bukkit.broadcastMessage(this.messages.get(this.counter));
			this.counter++;
		} else {
			this.counter = 0;
		}
	}

}
