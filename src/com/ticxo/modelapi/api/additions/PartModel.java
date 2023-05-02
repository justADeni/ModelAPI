package com.ticxo.modelapi.api.additions;

import java.util.ArrayList;
import java.util.List;

import us.fihgu.toolbox.resourcepack.model.DisplayEntry;
import us.fihgu.toolbox.resourcepack.model.DisplayOptions;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.ModelElement;

public class PartModel extends ItemModel{

	private double[] rotation = new double[3],
			translation = {0, -6.4, 0},
			scale = {2.285, 2.285, 2.285};
	private List<ModelElement> elements = new ArrayList<ModelElement>();
	
	public PartModel(double[] r) {
		rotation = r;
	}
	
	public PartModel(double rX, double rY, double rZ) {
		rotation[0] = rX;
		rotation[1] = rY;
		rotation[2] = rZ;
	}
	
	public void setElements(List<ModelElement> elements) {
		this.elements = elements;
	}
	
	public void addTranslation(double[] translation) {
		this.translation[0] += translation[0];
		this.translation[1] += translation[1];
		this.translation[2] += translation[2];
	}
	
	public ItemModel createPart(String texture) {
		
		ItemModel model = new ItemModel();
		
		addElements(model);
		
		model.putTexture("layer0", texture);
		
		DisplayOptions display = new DisplayOptions();
		display.setHead(new DisplayEntry(rotation, translation, scale));
		model.setDisplay(display);
		
		return model;
		
	}
	
	private void addElements(ItemModel model) {
		for(ModelElement e : elements) {
			model.addElements(e);
		}
	}
	
}
