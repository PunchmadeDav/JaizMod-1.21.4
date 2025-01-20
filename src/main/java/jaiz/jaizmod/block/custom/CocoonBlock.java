package jaiz.jaizmod.block.custom;


import jaiz.jaizmod.block.ModBlocks;
import jaiz.jaizmod.entity.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class CocoonBlock extends Block {

    private static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 8, 11);
    public static final IntProperty COCOON_HATCH = ModBlocks.COCOON_HATCH;

    public CocoonBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(COCOON_HATCH, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COCOON_HATCH);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public int getCOCOON_HATCHStage(BlockState state) {
        return state.get(COCOON_HATCH);
    }

    private boolean isReadyToCOCOON_HATCH(BlockState state) {
        return this.getCOCOON_HATCHStage(state) == 2;
    }

    private boolean isBroken(BlockState state) {
        return this.getCOCOON_HATCHStage(state) == 3;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = Direction.DOWN;
        return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
    }


    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!entity.bypassesLandingEffects()) {
            entity.handleFallDamage(fallDistance, 0.0F, world.getDamageSources().fall());
            world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.2f, 0.5f);
            world.setBlockState(pos, state.with(COCOON_HATCH, 3), Block.NOTIFY_LISTENERS);
        }
    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.isBroken(state)) {
        if (!this.isReadyToCOCOON_HATCH(state)) {
            world.playSound(null, pos, SoundEvents.BLOCK_HONEY_BLOCK_STEP, SoundCategory.BLOCKS, 0.2f, 0.9f + random.nextFloat() * 0.2f);
            world.setBlockState(pos, state.with(COCOON_HATCH, this.getCOCOON_HATCHStage(state) + 1), Block.NOTIFY_LISTENERS);
            return;
        }
        world.playSound(null, pos, SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
        world.setBlockState(pos, state.with(COCOON_HATCH, 3), Block.NOTIFY_LISTENERS);
        ModEntities.BUTTERFLY.spawn(world, pos, SpawnReason.MOB_SUMMONED);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        boolean bl = CocoonBlock.isAboveCOCOON_HATCHBooster(world, pos);
        if (!world.isClient() && bl) {
            world.syncWorldEvent(WorldEvents.SNIFFER_EGG_CRACKS, pos, 0);
        }
        int i = bl ? 2200 : 4400;
        int j = i / 3;
        world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(state));
        world.scheduleBlockTick(pos, this, j + world.random.nextInt(3000));
    }

    public static boolean isAboveCOCOON_HATCHBooster(BlockView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isIn(BlockTags.LOGS) || world.getBlockState(pos.down()).isIn(BlockTags.LEAVES);
    }


    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
}
