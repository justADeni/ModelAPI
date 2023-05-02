package com.ticxo.modelapi.api.modeling;

import com.ticxo.modelapi.ModelAPI;
import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.api.animation.Animation;
import com.ticxo.modelapi.api.animation.AnimationMap;
import com.ticxo.modelapi.tool.ASI;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModelEntity {

	private Entity ent;
	private EulerAngle head = new EulerAngle(0, 0, 0);
	private EulerAngle body = new EulerAngle(0, 0, 0);
	private Map<Part, ArmorStand> model = new LinkedHashMap<>();
	private Map<String, Part> parts = new LinkedHashMap<>();
	private Vector preVec = null;
	private String modelId;
	private List<String> state = new ArrayList<>();
	private SkeletonModel skeleton;
	private AnimationMap animation;
	private boolean render = true;

	public ModelEntity(Entity ent, String id, boolean addition) {

		ent.setMetadata("modeled", new FixedMetadataValue(ModelAPI.plugin, true));
		this.ent = ent;
		this.modelId = id;
		setVisible(addition);
		ModelManager.applyModel(this);

	}

	public ModelEntity(Entity ent, String id, boolean addition, boolean render) {

		ent.setMetadata("modeled", new FixedMetadataValue(ModelAPI.plugin, true));
		this.ent = ent;
		this.modelId = id;
		this.render = render;
		setVisible(addition);
		ModelManager.applyModel(this);

	}

	public void setRender(boolean render) {

		this.render = render;

	}

	public void setVisible(boolean visible) {

		if (ent instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) ent;
			//TODO: replace this with .setInvisible() maybe?
			if (visible) {
				le.removePotionEffect(PotionEffectType.INVISIBILITY);
			} else {
				le.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false), true);
			}
		}

	}

	public void remove(boolean kill) {

		if(kill) ent.remove();
		for (Map.Entry<Part, ArmorStand> modelData : model.entrySet()) {
			if (!modelData.getValue().isDead())
				modelData.getValue().remove();
		}
		model.clear();

	}
	
	public void teleportModel() {
		
		updateRotation();
		if(animation == null)
			return;
		if(skeleton.getHead() != null)
			teleportModel(skeleton.getHead(), ent);
		if(skeleton.getBody() != null)
			teleportModel(skeleton.getBody(), ent);
		
	}
	
	public void teleportModel(Bone bone, Entity ent) {
		
		Part part = parts.get(bone.getName());
		ArmorStand target = model.get(part);
		Animation a = null;
		for(String s : animation.getAnimations().keySet())
			if(state.contains(s) && animation.getAnimation(s).containsPartAnimation(part))
				a = animation.getAnimation(s);
		
		if(a != null)
			if(ent.equals(this.ent))
				a.entityParentConnection(ent, target, part, head, body);
			else
				a.partParentConnection((ArmorStand) ent, target, part, head, body);
		
		if(bone.getChilds().isEmpty()) return;
		for(Bone child : bone.getChilds())
			teleportModel(child, target);
		
	}

	public void generateModel() {

		ModelBase mb = ModelManager.getModel(modelId);
		skeleton = mb.getSkeltonModel();
		animation = mb.getAnimationTree() != null ? mb.getAnimationTree().getNewAnimationTree() : null;
		parts = mb.getParts();
		preVec = ent.getLocation().toVector();
		for (Map.Entry<String, Part> partData : parts.entrySet()) {
			ArmorStand m = (ArmorStand) ent.getWorld().spawnEntity(partData.getValue().getLocationOffset().getRelativeLocation(ent.getLocation()), EntityType.ARMOR_STAND);
			m = ASI.applyModel(ASI.iniArmorStand(m, partData.getKey()), partData.getValue());
			model.put(partData.getValue(), m);
		}

	}
	
	public void addState(String state) {
		if(!this.state.contains(state)) this.state.add(state);
	}
	
	public void removeState(String state) {
		if(this.state.contains(state)) this.state.remove(state);
	}

	public boolean containState(String state) {
		return this.state.contains(state);
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
	
	public List<String> getStates(){
		return state;
	}
	
	private void updateRotation() {
		
		head = new EulerAngle(Math.toRadians(ent.getLocation().getPitch() % 360), Math.toRadians(ent.getLocation().getYaw() % 360), 0);
		if(preVec != null && !preVec.equals(ent.getLocation().toVector().setY(0))) {
			body = new EulerAngle(0, Math.toRadians((ent.getLocation().getYaw() + clamp(angleDiff(Math.toRadians(ent.getLocation().getYaw()), getAngle(ent.getLocation().toVector().subtract(preVec))), -50, 50) + 360) % 360), 0);
			preVec = ent.getLocation().toVector();
			preVec.setY(0);
			removeState("idle");
			addState("walk");
		}else {
			body = new EulerAngle(0, Math.toRadians((ent.getLocation().getYaw() + clamp(angleDiff(Math.toRadians(ent.getLocation().getYaw()), body.getY()), -50, 50) + 360) % 360), 0);
			removeState("walk");
			addState("idle");
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
