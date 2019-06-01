package com.ticxo.modelapi.api.modeling;

import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Offset {

	private double x, y, z;

	public Offset(double x, double y, double z) {

		set(x, y, z);

	}

	public static Offset lerp(Offset start, Offset end, double t) {
		
		return new Offset(start.getX() * (1 - t) + end.getX() * t, start.getY() * (1 - t) + end.getY() * t, start.getZ() * (1 - t) + end.getZ() * t);
		
	}
	
	public void set(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;

	}

	public void add(double x, double y, double z) {

		this.x += x;
		this.y += y;
		this.z += z;

	}

	public void add(Offset o) {

		this.x += o.getX();
		this.y += o.getY();
		this.z += o.getZ();

	}

	public double getX() {

		return x;

	}

	public double getY() {

		return y;

	}

	public double getZ() {

		return z;

	}
	
	public Location getRelativeLocation(Location point) {
		
		Vector offset = new Vector(x, y, z);
		
		offset = rotatePitch(offset, point.getPitch());
		offset = rotateYaw(offset, point.getYaw());
		//offset = rotateRoll(offset, point.getPitch());
		point.add(offset);
		point.setYaw(0);
		point.setPitch(0);
		
		return point;
		
	}

	public Location getRelativeLocation(Location point, EulerAngle angle) {
		
		Vector offset = new Vector(x, y, z);
		
		offset = rotatePitch(offset, (float) Math.toDegrees(angle.getX()));
		offset = rotateYaw(offset, (float) Math.toDegrees(angle.getY()));
		offset = rotateRoll(offset, (float) Math.toDegrees(angle.getZ()));
		point.add(offset);
		point.setYaw(0);
		point.setPitch(0);
		
		return point;
		
	}
	//Rz
	private Vector rotateRoll(Vector vec, float roll) {
		
		double rRoll = Math.toRadians(roll);
		double x = vec.getX();
		double y = vec.getY();
		
		vec.setX((x * Math.cos(rRoll)) + (y * Math.sin(rRoll)));
		vec.setY(-(x * Math.sin(rRoll)) + (y * Math.cos(rRoll)));
		
		return vec;
		
	}
	//Rx
	private Vector rotatePitch(Vector vec, float pitch) {
		
		double rPitch = Math.toRadians(pitch);
		double y = vec.getY();
		double z = vec.getZ();
		
		vec.setY((y * Math.cos(rPitch)) - (z * Math.sin(rPitch)));
		vec.setZ((y * Math.sin(rPitch)) + (z * Math.cos(rPitch)));
		
		return vec;
		
	}
	//Ry
	private Vector rotateYaw(Vector vec, float yaw) {
		
		double rYaw = Math.toRadians(yaw);
		double x = vec.getX();
		double z = vec.getZ();
		
		vec.setX((x * Math.cos(rYaw)) - (z * Math.sin(rYaw)));
		vec.setZ((x * Math.sin(rYaw)) + (z * Math.cos(rYaw)));
		
		return vec;
			
	}
}






















