package nl.mightydev.lumberjack.util;

import nl.mightydev.lumberjack.Settings;

import org.bukkit.entity.Player;

public class Message {
	private static final String prefix = "[Lumberjack] ";
	private static final String normalPrefix = Settings.Color.NOTICE + prefix + Settings.Color.NORMAL;
	private static final String errorPrefix = Settings.Color.ERROR + prefix + Settings.Color.NORMAL;
	private static final String warningPrefix = Settings.Color.WARNING + prefix + Settings.Color.NORMAL;
	
	public static void send(Player recepient, String message) {
		recepient.sendMessage(normalPrefix + message);
	}
	
	public static void sendError(Player recepient, String error) {
		recepient.sendMessage(errorPrefix + error);
	}
	
	public static void sendWarning(Player recepient, String warning) {
		recepient.sendMessage(warningPrefix + warning);
	}
	
	public static String bad(String text) {
		return Settings.Color.BAD + text + Settings.Color.NORMAL;
	}
	
	public static String good(String text) {
		return Settings.Color.GOOD + text + Settings.Color.NORMAL;
	}
	
	public static String strong(String text) {
		return Settings.Color.STRONG + text + Settings.Color.NORMAL;
	}
}
