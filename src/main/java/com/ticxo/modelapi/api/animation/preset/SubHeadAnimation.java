package com.ticxo.modelapi.api.animation.preset;

import com.ticxo.modelapi.api.animation.Animation;
import com.ticxo.modelapi.api.modeling.Offset;
import com.ticxo.modelapi.api.modeling.Part;
import com.ticxo.modelapi.math.Quaternion;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

public class SubHeadAnimation implements Animation{

	@Override
	public void entityParentConnection(Entity parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body) {
		
	}

	@Override
	public void partParentConnection(ArmorStand parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body) {
		
		Offset pos = part.getLocationOffset();
		
		target.teleport(pos.getRelativeLocation(parent.getLocation(), parent.getHeadPose()));
		target.setHeadPose(Quaternion.combine(part.getRotationOffset(), head));
		
	}

	@Override
	public Animation createAnimation() {
		return new SubHeadAnimation();
	}

	@Override
	public boolean containsPartAnimation(Part part) {
		return true;
	}

}
