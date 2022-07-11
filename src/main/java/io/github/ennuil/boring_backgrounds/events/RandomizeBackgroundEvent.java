package io.github.ennuil.boring_backgrounds.events;

import org.quiltmc.qsl.screen.api.client.ScreenEvents;

import io.github.ennuil.boring_backgrounds.utils.BackgroundUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

// This event randomizes the background if the relevant option is enabled
public class RandomizeBackgroundEvent implements ScreenEvents.BeforeInit {
	private static Screen oldScreen;

	@Override
	public void beforeInit(Screen screen, MinecraftClient client, int scaledWidth, int scaledHeight) {
		if (BackgroundUtils.randomizeOnNewScreen) {
			if (screen != oldScreen) BackgroundUtils.updateBackground();
			oldScreen = screen;
		}
	}
}
