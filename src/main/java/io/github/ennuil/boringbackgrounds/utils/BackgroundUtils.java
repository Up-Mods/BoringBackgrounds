package io.github.ennuil.boringbackgrounds.utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;

public class BackgroundUtils {
    // The logger, used to inform the user
    public static final Logger LOGGER = LogManager.getFormatterLogger("Boring Backgrounds");
    // The path to the global background_settings.json equivalent
    public static final Path GLOBAL_CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("boringbackgrounds.json");

    // The list of textures storing all the potential background IDs
    public static List<Identifier> textures = new ArrayList<>();
    // The list of textures storing the weighted indexes for the texture list
    public static List<Integer> textureIndices = new ArrayList<>();
    // The current background, overrides the vanilla one
    public static Identifier backgroundTexture;
    // The "randomize_on_new_screen" option's value
    public static boolean randomizeOnNewScreen;

    // Chooses a random background
    public static void updateBackground() {
        backgroundTexture = !textures.isEmpty()
            ? textures.get(textureIndices.get(new Random().nextInt(textureIndices.size())))
            // If there aren't any texture IDs, just use the vanilla background
            : DrawableHelper.OPTIONS_BACKGROUND_TEXTURE;
    }
}
