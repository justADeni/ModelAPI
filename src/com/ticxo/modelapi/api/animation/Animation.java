package com.ticxo.modelapi.api.animation;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

import com.ticxo.modelapi.api.modeling.Part;

public interface Animation {

	public void entityParentConnection(Entity parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body);
	public void partParentConnection(ArmorStand parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body);
	public Animation createAnimation();
	public boolean containsPartAnimation(Part part);
	
}
