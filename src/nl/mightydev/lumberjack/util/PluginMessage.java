package nl.mightydev.lumberjack.util;

import java.util.logging.Logger;

public abstract class PluginMessage {
	private static Logger log;
	private static String prefix;
	
	public static void initialize(String plugin_name) {
		log = Logger.getLogger("Minecraft");
		prefix = "[" + plugin_name + "] ";
	}
	
	public static void send(String message) {
		log.info(prefix + message);
	}
	
	public static void raw(String message) {
		log.info(message);
	}
}
