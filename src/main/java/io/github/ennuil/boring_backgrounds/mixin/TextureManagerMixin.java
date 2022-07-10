package io.github.ennuil.boring_backgrounds.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import io.github.ennuil.boring_backgrounds.utils.BackgroundUtils;

@Mixin(TextureManager.class)
public class TextureManagerMixin {
    @ModifyVariable(
        at = @At(
            value = "INVOKE",
            target = "java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;"
        ),
        method = "getTexture(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/texture/AbstractTexture;",
        argsOnly = true
    )
    private Identifier redirectBackgroundTexture(Identifier id) {
        return id == DrawableHelper.OPTIONS_BACKGROUND_TEXTURE ? BackgroundUtils.backgroundTexture : id;
    }
}
