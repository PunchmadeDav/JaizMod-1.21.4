package jaiz.jaizmod.entity.fruit_bat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class FruitBatRenderState extends LivingEntityRenderState {
    public boolean roosting;
    public AnimationState roostingAnimationState = new AnimationState();
    public AnimationState flyingAnimationState = new AnimationState();
    public Object texture;
}
