package nl.mightydev.lumberjack;

import nl.mightydev.lumberjack.player_data.PlayerData;
import nl.mightydev.lumberjack.util.LumberjackConfiguration;
import nl.mightydev.lumberjack.util.Message;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class OnPlayerJoin extends PlayerListener {
	
	public static OnPlayerJoin instance = new OnPlayerJoin();
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		if(LumberjackConfiguration.showLoginMessage() == false) return;
		
		Player p = event.getPlayer();
		PlayerData d = PlayerData.get(p);
		
		String s = d.enabled() ? Message.good("enabled") : Message.bad("disabled");
		Message.send(p, "Lumberjack loaded and " + s);
		
	}
}
