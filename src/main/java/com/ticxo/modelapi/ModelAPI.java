package com.ticxo.modelapi;

import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.api.modeling.ModelBase;
import com.ticxo.modelapi.api.modeling.ModelEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ModelAPI extends JavaPlugin {

	public static JavaPlugin plugin;
	
	private static PluginManager pm;
	private static ConsoleCommandSender cs; 
	private static Commands commands = new Commands();
	
	public void onEnable() {

		plugin = this;
		pm = Bukkit.getServer().getPluginManager();
		cs = Bukkit.getServer().getConsoleSender();
		
		pm.registerEvents(new Event(), this);
		
		cs.sendMessage("[ModelAPI] Enabled!");
		
		getCommand("disguise").setExecutor(commands);
		getCommand("undisguise").setExecutor(commands);
		getCommand("state").setExecutor(commands);
		
		ModelManager.renderModel();
		
	}

	public void onDisable() {

		ModelManager.removeAllModels();
		
		cs.sendMessage("[ModelAPI] Disabled!");

	}
	
	public static void registerPlugin(JavaPlugin plugin, String texture) {
		ModelManager.registerPlugin(plugin, texture);
	}
	
	public static void registerModel(ModelBase model) {
		ModelManager.registerModel(model);
	}
	
	public static void registerModels(List<ModelBase> models) {
		for(ModelBase model : models) {
			registerModel(model);
		}
	}
	
	public static ModelEntity spawnEntity(Entity ent, String modelId, boolean addition) {
		return new ModelEntity(ent, modelId, addition);
	}
	
	public static ModelEntity spawnEntity(Location loc, EntityType type, String modelId, boolean addition) {
		return spawnEntity(loc.getWorld().spawnEntity(loc, type), modelId, addition);
	}
	
	public static ModelEntity spawnEntity(Entity ent, String pluginId, String name, boolean addition) {
		return spawnEntity(ent, pluginId + ":" + name, addition);
	}
	
	public static ModelEntity spawnEntity(Location loc, EntityType type, String pluginId, String name, boolean addition) {
		return spawnEntity(loc.getWorld().spawnEntity(loc, type), pluginId, name, addition);
	}
	
	public static ModelEntity spawnEntity(Entity ent, JavaPlugin plugin, String name, boolean addition) {
		return spawnEntity(ent, plugin.getDescription().getName(), name, addition);
	}
	
	public static ModelEntity spawnEntity(Location loc, EntityType type, JavaPlugin plugin, String name, boolean addition) {
		return spawnEntity(loc, type, plugin.getDescription().getName(), name, addition);
	}
	
	public static ModelEntity getModelEntity(Entity ent) {
		if(ent.hasMetadata("modeled"))
			for(int i = 0; i < ModelManager.getEntityList().size(); i++)
				if(ent.equals(ModelManager.getEntityList().get(i).getEntity()))
					return ModelManager.getEntityList().get(i);
		return null;
	}

}