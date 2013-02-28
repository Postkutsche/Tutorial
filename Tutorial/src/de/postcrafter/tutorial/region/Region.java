package de.postcrafter.tutorial.region;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Region implements ConfigurationSerializable {
	
	private int minX, minY, minZ;
	private int maxX, maxY, maxZ;
	private World world;

	public Region(Location loc1, Location loc2) {
		this.minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		this.minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		this.minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

		this.maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		this.maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		this.maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		
		this.world = loc1.getWorld();
	}

	public boolean isInside(Location loc) {
		if (loc.getWorld() != this.world) {
			return false;
		}
		if (loc.getBlockX() >= this.minX && loc.getBlockX() <= this.maxX
				&& loc.getBlockY() >= this.minY && loc.getBlockY() <= this.maxX
				&& loc.getBlockZ() >= this.minZ && loc.getBlockZ() <= this.maxZ) {
			return true;
		}
		return false;
	}
	
	public void set(Material mat, byte damage) {
		for (int x = this.minX; x <= this.maxX; x++) {
			for (int y = this.minY; y <= this.maxY; y++) {
				for (int z = this.minZ; z <= this.maxZ; z++) {
					Block b = this.world.getBlockAt(x, y, z);
					b.setType(mat);
					b.setData(damage);
				}
			}
		}
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> o = new HashMap<String, Object>();
		
		o.put("minX", this.minX);
		o.put("minY", this.minY);
		o.put("minZ", this.minZ);

		o.put("maxX", this.maxX);
		o.put("maxY", this.maxY);
		o.put("maxZ", this.maxZ);
		
		o.put("world", this.world.getName());
		return o;
	}
}
