package io.github.ennuil.boring_backgrounds.utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.quiltmc.loader.api.QuiltLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.random.RandomGenerator;

public class BackgroundUtils {
	// The logger, used to inform the user
	public static final Logger LOGGER = LoggerFactory.getLogger("Boring Backgrounds");
	// The path to the global background_settings.json equivalent
	public static final Path GLOBAL_CONFIG_PATH = QuiltLoader.getConfigDir().resolve("boring_backgrounds/config.json");

	private static final RandomGenerator RANDOM = RandomGenerator.createLegacy();

	// The list of textures storing all the potential background IDs
	public static List<Identifier> textures = new ArrayList<>();
	// The list of textures storing the weighted indexes for the texture list
	public static List<Integer> textureIndices = new IntArrayList();
	// The current background, overrides the vanilla one
	public static Identifier backgroundTexture;
	// The "randomize_on_new_screen" option's value
	public static boolean randomizeOnNewScreen;

	// Chooses a random background
	public static void updateBackground() {
		backgroundTexture = !textures.isEmpty()
			? textures.get(textureIndices.get(RANDOM.nextInt(textureIndices.size())))
			// If there aren't any texture IDs, just use the vanilla background
			: DrawableHelper.OPTIONS_BACKGROUND_TEXTURE;
	}
}
