package jaiz.jaizmod.entity.sniffer_mixins;

import com.google.common.collect.Maps;
import jaiz.jaizmod.JaizMod;
import jaiz.jaizmod.entity.dragonfly.DragonflyVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SnifferEntityModel;
import net.minecraft.client.render.entity.state.SnifferEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class SnifferWoolFeatureRenderer extends FeatureRenderer<SnifferEntityRenderState, SnifferEntityModel> {
    public SnifferWoolFeatureRenderer(FeatureRendererContext<SnifferEntityRenderState, SnifferEntityModel> context) {
        super(context);
    }

    public static final Map<SnifferVariant, Identifier> SNIFFER_VARIANT =
            Util.make(Maps.newEnumMap(SnifferVariant.class), (map) -> {
                map.put(SnifferVariant.DEFAULT, Identifier.ofVanilla("textures/entity/sniffer/wool.png"));
                map.put(SnifferVariant.BLUE, Identifier.ofVanilla("textures/entity/sniffer/wool_blue.png"));
                map.put(SnifferVariant.AUTUMN, Identifier.ofVanilla("textures/entity/sniffer/wool_autumn.png"));
                map.put(SnifferVariant.LIME, Identifier.ofVanilla("textures/entity/sniffer/wool_lime.png"));
                map.put(SnifferVariant.DANDELION, Identifier.ofVanilla("textures/entity/sniffer/wool_dandelion.png"));
                map.put(SnifferVariant.DUSK, Identifier.ofVanilla("textures/entity/sniffer/wool_dusk.png"));
                map.put(SnifferVariant.GREY, Identifier.ofVanilla("textures/entity/sniffer/wool_grey.png"));
                map.put(SnifferVariant.WHITE, Identifier.ofVanilla("textures/entity/sniffer/wool_white.png"));
                map.put(SnifferVariant.DUSK_LIME, Identifier.ofVanilla("textures/entity/sniffer/wool_dusky_lime.png"));
                map.put(SnifferVariant.GREEN, Identifier.ofVanilla("textures/entity/sniffer/wool_green.png"));
                map.put(SnifferVariant.PALE, Identifier.ofVanilla("textures/entity/sniffer/wool_pale.png"));
                map.put(SnifferVariant.SUNSET, Identifier.ofVanilla("textures/entity/sniffer/wool_sunset.png"));
                map.put(SnifferVariant.MUSKY, Identifier.ofVanilla("textures/entity/sniffer/wool_musky.png"));


            });

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, SnifferEntityRenderState state, float limbAngle, float limbDistance) {
        if (!state.invisible) {
            renderModel(this.getContextModel(), SNIFFER_VARIANT.get(((SnifferRenderStateMixinAccessor)state).getTexture()), matrices, vertexConsumers, light, state, -1);
        }
    }
}
