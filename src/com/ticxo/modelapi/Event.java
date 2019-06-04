package com.ticxo.modelapi;

import java.util.Map.Entry;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
	
}