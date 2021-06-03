package io.github.joaoh1.boringbackgrounds.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import io.github.joaoh1.boringbackgrounds.utils.BackgroundUtils;

@Mixin(TextureManager.class)
public class TextureManagerMixin {
	@Inject(
		at = @At("TAIL"),
		method = "getTexture(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/texture/AbstractTexture;",
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private AbstractTexture redirectBackgroundTexture(Identifier id, CallbackInfoReturnable<AbstractTexture> ci, AbstractTexture abstractTexture) {
		// If the identifier is the same as the background texture, then change it to the chosen texture
		if (id == DrawableHelper.OPTIONS_BACKGROUND_TEXTURE) {
			abstractTexture = new ResourceTexture(BackgroundUtils.backgroundTexture);
			this.registerTexture(id, abstractTexture);
			ci.setReturnValue(abstractTexture);
		}

		return ci.getReturnValue();
	}

	@Shadow
	private void registerTexture(Identifier id, AbstractTexture abstractTexture) {}
}
