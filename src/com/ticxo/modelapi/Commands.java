package com.ticxo.modelapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.api.modeling.ModelEntity;

public class Commands implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			ModelEntity me = null;
			if(!player.hasPermission("modelapi.command")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to do that");
				return true;
			}
			switch (cmd.getName().toLowerCase()) {
			case "disguise":
				if(!player.hasPermission("modelapi.command.disguise")) {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that");
					return true;
				}
				if(args.length != 1) return false;
				if (ModelManager.getModel(args[0]) != null) {
					ModelAPI.spawnEntity(player, args[0], false);
					sender.sendMessage(ChatColor.GREEN + "You are now disguised as " + args[0].split(":")[1] + " from " + args[0].split(":")[0] + ".");
				} else
					sender.sendMessage(ChatColor.RED + "Model " + args[0].split(":")[1] + " does not exist in " + args[0].split(":")[0] + ".");
				break;
			case "undisguise":
				if(!player.hasPermission("modelapi.command.undisguise")) {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that");
					return true;
				}
				me = ModelAPI.getModelEntity(player);
				if (me != null) {
					ModelManager.revertModel(me);
					sender.sendMessage(ChatColor.GREEN + "Disguise removed.");
				} else
					sender.sendMessage(ChatColor.RED + "You are not disguised.");
				break;
			case "state":
				if(!player.hasPermission("modelapi.command.state")) {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that");
					return true;
				}
				if(args.length < 2) return false;
				me = ModelAPI.getModelEntity(player);
				if(me != null) {
					if(args[0].toLowerCase().equals("add")) {
						me.addState(args[1]);
						if(args.length == 3 && StringUtils.isNumeric(args[2])) {
							BukkitRunnable br = new BukkitRunnable() {
								@Override
								public void run() {
									ModelAPI.getModelEntity(player).removeState(args[1]);
								}
							};
							br.runTaskLater(ModelAPI.plugin, Integer.parseInt(args[2]));
						}
					}else if(args[0].toLowerCase().equals("remove")) {
						me.removeState(args[1]);
						if(args.length == 3 && StringUtils.isNumeric(args[2])) {
							BukkitRunnable br = new BukkitRunnable() {
								@Override
								public void run() {
									ModelAPI.getModelEntity(player).addState(args[1]);
								}
							};
							br.runTaskLater(ModelAPI.plugin, Integer.parseInt(args[2]));
						}
					}
					sender.sendMessage(ChatColor.GREEN + "Current state: " + me.getStates());
				}else {
					sender.sendMessage(ChatColor.RED + "You are not disguised.");
				}
				break;
			}
			
		} else {
			sender.sendMessage(ChatColor.RED + "This is a player only command");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			List<String> tabs = new ArrayList<String>();
			switch (cmd.getName().toLowerCase()) {
			case "disguise":
				if (!args[0].equals("")) {
					for(String key : ModelManager.getModelList().keySet())
						if(key.startsWith(args[0].toLowerCase()))
							tabs.add(key);
					return tabs;
				}else 
					return new ArrayList<String>(ModelManager.getModelList().keySet());
			case "state":
				if (!(args[0].equals("add") || args[0].equals("remove"))) {
					tabs.add("add");
					tabs.add("remove");
					return tabs;
				}
			}
		}
		return null;
	}

}
