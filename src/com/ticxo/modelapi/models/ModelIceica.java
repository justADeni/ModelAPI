package com.ticxo.modelapi.models;

import com.ticxo.modelapi.api.ModelBase;
import com.ticxo.modelapi.api.ModelRenderer;

public class ModelIceica extends ModelBase {

	ModelRenderer head;
	ModelRenderer body;
	ModelRenderer rightarm;
	ModelRenderer leftarm;
	ModelRenderer rightleg;
	ModelRenderer leftleg;
	ModelRenderer hip;

	public ModelIceica() {
		super("iceica", 128);
		
		head = new ModelRenderer(this, 0, 0, "head");
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 2F, 0F);
		head.setMirror(false);
		head.setRotation(0F, 0F, 0F);
		addPart(head);

		body = new ModelRenderer(this, 16, 16, "body");
		body.addBox(-3F, 0F, -1.5F, 6, 8, 3);
		body.setRotationPoint(0F, 2F, 0F);
		body.setMirror(false);
		body.setRotation(0F, 0F, 0F);
		addPart(body);

		rightarm = new ModelRenderer(this, 40, 16, "rightarm");
		rightarm.addBox(-1F, -2F, -1.5F, 2, 11, 3);
		rightarm.setRotationPoint(-4F, 4F, 0F);
		rightarm.setMirror(false);
		rightarm.setRotation(0F, 0F, 0.1963495F);
		addPart(rightarm);

		leftarm = new ModelRenderer(this, 40, 16, "leftarm");
		leftarm.addBox(-1F, -2F, -1.5F, 2, 11, 3);
		leftarm.setRotationPoint(4F, 4F, 0F);
		leftarm.setRotation(0F, 0F, -0.1963495F);
		leftarm.setMirror(true);
		addPart(leftarm);

		rightleg = new ModelRenderer(this, 0, 16, "rightleg");
		rightleg.addBox(-1.5F, 0F, -1.5F, 3, 11, 3);
		rightleg.setRotationPoint(-2F, 13F, 0F);
		rightleg.setMirror(false);
		rightleg.setRotation(0F, 0F, 0F);
		addPart(rightleg);

		leftleg = new ModelRenderer(this, 0, 16, "leftleg");
		leftleg.addBox(-1.5F, 0F, -1.5F, 3, 11, 3);
		leftleg.setRotationPoint(2F, 13F, 0F);
		leftleg.setRotation(0F, 0F, 0F);
		leftleg.setMirror(true);
		addPart(leftleg);

		hip = new ModelRenderer(this, 16, 32, "hip");
		hip.addBox(-4F, 8F, -1.5F, 8, 3, 4);
		hip.setRotationPoint(0F, 2F, -0.5F);
		hip.setMirror(false);
		hip.setRotation(0F, 0F, 0F);
		addPart(hip);
		
	}
}
