package com.ticxo.modelapi.api.additions;

import com.chrismin13.additionsapi.items.CustomItem;
import us.fihgu.toolbox.item.DamageableItem;
import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.resourcepack.model.ModelAxis;
import us.fihgu.toolbox.resourcepack.model.ModelElement;
import us.fihgu.toolbox.resourcepack.model.Predicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityModelPart extends CustomItem implements ModelInjector{

	private String defaultTexture, partName;
	private double[] 
			rotation = new double[3],
			translation = new double[3];
	private List<ModelElement> elements = new ArrayList<ModelElement>();
	private final HashMap<String, Short> overrideModels = new HashMap<String, Short>();
	
	public EntityModelPart(DamageableItem dItem, String idName, String defaultTexture, String partName) {
		super(dItem.getMaterial(), 1, (short) 0, idName);
		this.defaultTexture = defaultTexture;
		this.partName = partName;
		this.addTexture(defaultTexture);
		setDisplayName(idName);
		setUnbreakable(true);
		setUnbreakableVisibility(false);
	}
	
	public void addBox(double x, double y, double z, double ox, double oy, double oz, ModelAxis axis, double angle, double rx, double ry, double rz, int t, int tx, int ty) {
		addBox(new Element(x, y, z, ox, oy, oz, axis, angle, rx, ry, rz, t, tx, ty));
	}
	
	public void addBox(double x, double y, double z, double ox, double oy, double oz, ModelAxis axis, double angle, double rx, double ry, double rz, int t, int tx, int ty, double bx, double by, double bz) {
		addBox(new Element(x, y, z, ox, oy, oz, axis, angle, rx, ry, rz, t, tx, ty, bx, by, bz));
	}
	
	public void addBox(Element e) {
		addBox(e.createModelElement());
	}
	
	public void addBox(ModelElement e) {
		elements.add(e);
	}
	
	public void setRotation(double x, double y, double z) {
		rotation[0] = x;
		rotation[1] = y;
		rotation[2] = z;
	}
	
	public void addTranslation(double x, double y, double z) {
		translation[0] = x;
		translation[1] = y;
		translation[2] = z;
	}
	
	@Override
	public HashMap<String, Short> getAllTextures() {
		return overrideModels;
	}

	@Override
	public HashMap<ModelInjection, Short> getOverrideEntries() {
		final HashMap<ModelInjection, Short> map = new HashMap<ModelInjection, Short>();
		String name = this.getIdName().split(":")[0];
		for (String texture : overrideModels.keySet()) {
			texture = texture.toLowerCase();
			map.put(new ModelInjection(new Predicate(), name + ":item/" + texture + "/" + partName,
					createPartModel().createPart(name + ":entity/" + texture)),
					overrideModels.get(texture));
		}
		return map;
	}

	private PartModel createPartModel() {
		
		PartModel model = new PartModel(rotation);
		model.setElements(elements);
		model.addTranslation(translation);
		
		return model;
	}

	@Override
	public String getDefaultTexture() {
		return new String(defaultTexture);
	}

}
