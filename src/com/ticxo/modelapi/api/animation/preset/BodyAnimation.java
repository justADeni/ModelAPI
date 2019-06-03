package com.ticxo.modelapi.api.animation.preset;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

import com.ticxo.modelapi.api.animation.Animation;
import com.ticxo.modelapi.api.modeling.Offset;
import com.ticxo.modelapi.api.modeling.Part;
import com.ticxo.modelapi.math.Quaternion;

public class BodyAnimation implements Animation{

	@Override
	public void entityParentConnection(Entity parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body) {
		
		Offset pos = part.getLocationOffset();
		Location l = parent.getLocation();
		l.setYaw(0);
		l.setPitch(0);
		l.add(pos.getX(), pos.getY() - 0.725, pos.getZ());
		
		target.teleport(l);
		target.setHeadPose(Quaternion.combine(part.getRotationOffset(), body));
		
	}

	@Override
	public void partParentConnection(ArmorStand parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body) {
		
		Offset pos = part.getLocationOffset();
		
		target.teleport(pos.getRelativeLocation(parent.getLocation(), parent.getHeadPose()));
		target.setHeadPose(Quaternion.combine(part.getRotationOffset(), parent.getHeadPose()));
		
	}

	@Override
	public Animation createAnimation() {
		return new BodyAnimation();
	}

}
