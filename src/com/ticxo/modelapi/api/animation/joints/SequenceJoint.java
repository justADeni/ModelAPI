package com.ticxo.modelapi.api.animation.joints;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

import com.ticxo.modelapi.api.animation.Joint;
import com.ticxo.modelapi.api.animation.Sequence;
import com.ticxo.modelapi.api.modeling.Offset;
import com.ticxo.modelapi.api.modeling.Part;
import com.ticxo.modelapi.math.Quaternion;

public class SequenceJoint implements Joint {

	private Sequence animation;
	private String trigger = "", partType = "body";
	private int frame = 0;
	private boolean mustFinish = true;
	
	public SequenceJoint(Sequence animation, boolean mustFinish) {
		this.animation = animation;
		this.mustFinish = mustFinish;
	}
	
	public SequenceJoint(Sequence animation, String partType, boolean mustFinish) {
		this.animation = animation;
		this.partType = partType;
		this.mustFinish = mustFinish;
	}
	
	public SequenceJoint(String trigger, Sequence animation, boolean mustFinish) {
		this.trigger = trigger;
		this.animation = animation;
		this.mustFinish = mustFinish;
	}
	
	public SequenceJoint(String trigger, Sequence animation, String partType, boolean mustFinish) {
		this.trigger = trigger;
		this.animation = animation;
		this.partType = partType;
		this.mustFinish = mustFinish;
	}
	
	@Override
	public void entityParentConnection(Entity parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body, List<String> state) {
		
		Offset pos = part.getLocationOffset(),
				fpos = animation.getOffset(frame);
		
		Location l = parent.getLocation();
		l.setYaw(0);
		l.setPitch(0);
		l.add(pos.getX() + fpos.getX(), pos.getY() - 0.725 + fpos.getY(), pos.getZ() + fpos.getZ());
		
		target.teleport(l);
		EulerAngle angle = Quaternion.combine(part.getRotationOffset(), animation.getRotation(frame));
		if(partType.toLowerCase().equals("head"))
			angle = Quaternion.combine(angle, head);
		else
			angle = Quaternion.combine(angle, body);
		target.setHeadPose(angle);
		
		if(state.contains(trigger) || (mustFinish && frame > 0)) {
			frame++;
			if(frame >= animation.getLength()){
				frame = 0;
				state.remove(trigger);
			}
		}else if(trigger.equals("")) {
			frame++;
			frame %= animation.getLength();
		}
	}

	@Override
	public void partParentConnection(ArmorStand parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body, List<String> state) {

		Offset pos = part.getLocationOffset(),
				fpos = animation.getOffset(frame),
				apos = new Offset(pos.getX() + fpos.getX(), pos.getY() + fpos.getY(), pos.getZ() + fpos.getZ());
		
		target.teleport(apos.getRelativeLocation(parent.getLocation(), parent.getHeadPose()));
		EulerAngle angle = Quaternion.combine(part.getRotationOffset(), animation.getRotation(frame));
		if(partType.toLowerCase().equals("head"))
			angle = Quaternion.combine(angle, head);
		else
			angle = Quaternion.combine(angle, parent.getHeadPose());
		target.setHeadPose(angle);
		
		if(state.contains(trigger) || (mustFinish && frame > 0)) {
			frame++;
			if(frame >= animation.getLength()){
				frame = 0;
				state.remove(trigger);
			}
		}else if(trigger.equals("")) {
			frame++;
			frame %= animation.getLength();
		}
		
	}

	@Override
	public Joint createAnimation() {
		return new SequenceJoint(trigger, animation.createSequence(), partType, mustFinish);
	}

}
