package jaiz.jaizmod.block.custom;

import jaiz.jaizmod.JaizMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class AncientSproutBlock extends FlowerBlock {
    public AncientSproutBlock(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, Settings settings) {
        super(stewEffect, effectLengthInSeconds, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Direction direction = Direction.fromHorizontalDegrees(random.nextDouble());
        double d = (double)pos.getX() + 0.55 - (double)(random.nextFloat() * 0.1F);
        double e = (double)pos.getY() + 0.55 - (double)(random.nextFloat() * 0.1F);
        double f = (double)pos.getZ() + 0.55 - (double)(random.nextFloat() * 0.1F);
        double g = (double)(0.4F - (random.nextFloat() + random.nextFloat()) * 0.4F);
        if (random.nextInt(5) == 0) {
            world.addParticle(
                    JaizMod.SPROUT_PARTICLE,
                    d + (double)direction.getOffsetX() * g,
                    e + (double)direction.getOffsetY() * g,
                    f + (double)direction.getOffsetZ() * g,
                    random.nextGaussian() * 0.001,
                    random.nextGaussian() * 0.001,
                    random.nextGaussian() * 0.001
            );
        }
    }
}
