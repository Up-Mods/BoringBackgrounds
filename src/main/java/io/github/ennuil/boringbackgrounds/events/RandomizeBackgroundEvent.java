package io.github.ennuil.boringbackgrounds.events;

import io.github.ennuil.boringbackgrounds.utils.BackgroundUtils;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.Screen;

// This event randomizes the background if the relevant option is enabled
public class RandomizeBackgroundEvent {
    private static Screen oldScreen;

    public static void registerEvent() {
        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (BackgroundUtils.randomizeOnNewScreen) {
                if (screen != oldScreen) BackgroundUtils.updateBackground();
                oldScreen = screen;
            }
        });
    }
}
