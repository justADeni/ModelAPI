package com.ticxo.modelapi.api;

import org.bukkit.util.EulerAngle;

public class ModelRenderer {

	private ModelBase model;
	private String partId;
	private Integer textureX;
	private Integer textureY;
	private Box box;
	private Boolean mirror;
	private Boolean render;
	private EulerAngle rotateAngle;

	public ModelRenderer(ModelBase model, Integer offsetX, Integer offsetY, String id) {

		this.model = model;
		this.partId = id;
		textureX = offsetX;
		textureY = offsetY;

	}

	// Add a new box
	// offset: offset from rotation point
	public void addBox(Float offsetX, Float offsetY, Float offsetZ, Integer sizeX, Integer sizeY, Integer sizeZ) {

		box = new Box(offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ);

	}

	// Set box rotation point/pivot
	public void setRotationPoint(Float x, Float y, Float z) {

	}

	// Stretch texture to fit box
	public void setTextureSize(Integer x, Integer y, Integer z) {

	}

	// Set if the box would render
	public void setVisible(Boolean render) {

		this.render = render;

	}

	// Rotate the box
	public void setRotation(Float x, Float y, Float z) {

		rotateAngle = new EulerAngle(x, y, z);

	}

	// Mirror the texture
	public void setMirror(Boolean mirror) {

		this.mirror = mirror;

	}

	// Generate resource pack usable model file
	public void generateModel() {

		JsonModel j = new JsonModel(this);
		j.exportJson();
		
	}

	public Box getBox() {

		return box;

	}

	public ModelBase getModel() {

		return model;

	}

	public Boolean isMirror() {

		return mirror;

	}

	public Boolean isVisible() {

		return render;

	}

	public Integer getTextureOffset(String dir) {

		if (dir.equalsIgnoreCase("x")) {
			return textureX;
		} else if (dir.equalsIgnoreCase("y")) {
			return textureY;
		}
		return null;
	}

	public EulerAngle getRotation() {

		return rotateAngle;

	}

	public String getPartId() {

		return partId;

	}
}
