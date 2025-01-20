package jaiz.jaizmod.entity.thrown_entity;

import jaiz.jaizmod.block.ModBlocks;
import jaiz.jaizmod.entity.ModEntities;
import jaiz.jaizmod.item.ModItems;
import jaiz.jaizmod.statuseffects.ModStatusEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static jaiz.jaizmod.JaizMod.STINK_ITEM_PARTICLE;

public class GuanoEntity extends ThrownItemEntity {
    public GuanoEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public GuanoEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.GUANO, owner, world, stack);
    }

    public GuanoEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModEntities.GUANO, x, y, z, world, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GUANO;
    }



    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getWorld().isClient) {
        Entity entity = entityHitResult.getEntity();
        if(entity instanceof LivingEntity) {
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(ModStatusEffects.STINKY, 600, 0));
        }
        if(this.isOnFire()){
            entity.serverDamage(entity.getDamageSources().thrown(this, this.getOwner()), 8.0F);
            entity.setOnFireFor(3.0f);
        } else{
            entity.serverDamage(entity.getDamageSources().thrown(this, this.getOwner()), 1.0F);
        }
        this.playSound(SoundEvents.ENTITY_SLIME_HURT, 1.0f, 1.0f);
        this.getWorld().sendEntityStatus(this,
                EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
        this.discard();
        super.onEntityHit(entityHitResult);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        World world = this.getWorld();
        if (!this.getWorld().isClient) {
            this.playSound(SoundEvents.ENTITY_SLIME_HURT, 1.0f, 1.0f);
            this.getWorld().sendEntityStatus(this,
                    EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.emitGameEvent(GameEvent.BLOCK_PLACE);
            BlockPos blockPos = this.getBlockPos();
            BlockState blockState = ModBlocks.GUANO_PILE.getDefaultState();
            if(this.getBlockStateAtPos().isAir())
            {if(this.getWorld().getBlockState(this.getBlockPos().down()).isFullCube(world, this.getBlockPos().down())) {
            world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL);
                world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(this, blockState));}
            } else {
                this.entityItemDropper();
            }
            this.discard();
        }
        super.onCollision(hitResult);
    }

    public void entityItemDropper() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dropStack(serverWorld, new ItemStack(ModItems.GUANO));
        }
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getStack();
        return !itemStack.isEmpty() && !itemStack.isOf(this.getDefaultItem())
                ? new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack)
                : STINK_ITEM_PARTICLE;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for (int i = 0; i < 2; i++) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

}
