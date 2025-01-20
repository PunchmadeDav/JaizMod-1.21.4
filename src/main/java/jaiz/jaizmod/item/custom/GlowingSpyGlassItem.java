package jaiz.jaizmod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GlowingSpyGlassItem extends SpyglassItem {

    public GlowingSpyGlassItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(itemStack, 60);
        int o = user.getRandom().nextInt(9) + 3;
        for (int i = 0; i < o; i++) {
            user.getWorld().addParticle(ParticleTypes.GLOW, user.getParticleX(1.2), user.getRandomBodyY(), user.getParticleZ(1.2), 0.0, 0.0, 0.0);
        }
        user.playSound(SoundEvents.ENTITY_GLOW_SQUID_AMBIENT, 1.0F, 1.0F);
        user.playSound(SoundEvents.ITEM_SPYGLASS_USE, 1.0F, 1.0F);
        if (!user.getAbilities().creativeMode) {
            itemStack.damage(1, user, LivingEntity.getSlotForHand(hand));
        }
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 3, 0, false, false));
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        this.playStopUsingSound(user);
        return true;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        this.playStopUsingSound(user);
        return stack;
    }

    private void playStopUsingSound(LivingEntity user) {
        user.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0F, 1.0F);
        user.playSound(SoundEvents.ENTITY_GLOW_SQUID_AMBIENT, 1.0F, 0.7F);
    }

}
