package com.ticxo.modelapi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.ticxo.modelapi.ModelAPI;
import com.ticxo.modelapi.api.additions.EntityModelPart;
import com.ticxo.modelapi.api.modeling.ModelBase;
import com.ticxo.modelapi.api.modeling.ModelEntity;

public class ModelManager {

	private static Map<String, ModelBase> models = new HashMap<String, ModelBase>();
	private static List<EntityModelPart> modelParts = new ArrayList<EntityModelPart>();
	private static List<ModelEntity> entityList = new ArrayList<ModelEntity>();
	private static Map<JavaPlugin, String> textureMap = new HashMap<JavaPlugin, String>();

	private static BukkitRunnable renderer;

	public static void registerModel(ModelBase model) {

		models.put(model.getId(), model);

	}

	public static void registerModelPart(EntityModelPart emp) {
		
		modelParts.add(emp);

	}
	
	public static void applyModel(ModelEntity modelEntity) {

		entityList.add(modelEntity);

	}
	
	public static void registerPlugin(JavaPlugin plugin, String texture) {
		textureMap.put(plugin, texture);
	}

	public static void renderModel() {
		
		List<ModelEntity> remover = new ArrayList<ModelEntity>();
		
		renderer = new BukkitRunnable() {

			public void run() {
				if (!entityList.isEmpty()) {
					for (ModelEntity ent : entityList) {
						if (!ent.isDead() && ent.isRender() && models.containsKey(ent.getModelId())) {
							if (ent.hasModel()) {
								ent.teleportModel();
							} else {
								ent.generateModel();
							}
						}else {
							ent.remove(true);
							remover.add(ent);
						}
					}
					entityList.removeAll(remover);
					remover.clear();
				}

			}
		};

		renderer.runTaskTimer(ModelAPI.plugin, 0, 1);

	}
	
	public static void removeAllModels() {
		for (ModelEntity ent : entityList) {
			ent.remove(true);
		}
		entityList.clear();
	}
	
	public static Entity revertModel(ModelEntity modelEntity) {

		entityList.remove(modelEntity);
		modelEntity.remove(false);
		return modelEntity.getEntity();
		
	}
	
	public static ModelBase getModel(String id) {
		return models.get(id);
	}
	
	public static Map<String, ModelBase> getModelList(){
		return models;
	}
	
	public static List<EntityModelPart> getModelPartList(){
		return modelParts;
	}
	
	public static List<ModelEntity> getEntityList(){
		return entityList;
	}
	
	public static Map<JavaPlugin, String> getTextureMap(){
		return textureMap;
	}

}
