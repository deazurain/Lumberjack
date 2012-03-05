package nl.mightydev.lumberjack;

import java.util.Random;

import nl.mightydev.lumberjack.player_data.PlayerData;
import nl.mightydev.lumberjack.util.LumberjackConfiguration;
import nl.mightydev.lumberjack.util.Message;
import nl.mightydev.lumberjack.util.PluginMessage;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;

public class OnPlayerHit extends BlockListener {
	
	private Random random = new Random();
	
	public final static OnPlayerHit instance = new OnPlayerHit();
	
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled()) return;
		
		if(event instanceof LumberjackBlockBreakEvent) return;

		if(Plugin.manager.isPluginEnabled("mcMMO") && LumberjackConfiguration.mcMMOCheck()) {
			try {
				ClassLoader cl = Plugin.manager.getPlugin("mcMMO").getClass().getClassLoader();
				Class<?> c = Class.forName("com.gmail.nossr50.datatypes.FakeBlockBreakEvent", false, cl);
				if(c.isInstance(event)) return;
			} catch (ClassNotFoundException e) {
				PluginMessage.send("mcMMO's FakeBlockBreakEvent class not found, path might have been changed, contact Lumberjack author!");
			}
		}
		
		Player p = event.getPlayer();
		PlayerData d = PlayerData.get(p);
		
		if(p.getGameMode() == GameMode.SURVIVAL && d.enabled()) {
			boolean cancel = doActions(p, d, event.getBlock());
			event.setCancelled(cancel);
		}
	}
	
	private boolean doActions(Player p, PlayerData d, Block b) {

		World world = b.getWorld();
		
		if(b.getType() != Material.LOG) return false;
		
		MinecraftTree tree = d.lastTree();
		if(tree == null) {
			tree = new MinecraftTree(b);
			d.lastTree(tree);
		}
		else {
			tree.refresh(world);
			if(tree.isInTrunk(b) == false) {
				tree = new MinecraftTree(b);
				d.lastTree(tree);
			}
		}
			
		if(tree.isNatural() == false) return false;
	
		if (d.silent() == false) { 
			String message;
			switch(random.nextInt(100)) {
				case 0: message = "Chop it like its hot"; break;
				case 1: message = "Do you feel the magic?"; break;
				case 2: message = "So easy..."; break;
				case 3: message = "Comfortably collecting wood yeah!"; break;
				case 4: message = "Be gone tree"; break;
				case 5: message = "May the axe be with you"; break;
				case 6: message = "Who needs an axe if you've got hands?"; break;
				case 7: message = "Who needs a hammer if you've got a workbe.. wait what?"; break;
				default: message = null;
			}
			if(message != null) Message.send(p, message);
		}
		
		if(LumberjackConfiguration.breakFull()) {
			Block highest;
			while((highest = tree.removeTrunkTop()) != null) {
				if(b.getLocation().equals(highest.getLocation())) {
					continue;
				}
				fakeBlockBreak(highest, p);
			}
			return false; // don't cancel the event
		}
		else {
			Block highest = tree.removeTrunkTop();
			
			// no more blocks in tree
			if(highest == null) return false;
			if(b.getLocation().equals(highest.getLocation())) {
				return false; // don't cancel, natural flow
			}
			fakeBlockBreak(highest, p);
		}
		
		return true; // cancel the breaking of the current block
	}
	
	private boolean fakeBlockBreak(Block block, Player player) {

		BlockBreakEvent break_event = new LumberjackBlockBreakEvent(block, player);
		Plugin.manager.callEvent(break_event);
		if(break_event.isCancelled()) return false;
		
		// reduce durability
		ItemStack item_in_hand = player.getItemInHand();
		short dur = (short) (item_in_hand.getDurability() + 1);
		item_in_hand.setDurability((short) dur);
		//Message.send(player, "new dur: " + dur); // TODO: remove need to break item when dur = ???

		// artificial item drop
		Material material = block.getType();
		int amount = 1;
		byte data = block.getData();
		short damage = 0;
		ItemStack drop = new ItemStack(material, amount, damage, data);
		block.getWorld().dropItemNaturally(block.getLocation(), drop);
		
		// destroy highest block
		block.setData((byte)0);
		block.setType(Material.AIR);
		
		return true;
	}

}