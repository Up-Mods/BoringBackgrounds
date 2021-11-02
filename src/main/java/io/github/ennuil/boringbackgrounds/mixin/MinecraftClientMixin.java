package io.github.ennuil.boringbackgrounds.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.ennuil.boringbackgrounds.utils.BackgroundUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    //The mixin behind the "randomize_on_new_screen"'s behavior
    @Inject(
        at = @At("HEAD"),
        method = "setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"
    )
    public void changeBackgroundAfterSetScreen(Screen screen, CallbackInfo info) {
        if (BackgroundUtils.randomizeOnNewScreen) {
            BackgroundUtils.updateBackground();
        }
    }
}