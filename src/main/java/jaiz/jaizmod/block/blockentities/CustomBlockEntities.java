package jaiz.jaizmod.block.blockentities;

import jaiz.jaizmod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CustomBlockEntities {

    public static final BlockEntityType<SquidLightBlockEntity> SQUID_LIGHT_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SquidLightBlockEntity::new, ModBlocks.WATER_TEMPORARY_LIGHT).build();

    public static void registerBlockEntities(){
        Registry.register(Registries.BLOCK_ENTITY_TYPE, ("ticking_block"), SQUID_LIGHT_BLOCK_ENTITY);
    }

}
