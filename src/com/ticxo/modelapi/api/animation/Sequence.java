package com.ticxo.modelapi.api.animation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.EulerAngle;

import com.ticxo.modelapi.math.Quaternion;

public class Sequence {

	private List<KeyFrame> keys = new ArrayList<KeyFrame>();
	private int stage = 0;
	
	public Sequence(List<KeyFrame> keys) {
		this.keys = keys;
	}
	
	public EulerAngle getRotation(int f) {
		
		double frame = f;
		frame %= getLength() + 1;
		for(int i = keys.size() - 1; i >= 0; i--) {
			if(frame >= keys.get(i).getFrame()) {
				stage = i % (keys.size() - 1);
				break;
			}
		}
		
		return Quaternion.slerp(keys.get(stage).getRotation(), keys.get(stage + 1).getRotation(), (frame - keys.get(stage).getFrame()) / (keys.get(stage + 1).getFrame() - keys.get(stage).getFrame()));
		
	}
	
	public int getLength() {
		return keys.get(keys.size() - 1).getFrame();
	}
	
	public Sequence createSequence() {
		return new Sequence(keys);
	}
	
}
