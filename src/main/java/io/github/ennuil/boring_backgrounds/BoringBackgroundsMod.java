package io.github.ennuil.boring_backgrounds;

import io.github.ennuil.boring_backgrounds.data.BackgroundSettingsLoader;
import net.minecraft.resource.ResourceType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

public class BoringBackgroundsMod implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		// Registers the background loader
		ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerReloader(new BackgroundSettingsLoader());
	}
}
