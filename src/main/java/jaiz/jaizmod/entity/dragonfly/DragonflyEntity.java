package jaiz.jaizmod.entity.dragonfly;

import jaiz.jaizmod.entity.ModEntities;
import jaiz.jaizmod.entity.butterfly.ButterflyEntity;
import jaiz.jaizmod.entity.butterfly.ButterflyVariant;
import jaiz.jaizmod.item.ModItems;
import jaiz.jaizmod.util.Bottlable;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;

public class DragonflyEntity extends AnimalEntity implements Flutterer, Bottlable {
    private static final TrackedData<Boolean> FROM_BOTTLE = DataTracker.registerData(DragonflyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public DragonflyEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 4;
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.lookControl = new ButterflyLookControl(this);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBottle();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return Bottlable.tryBottle(player, hand, this).orElse(super.interactMob(player, hand));
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        if (world.getBlockState(pos).isAir()) {
            return 10.0f;
        }
        return 0.0f;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.BEE_FOOD);
    }

    public AnimationState dragonflyAnimationState = new AnimationState();
    private int dragonflyAnimationTimeout = 0;

    private void setupAnimationStates() {
        if (this.isOnGround() && this.navigation.isIdle()) {
            this.dragonflyAnimationState.stop();
        }
        if (this.dragonflyAnimationTimeout <= 0) {
            this.dragonflyAnimationTimeout = 10;
            if (this.isOnGround() && this.navigation.isIdle()) {
                this.dragonflyAnimationState.stop();
            } else {
                this.dragonflyAnimationState.start(this.age);
            }
        } else {
            --this.dragonflyAnimationTimeout;
        }
    }

    @Override
    @Nullable
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return ModEntities.DRAGONFLY.spawn(serverWorld, DragonflyEntity.this.getBlockPos(), SpawnReason.MOB_SUMMONED);
    }

    @Override
    protected void initGoals() {

        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(3, new DragonflyWanderAroundGoal());
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, stack -> stack.isIn(ItemTags.BEE_FOOD), false));
    }


    public static DefaultAttributeContainer.Builder createDragonflyAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.TEMPT_RANGE, 16)
                .add(EntityAttributes.MAX_HEALTH, 8)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.FOLLOW_RANGE, 10)
                .add(EntityAttributes.SCALE, 1.0f)
                .add(EntityAttributes.FLYING_SPEED, 0.6f);
    }

    @Override
    protected void onGrowUp() {
        if (!this.isBaby() && this.hasVehicle() && this.getVehicle() instanceof AbstractBoatEntity abstractBoatEntity && !abstractBoatEntity.isSmallerThanBoat(this)) {
            this.stopRiding();
        }
        if(this.isBaby()){
            Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.SCALE)).setBaseValue(0.5);
        } else {
            Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.SCALE)).setBaseValue(1.0);
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {

            @Override
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }

            @Override
            public void tick() {
                super.tick();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanPathThroughDoors(true);
        return birdNavigation;
    }

    @Override
    public boolean isInAir() {
        return !this.isOnGround();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBottle", this.isFromBottle());
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBottle(nbt.getBoolean("FromBottle"));
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(FROM_BOTTLE, false);
        builder.add(DATA_ID_TYPE_VARIANT, 0);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(DragonflyEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        DragonflyVariant variant = Util.getRandom(DragonflyVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public Object getVariant() {
        return DragonflyVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(DragonflyVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getDragonflyVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    @Override
    public boolean isFromBottle() {
        return this.dataTracker.get(FROM_BOTTLE);
    }

    @Override
    public void setFromBottle(boolean fromBottle) {
        this.dataTracker.set(FROM_BOTTLE, fromBottle);
    }

    @Override
    public ItemStack getBottleItem() {
        return new ItemStack(ModItems.DRAGONFLY_BOTTLE);
    }


    @Override
    public void copyDataToStack(ItemStack stack) {
        Bottlable.copyDataToStack(this, stack);
        NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, stack, nbtCompound -> nbtCompound.putInt("BottleVariantTag", this.getDragonflyVariant()));
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bottlable.copyDataFromNbt(this, nbt);
        if (nbt.contains("BottleVariantTag", NbtElement.INT_TYPE)) {
            this.setVariant(DragonflyVariant.byId(nbt.getInt("BottleVariantTag")));
        }
    }




    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            setupAnimationStates();
        }

        if (this.isTouchingWater()){
            this.setVelocity(this.getVelocity().add(0.0, 0.01, 0.0));
        }

        if (DragonflyEntity.this.random.nextInt(100) == 1 && this.isInAir()){
            this.playSound(SoundEvents.ENTITY_BEE_LOOP, 0.3f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 0.8f);
        }

    }

    static class ButterflyLookControl
            extends LookControl {
        ButterflyLookControl(MobEntity entity) {
            super(entity);
        }

        @Override
        public void tick() {
            super.tick();
        }

    }


    class DragonflyWanderAroundGoal
            extends Goal {

        DragonflyWanderAroundGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return DragonflyEntity.this.navigation.isIdle() && DragonflyEntity.this.random.nextInt(20) == 1;
        }

        @Override
        public boolean shouldContinue() {
            return DragonflyEntity.this.navigation.isFollowingPath();
        }

        @Override
        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                DragonflyEntity.this.navigation.startMovingAlong(DragonflyEntity.this.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            vec3d2 = DragonflyEntity.this.getRotationVec(0.0f);
            int i = 8;
            Vec3d vec3d3 = AboveGroundTargeting.find(DragonflyEntity.this, 4, 14, vec3d2.x, vec3d2.z, 1.5707964f, 3, 1);
            if (vec3d3 != null) {
                return vec3d3;
            }return NoPenaltySolidTargeting.find(DragonflyEntity.this, 4, 8, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);}

    }

}
