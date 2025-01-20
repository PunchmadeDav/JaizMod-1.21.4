package jaiz.jaizmod.entity.firefly;


import jaiz.jaizmod.JaizMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;


@Environment(EnvType.CLIENT)
public class FireFliesFeatureRenderer<M extends Fireflies> extends EyesFeatureRenderer<FireFlyRenderState, M> {

    private static final RenderLayer GLOW = RenderLayer.getEyes(Identifier.of(JaizMod.MOD_ID, "textures/entity/fireflies/fireflies.png"));

    public FireFliesFeatureRenderer(FeatureRendererContext<FireFlyRenderState, M> featureRendererContext) {
        super(featureRendererContext);
    }


    @Override
    public RenderLayer getEyesTexture() {
        return GLOW;
    }
}


