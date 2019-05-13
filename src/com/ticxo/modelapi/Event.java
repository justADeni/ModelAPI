package com.ticxo.modelapi;

import java.util.Map.Entry;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.events.AdditionsAPIInitializationEvent;
import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.api.additions.EntityModelPart;

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
	public void onAttack(EntityDamageEvent e) {
		if(e.getEntityType().equals(EntityType.ARMOR_STAND) && e.getEntity().isSilent()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onArmorStandInteract(PlayerArmorStandManipulateEvent e) {
		ArmorStand a = e.getRightClicked();
		if(a.hasMetadata("partId")) {
			e.setCancelled(true);
		}
	}
	
}