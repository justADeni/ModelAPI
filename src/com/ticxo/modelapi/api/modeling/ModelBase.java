package com.ticxo.modelapi.api.modeling;

import java.util.HashMap;
import java.util.Map;

import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.api.additions.EntityModelPart;

import us.fihgu.toolbox.item.DamageableItem;

public class ModelBase {

	private String id, texture;
	private SkeletonModel skeleton;
	private Map<String, Part> parts = new HashMap<String, Part>();
	
	public ModelBase(String id, String texture) {
		this.id = id;
		this.texture = texture;
	}
	
	public void addPart(Part part) {
		parts.put(part.getModelName(), part);
	}
	
	public EntityModelPart createPart(String partName, DamageableItem dItem) {
		EntityModelPart emp = new EntityModelPart(dItem, id + "/" + partName, texture, partName);
		ModelManager.registerModelPart(emp);
		return emp;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setSkeletonModel(SkeletonModel skeleton) {
		this.skeleton = skeleton;
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
	
}
