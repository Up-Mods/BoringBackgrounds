package io.github.ennuil.boringbackgrounds.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;

import io.github.ennuil.boringbackgrounds.utils.BackgroundUtils;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class BackgroundSettingsLoader implements SimpleResourceReloadListener<BackgroundSettings> {
    private static final Identifier FABRIC_ID = new Identifier("boringbackgrounds", "data_loader");
    
    @Override
    public CompletableFuture<BackgroundSettings> load(ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (BackgroundUtils.GLOBAL_CONFIG_PATH.toFile().exists() && BackgroundUtils.GLOBAL_CONFIG_PATH.toFile().canRead()) {
                    Reader globalReader;
                    BackgroundUtils.LOGGER.warn("[Boring Backgrounds] Found a global background settings file in minecraft/config/boringbackgrounds.json. Settings provided by resource packs will be ignored!");
                    globalReader = Files.newBufferedReader(BackgroundUtils.GLOBAL_CONFIG_PATH, StandardCharsets.UTF_8);
                    
                    var result = BackgroundSettings.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(globalReader)).map(Pair::getFirst).result();
                    globalReader.close();
                    if (result.isPresent()) {
                        return result.get();
                    } else {
                        BackgroundUtils.LOGGER.error("Failed to load the global settings! Attempting to load from resource packs...");
                    };
                }
                for (Identifier identifier : manager.findResources("backgrounds", filename -> filename.endsWith(".json"))) {
                    Reader packReader;
                    // This kinda odd thingy should allow for support of JSON5 Everywhere
                    String idPath = identifier.getPath();
                    if (!idPath.substring(0, idPath.lastIndexOf('.')).endsWith("background_settings")) {
                        continue;
                    }
                    packReader = new BufferedReader(new InputStreamReader(manager.getResource(identifier).getInputStream(), StandardCharsets.UTF_8));

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
    public Identifier getFabricId() {
        return FABRIC_ID;
    }
}