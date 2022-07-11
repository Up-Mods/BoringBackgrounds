package io.github.ennuil.boring_backgrounds.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.quiltmc.qsl.resource.loader.api.reloader.SimpleResourceReloader;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;

import io.github.ennuil.boring_backgrounds.utils.BackgroundUtils;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class BackgroundSettingsLoader implements SimpleResourceReloader<BackgroundSettings> {
	private static final Identifier QUILT_ID = new Identifier("boringbackgrounds", "data_loader");

	@Override
	public CompletableFuture<BackgroundSettings> load(ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				if (BackgroundUtils.GLOBAL_CONFIG_PATH.toFile().exists() && BackgroundUtils.GLOBAL_CONFIG_PATH.toFile().canRead()) {
					BackgroundUtils.LOGGER.warn("[Boring Backgrounds] Found a global background settings file in minecraft/config/boring_backgrounds/config.json. Settings provided by resource packs will be ignored!");
					var globalReader = Files.newBufferedReader(BackgroundUtils.GLOBAL_CONFIG_PATH, StandardCharsets.UTF_8);
					var result = BackgroundSettings.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(globalReader)).map(Pair::getFirst).result();
					globalReader.close();

					if (result.isPresent()) {
						return result.get();
					} else {
						BackgroundUtils.LOGGER.error("Failed to load the global settings! Attempting to load from resource packs...");
					};
				}

				for (Entry<Identifier, Resource> entry : manager.findResources("backgrounds", filename -> filename.getPath().endsWith(".json")).entrySet()) {
					var id = entry.getKey();
					var resource = entry.getValue();
					if (!id.getPath().substring(0, id.getPath().lastIndexOf('.')).endsWith("background_settings")) continue;

					var packReader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8));
					var result = BackgroundSettings.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(packReader)).map(Pair::getFirst).result();
					packReader.close();

					if (result.isPresent()) {
						return result.get();
					} else {
						BackgroundUtils.LOGGER.error("Failed to load settings provided by a resource pack!");
					};
				}
			} catch (IOException | JsonParseException e) {
				BackgroundUtils.LOGGER.error("[Boring Backgrounds] Failed to load the background settings! The following error has been printed: " + e);
			}

			return BackgroundSettings.getDefaultSettings();
		}, executor);
	}

	@Override
	public CompletableFuture<Void> apply(BackgroundSettings data, ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.runAsync(() -> {
			List<Identifier> textures = new ArrayList<>();
			List<Integer> textureIndices = new ArrayList<>();

			data.textures().forEach((key, value) -> {
				textures.add(key);
				for (int i = 0; i < value; i++) {
					textureIndices.add(textures.size() - 1);
				}
			});

			BackgroundUtils.textures = textures;
			BackgroundUtils.textureIndices = textureIndices;

			BackgroundUtils.randomizeOnNewScreen = data.randomizeOnNewScreen();
			BackgroundUtils.updateBackground();
		});
	}

	@Override
	public Identifier getQuiltId() {
		return QUILT_ID;
	}
}
