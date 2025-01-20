package jaiz.jaizmod.item.custom;

import jaiz.jaizmod.util.Bottlable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;


public class EntityBottle extends Item {
    private final EntityType<? extends MobEntity> entityType;

    public EntityBottle(EntityType<? extends MobEntity> type, Item.Settings settings) {
        super(settings);
        this.entityType = type;
    }

    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerWorld) {
            this.spawnEntity((ServerWorld)world, stack, pos);
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }


    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        MobEntity mobEntity = this.entityType.create(world, EntityType.copier(world, stack, null), pos, SpawnReason.BUCKET, true, false);
        if (mobEntity instanceof Bottlable bottlable) {
            NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
            bottlable.copyDataFromNbt(nbtComponent.copyNbt());
            bottlable.setFromBottle(true);
        }
        if (mobEntity != null) {
            world.spawnEntityAndPassengers(mobEntity);
            mobEntity.playAmbientSound();
        }
    }

    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        return !player.isInCreativeMode() ? new ItemStack(Items.GLASS_BOTTLE) : stack;
    }

    /*@Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
        BlockPos blockPos = blockHitResult.getBlockPos();
        this.onEmptied(user, world, itemStack, blockPos);
        if (user instanceof ServerPlayerEntity) {
            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, blockPos, itemStack);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, user, getEmptiedStack(itemStack, user));
            return ActionResult.SUCCESS.withNewHandStack(itemStack2);
    }*/

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos2;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                blockPos2 = blockPos;
            } else {
                blockPos2 = blockPos.offset(direction);
            }
            if (context.getPlayer() != null) {
                ItemStack itemStack = context.getPlayer().getStackInHand(context.getHand());
                this.onEmptied(context.getPlayer(), world, itemStack, blockPos2);
                if (context.getPlayer() instanceof ServerPlayerEntity) {
                    Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) context.getPlayer(), blockPos2, itemStack);
                }
                context.getPlayer().incrementStat(Stats.USED.getOrCreateStat(this));
                itemStack.decrement(1);
                ItemStack itemStack2 = getEmptiedStack(itemStack, context.getPlayer());
                context.getPlayer().setStackInHand(context.getHand(), itemStack2);
                return ActionResult.SUCCESS.withNewHandStack(itemStack2);
            }
        }
        return ActionResult.SUCCESS;
    }

}


