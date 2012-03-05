package nl.mightydev.lumberjack.player_data;

import java.util.HashMap;
import java.util.Map;

import nl.mightydev.lumberjack.MinecraftTree;
import nl.mightydev.lumberjack.util.LumberjackConfiguration;

import org.bukkit.entity.Player;

public class PlayerData {
	private static final Map<String, PlayerData> map = new HashMap<String, PlayerData>();
	private static Player cached_player;
	private static PlayerData cached_data;
	
	public static PlayerData get(Player p) {
		if(cached_player == p) return cached_data;

		String name = p.getName();
		
		cached_player = p;
		cached_data = map.get(name);
		
		if (cached_data == null) {
			cached_data = new PlayerData();
			map.put(name, cached_data);
		}

		return cached_data;
	}
	
	public static void load() {
		PlayerDataReader.read(map);
	}
	
	public static void store() {
		PlayerDataWriter.write(map);
	}

	//persistent data
	private boolean enabled;
	private boolean silent;
	
	//temporary data
	private MinecraftTree last_tree;
	
	public PlayerData() {
		enabled = LumberjackConfiguration.enabledByDefault();
		silent = LumberjackConfiguration.silentByDefault();
		last_tree = null;
	}
	
	public boolean enabled() { return enabled; }
	public void enabled(boolean enabled) { this.enabled = enabled; }
	
	public boolean silent() { return silent; }
	public void silent(boolean silent) { this.silent = silent; }
	
	public MinecraftTree lastTree() { return last_tree; }
	public void lastTree(MinecraftTree tree) { this.last_tree = tree; }
	
}
