package jaiz.jaizmod.item.custom;

import jaiz.jaizmod.entity.thrown_entity.GuanoEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class GuanoItem extends Item implements ProjectileItem {


    public GuanoItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.NEUTRAL, 0.3f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

        if (world instanceof ServerWorld serverWorld) {
            ProjectileEntity.spawnWithVelocity(GuanoEntity::new, serverWorld, itemStack, user, 0.0F, 1.0f, 1.0F);
        }



        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        return new GuanoEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
    }

}
