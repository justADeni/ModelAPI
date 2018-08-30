package com.ticxo.modelapi.api.items;

import java.util.HashMap;

import org.bukkit.Material;

import com.chrismin13.additionsapi.items.CustomItem;
import com.ticxo.modelapi.api.ModelRenderer;

import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.resourcepack.model.Predicate;

public class CustomEntityPart extends CustomItem implements ModelInjector {

	private final HashMap<String, Short> overrideModels = new HashMap<String, Short>();
	private ModelRenderer model;
	
	public CustomEntityPart(Material material, String idName, ModelRenderer model) {
		super(material, 1, (short) 0, idName);
		
		this.model = model;
		
	}

	@Override
	public HashMap<String, Short> getAllTextures() {

		return null;
	}

	@Override
	public String getDefaultTexture() {

		return null;
	}

	@Override
	public HashMap<ModelInjection, Short> getOverrideEntries() {

		HashMap<ModelInjection, Short> map = new HashMap<ModelInjection, Short>();
		String name = getIdName().split(":")[0];
		
		for (String texture : this.overrideModels.keySet()) {
			texture = texture.toLowerCase();
			map.put(
					new ModelInjection(
							new Predicate(), 
							name + ":item/" + texture,
							EntityModel.createEntityPartModel(name, model)),
							(Short) this.overrideModels.get(texture));
		}
		
		return map;
	}

}
