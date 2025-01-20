package jaiz.jaizmod.mixin;

import jaiz.jaizmod.entity.sniffer_mixins.SnifferRenderStateMixinAccessor;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.SnifferEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SnifferEntityRenderState.class)
public abstract class SnifferEntityRenderStateMixin extends LivingEntityRenderState implements SnifferRenderStateMixinAccessor {

    @Unique
    public Object texture;

    @Unique
    public void setTexture(Object value) {
        texture = value;
    }

    @Unique
    public Object getTexture() {
        return texture;
    }

    @Unique
    public boolean snowy;
    @Unique
    public void setSnowy(boolean value) {
        snowy = value;
    }
    @Unique
    public boolean getSnowy() {
        return snowy;
    }

    @Unique
    public boolean bull;
    @Unique
    public void setBull(boolean value) {
        bull = value;
    }
    @Unique
    public boolean getBull() {
        return bull;
    }

    @Unique
    public boolean muddy;
    @Unique
    public void setMuddy(boolean value) {
        muddy = value;
    }
    @Unique
    public boolean getMuddy() {
        return muddy;
    }

    @Unique
    public boolean blossom;
    @Unique
    public void setCherryBlossom(boolean value) {
        blossom = value;
    }
    @Unique
    public boolean getCherryBlossom() {
        return blossom;
    }

    @Unique
    public boolean mossy;
    @Unique
    public void setMossy(boolean value) {
        mossy = value;
    }
    @Unique
    public boolean getMossy() {
        return mossy;
    }

    @Unique
    public boolean saddle;
    @Unique
    public void setSaddle(boolean value) {
        saddle = value;
    }
    @Unique
    public boolean getSaddle() {
        return saddle;
    }
}
