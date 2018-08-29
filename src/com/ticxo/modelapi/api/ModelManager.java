package com.ticxo.modelapi.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ModelManager {

	private JavaPlugin plugin;
	private Map<String, ModelBase> regModels = new HashMap<String, ModelBase>();
	private Map<Entity, String> entityModels = new HashMap<Entity, String>();
	private BukkitRunnable apply;

	public ModelManager(JavaPlugin plugin) {

		this.plugin = plugin;
		
		apply = new BukkitRunnable() {
			public void run() {
				Iterator<Entry<Entity, String>> it = entityModels.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Entity, String> p = (Map.Entry<Entity, String>)it.next();
					teleportModel(p.getKey(), p.getValue());
					it.remove();
				}
			}
		};
		
		apply.runTaskTimer(plugin, 0, 1);
		
	}

	public void registerModel(String id, ModelBase model) {

		regModels.put(id, model);

	}

	public void registerModel(ModelBase model) {

		regModels.put(model.modelId, model);

	}

	public void applyModel(Entity ent, String id) {

		entityModels.put(ent, id);

	}
	
	private void teleportModel(Entity ent, String id) {
		
		
		
	}

}
