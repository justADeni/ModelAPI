package com.ticxo.modelapi.tool;

import org.bukkit.entity.ArmorStand;
import org.bukkit.metadata.FixedMetadataValue;

import com.ticxo.modelapi.ModelAPI;
import com.ticxo.modelapi.api.modeling.Part;

public class ASI {

	public static ArmorStand iniArmorStand(ArmorStand a, String partId) {
		
		a.setInvulnerable(true);
		a.setArms(true);
		a.setBasePlate(false);
		a.setGravity(false);
		a.setSilent(true);
		a.setVisible(false);
		a.setMetadata("partId", new FixedMetadataValue(ModelAPI.plugin, partId));
		
		return a;
		
	}
	
	public static ArmorStand applyModel(ArmorStand a, Part p) {
		
		a.setHelmet(p.getItem());
		
		return a;
		
	}
	
}
