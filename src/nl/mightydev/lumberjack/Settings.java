package nl.mightydev.lumberjack;

import org.bukkit.ChatColor;

public class Settings {
	public static final ChatColorSettings Color = new ChatColorSettings();
	public static class ChatColorSettings {
		public final ChatColor NORMAL = ChatColor.GRAY;
		public final ChatColor ERROR = ChatColor.DARK_RED;
		public final ChatColor WARNING = ChatColor.GOLD;
		public final ChatColor NOTICE = ChatColor.BLUE;
		public final ChatColor GOOD = ChatColor.GREEN;
		public final ChatColor BAD = ChatColor.RED;
		public final ChatColor STRONG = ChatColor.AQUA;
	}
	
}
