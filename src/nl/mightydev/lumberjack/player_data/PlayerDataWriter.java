package nl.mightydev.lumberjack.player_data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import nl.mightydev.lumberjack.Plugin;
import nl.mightydev.lumberjack.util.PluginMessage;

class PlayerDataWriter {

	public static void write(Map<String, PlayerData> map) {

		String path = Plugin.player_settings_filepath;
		
		try {
			
			FileWriter writer = new FileWriter(path, false);
			writer.write("Lumberjack " + Plugin.version + " player settings\n");
			
			for(Map.Entry<String,PlayerData> entry : map.entrySet()) {
				write(writer, entry.getKey(), entry.getValue());
			}
			
			writer.close();
			
		} catch (IOException e) {
			PluginMessage.send("Failed to write player settings");
			System.out.println(e);
		}
		
	}

	private static void write(FileWriter writer, String player_name, PlayerData player_data) throws IOException {
		
		writer.write(player_name + ":");
		
		writeProperty(writer, "enabled", player_data.enabled());
		writer.write(",");
		
		writeProperty(writer, "silent", player_data.enabled());
		writer.write("\n");
		
	}
	
	private static void writeProperty(FileWriter writer, String property, boolean value) throws IOException {
		writer.write(property + "=" + (value ? "true" : "false"));
	}

}
