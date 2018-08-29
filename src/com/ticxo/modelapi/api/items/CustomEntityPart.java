package com.ticxo.modelapi.api.items;

import java.util.HashMap;

import com.chrismin13.additionsapi.items.textured.CustomTexturedItem;
import com.ticxo.modelapi.api.ModelRenderer;

import us.fihgu.toolbox.item.DamageableItem;
import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.resourcepack.model.Predicate;

public class CustomEntityPart extends CustomTexturedItem {

	private final HashMap<String, Short> overrideModels = new HashMap<String, Short>();
	private ModelRenderer model;
	
	public CustomEntityPart(DamageableItem dItem, String idName, String defaultTexture, ModelRenderer model) {
		super(dItem, idName, defaultTexture);
		this.model = model;
	}

	@Override
	public HashMap<ModelInjection, Short> getOverrideEntries() {

		HashMap<ModelInjection, Short> map = new HashMap<ModelInjection, Short>();
		String name = getIdName().split(":")[0];
		for (String texture : overrideModels.keySet()) {
			texture = texture.toLowerCase();
			map.put(new ModelInjection(new Predicate(), name + ":item/" + texture,
					EntityModel.createEntityPartModel(name, model)),
					(Short) overrideModels.get(texture));
		}
		return map;

	}

}
