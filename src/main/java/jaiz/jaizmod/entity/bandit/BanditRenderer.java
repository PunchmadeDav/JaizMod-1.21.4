package jaiz.jaizmod.entity.bandit;

import com.google.common.collect.Maps;
import jaiz.jaizmod.JaizMod;
import jaiz.jaizmod.entity.ModModelLayers;
import jaiz.jaizmod.entity.dragonfly.DragonflyVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.entity.state.FrogEntityRenderState;
import net.minecraft.client.render.entity.state.SnifferEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class BanditRenderer extends MobEntityRenderer<BanditEntity, BanditRenderState, Bandit> {


    public static final Map<BanditVariant, Identifier> BANDIT_VARIANT =
            Util.make(Maps.newEnumMap(BanditVariant.class), (map) -> {
                map.put(BanditVariant.ORIGINAL, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_1.png"));
                map.put(BanditVariant.RED_MASK, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_2.png"));
                map.put(BanditVariant.GLOVES, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_3.png"));
                map.put(BanditVariant.BROWN_SHIRT, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_4.png"));
                map.put(BanditVariant.HAT, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_5.png"));
                map.put(BanditVariant.PAINTED, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_6.png"));
                map.put(BanditVariant.HAND_PRINT, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_7.png"));
                map.put(BanditVariant.SKI_MASK, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_8.png"));
                map.put(BanditVariant.RED, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_9.png"));
                map.put(BanditVariant.GREEN, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_10.png"));
                map.put(BanditVariant.BLUE, Identifier.of(JaizMod.MOD_ID, "textures/entity/bandit/bandit_11.png"));
            });



    public BanditRenderer(EntityRendererFactory.Context context) {
        super(context, new Bandit(context.getPart(ModModelLayers.BANDIT)), 0.35f);
        this.addFeature(new HeldItemFeatureRenderer<>(this));
    }

    @Override
    public BanditRenderState createRenderState() {
        return new BanditRenderState();
    }

    protected void scale(BanditEntity banditEntity, MatrixStack matrixStack, float f) {
        float g = 0.9375F * banditEntity.getScaleFactor();
        matrixStack.scale(g, g, g);
    }

    @Override
    public Identifier getTexture(BanditRenderState state) {
        return BANDIT_VARIANT.get(state.texture);
    }

    public void updateRenderState(BanditEntity bandit, BanditRenderState banditstate, float f) {
        super.updateRenderState(bandit, banditstate, f);
        ArmedEntityRenderState.updateRenderState(bandit, banditstate, this.itemModelResolver);
        banditstate.tradeAnimationState.copyFrom(bandit.tradeAnimationState);
        banditstate.attackAnimationState.copyFrom(bandit.attackAnimationState);
        banditstate.texture = bandit.getVariant();
    }
}
