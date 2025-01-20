package jaiz.jaizmod.entity.sniffer_mixins;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SnifferEntityModel;
import net.minecraft.client.render.entity.state.SnifferEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SnifferBullFeatureRenderer extends FeatureRenderer<SnifferEntityRenderState, SnifferEntityModel> {
    public SnifferBullFeatureRenderer(FeatureRendererContext<SnifferEntityRenderState, SnifferEntityModel> context) {
        super(context);
    }

    private static final Identifier BULL_TEXTURE = Identifier.ofVanilla("textures/entity/sniffer/bull.png");

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, SnifferEntityRenderState state, float limbAngle, float limbDistance) {
        if (!state.invisible) {
            if (((SnifferRenderStateMixinAccessor)state).getBull()) {
                renderModel(this.getContextModel(), BULL_TEXTURE, matrices, vertexConsumers, light, state, -1);
            }
        }
    }
}
