package com.ticxo.modelapi.api.animation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.EulerAngle;

import com.ticxo.modelapi.api.modeling.Offset;
import com.ticxo.modelapi.math.Quaternion;

public class Sequence {

	private List<KeyFrame> keys = new ArrayList<KeyFrame>();
	
	public Sequence(List<KeyFrame> keys) {
		this.keys = keys;
	}
	
	public EulerAngle getRotation(int f) {
		
		double frame = f;
		int stage = 0;
		frame %= getLength() + 1;
		for(int i = keys.size() - 1; i >= 0; i--) {
			if(frame >= keys.get(i).getFrame()) {
				stage = i % (keys.size() - 1);
				break;
			}
		}
		
		double t = (frame - keys.get(stage).getFrame()) / (keys.get(stage + 1).getFrame() - keys.get(stage).getFrame());
		
		return Quaternion.slerp(keys.get(stage).getRotation(), keys.get(stage + 1).getRotation(), t);
		
	}
	
	public Offset getOffset(int f) {
		
		double frame = f;
		int stage = 0;
		frame %= getLength() + 1;
		for(int i = keys.size() - 1; i >= 0; i--) {
			if(frame >= keys.get(i).getFrame()) {
				stage = i % (keys.size() - 1);
				break;
			}
		}
		
		double t = (frame - keys.get(stage).getFrame()) / (keys.get(stage + 1).getFrame() - keys.get(stage).getFrame());
		
		return Offset.lerp(keys.get(stage).getOffset(), keys.get(stage + 1).getOffset(), t);
		
	}
	
	public int getLength() {
		return keys.get(keys.size() - 1).getFrame();
	}
	
	public Sequence createSequence() {
		return new Sequence(keys);
	}
	
}
