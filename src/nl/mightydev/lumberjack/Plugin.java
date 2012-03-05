package nl.mightydev.lumberjack;

import java.io.File;

import nl.mightydev.lumberjack.player_data.PlayerData;
import nl.mightydev.lumberjack.util.LumberjackConfiguration;
import nl.mightydev.lumberjack.util.Message;
import nl.mightydev.lumberjack.util.PluginMessage;
import nl.mightydev.version.LumberjackVersion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

	public static String name = "Lumberjack";
	public static LumberjackVersion version = null; 
	public static PluginManager manager = null;
	public static String directory = null;
	public static String configuration_filepath;
	public static String player_settings_filepath;
	
	public void onEnable() {
		
		PluginDescriptionFile d = getDescription();
		Plugin.name = d.getName();
		Plugin.version = new LumberjackVersion(d.getVersion());
		
		PluginMessage.initialize(Plugin.name);
		PluginMessage.send("version " + Plugin.version + " enabled!");
		
		manager = this.getServer().getPluginManager();
		
		manager.registerEvent(Event.Type.BLOCK_BREAK, OnPlayerHit.instance, Event.Priority.Normal, this);
		manager.registerEvent(Event.Type.PLAYER_JOIN, OnPlayerJoin.instance, Event.Priority.Normal, this);
		
		File data_folder = getDataFolder();
		
		if(data_folder.exists() == false) {
			data_folder.mkdirs();
		}
		
		Plugin.directory = data_folder.getPath() + "/";
		Plugin.configuration_filepath = Plugin.directory + "lumberjack.config";
		Plugin.player_settings_filepath = Plugin.directory + "lumberjack.player.settings";
		
		LumberjackConfiguration.load();
		LumberjackConfiguration.write();
		PlayerData.load();
		
	}
	
	public void onDisable() {
		PluginMessage.send("disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("this command can only be run by a player");
			return false;
		}
		
		if(arguments.length == 0) {
			sender.sendMessage("this command requires at least 1 argument");
			return false;
		}
		
		Player player = (Player) sender;
		PlayerData data = PlayerData.get(player);
		
		int i = 0;
		while(i < arguments.length) {
			String c = arguments[i++];
			
			if(c.equals("enable") || c.equals("e")) {
				data.enabled(true);
				Message.send(player, Message.good("enabled"));
			}
			else if(c.equals("disable") || c.equals("d")) {
				data.enabled(false);
				Message.send(player, Message.bad("disabled"));
			}
			else if(c.equals("silent") || c.equals("s")) {
				data.silent(true);
				Message.send(player, "Reduced number of messages");
			}
			else if(c.equals("normal") || c.equals("n")) {
				data.silent(false);
				Message.send(player, "Normal number of messages");
			}
			else {
				Message.sendError(player, "Unknown " + Plugin.name + " command " + Message.strong(c));
				return false;
			}
		}
		
		PlayerData.store();
		
		return true;
	}

}
