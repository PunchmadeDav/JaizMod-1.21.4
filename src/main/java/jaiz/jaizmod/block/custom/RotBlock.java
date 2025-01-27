package jaiz.jaizmod.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jaiz.jaizmod.block.ModBlocks;
import jaiz.jaizmod.worldgen.features.ModConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;


import static jaiz.jaizmod.block.custom.RottingLog.ROTTING;

public class RotBlock extends Block implements Fertilizable{
    public static final BooleanProperty GROWING = ModBlocks.GROWING;

    public static final MapCodec<RotBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            RegistryKey.createCodec(RegistryKeys.CONFIGURED_FEATURE).fieldOf("feature").forGetter(block -> block.feature), createSettingsCodec()
                    )
                    .apply(instance, RotBlock::new)
    );
    private final RegistryKey<ConfiguredFeature<?, ?>> feature;



    public RotBlock(RegistryKey<ConfiguredFeature<?, ?>> feature, Settings settings) {
        super(settings);
        this.feature = feature;
        this.getDefaultState().with(GROWING, false);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
            return super.getPlacementState(ctx).with(GROWING, false);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        BlockState blockState2 = ModBlocks.ROTTEN_LOG.getDefaultState().with(ROTTING, 1);
        int i =random.nextInt(12);

        if (blockState.isIn(BlockTags.LOGS)) {
            world.setBlockState(blockPos, blockState2);
        } else{
            world.getRegistryManager()
                    .getOptional(RegistryKeys.CONFIGURED_FEATURE)
                    .flatMap(registry -> registry.getOptional(this.feature))
                    .ifPresent(entry -> ((ConfiguredFeature<?, ?>)entry.value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up()));
            if(i >= 10)
            {
                world.getRegistryManager()
                        .getOptional(RegistryKeys.CONFIGURED_FEATURE)
                        .flatMap(registry -> registry.getOptional(ModConfiguredFeatures.COLUMBINE_PATCH))
                        .ifPresent(entry -> ((ConfiguredFeature<?, ?>)entry.value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up()));

            }
            else if(i == 6) {
            world.getRegistryManager()
                    .getOptional(RegistryKeys.CONFIGURED_FEATURE)
                    .flatMap(registry -> registry.getOptional(ModConfiguredFeatures.RED_MUSHROOM))
                    .ifPresent(entry -> ((ConfiguredFeature<?, ?>)entry.value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up()));
            }
            else if(i == 5) {
                world.getRegistryManager()
                        .getOptional(RegistryKeys.CONFIGURED_FEATURE)
                        .flatMap(registry -> registry.getOptional(ModConfiguredFeatures.BROWN_MUSHROOM))
                        .ifPresent(entry -> ((ConfiguredFeature<?, ?>)entry.value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up()));
            }
            else {
                world.getRegistryManager()
                        .getOptional(RegistryKeys.CONFIGURED_FEATURE)
                        .flatMap(registry -> registry.getOptional(ModConfiguredFeatures.ROT_ROOTS))
                        .ifPresent(entry -> ((ConfiguredFeature<?, ?>)entry.value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up()));
            }



        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 1) {
            return;
        }
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        BlockState blockState2 = ModBlocks.ROTTEN_LOG.getDefaultState().with(ROTTING, 1);
        if (blockState.isIn(BlockTags.LOGS)) {
            if (!blockState.isOf(ModBlocks.ROTTEN_LOG)) {
                world.setBlockState(blockPos, blockState2);
            }
        }

        BlockState blockStatetrue = state.with(GROWING, true);
        BlockState blockStatefalse = state.with(GROWING, false);
        BlockPos blockPos2 = pos.up();
        BlockState blockState3 = world.getBlockState(blockPos2);
        if (blockState3.isSolidBlock(world, blockPos2)) {
            world.setBlockState(pos, blockStatetrue, Block.NOTIFY_ALL);
        } else {
            world.setBlockState(pos, blockStatefalse, Block.NOTIFY_ALL);
        }

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GROWING);
    }

    @Override
    public Fertilizable.FertilizableType getFertilizableType() {
        return Fertilizable.FertilizableType.NEIGHBOR_SPREADER;
    }




}
