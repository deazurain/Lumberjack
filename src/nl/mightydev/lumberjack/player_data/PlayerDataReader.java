package nl.mightydev.lumberjack.player_data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import nl.mightydev.lumberjack.Plugin;
import nl.mightydev.lumberjack.util.PluginMessage;
import nl.mightydev.version.LumberjackVersion;

class PlayerDataReader {

	public static void read(Map<String, PlayerData> map) {

		String path = Plugin.player_settings_filepath;
		
		try {
			
			FileReader reader = new FileReader(path);
			Scanner scanner = new Scanner(reader);
			
			String line = getLine(scanner);

			if(line == null) {
				PluginMessage.send("warning: no line found while reading player settings");
				return;
			}
			
			String[] parts = line.split(" ");
			
			if(parts.length != 4 || 
				!parts[0].equals("Lumberjack") || 
				!parts[2].equals("player") ||
				!parts[3].equals("settings")) {
				PluginMessage.send("warning: invalid player settings header");
				return;
			}
			
			LumberjackVersion version = new LumberjackVersion(parts[1]);
			
			if(!version.equals(Plugin.version)) {
				PluginMessage.send("waring: converting from and old player settings file");
			}
			
			while((line = getLine(scanner)) != null) {
				read(map, line);
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			PluginMessage.send("Player settings file doesn't exist yet");
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}

	private static String getLine(Scanner s) {
		try {
			return s.nextLine();
		} catch (Exception e) {
			return null;
		}
	}
	private static void read(Map<String,PlayerData> map, String line) {
		
		String[] sections = line.split(":");
		if(sections.length != 2) return;
		
		String player_name = sections[0];
		PlayerData player_data = new PlayerData();
		
		String[] properties = sections[1].split(",");
		
		for(String property : properties) {
			String[] parts = property.split("=");
			if(parts.length != 2) continue;
			setProperty(player_data, parts[0], parts[1]);
		}
		
		map.put(player_name, player_data);
		
	}
	
	private static void setProperty(PlayerData data, String property, String value) {
		
		if(property.equals("enabled")) {
			data.enabled(value.equals("true"));
		}
		else if(property.equals("silent")) {
			data.silent(value.equals("true"));
		}
		
	}

}
