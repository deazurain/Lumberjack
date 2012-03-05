package nl.mightydev.lumberjack;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

@SuppressWarnings("serial")
public class LumberjackBlockBreakEvent extends BlockBreakEvent {

	public LumberjackBlockBreakEvent(Block theBlock, Player player) {
		super(theBlock, player);
	}

}
