package jaiz.jaizmod.item.custom;

import jaiz.jaizmod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
import java.util.function.Supplier;

public record ModToolMaterial(
        TagKey<Block> incorrectBlocksForDrops, int durability, float speed, float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems
) {

    public static final net.minecraft.item.ToolMaterial BATTERED = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 712, 15f, 4.0f, 20, ModTags.Items.BATTERED_REPARABLE);

    public static final net.minecraft.item.ToolMaterial OBSIDIAN_DAGGER = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 400, 0.4f, 4.0f, 8, ModTags.Items.OBSIDIAN_SHARDS);
    public static final net.minecraft.item.ToolMaterial SHARP_OBSIDIAN_DAGGER = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 200, 0.4f, 6.0f, 10, ModTags.Items.OBSIDIAN_SHARDS);
    public static final net.minecraft.item.ToolMaterial WEDGE_OBSIDIAN_DAGGER = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 600, 0.4f, 2.0f, 12, ModTags.Items.OBSIDIAN_SHARDS);
    public static final net.minecraft.item.ToolMaterial OBSIDIAN_SWORD = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 0.4f, 6.0f, 12, ModTags.Items.OBSIDIAN_SHARDS);
    public static final net.minecraft.item.ToolMaterial SHARP_OBSIDIAN_SWORD = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 700, 0.4f, 8.0f, 16, ModTags.Items.OBSIDIAN_SHARDS);
    public static final net.minecraft.item.ToolMaterial WEDGE_OBSIDIAN_SWORD = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 0.4f, 6.0f, 18, ModTags.Items.OBSIDIAN_SHARDS);
    public static final net.minecraft.item.ToolMaterial WEDGE_REGULAR_OBSIDIAN_SWORD = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1300, 0.4f, 6.0f, 12, ModTags.Items.OBSIDIAN_SHARDS);
    public static final net.minecraft.item.ToolMaterial SHARP_WEDGE_OBSIDIAN_SWORD = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1050, 0.4f, 6.0f, 12, ModTags.Items.OBSIDIAN_SHARDS);
    public static final net.minecraft.item.ToolMaterial SHARP_REGULAR_OBSIDIAN_SWORD = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 900, 0.4f, 7.0f, 12, ModTags.Items.OBSIDIAN_SHARDS);
    public static final net.minecraft.item.ToolMaterial MAYAN_SWORD = new net.minecraft.item.ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 128, 0.2f, 9.0f, 18, ModTags.Items.OBSIDIAN_SHARDS);



}
