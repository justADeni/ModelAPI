package com.ticxo.modelapi.api.mythicmobs;

import com.ticxo.modelapi.ModelAPI;
import com.ticxo.modelapi.api.modeling.ModelEntity;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.mechanics.CustomMechanic;

public class ModelStateMechanic extends SkillMechanic implements ITargetedEntitySkill{

	private String state;
	private boolean remove = false;
	
	public ModelStateMechanic(CustomMechanic holder, MythicLineConfig mlc) {
		super(holder.getConfigLine(), mlc);
		setAsyncSafe(false);
		
		state = mlc.getString("state");
		state = state.substring(1, state.length() - 1);
		remove = mlc.getBoolean("remove");
		
	}

	@Override
	public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {

		ModelEntity bukkitTarget = ModelAPI.getModelEntity(BukkitAdapter.adapt(target));
		if(remove)
			bukkitTarget.removeState(state);
		else
			bukkitTarget.addState(state);
		
		return true;
	}

}
