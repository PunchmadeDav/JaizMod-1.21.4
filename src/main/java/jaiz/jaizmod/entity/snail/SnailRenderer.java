package jaiz.jaizmod.entity.snail;

import jaiz.jaizmod.JaizMod;
import jaiz.jaizmod.entity.ModModelLayers;
import jaiz.jaizmod.entity.caterpillar.Caterpillar;
import jaiz.jaizmod.entity.caterpillar.CaterpillarEntity;
import jaiz.jaizmod.entity.caterpillar.CaterpillarRenderState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SnailRenderer extends MobEntityRenderer<SnailEntity, LivingEntityRenderState, Snail> {
    private static final Identifier TEXTURE = Identifier.of(JaizMod.MOD_ID, "textures/entity/snail.png");

    public SnailRenderer(EntityRendererFactory.Context context) {
        super(context, new Snail(context.getPart(ModModelLayers.SNAIL)), 0f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    public void render(SnailEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }
}
