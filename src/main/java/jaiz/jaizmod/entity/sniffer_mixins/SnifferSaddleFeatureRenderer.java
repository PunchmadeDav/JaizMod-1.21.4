package jaiz.jaizmod.entity.sniffer_mixins;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SnifferEntityModel;
import net.minecraft.client.render.entity.state.SnifferEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SnifferSaddleFeatureRenderer extends FeatureRenderer<SnifferEntityRenderState, SnifferEntityModel> {
    public SnifferSaddleFeatureRenderer(FeatureRendererContext<SnifferEntityRenderState, SnifferEntityModel> context) {
        super(context);
    }

    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/sniffer/saddle.png");

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, SnifferEntityRenderState state, float limbAngle, float limbDistance) {
        if (!state.invisible) {
            if (((SnifferRenderStateMixinAccessor)state).getSaddle()) {
                renderModel(this.getContextModel(), TEXTURE, matrices, vertexConsumers, light, state, -1);
            }
        }
    }
}
