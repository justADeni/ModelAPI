package com.ticxo.modelapi.api.mythicmobs;

import com.ticxo.modelapi.ModelAPI;
import com.ticxo.modelapi.api.modeling.ModelEntity;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;

public class ModelStateCondition extends SkillCondition implements IEntityCondition{

	private String state;
	
	public ModelStateCondition(String line, MythicLineConfig mlc) {
		super(line);
		
		state = mlc.getString(new String[] { "state", "s" });
		
	}

	@Override
	public boolean check(AbstractEntity target) {
		
		ModelEntity me = ModelAPI.getModelEntity(BukkitAdapter.adapt(target));
		if(me == null) return false;
		if(!me.containState(state)) return false;
		
		return true;
		
	}

	

}
