package com.ticxo.modelapi.api.animation;

import java.util.List;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

import com.ticxo.modelapi.api.modeling.Part;

public interface Joint {

	public void entityParentConnection(Entity parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body, List<String> state);
	public void partParentConnection(ArmorStand parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body, List<String> state);
	public Joint createAnimation();
	
}
