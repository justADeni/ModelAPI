package com.ticxo.modelapi;

import java.util.Map.Entry;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.events.AdditionsAPIInitializationEvent;
import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.api.additions.EntityModelPart;
import com.ticxo.modelapi.api.modeling.ModelEntity;
import com.ticxo.modelapi.api.mythicmobs.ModelMechanic;
import com.ticxo.modelapi.api.mythicmobs.ModelStateMechanic;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;

public class Event implements Listener{

	@EventHandler
	public void onAdditionsAPIInitialize(AdditionsAPIInitializationEvent e) {

		for(Entry<JavaPlugin, String> entry : ModelManager.getTextureMap().entrySet()) {
			e.addResourcePackFromPlugin(entry.getKey(), entry.getValue());
		}
		for(EntityModelPart cItem : ModelManager.getModelPartList()) {
			e.addCustomItem(cItem);
		}
		
	}
	
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
	public void onAttack(EntityDamageByEntityEvent e) {
		ModelEntity attacker = ModelAPI.getModelEntity(e.getDamager());
		ModelEntity victim = ModelAPI.getModelEntity(e.getEntity());
		if(attacker != null)
			switch(e.getCause()) {
			case ENTITY_ATTACK:
				attacker.addState("melee attack");
				break;
			case PROJECTILE:
				attacker.addState("range attack");
				break;
			default:
				attacker.addState("attack");
				break;
		}
		if(victim != null)
			switch(e.getCause()) {
			case ENTITY_ATTACK:
				attacker.addState("melee damaged");
				break;
			case PROJECTILE:
				attacker.addState("range damaged");
				break;
			default:
				attacker.addState("damaged");
				break;
		}
	}
	
}