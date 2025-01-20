package jaiz.jaizmod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import jaiz.jaizmod.statuseffects.HypnoStatusEffect;
import jaiz.jaizmod.statuseffects.ModStatusEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHUDMixin {
    private static final Identifier HYPNOSIS_OUTLINE = Identifier.ofVanilla("textures/misc/hypnosis_outline.png");

    @Mutable
    @Final
    @Shadow
    private final MinecraftClient client;

    protected InGameHUDMixin(MinecraftClient client) {
        this.client = client;
    }

    private void renderHypnosisOverlay(DrawContext context, float opacity) {
        int i = ColorHelper.getWhite(opacity);
        context.drawTexture(
                RenderLayer::getGuiTexturedOverlay,
                HYPNOSIS_OUTLINE,
                0,
                0,
                0.0F,
                0.0F,
                context.getScaledWindowWidth(),
                context.getScaledWindowHeight(),
                context.getScaledWindowWidth(),
                context.getScaledWindowHeight(),
                i
        );
    }

    @Inject(at = @At("TAIL"), method = "renderMiscOverlays")
    private void init(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {

        assert this.client.player != null;
        if (this.client.player.hasStatusEffect(ModStatusEffects.HYPNO)) {
            this.renderHypnosisOverlay(context, HypnoStatusEffect.hypnopulse);
        }
    }

}
