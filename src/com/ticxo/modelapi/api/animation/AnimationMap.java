package com.ticxo.modelapi.api.animation;

import java.util.HashMap;
import java.util.Map;

public class AnimationMap {
	
	private Map<String, Animation> nodes = new HashMap<String, Animation>();
	
	public void setNodes(Map<String, Animation> nodes) {
		this.nodes = nodes;
	}
	
	public void addNode(String nodeId, Animation node) {
		nodes.put(nodeId, node);
	}
	
	public Animation getAnimation(String state) {
		return nodes.get(state);
	}
	
	public Map<String, Animation> getAnimations(){
		return nodes;
	}
	
	public AnimationMap getNewAnimationTree() {
		AnimationMap am = new AnimationMap();
		am.setNodes(nodes);
		return am;
	}
	
}
