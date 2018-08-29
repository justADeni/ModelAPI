package com.ticxo.modelapi.api.items;

import java.util.HashMap;

import org.bukkit.Material;

import com.chrismin13.additionsapi.items.CustomItem;

import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.item.ModelInjector;

public class CustomEntityPart extends CustomItem implements ModelInjector {

	public CustomEntityPart(Material material, int amount, short durability, String idName) {
		super(material, amount, durability, idName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<String, Short> getAllTextures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultTexture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<ModelInjection, Short> getOverrideEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
