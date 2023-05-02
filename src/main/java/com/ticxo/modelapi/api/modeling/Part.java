package com.ticxo.modelapi.api.modeling;

import com.chrismin13.additionsapi.items.CustomItemStack;
import com.ticxo.modelapi.api.additions.EntityModelPart;
import com.ticxo.modelapi.math.Quaternion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class Part {

	private String modelName;
	private EntityModelPart model;
	private Offset pos;
	private EulerAngle rot;

	public Part(EntityModelPart model, String modelName, Offset pos, Quaternion rot) {

		this.model = model;
		this.pos = pos;
		this.rot = Quaternion.toEuler(rot);
		this.modelName = modelName;

	}

	public Part(EntityModelPart model, Offset pos, Quaternion rot) {

		this.model = model;
		this.pos = pos;
		this.rot = Quaternion.toEuler(rot);
		this.modelName = model.getIdName().split(":")[1];

	}

	public Part(EntityModelPart model, String modelName, Offset pos, EulerAngle rot) {

		this.model = model;
		this.pos = pos;
		this.rot = rot;
		this.modelName = modelName;

	}

	public Part(EntityModelPart model, Offset pos, EulerAngle rot) {

		this.model = model;
		this.pos = pos;
		this.rot = rot;
		this.modelName = model.getIdName().split(":")[1];

	}

	public ItemStack getItem() {
		return new CustomItemStack(model).getItemStack();

	}

	public Offset getLocationOffset() {
		return pos;
	}

	public EulerAngle getRotationOffset() {
		return rot;
	}

	public String getModelName() {
		return modelName;
	}

}
