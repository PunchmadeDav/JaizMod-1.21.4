package jaiz.jaizmod.entity.mason_mouth;

import com.google.common.collect.ImmutableMap;
import jaiz.jaizmod.JaizMod;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MasonMouthFeatureRenderer extends FeatureRenderer<MasonMouthRenderState, Masonmouth> {

    public MasonMouthFeatureRenderer(FeatureRendererContext<MasonMouthRenderState, Masonmouth> context) {
        super(context);
    }

    private static final Identifier CRACK_TEXTURE = Identifier.of(JaizMod.MOD_ID, "textures/entity/mason_mouth/cracked.png");

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, MasonMouthRenderState state, float limbAngle, float limbDistance) {
        if (!state.invisible) {
            if (state.cracked) {
                renderModel(this.getContextModel(), CRACK_TEXTURE, matrices, vertexConsumers, light, state, -1);
            }
        }
    }
}
