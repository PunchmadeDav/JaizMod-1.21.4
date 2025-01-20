package jaiz.jaizmod.entity.fruit_bat;

import jaiz.jaizmod.JaizMod;
import jaiz.jaizmod.entity.ModModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BatEntityModel;

import net.minecraft.client.render.entity.state.BatEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.Identifier;

public class FruitBatRenderer extends MobEntityRenderer<FruitBatEntity, FruitBatRenderState, FruitBat> {

    private static final Identifier TEXTURE = Identifier.of(JaizMod.MOD_ID, "textures/entity/bats/fruit_bat.png");

    public FruitBatRenderer(EntityRendererFactory.Context context) {
        super(context, new FruitBat(context.getPart(ModModelLayers.FRUIT_BAT)), 0.25f);
    }

    public FruitBatRenderState createRenderState() {
        return new FruitBatRenderState();
    }


    @Override
    public Identifier getTexture(FruitBatRenderState state) {
        return TEXTURE;

    }

    public void updateRenderState(FruitBatEntity batEntity, FruitBatRenderState FruitBatRenderState, float f) {
        super.updateRenderState(batEntity, FruitBatRenderState, f);
        FruitBatRenderState.roosting = batEntity.isRoosting();
        FruitBatRenderState.flyingAnimationState.copyFrom(batEntity.flyingAnimationState);
        FruitBatRenderState.roostingAnimationState.copyFrom(batEntity.roostingAnimationState);
    }
}
