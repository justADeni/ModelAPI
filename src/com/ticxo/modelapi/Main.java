package com.ticxo.modelapi;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.models.ModelIceica;

public class Main extends JavaPlugin {

	public static JavaPlugin plugin;

	private static PluginManager pm;
	private static ConsoleCommandSender cs;
	private static ModelManager mm;

	public void onEnable() {

		plugin = this;
		pm = Bukkit.getServer().getPluginManager();
		cs = Bukkit.getServer().getConsoleSender();
		
		cs.sendMessage("Enabled!");

		registerModels();
		
	}

	public void onDisable() {

		cs.sendMessage("Disabled!");

	}
	
	public void registerModels() {
		
		mm = new ModelManager(this);
		mm.registerModel(new ModelIceica());
		
	}

}
