package com.ticxo.modelapi.api.animation;

import com.ticxo.modelapi.api.modeling.Part;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

public interface Animation {

	void entityParentConnection(Entity parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body);
	void partParentConnection(ArmorStand parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body);
	Animation createAnimation();
	boolean containsPartAnimation(Part part);
	
}
