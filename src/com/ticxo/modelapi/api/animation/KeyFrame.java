package com.ticxo.modelapi.api.animation;

import org.bukkit.util.EulerAngle;

public class KeyFrame {

	private EulerAngle rotation;
	private int frame;
	
	public KeyFrame(int frame, EulerAngle rotation) {
		
		setFrame(frame);
		setRotation(rotation);
		
	}

	public EulerAngle getRotation() {
		return rotation;
	}

	public int getFrame() {
		return frame;
	}

	public void setRotation(EulerAngle rotation) {
		this.rotation = rotation;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}
	
	
	
}
