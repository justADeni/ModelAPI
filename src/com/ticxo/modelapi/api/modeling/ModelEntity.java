package com.ticxo.modelapi.api.modeling;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.math.Quaternion;
import com.ticxo.modelapi.tool.ASI;

public class ModelEntity {

	private Entity ent;
	private EulerAngle head = new EulerAngle(0, 0, 0);
	private EulerAngle body = new EulerAngle(0, 0, 0);
	private Map<Part, ArmorStand> model = new HashMap<Part, ArmorStand>();
	private Map<String, Part> parts = new HashMap<String, Part>();
	private Vector preVec = null;
	private String modelId;
	private SkeletonModel skeleton;
	private boolean render = true;

	public ModelEntity(Entity ent, String id, boolean addition) {

		this.ent = ent;
		this.modelId = id;
		setVisible(addition);
		ModelManager.applyModel(this);

	}

	public ModelEntity(Entity ent, String id, boolean addition, boolean render) {

		this.ent = ent;
		this.modelId = id;
		this.render = render;
		setVisible(addition);
		ModelManager.applyModel(this);

	}

	public void setRender(boolean render) {

		this.render = render;

	}

	public void remove() {

		ent.remove();
		for (Map.Entry<Part, ArmorStand> modelData : model.entrySet()) {
			if (!modelData.getValue().isDead())
				modelData.getValue().remove();
		}
		model.clear();

	}
	
	public void teleportModel() {
		
		updateRotation();
		if(skeleton.getHead() != null)
			teleportModel(skeleton.getHead());
		if(skeleton.getBody() != null)
			teleportModel(skeleton.getBody());
		
	}
	
	public void teleportModel(Bone bone) {
		teleportModel(bone, ent);
	}
	
	public void teleportModel(Bone bone, Entity ent) {
		
		Part p = parts.get(bone.getName());
		Offset pos = p.getLocationOffset();
		ArmorStand m = model.get(p);
		
		switch(p.getPartType()) {
		case BODY:
			if(ent.equals(this.ent)) {
				m.teleport(pos.getRelativeLocation(ent.getLocation(), body));
				m.setHeadPose(Quaternion.combine(p.getRotationOffset(), body));
			}else {
				m.teleport(pos.getRelativeLocation(ent.getLocation(), ((ArmorStand) ent).getHeadPose()));
				m.setHeadPose(Quaternion.combine(p.getRotationOffset(), ((ArmorStand) ent).getHeadPose()));
			}
			break;
		case HEAD:
			if(ent.equals(this.ent)) {
				m.teleport(pos.getRelativeLocation(ent.getLocation(), head));
				m.setHeadPose(Quaternion.combine(p.getRotationOffset(), head));
			}else {
				m.teleport(pos.getRelativeLocation(ent.getLocation(), ((ArmorStand) ent).getHeadPose()));
				m.setHeadPose(Quaternion.combine(p.getRotationOffset(), ((ArmorStand) ent).getHeadPose()));
			}
			break;
		case SUBHEAD:
			m.teleport(pos.getRelativeLocation(ent.getLocation(), ((ArmorStand) ent).getHeadPose()));
			m.setHeadPose(Quaternion.combine(p.getRotationOffset(), head));
			break;
		case SEGMENT:
			
			break;
		default:
			break;
		}
		
		if(bone.getChilds().isEmpty()) return;
		for(Bone child : bone.getChilds()) {
			teleportModel(child, m);
		}
		
	}

	public void generateModel() {

		skeleton = ModelManager.getModel(modelId).getSkeltonModel();
		parts = ModelManager.getModel(modelId).getParts();
		preVec = ent.getLocation().toVector();
		for (Map.Entry<String, Part> partData : parts.entrySet()) {
			ArmorStand m = (ArmorStand) ent.getWorld().spawnEntity(partData.getValue().getLocationOffset().getRelativeLocation(ent.getLocation()),EntityType.ARMOR_STAND);
			m = ASI.applyModel(ASI.iniArmorStand(m, partData.getKey()), partData.getValue());
			model.put(partData.getValue(), m);
		}

	}

	public Entity getEntity() {

		return ent;

	}

	public String getModelId() {

		return modelId;

	}

	public boolean isRender() {

		return render;

	}

	public boolean isDead() {

		return ent.isDead() && isLocationFinite();

	}

	public boolean hasModel() {

		return !model.isEmpty();

	}

	private void setVisible(boolean visible) {

		if (ent instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) ent;
			if (visible) {
				le.removePotionEffect(PotionEffectType.INVISIBILITY);
			} else {
				le.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false), true);
			}
		}

	}
	
	private void updateRotation() {
		
		head = new EulerAngle(Math.toRadians(ent.getLocation().getPitch()), Math.toRadians(ent.getLocation().getYaw()), 0);
		if(preVec != null && !preVec.equals(ent.getLocation().toVector())) {
			body = new EulerAngle(0, getAngle(ent.getLocation().toVector().subtract(preVec)), 0);
			preVec = ent.getLocation().toVector();
		}else {
			body = new EulerAngle(0, Math.toRadians(ent.getLocation().getYaw()) + Math.toRadians(clamp(angleDiff(Math.toRadians(ent.getLocation().getYaw()), body.getY()), -50, 50)), 0);
		}
		
	}

	private boolean isLocationFinite() {

		try {
			ent.getLocation().checkFinite();
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}
	
	private double getAngle(Vector vec) {
		Location temp = ent.getLocation();
		temp.setDirection(vec);
		return Math.toRadians(temp.getYaw());
	}
	
	private double clamp(double val, double min, double max) {
		return Math.max(min, Math.min(max, val));
	}

	private double angleDiff(double pivot, double limb) {
		pivot = Math.toDegrees(pivot) > 180 ? Math.toDegrees(pivot) - 360 : Math.toDegrees(pivot);
		limb = Math.toDegrees(limb) > 180 ? Math.toDegrees(limb) - 360 : Math.toDegrees(limb);

		double diff = limb - pivot;
		if (diff > 180) {
			diff -= 360;
		} else if (diff < -180) {
			diff += 360;
		}
		return diff;

	}

}
