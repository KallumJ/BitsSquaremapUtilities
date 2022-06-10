package team.bits.squaremap.utils;

import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.event.server.ServerStoppingEvent;
import team.bits.squaremap.utils.map.MapManager;


public class BitsSquaremapUtils implements ModInitializer, ServerStoppingEvent.Listener {

	@Override
	public void onInitialize() {
		EventManager.INSTANCE.registerEvents(this);
	}

	@Override
	public void onServerStopping(@NotNull ServerStoppingEvent event) {
		MapManager.save();
	}
}
