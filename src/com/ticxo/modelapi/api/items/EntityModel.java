package com.ticxo.modelapi.api.items;

import java.util.LinkedList;
import java.util.List;

import com.ticxo.modelapi.api.JsonModel;
import com.ticxo.modelapi.api.ModelRenderer;

import us.fihgu.toolbox.resourcepack.model.DisplayOptions;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.Model;
import us.fihgu.toolbox.resourcepack.model.ModelElement;
import us.fihgu.toolbox.resourcepack.model.OverrideEntry;

public class EntityModel extends Model {

	protected List<OverrideEntry> overrides;

	public List<OverrideEntry> getOverrides() {
		return this.overrides;
	}

	public void setOverrides(List<OverrideEntry> overrides) {
		this.overrides = overrides;
	}

	public void addOverride(OverrideEntry entry) {
		if (this.overrides == null) {
			this.overrides = new LinkedList<OverrideEntry>();
		}
		this.overrides.add(entry);
	}

	public static ItemModel createEntityPartModel(String name, ModelRenderer model) {
		
		ItemModel part = new ItemModel();
		JsonModel jm = new JsonModel(model);
		List<ModelElement> element = jm.getElements();
		DisplayOptions display = jm.getDisplay();
		
		part.setParent(null);
		part.putTexture("layer0", name + ":entity/" + model.getModel().modelId);
		part.setElements(element);
		part.setDisplay(display);
		
		return part;
	}

}
