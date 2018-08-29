package com.ticxo.modelapi.api;

import org.bukkit.util.Vector;

public class Box {

	private Vector offset;
	private Vector size;
	
	public Box(Float offsetX, Float offsetY, Float offsetZ, Integer sizeX, Integer sizeY, Integer sizeZ) {

		offset = new Vector(offsetX, offsetY, offsetZ);
		size = new Vector(sizeX, sizeY, sizeZ);
		
	}

	public Vector getOffset() {
		
		return offset;
		
	}

	public Vector getSize() {
		
		return size;
		
	}
	
}
