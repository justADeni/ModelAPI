package com.ticxo.modelapi.api.animation.joints;

import java.util.List;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

import com.ticxo.modelapi.api.animation.Joint;
import com.ticxo.modelapi.api.modeling.Offset;
import com.ticxo.modelapi.api.modeling.Part;
import com.ticxo.modelapi.math.Quaternion;

public class SubHeadJoint implements Joint{

	@Override
	public void entityParentConnection(Entity parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body, List<String> state) {
		
	}

	@Override
	public void partParentConnection(ArmorStand parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body, List<String> state) {
		
		Offset pos = part.getLocationOffset();
		
		target.teleport(pos.getRelativeLocation(parent.getLocation(), parent.getHeadPose()));
		target.setHeadPose(Quaternion.combine(part.getRotationOffset(), head));
		
	}

	@Override
	public Joint createAnimation() {
		return new SubHeadJoint();
	}

}
