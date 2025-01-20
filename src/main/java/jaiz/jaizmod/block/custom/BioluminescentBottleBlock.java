package jaiz.jaizmod.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BioluminescentBottleBlock extends LanternBlock {

    public BioluminescentBottleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(random.nextInt(2) == 1){
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = (double)i + random.nextDouble();
            double e = (double)j + 0.7;
            double f = (double)k + random.nextDouble();
            world.addParticle(ParticleTypes.GLOW, d, e, f, 0.0, 0.0, 0.0);
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            int s = random.nextInt(15);

            for (int l = 0; l < s; l++) {
                mutable.set(i + MathHelper.nextInt(random, -10, 10), j - random.nextInt(10), k + MathHelper.nextInt(random, -10, 10));
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isFullCube(world, mutable)) {
                    world.addParticle(
                            ParticleTypes.GLOW,
                            (double)mutable.getX() + random.nextDouble(),
                            (double)mutable.getY() + random.nextDouble() + random.nextInt(5),
                            (double)mutable.getZ() + random.nextDouble(),
                            0.0,
                            0.0,
                            0.0
                    );
                }
            }}
    }

}
