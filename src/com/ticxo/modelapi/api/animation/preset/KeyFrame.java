package com.ticxo.modelapi.api.animation.preset;

import org.bukkit.util.EulerAngle;

import com.ticxo.modelapi.api.modeling.Offset;

public class KeyFrame {

	private Offset offset = new Offset(0, 0, 0);
	private EulerAngle rotation = new EulerAngle(0, 0, 0);
	private int frame;
	
	public KeyFrame(int frame, Offset offset) {
		
		setFrame(frame);
		setOffset(offset);
		
	}
	
	public KeyFrame(int frame, EulerAngle rotation) {
		
		setFrame(frame);
		setRotation(rotation);
		
	}
	
	public KeyFrame(int frame, EulerAngle rotation, Offset offset) {
		
		setFrame(frame);
		setRotation(rotation);
		setOffset(offset);
		
	}

	public EulerAngle getRotation() {
		return rotation;
	}

	public Offset getOffset() {
		return offset;
	}
	
	public int getFrame() {
		return frame;
	}

	public void setRotation(EulerAngle rotation) {
		this.rotation = rotation;
	}

	public void setOffset(Offset offset) {
		this.offset = offset;
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
	}
	
	
	
}
