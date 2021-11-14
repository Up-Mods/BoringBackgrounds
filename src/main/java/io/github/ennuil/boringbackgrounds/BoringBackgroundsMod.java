package io.github.ennuil.boringbackgrounds;

import io.github.ennuil.boringbackgrounds.data.BackgroundSettingsLoader;
import io.github.ennuil.boringbackgrounds.events.RandomizeBackgroundEvent;
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
