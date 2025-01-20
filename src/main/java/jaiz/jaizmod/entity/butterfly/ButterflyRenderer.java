package jaiz.jaizmod.entity.butterfly;

import com.google.common.collect.Maps;
import jaiz.jaizmod.JaizMod;
import jaiz.jaizmod.entity.ModModelLayers;
import jaiz.jaizmod.entity.bandit.Bandit;
import jaiz.jaizmod.entity.bandit.BanditEntity;
import jaiz.jaizmod.entity.bandit.BanditRenderState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.RabbitEntityRenderer;
import net.minecraft.client.render.entity.state.RabbitEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class ButterflyRenderer extends MobEntityRenderer<ButterflyEntity, ButterflyRenderState, Butterfly> {

    private static final Identifier EMCHMU = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/butterfly_em.png");
    private static final Identifier SPPRINKLES = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/butterfly_spprinkles.png");
    private static final Identifier ROMEO = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/butterfly_romeo.png");
    private static final Identifier MEAT = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/meat_grinder_fly.png");
    private static final Identifier EPICCOOL = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/butterfly__epiccool_cinnabar_moth.png");
    private static final Identifier ETHANTIANYU = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/butterfly_ethantianyu.png");
    private static final Identifier DERNEV = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/butterfly_dernevs_enderfly.png");
    private static final Identifier TBK = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/butterfly_tbk.png");
    private static final Identifier ZUPERZ = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/butterfly_zuper.png");
    private static final Identifier ZUPER_SHINY = Identifier.of(JaizMod.MOD_ID,"textures/entity/butterfly/butterfly_zuper_2.png");


    public static final Map<ButterflyVariant, Identifier> BUTTERFLY_VARIANT =
            Util.make(Maps.newEnumMap(ButterflyVariant.class), (map) -> {
                map.put(ButterflyVariant.MONARCH, Identifier.of(JaizMod.MOD_ID, "textures/entity/butterfly/butterfly_monarch.png"));
                map.put(ButterflyVariant.WHITE_MONARCH, Identifier.of(JaizMod.MOD_ID, "textures/entity/butterfly/butterfly_white_monarch.png"));
                map.put(ButterflyVariant.YELLOW_MONARCH, Identifier.of(JaizMod.MOD_ID, "textures/entity/butterfly/butterfly_yellow_monarch.png"));
                map.put(ButterflyVariant.ATLAS_MOTH, Identifier.of(JaizMod.MOD_ID, "textures/entity/butterfly/butterfly_atlas_moth.png"));
                map.put(ButterflyVariant.LUNA_MOTH, Identifier.of(JaizMod.MOD_ID, "textures/entity/butterfly/butterfly_luna_moth.png"));
                map.put(ButterflyVariant.CABBAGE_MOTH, Identifier.of(JaizMod.MOD_ID, "textures/entity/butterfly/butterfly_cabbage_moth.png"));
                map.put(ButterflyVariant.CARINS_BIRDWING, Identifier.of(JaizMod.MOD_ID, "textures/entity/butterfly/butterfly_cairns_birdwing.png"));
                map.put(ButterflyVariant.ULYSSES, Identifier.of(JaizMod.MOD_ID, "textures/entity/butterfly/butterfly_ulysses_swallowtail.png"));
                map.put(ButterflyVariant.JAMPOTT, Identifier.of(JaizMod.MOD_ID, "textures/entity/butterfly/butterfly_jampott.png"));
            });

    public ButterflyRenderer(EntityRendererFactory.Context context) {
        super(context, new Butterfly(context.getPart(ModModelLayers.BUTTERFLY)), 0.35f);
    }

    @Override
    public ButterflyRenderState createRenderState() {
        return new ButterflyRenderState();
    }


    @Override
    public Identifier getTexture(ButterflyRenderState state) {
        if (state.isEm) {
            return EMCHMU;
        }else if (state.isZuperShiny) {
            return ZUPER_SHINY;
        }else if (state.isZuper) {
            return ZUPERZ;
        }else if (state.isSpprinkles) {
            return SPPRINKLES;
        }else if (state.isEthantianyu) {
            return ETHANTIANYU;
        }else if (state.isEpiccool) {
            return EPICCOOL;
        }else if (state.isTBK) {
            return TBK;
        }else if (state.isDernevs) {
            return DERNEV;
        }else if (state.isRomeo) {
            return ROMEO;
        }else if (state.isMeatGrinder) {
            return MEAT;
        }

        else {
            return BUTTERFLY_VARIANT.get(state.texture);
        }
    }

    public void updateRenderState(ButterflyEntity butterfly, ButterflyRenderState butterflyState, float f) {
        super.updateRenderState(butterfly, butterflyState, f);
        butterflyState.isEm = "emchmu".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isEm = "em".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isSpprinkles = "spprinkles".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isRomeo = "Romeo".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isRomeo = "RomeoSnowblitz".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isDernevs = "dernevs".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isEpiccool = "epiccool".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isEthantianyu = "Ethantianyu".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isTBK = "TBK".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isMeatGrinder = "MeatGrinder".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isZuper = "ZuperZ".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.isZuperShiny = "ZuperZShiny".equals(Formatting.strip(butterfly.getName().getString()));
        butterflyState.texture = butterfly.getVariant();
        butterflyState.flyingAnimationState.copyFrom(butterfly.butterflyAnimationState);
        butterflyState.idleAnimationState.copyFrom(butterfly.idlebutterflyAnimationState);
    }
}
