package jaiz.jaizmod.item.custom.teas;


import jaiz.jaizmod.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class NovelTeaItem
        extends TeaItem {
    public NovelTeaItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity && ((PlayerEntity)user).getAbilities().creativeMode) {
            user.emitGameEvent(GameEvent.DRINK);
            return itemStack;
        }
        if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
            user.serverDamage(world.getDamageSources().explosion(null), 20.0F);
            user.getWorld().createExplosion(user, user.getX(), user.getY(), user.getZ(), 1, World.ExplosionSourceType.TNT);
        }
        return new ItemStack(ModItems.TEA_CUP);
    }
}
