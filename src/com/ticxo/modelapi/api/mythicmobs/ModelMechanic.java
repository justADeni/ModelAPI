package com.ticxo.modelapi.api.mythicmobs;

import org.bukkit.entity.LivingEntity;

import com.ticxo.modelapi.ModelAPI;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.mechanics.CustomMechanic;

public class ModelMechanic extends SkillMechanic implements ITargetedEntitySkill{

	private String modelId;
	private boolean addon;
	
	public ModelMechanic(CustomMechanic holder, MythicLineConfig mlc) {
		super(holder.getConfigLine(), mlc);
		
		setAsyncSafe(false);
		
		modelId = mlc.getString(new String[] {"mid", "model", "modelid"});
		addon = mlc.getBoolean(new String[] {"add", "addon"}, false);
		
	}

	@Override
	public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
		LivingEntity bukkitTarget = (LivingEntity) BukkitAdapter.adapt(target);
		
		ModelAPI.spawnEntity(bukkitTarget, modelId, addon);
		
		return true;
	}

}
