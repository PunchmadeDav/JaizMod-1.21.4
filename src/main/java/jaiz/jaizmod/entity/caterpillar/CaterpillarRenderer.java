package jaiz.jaizmod.entity.caterpillar;

import com.google.common.collect.Maps;
import jaiz.jaizmod.JaizMod;
import jaiz.jaizmod.entity.ModModelLayers;
import jaiz.jaizmod.entity.butterfly.Butterfly;
import jaiz.jaizmod.entity.butterfly.ButterflyEntity;
import jaiz.jaizmod.entity.butterfly.ButterflyRenderState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class CaterpillarRenderer extends MobEntityRenderer<CaterpillarEntity, CaterpillarRenderState, Caterpillar> {


    public static final Map<CaterpillarVariant, Identifier> CATERPILLAR_VARIANT =
            Util.make(Maps.newEnumMap(CaterpillarVariant.class), (map) -> {
                map.put(CaterpillarVariant.GREEN, Identifier.of(JaizMod.MOD_ID, "textures/entity/caterpillar/caterpillar_green.png"));
                map.put(CaterpillarVariant.YELLOW, Identifier.of(JaizMod.MOD_ID, "textures/entity/caterpillar/caterpillar_yellow.png"));
                map.put(CaterpillarVariant.GREEN_RED, Identifier.of(JaizMod.MOD_ID, "textures/entity/caterpillar/caterpillar_green_red.png"));
            });

    public CaterpillarRenderer(EntityRendererFactory.Context context) {
        super(context, new Caterpillar(context.getPart(ModModelLayers.CATERPILLAR)), 0.35f);
    }

    @Override
    public CaterpillarRenderState createRenderState() {
        return new CaterpillarRenderState();
    }

    @Override
    public Identifier getTexture(CaterpillarRenderState state) {
        return CATERPILLAR_VARIANT.get(state.texture);
    }

    public void updateRenderState(CaterpillarEntity entity, CaterpillarRenderState entityState, float f) {
        super.updateRenderState(entity, entityState, f);
        entityState.texture = entity.getVariant();
    }
}
