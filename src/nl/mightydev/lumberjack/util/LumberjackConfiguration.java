package nl.mightydev.lumberjack.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import nl.mightydev.lumberjack.Plugin;

public abstract class LumberjackConfiguration {
	
	private static boolean enabled = true;
	private static boolean show_login_message = true;
	private static boolean silent = false;
	private static boolean mcMMO = true;
	private static boolean break_full = false;
	
	public static boolean enabledByDefault() {
		return enabled;
	}
	
	public static boolean showLoginMessage() {
		return show_login_message;
	}
	
	public static boolean silentByDefault() {
		return silent;
	}

	public static boolean mcMMOCheck() {
		return mcMMO;
	}
	
	public static boolean breakFull() {
		return break_full;
	}
	
	public static void load() {
		Properties p = new Properties();
		String path = Plugin.configuration_filepath;
		
		try {
			InputStream s = new FileInputStream(path);
			p.load(s);
		} catch (FileNotFoundException e) {
			// file is not there, create a file with the default settings :)
			PluginMessage.send("Creating default Lumberjack configuration file");
			write();
			return;
		} catch (IOException e) {
			// something that shouldn't have gone wrong went wrong
			PluginMessage.send("Failed to read Lumberjack config file");
			e.printStackTrace();
			return;
		}
		
		String s_enabled = p.getProperty("enabled", enabled ? "true" : "false");
		String s_show_login_message = p.getProperty("show_login_message", show_login_message ? "true" : "false");
		String s_silent = p.getProperty("silent", silent ? "true" : "false");
		String s_mcMMO = p.getProperty("mcMMO", mcMMO ? "true" : "false");
		String s_break_full = p.getProperty("break_full", break_full ? "true" : "false");
		
		enabled = Boolean.parseBoolean(s_enabled);
		show_login_message = Boolean.parseBoolean(s_show_login_message);
		silent = Boolean.parseBoolean(s_silent);
		mcMMO = Boolean.parseBoolean(s_mcMMO);
		break_full = Boolean.parseBoolean(s_break_full);
	}
	
	public static void write() {
		String path = Plugin.configuration_filepath;
		
		try {
			Properties p = new Properties();
			OutputStream s = new FileOutputStream(path);
			p.setProperty("enabled", enabled ? "true" : "false");
			p.setProperty("show_login_message", show_login_message ? "true" : "false");
			p.setProperty("silent", silent ? "true" : "false");
			p.setProperty("mcMMO", mcMMO ? "true" : "false");
			p.setProperty("break_full", break_full ? "true" : "false");
			
			p.store(s, "Lumberjack configuration file");
			
		} catch (FileNotFoundException e) {
			PluginMessage.send("Lumberjack failed to open output stream to write config file");
			e.printStackTrace();
		} catch (IOException e) {
			PluginMessage.send("Lumberjack failed to write config file");
			e.printStackTrace();
		}
		
	}


}
