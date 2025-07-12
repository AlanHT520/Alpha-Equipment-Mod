package net.alan.ae.items;


import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.component.type.WeaponComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public record AeToolMaterial(TagKey<Block> incorrectBlocksForDrops, int durability, float speed, float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems) {
    public static final ToolMaterial BLOOD;
    public static final ToolMaterial SCULK;
    public static final ToolMaterial ICE;
    public static final ToolMaterial BLAZE;
    public static final ToolMaterial CORE;
    public static final ToolMaterial THREAD;
    public static final ToolMaterial PANDA;
    public static final ToolMaterial WITHER;
    public static final ToolMaterial ENDER;
    public static final ToolMaterial REDSTONE;
    public static final ToolMaterial COMMAND;
    static {
        BLOOD = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 1540, 8.0F, 4.5F, 6, ItemTags.IRON_TOOL_MATERIALS);
        SCULK = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 940, 7.0F, 4.5F, 6, ItemTags.IRON_TOOL_MATERIALS);
        ICE = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 1880, 7.5F, 4.5F, 6, ItemTags.IRON_TOOL_MATERIALS);
        BLAZE = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 904, 7.7F, 4.5F, 6, ItemTags.IRON_TOOL_MATERIALS);
        CORE = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 3840, 8.0F, 4.5F, 6, ItemTags.DIAMOND_TOOL_MATERIALS);
        THREAD = new ToolMaterial(BlockTags.INCORRECT_FOR_GOLD_TOOL, 1840, 8.5F, 3.5F, 3, ItemTags.GOLD_TOOL_MATERIALS);
        PANDA = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 2304, 9.0F, 4.0F, 6, ItemTags.IRON_TOOL_MATERIALS);
        WITHER = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 5120, 9.0F, 4.0F, 6, ItemTags.NETHERITE_TOOL_MATERIALS);
        ENDER = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2048, 10.0F, 3.8F, 4, ItemTags.DIAMOND_TOOL_MATERIALS);
        REDSTONE = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 4096, 9.0F, 5.0F, 7, ItemTags.NETHERITE_TOOL_MATERIALS);
        COMMAND = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2147483647, 9.0F, 5.0F, 7, ItemTags.NETHERITE_TOOL_MATERIALS);
    }


    private Item.Settings applyBaseSettings(Item.Settings settings) {
        return settings.maxDamage(this.durability).repairable(this.repairItems).enchantable(this.enchantmentValue);
    }

    public Item.Settings applyToolSettings(Item.Settings settings, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed, float disableBlockingForSeconds) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        return this.applyBaseSettings(settings).component(DataComponentTypes.TOOL, new ToolComponent(List.of(ToolComponent.Rule.ofNeverDropping(registryEntryLookup.getOrThrow(this.incorrectBlocksForDrops)), ToolComponent.Rule.ofAlwaysDropping(registryEntryLookup.getOrThrow(effectiveBlocks), this.speed)), 1.0F, 1, true)).attributeModifiers(this.createToolAttributeModifiers(attackDamage, attackSpeed)).component(DataComponentTypes.WEAPON, new WeaponComponent(2, disableBlockingForSeconds));
    }

    private AttributeModifiersComponent createToolAttributeModifiers(float attackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder().add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)(attackDamage + this.attackDamageBonus), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    public Item.Settings applySwordSettings(Item.Settings settings, float attackDamage, float attackSpeed) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        return this.applyBaseSettings(settings).component(DataComponentTypes.TOOL, new ToolComponent(List.of(ToolComponent.Rule.ofAlwaysDropping(RegistryEntryList.of(new RegistryEntry[]{Blocks.COBWEB.getRegistryEntry()}), 15.0F), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2, false)).attributeModifiers(this.createSwordAttributeModifiers(attackDamage, attackSpeed)).component(DataComponentTypes.WEAPON, new WeaponComponent(1));
    }

    private AttributeModifiersComponent createSwordAttributeModifiers(float attackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder().add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)(attackDamage + this.attackDamageBonus), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }
}
