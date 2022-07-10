package io.github.ennuil.boring_backgrounds.data;

import java.util.Map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;

public record BackgroundSettings(Map<Identifier, Integer> textures, boolean randomizeOnNewScreen) {
    public static final Codec<BackgroundSettings> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.unboundedMap(Identifier.CODEC, Codec.INT).fieldOf("textures").forGetter(BackgroundSettings::textures),
            Codec.BOOL.fieldOf("randomize_on_new_screen").forGetter(BackgroundSettings::randomizeOnNewScreen)
        )
        .apply(instance, BackgroundSettings::new)
    );

    public static BackgroundSettings getDefaultSettings() {
        return new BackgroundSettings(Map.of(DrawableHelper.OPTIONS_BACKGROUND_TEXTURE, 1), false);
    }
}
