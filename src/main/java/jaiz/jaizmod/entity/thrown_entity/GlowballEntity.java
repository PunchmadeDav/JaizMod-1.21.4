package jaiz.jaizmod.entity.thrown_entity;


import jaiz.jaizmod.entity.ModEntities;
import jaiz.jaizmod.item.ModItems;
import jaiz.jaizmod.statuseffects.ModStatusEffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;


public class GlowballEntity extends ThrownItemEntity {
    public GlowballEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }


    public GlowballEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.GLOWBALL, owner, world, stack);
    }

    public GlowballEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModEntities.GLOWBALL, x, y, z, world, stack);
    }


    @Override
    protected Item getDefaultItem() {
        return ModItems.GLOW_BALL;
    }



    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getWorld().isClient) {
        Entity entity = entityHitResult.getEntity();
        if(entity instanceof LivingEntity) {
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(ModStatusEffects.SHIMMER, 1200, 0));
        }
        entity.serverDamage(entity.getDamageSources().thrown(this, this.getOwner()), 1.0F);
        this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
        this.discard();
        super.onEntityHit(entityHitResult);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (!this.getWorld().isClient) {
            this.playSound(SoundEvents.BLOCK_SNOW_BREAK, 1.0f, 1.0f);
            this.getWorld().sendEntityStatus(this,
                    EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }

        super.onCollision(hitResult);
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getStack();
        return !itemStack.isEmpty() && !itemStack.isOf(this.getDefaultItem())
                ? new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack)
                : ParticleTypes.GLOW;
    }

    private ParticleEffect getParticleParameters2() {
        ItemStack itemStack = this.getStack();
        return !itemStack.isEmpty() && !itemStack.isOf(this.getDefaultItem())
                ? new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack)
                : ParticleTypes.ITEM_SNOWBALL;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ParticleEffect particleEffect = this.getParticleParameters();
            ParticleEffect particleEffect2 = this.getParticleParameters2();

            for (int i = 0; i < 2; i++) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
            for (int i = 0; i < 2; i++) {
                this.getWorld().addParticle(particleEffect2, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

}
