package com.ticxo.modelapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ticxo.modelapi.api.mythicmobs.ModelMechanic;
import com.ticxo.modelapi.api.mythicmobs.ModelStateCondition;
import com.ticxo.modelapi.api.mythicmobs.ModelStateMechanic;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicConditionLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;

public class MythicMobsEvents implements Listener{

	@EventHandler
	public void onMythicMechanicLoad(MythicMechanicLoadEvent e) {
		SkillMechanic m;
		switch(e.getMechanicName().toUpperCase()) {
		case "MODEL":
			m = new ModelMechanic(e.getContainer(), e.getConfig());
			e.register(m);
			break;
		case "STATE":
			m = new ModelStateMechanic(e.getContainer(), e.getConfig());
			e.register(m);
			break;
		}
	}
	
	@EventHandler
	public void onMythicConditionLoad(MythicConditionLoadEvent e) {
		SkillCondition m;
		switch(e.getConditionName().toUpperCase()) {
		case "STATE":
			m = new ModelStateCondition(e.getContainer().getConditionArgument(), e.getConfig());
			e.register(m);
			break;
		}
			
	}
	
}
