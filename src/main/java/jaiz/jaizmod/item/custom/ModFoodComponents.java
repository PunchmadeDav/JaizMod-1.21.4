package jaiz.jaizmod.item.custom;


import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents{

    public static final FoodComponent RARE_SPICES = new FoodComponent.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static final FoodComponent GOURMET_MEAL = new FoodComponent.Builder().nutrition(12).saturationModifier(1.4f).build();

    public static final FoodComponent COCOON = new FoodComponent.Builder().nutrition(1).saturationModifier(0.1f).build();

    public static final FoodComponent TEA = new FoodComponent.Builder().nutrition(8).saturationModifier(0.7f).alwaysEdible().build();
    public static final FoodComponent WITHER_ROSE_TEA = new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f).alwaysEdible().build();
    public static final FoodComponent SPORE_BLOSSOM_TEA = new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f).alwaysEdible().build();
    public static final FoodComponent GLOW_BERRY_TEA = new FoodComponent.Builder().nutrition(10).saturationModifier(0.6f).alwaysEdible().build();
    public static final FoodComponent TORCH_FLOWER_TEA = new FoodComponent.Builder().nutrition(8).saturationModifier(0.7f).alwaysEdible().build();
    public static final FoodComponent PITCHER_PLANT_TEA = new FoodComponent.Builder().nutrition(8).saturationModifier(0.7f).alwaysEdible().build();
    public static final FoodComponent NETHER_FUNGAL_TEA = new FoodComponent.Builder().nutrition(8).saturationModifier(0.7f).alwaysEdible().build();
    public static final FoodComponent CHORUS_TEA = new FoodComponent.Builder().nutrition(8).saturationModifier(0.7f).alwaysEdible().build();

    public static final FoodComponent SNIFFER_MEAT = new FoodComponent.Builder().nutrition(8).saturationModifier(0.5f).build();
    public static final FoodComponent COOKED_SNIFFER_MEAT = new FoodComponent.Builder().nutrition(12).saturationModifier(1.0f).build();
}
