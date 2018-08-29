package com.ticxo.modelapi.api;

import java.util.ArrayList;
import java.util.List;

public class ModelBase {

	public String modelId;
	public Integer textureSize;

	private List<ModelRenderer> parts = new ArrayList<ModelRenderer>();
	
	public ModelBase(String id, Integer size) {
		
		this.modelId = id;
		this.textureSize = size;
		
	}

	public void addPart(ModelRenderer model) {
		
		parts.add(model);
		
	}
	
	public void generateJsons() {
		
		for(ModelRenderer m : parts) m.generateModel();
		
	}
	
	public void createModel() {

		
		
	}

}
