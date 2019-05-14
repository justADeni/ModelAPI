package com.ticxo.modelapi.api.additions;

import us.fihgu.toolbox.resourcepack.model.ElementFaceEntry;
import us.fihgu.toolbox.resourcepack.model.ElementFaceOptions;
import us.fihgu.toolbox.resourcepack.model.ElementRotation;
import us.fihgu.toolbox.resourcepack.model.ModelAxis;
import us.fihgu.toolbox.resourcepack.model.ModelElement;
import us.fihgu.toolbox.resourcepack.model.ModelFace;

public class Element {

	ModelAxis axis;
	double angle;
	double[] from = {8, 8, 8},
			to = {8, 8, 8},
			origin = {8, 8, 8};
	double[][] uvs = new double[6][5];
	
	public Element(double x, double y, double z, double ox, double oy, double oz, ModelAxis axis, double angle, double rx, double ry, double rz, int t, int tx, int ty) {
		
		angle = Math.round(angle*(2d/45d));
		convertBox(x, y, z, ox, oy, oz);
		createUVs(t, tx, ty, x, y, z);
		setRotation(axis, (int) angle, rx, ry, rz);
		
	}
	
	public Element(double x, double y, double z, double ox, double oy, double oz, ModelAxis axis, double angle, double rx, double ry, double rz, int t, int tx, int ty, double bx, double by, double bz) {
		
		angle = Math.round(angle*(2d/45d));
		convertBox(x, y, z, ox, oy, oz);
		createUVs(t, tx, ty, bx, by, bz);
		setRotation(axis, (int) angle, rx, ry, rz);
		
	}
	
	public ModelElement createModelElement() {
		
		ElementRotation rotation = new ElementRotation(origin, axis, angle, false);
		ElementFaceOptions faces = new ElementFaceOptions();
		faces.setNorth(new ElementFaceEntry(getUV(0), "layer0", ModelFace.north, uvs[0][4], null));
		faces.setEast(new ElementFaceEntry(getUV(1), "layer0", ModelFace.east, uvs[1][4], null));
		faces.setSouth(new ElementFaceEntry(getUV(2), "layer0", ModelFace.south, uvs[2][4], null));
		faces.setWest(new ElementFaceEntry(getUV(3), "layer0", ModelFace.west, uvs[3][4], null));
		faces.setUp(new ElementFaceEntry(getUV(4), "layer0", ModelFace.up, uvs[4][4], null));
		faces.setDown(new ElementFaceEntry(getUV(5), "layer0", ModelFace.down, uvs[5][4], null));
		ModelElement e = new ModelElement(from, to, rotation, true, faces);
		
		return e;
	}
	
	private void convertBox(double x, double y, double z, double ox, double oy, double oz) {
		
		from[0] += ox;
		from[1] += oy;
		from[2] += oz;
		
		to[0] += x + ox;
		to[1] += y + oy;
		to[2] += z + oz;
		
	}
	
	private void setRotation(ModelAxis axis, int a, double rx, double ry, double rz) {
		
		a = (16 + a) % 16;
		this.axis = axis;
		this.angle = (((a + 2) * 22.5f) % 90f) - 45; //counter-clockwise, 16 = 1 rotation, -2 <= face <= 1
		origin[0] += rx;
		origin[1] += ry;
		origin[2] += rz;
				
		cycleUVs(axis, (int) Math.floor((a + 2) / 4));
		
	}
	
	private void cycleUVs(ModelAxis axis, int d) {
		
		for(int i = 0; i < d; i++) {
			double[] temp;
			switch(axis) {
			case x:
				temp = uvs[0];
				uvs[0] = uvs[5];
				uvs[5] = uvs[2];
				uvs[2] = uvs[4];
				uvs[4] = temp;
				break;
			case y:
				temp = uvs[0];
				uvs[0] = uvs[1];
				uvs[1] = uvs[2];
				uvs[2] = uvs[3];
				uvs[3] = temp;
				break;
			case z:
				temp = uvs[4];
				uvs[4] = uvs[1];
				uvs[1] = uvs[5];
				uvs[5] = uvs[3];
				uvs[3] = temp;
				break;
			}
		}
		rotateUV(axis, d);
		
	}
	
	private void createUVs(float t, float tx, float ty, double x, double y, double z) {
		
		tx *= 16/t;
		ty *= 16/t;
		x *= 16/t;
		y *= 16/t;
		z *= 16/t;
		
		setUV(uvs[0], tx + z, ty + z, x, y, 0);
		setUV(uvs[1], tx, ty + z, z, y, 0);
		setUV(uvs[2], tx + z + x + z, ty + z, x, y, 0);
		setUV(uvs[3], tx + z + x, ty + z, z, y, 0);
		setUV(uvs[4], tx + z, ty, x, z, 180);
		setUV(uvs[5], tx + z + x, ty, x, z, 180);
		
	}
	
	private void rotateUV(ModelAxis axis, int d) {
		int[][] xRot = {
				{0, 0, 0, 0, 180, 180},
				{0, 270, 180, 90, 180, 0},
				{180, 180, 180, 180, 180, 180},
				{0, 90, 180, 270, 0, 180}
		};
		int[][] zRot = {
				{0, 0, 0, 0, 180, 180},
				{90, 90, 270, 90, 270, 270},
				{180, 180, 180, 180, 0, 0},
				{270, 270, 90, 270, 90, 90}
		};
		switch(axis) {
		case x:
			for(int i = 0; i < 6; i++) {
				uvs[i][4] = xRot[d % 4][i];
			}
			break;
		case y:
			for(int i = 0; i < d; i++) {
				uvs[4][4] -= +90;
				uvs[5][4] += 90;
			}
			break;
		case z:
			for(int i = 0; i < 6; i++) {
				uvs[i][4] = zRot[d % 4][i];
			}
			break;
		}
		normalizeUV();
	}
	
	private void setUV(double[] face, double d, double e, double x, double y, float rot) {
		
		face[0] = d;
		face[1] = e;
		face[2] = d + x;
		face[3] = e + y;
		face[4] = rot;
		
	}
	
	private void normalizeUV() {
		for(int i = 0; i < 6; i++) {
			uvs[i][4] = (360 + uvs[i][4]) % 360;
		}
	}
	
	private double[] getUV(int i) {
		return new double[] {uvs[i][0], uvs[i][1], uvs[i][2], uvs[i][3]};
	}
	
}
