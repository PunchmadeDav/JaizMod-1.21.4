package jaiz.jaizmod.entity.caterpillar;

import jaiz.jaizmod.block.ModBlocks;
import jaiz.jaizmod.entity.butterfly.ButterflyEntity;
import jaiz.jaizmod.entity.butterfly.ButterflyVariant;
import jaiz.jaizmod.item.ModItems;
import jaiz.jaizmod.util.Bottlable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class CaterpillarEntity extends AnimalEntity implements Bottlable {
    private static final TrackedData<Boolean> FROM_BOTTLE = DataTracker.registerData(CaterpillarEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public CaterpillarEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 4;
    }

    public int cocoonTime = this.random.nextInt(9000) + 2000;

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBottle();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return Bottlable.tryBottle(player, hand, this).orElse(super.interactMob(player, hand));
    }

    @Override
    public void tick() {
        if (!this.getWorld().isClient && this.isAlive() && --this.cocoonTime <= 0 && this.getBlockStateAtPos().isAir()) {
            World world = this.getWorld();
            this.playSound(SoundEvents.ENTITY_TURTLE_EGG_CRACK, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            this.emitGameEvent(GameEvent.BLOCK_PLACE);
            BlockPos blockPos2 = this.getBlockPos();
            BlockState blockState = ModBlocks.COCOON_BLOCK.getDefaultState();
            world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
            world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this, blockState));
            this.discard();
        } else if (!this.getWorld().isClient && this.isAlive() && --this.cocoonTime <= 0 && this.entityItemDropper()) {
            this.discard();
        }
        super.tick();
    }

    public boolean entityItemDropper() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dropStack(serverWorld, new ItemStack(ModItems.COCOON_BLOCK));
            this.playSound(SoundEvents.ENTITY_TURTLE_EGG_CRACK, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
        }
        return true;
    }

    @Override
    protected void initGoals() {
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new WanderAroundGoal(this, 1.25));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, stack -> stack.isIn(ItemTags.BEE_FOOD), false));
    }


    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 5)
                .add(EntityAttributes.TEMPT_RANGE, 16)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.125f)
                .add(EntityAttributes.FOLLOW_RANGE, 10);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
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

    private int getCaterpillarVariant() {
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
        return new ItemStack(ModItems.CATERPILLAR_BOTTLE);
    }


    @Override
    public void copyDataToStack(ItemStack stack) {
        Bottlable.copyDataToStack(this, stack);
        NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, stack, nbtCompound -> nbtCompound.putInt("BottleVariantTag", this.getCaterpillarVariant()));
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bottlable.copyDataFromNbt(this, nbt);
        if (nbt.contains("BottleVariantTag", NbtElement.INT_TYPE)) {
            this.setVariant(CaterpillarVariant.byId(nbt.getInt("BottleVariantTag")));
        }
    }



    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(CaterpillarEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        CaterpillarVariant variant = Util.getRandom(CaterpillarVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public Object getVariant() {
        return CaterpillarVariant.byId(this.getTypeVariant() & 255);
    }

    public int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(CaterpillarVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

}
