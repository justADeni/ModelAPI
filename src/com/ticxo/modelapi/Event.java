package com.ticxo.modelapi;

import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.events.AdditionsAPIInitializationEvent;
import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.api.additions.EntityModelPart;
import com.ticxo.modelapi.api.mythicmobs.ModelMechanic;
import com.ticxo.modelapi.api.mythicmobs.ModelStateCondition;
import com.ticxo.modelapi.api.mythicmobs.ModelStateMechanic;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicConditionLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
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
	public void onMythicConditionLoad(MythicConditionLoadEvent e) {
		SkillCondition m;
		switch(e.getConditionName().toUpperCase()) {
		case "STATE":
			m = new ModelStateCondition(e.getContainer().getConditionArgument(), e.getConfig());
			e.register(m);
			break;
		}
			
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getItem() != null && e.getItem().getType().equals(Material.PIG_SPAWN_EGG) && e.getItem().containsEnchantment(Enchantment.DURABILITY) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			e.setCancelled(true);
	}
	/*
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
				victim.addState("melee damaged");
				break;
			case PROJECTILE:
				victim.addState("range damaged");
				break;
			default:
				victim.addState("damaged");
				break;
		}
	}*/
	
}