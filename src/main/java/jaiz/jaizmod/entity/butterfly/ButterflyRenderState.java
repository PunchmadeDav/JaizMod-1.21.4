package jaiz.jaizmod.entity.butterfly;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class ButterflyRenderState extends LivingEntityRenderState {

    public boolean isEm;
    public boolean isSpprinkles;
    public boolean isRomeo;
    public boolean isEthantianyu;
    public boolean isTBK;
    public boolean isEpiccool;
    public boolean isDernevs;
    public boolean isZuper;
    public boolean isZuperShiny;
    public boolean isMeatGrinder;

    public AnimationState flyingAnimationState = new AnimationState();
    public AnimationState idleAnimationState = new AnimationState();
    public Object texture;
}
