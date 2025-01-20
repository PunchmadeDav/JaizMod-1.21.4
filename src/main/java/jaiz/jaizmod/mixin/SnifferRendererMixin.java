package jaiz.jaizmod.mixin;

import jaiz.jaizmod.entity.mason_mouth.MasonMouthFeatureRenderer;
import jaiz.jaizmod.entity.sniffer_mixins.*;
import jaiz.jaizmod.item.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SnifferEntityRenderer;
import net.minecraft.client.render.entity.model.SnifferEntityModel;
import net.minecraft.client.render.entity.state.SnifferEntityRenderState;
import net.minecraft.entity.passive.SnifferEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnifferEntityRenderer.class)
public abstract class SnifferRendererMixin extends AgeableMobEntityRenderer<SnifferEntity, SnifferEntityRenderState, SnifferEntityModel> {

    public SnifferRendererMixin(EntityRendererFactory.Context context, SnifferEntityModel model, SnifferEntityModel babyModel, float shadowRadius) {
        super(context, model, babyModel, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void getTexturedModelData(EntityRendererFactory.Context context, CallbackInfo ci){
        this.addFeature(new SnifferWoolFeatureRenderer(this));
        this.addFeature(new SnifferBullFeatureRenderer(this));
        this.addFeature(new SnifferMuddyFeatureRenderer(this));
        this.addFeature(new SnifferMossyFeatureRenderer(this));
        this.addFeature(new SnifferBlossomFeatureRenderer(this));
        this.addFeature(new SnifferSnowFeatureRenderer(this));
        this.addFeature(new SnifferSaddleFeatureRenderer(this));
    }

    @Inject(method = "updateRenderState", at = @At("TAIL"))
    public void updateRenderState(SnifferEntity sniffer, SnifferEntityRenderState state, float f, CallbackInfo ci){
        ((SnifferRenderStateMixinAccessor)state).setSnowy(((SnifferMixinAccessor)sniffer).isSnowy());
        ((SnifferRenderStateMixinAccessor)state).setBull(((SnifferMixinAccessor)sniffer).isBull());
        ((SnifferRenderStateMixinAccessor)state).setCherryBlossom(((SnifferMixinAccessor)sniffer).isCherryBlossom());
        ((SnifferRenderStateMixinAccessor)state).setMossy(((SnifferMixinAccessor)sniffer).isMossy());
        ((SnifferRenderStateMixinAccessor)state).setMuddy(((SnifferMixinAccessor)sniffer).isMuddy());
        ((SnifferRenderStateMixinAccessor)state).setSaddle(((SnifferMixinAccessor)sniffer).isSaddled());
        ((SnifferRenderStateMixinAccessor)state).setTexture((((SnifferMixinAccessor)sniffer).getVariant()));
    }

}
