package io.github.ennuil.boring_backgrounds;

import io.github.ennuil.boring_backgrounds.data.BackgroundSettingsLoader;
import io.github.ennuil.boring_backgrounds.events.RandomizeBackgroundEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class BoringBackgroundsMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Registers the background loader
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new BackgroundSettingsLoader());

        // Registers the background-randomizing event
        RandomizeBackgroundEvent.registerEvent();
    }
}
