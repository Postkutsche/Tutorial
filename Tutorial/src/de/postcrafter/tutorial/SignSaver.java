package de.postcrafter.tutorial;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class SignSaver {

	private String[] lines;
	
	private SignSaver(String[] lines) {
		this.lines = lines;
	}
	
	public String[] getLines() {
		return this.lines;
	}
	
	public String getLine(int index) {
		return this.lines[index];
	}
	
	public static SignSaver create(Sign sign) {
		Block b = sign.getBlock();
		SignSaver save = new SignSaver(sign.getLines());
		b.setMetadata("SignSaver", new FixedMetadataValue(Tutorial.getTutorial(), save));
		return save;
	}
	
	public static SignSaver get(Block b) {
		for (MetadataValue value : b.getMetadata("SignSaver")) {
			Object o = value.value();
			if (o instanceof SignSaver) {
				return (SignSaver) o;
			}
		}
		return null;
	}
	
	public static boolean has(Block b) {
		return SignSaver.get(b) != null;
	}
}
