package com.ticxo.modelapi.api.modeling;

import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.api.additions.EntityModelPart;
import com.ticxo.modelapi.api.animation.AnimationMap;
import us.fihgu.toolbox.item.DamageableItem;

import java.util.HashMap;
import java.util.Map;

public class ModelBase {

	private String id, texture;
	private SkeletonModel skeleton;
	private AnimationMap animation;
	private Map<String, Part> parts = new HashMap<>();
	
	public ModelBase(String id, String texture) {
		this.id = id.toLowerCase();
		this.texture = texture.toLowerCase();
	}
	
	public void addPart(Part part) {
		parts.put(part.getModelName(), part);
	}
	
	public EntityModelPart createPart(String partName, DamageableItem dItem) {
		EntityModelPart emp = new EntityModelPart(dItem, id + "/" + partName.toLowerCase(), texture, partName.toLowerCase());
		ModelManager.registerModelPart(emp);
		return emp;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setSkeletonModel(SkeletonModel skeleton) {
		this.skeleton = skeleton;
	}
	
	public void setAnimationMap(AnimationMap animation) {
		this.animation = animation;
	}
	
	public String getId() {
		return id;
	}
	
	public Map<String, Part> getParts(){
		return parts;
	}
	
	public SkeletonModel getSkeltonModel() {
		return skeleton;
	}
	
	public AnimationMap getAnimationTree() {
		return animation;
	}
	
}
