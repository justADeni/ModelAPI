package com.ticxo.modelapi.api.modeling;

import com.ticxo.modelapi.api.additions.EntityModelPart;

import java.util.ArrayList;
import java.util.List;

public class Bone {

	private String name;
	private List<Bone> childs = new ArrayList<>();
	
	public Bone(String name) {
		this.name = name;
	}
	
	public Bone(EntityModelPart emp) {
		this.name = emp.getIdName().split(":")[1];
	}
	
	public void addChild(Bone bone) {
		childs.add(bone);
	}
	
	public void addChild(EntityModelPart emp) {
		childs.add(new Bone(emp));
	}
	
	public void addChilds(List<Bone> bones) {
		childs.addAll(bones);
	}
	
	public List<Bone> getChilds(){
		return childs;
	}
	
	public String getName() {
		return name;
	}
	
}
