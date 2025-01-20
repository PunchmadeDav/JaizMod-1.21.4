package jaiz.jaizmod.entity;

import jaiz.jaizmod.JaizMod;
import jaiz.jaizmod.entity.bandit.BanditEntity;
import jaiz.jaizmod.entity.butterfly.ButterflyEntity;
import jaiz.jaizmod.entity.caterpillar.CaterpillarEntity;
import jaiz.jaizmod.entity.dragonfly.DragonflyEntity;
import jaiz.jaizmod.entity.firefly.FireFlySwarmEntity;
import jaiz.jaizmod.entity.fruit_bat.FruitBatEntity;
import jaiz.jaizmod.entity.mason_mouth.MasonmouthEntity;
import jaiz.jaizmod.entity.snail.SnailEntity;
import jaiz.jaizmod.entity.thrown_entity.DynamiteEntity;
import jaiz.jaizmod.entity.thrown_entity.GlowballEntity;
import jaiz.jaizmod.entity.thrown_entity.GuanoEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<MasonmouthEntity> MASON_MOUTH = registerEntityType("mason_mouth",
            EntityType.Builder.create(MasonmouthEntity::new, SpawnGroup.MISC).dimensions(0.875f, 1.0f));

    public static final EntityType<BanditEntity> BANDIT = registerEntityType("bandit",
            EntityType.Builder.create(BanditEntity::new, SpawnGroup.MISC).dimensions(0.6f, 1.9f));

    public static final EntityType<DynamiteEntity> DYNAMITE = registerEntityType("dynamite",
            EntityType.Builder.<DynamiteEntity>create(DynamiteEntity::new, SpawnGroup.MISC).dimensions(0.4f, 0.4f));

    public static final EntityType<GuanoEntity> GUANO = registerEntityType("guano",
            EntityType.Builder.<GuanoEntity>create(GuanoEntity::new, SpawnGroup.MISC).dimensions(0.4f, 0.4f));

    public static final EntityType<GlowballEntity> GLOWBALL = registerEntityType("glowball",
            EntityType.Builder.<GlowballEntity>create(GlowballEntity::new, SpawnGroup.MISC).dimensions(0.4f, 0.4f));

    public static final EntityType<FruitBatEntity> FRUIT_BAT = registerEntityType("fruit_bat",
            EntityType.Builder.create(FruitBatEntity::new, SpawnGroup.AMBIENT).dimensions(0.6f, 1.0f));

    public static final EntityType<ButterflyEntity> BUTTERFLY = registerEntityType("butterfly",
        EntityType.Builder.create(ButterflyEntity::new, SpawnGroup.CREATURE).dimensions(0.6f, 0.6f));

    public static final EntityType<FireFlySwarmEntity> FIRE_FLY_SWARM = registerEntityType("fire_fly_swarm",
        EntityType.Builder.create(FireFlySwarmEntity::new, SpawnGroup.CREATURE).dimensions(1.0f, 1.0f));

    public static final EntityType<DragonflyEntity> DRAGONFLY = registerEntityType("dragonfly",
        EntityType.Builder.create(DragonflyEntity::new, SpawnGroup.CREATURE).dimensions(0.7f, 0.7f));

    public static final EntityType<CaterpillarEntity> CATERPILLAR = registerEntityType("caterpillar",
        EntityType.Builder.create(CaterpillarEntity::new, SpawnGroup.CREATURE).dimensions(0.6f, 0.4f));

    public static final EntityType<SnailEntity> SNAIL = registerEntityType("snail",
        EntityType.Builder.create(SnailEntity::new, SpawnGroup.CREATURE).dimensions(.4f, .4f));

    public static <T extends Entity> EntityType<T> registerEntityType(String path, EntityType.Builder<T> entityTypeBuilder) {
        Identifier id = Identifier.of(JaizMod.MOD_ID, path);
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, id);
        return Registry.register(Registries.ENTITY_TYPE, key, entityTypeBuilder.build(key));
    }
}

