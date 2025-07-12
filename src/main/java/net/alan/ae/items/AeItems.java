package net.alan.ae.items;

import net.alan.ae.Ae;
import net.alan.ae.entity.AlphaEntityType;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;
import net.alan.ae.items.equipment.AeArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public class AeItems {

    private static Function<Item.Settings, Item> createBlockItemWithUniqueName(Block block) {
        return (settings) -> new BlockItem(block, settings.useItemPrefixedTranslationKey());
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Ae.MOD_ID,id));
    }


    public static Item register(String id, Function<Item.Settings, Item> factory) {
        return register(keyOf(id), factory, new Item.Settings());
    }

    public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register(keyOf(id), factory, settings);
    }

    public static Item register(String id, Item.Settings settings) {
        return register(keyOf(id), Item::new, settings);
    }


    public static Item register(String id) {
        return register(keyOf(id), Item::new, new Item.Settings());
    }


    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory) {
        return register(key, factory, new Item.Settings());
    }

    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(key));
        return (Item) Registry.register(Registries.ITEM, key, item);
    }


    public static final Item COMMAND_BOOK;
    public static final Item COMMAND_SWORD;
    public static final Item COMMAND_AXE;
    public static final Item COMMAND_PICKAXE;
    public static final Item COMMAND_SHOVEL;
    public static final Item COMMAND_HOE;

    public static final Item ICE_CORE;
    public static final Item BLAZE_CORE;

    public static final Item ICE_SWORD;
    public static final Item BLAZE_SWORD;
    public static final Item SCULK_SWORD;
    public static final Item SCULK_PICKAXE;

    public static final Item BLOOD_SWORD;
    public static final Item CORE_SWORD;
    public static final Item THREAD_SWORD;
    public static final Item PANDA_SWORD;
    public static final Item WITHER_SWORD;

    public static final Item WISH_SWORD;
    public static final Item ENDER_SWORD;
    public static final Item ENDERED_SWORD;
    public static final Item PANDA_PICKAXE;
    public static final Item CORE_PICKAXE;
    public static final Item THREAD_PICKAXE;
    public static final Item REDSTONE_CLAYMORE;
    public static final Item PANDA_HELMET;
    public static final Item THREAD_HELMET;
    public static final Item CORE_HELMET;
    public static final Item BLACKSTONE_HELMET;
    public static final Item PANDA_CHESTPLATE;
    public static final Item THREAD_CHESTPLATE;
    public static final Item CORE_CHESTPLATE;
    public static final Item BLACKSTONE_CHESTPLATE;
    public static final Item PANDA_LEGGINGS;
    public static final Item THREAD_LEGGINGS;
    public static final Item CORE_LEGGINGS;
    public static final Item PANDA_BOOTS;
    public static final Item THREAD_BOOTS;
    public static final Item CORE_BOOTS;
    public static final Item BLACKSTONE_BOOTS;

    public static final Item WILDFIRE_SPAWN_EGG;

    static {
        ICE_CORE = register("ice_core", (new Item.Settings())
              .rarity(Rarity.RARE)
        );
        BLAZE_CORE = register("blaze_core", (new Item.Settings())
                .rarity(Rarity.RARE)
                .fireproof()
        );

        COMMAND_SWORD = register("command_sword", (new Item.Settings())
                .sword(AeToolMaterial.COMMAND, 12.0F, -2.6F)
                .rarity(Rarity.EPIC)
                .fireproof()
        );
        COMMAND_AXE = register("command_axe", (new Item.Settings())
                .axe(AeToolMaterial.COMMAND, 13.0F, -3.0F)
                .rarity(Rarity.EPIC)
                .fireproof()
        );
        COMMAND_PICKAXE = register("command_pickaxe", (new Item.Settings())
                .axe(AeToolMaterial.COMMAND, 9.0F, -2.4F)
                .rarity(Rarity.EPIC)
                .fireproof()
        );
        COMMAND_SHOVEL = register("command_shovel", (new Item.Settings())
                .axe(AeToolMaterial.COMMAND, 10.0F, -2.4F)
                .rarity(Rarity.EPIC)
                .fireproof()
        );
        COMMAND_HOE = register("command_hoe", (new Item.Settings())
                .axe(AeToolMaterial.COMMAND, 7.0F, -2.4F)
                .rarity(Rarity.EPIC)
                .fireproof()
        );


        COMMAND_BOOK = register("command_book", (new Item.Settings()
                .rarity(Rarity.EPIC))
                .fireproof()
        );


        BLOOD_SWORD = register("blood_sword", (new Item.Settings())
                .sword(AeToolMaterial.CORE, 6.0F, -2.4F)
                .rarity(Rarity.RARE)
                .fireproof()
        );

        CORE_SWORD = register("core_sword", (new Item.Settings())
                .sword(AeToolMaterial.CORE, 6.0F, -2.4F)
                .rarity(Rarity.RARE)
                .fireproof()
        );
        THREAD_SWORD = register("thread_sword", (new Item.Settings())
                .sword(AeToolMaterial.THREAD, 6.0F, -2.4F)
                .rarity(Rarity.UNCOMMON)
                .fireproof()
        );
        PANDA_SWORD = register("panda_sword", (new Item.Settings())
                .sword(AeToolMaterial.PANDA, 5.5F, -2.4F)
                .rarity(Rarity.UNCOMMON)
        );
        WITHER_SWORD = register("wither_sword", (new Item.Settings())
                .sword(AeToolMaterial.WITHER, 7F, -2.4F)
                .rarity(Rarity.EPIC)
        );
        ICE_SWORD = register("ice_sword", (new Item.Settings())
                .sword(AeToolMaterial.ICE, 4.0F, -2.6F)
                .rarity(Rarity.COMMON)
        );
        BLAZE_SWORD = register("blaze_sword", (new Item.Settings())
                        .sword(AeToolMaterial.BLAZE, 4.5F, -2.6F)
                        .rarity(Rarity.COMMON)
                        .fireproof()
        );
        SCULK_SWORD = register("sculk_sword", (new Item.Settings())
                .sword(AeToolMaterial.SCULK, 5.0F, -2.6F)
                .rarity(Rarity.UNCOMMON)
        );
        SCULK_PICKAXE = register("sculk_pickaxe", (new Item.Settings())
               .pickaxe(AeToolMaterial.SCULK, 5.0F, -2.4F)
               .rarity(Rarity.UNCOMMON)
        );
        ENDER_SWORD = register("ender_sword", (new Item.Settings())
                .sword(AeToolMaterial.ENDER, 5.5F, -2.4F)
                .rarity(Rarity.EPIC)
        );
        ENDERED_SWORD = register("endered_sword", (new Item.Settings())
                .sword(AeToolMaterial.ENDER, 6F, -2.4F)
                .rarity(Rarity.EPIC)
                .fireproof()
        );
        WISH_SWORD = register("wish_sword", (new Item.Settings())
                .sword(AeToolMaterial.PANDA, 5.7F, -2.4F)
                .rarity(Rarity.RARE)
                .fireproof()
        );
        PANDA_PICKAXE = register("panda_pickaxe", (new Item.Settings())
                .pickaxe(AeToolMaterial.PANDA, 3.0F, -2.1F)
                .rarity(Rarity.UNCOMMON)
                .fireproof()
        );
        CORE_PICKAXE = register("core_pickaxe", (new Item.Settings())
                .pickaxe(AeToolMaterial.CORE, 3.0F, -2.1F)
                .rarity(Rarity.RARE)
                .fireproof()
        );
        THREAD_PICKAXE = register("thread_pickaxe", (new Item.Settings())
                .pickaxe(AeToolMaterial.THREAD, 3.0F, -2.1F)
                .rarity(Rarity.UNCOMMON)
                .fireproof()
        );
        REDSTONE_CLAYMORE = register("redstone_claymore", (new Item.Settings())
                .sword(AeToolMaterial.REDSTONE, 6.0F, -2.4F)
                .rarity(Rarity.EPIC)
                .fireproof()
        );



        CORE_HELMET = register("core_helmet", (new Item.Settings())
                .armor(AeArmorMaterials.CORE, EquipmentType.HELMET)
                .fireproof()
                .rarity(Rarity.UNCOMMON));
        THREAD_HELMET = register("thread_helmet", (new Item.Settings())
                .armor(AeArmorMaterials.THREAD, EquipmentType.HELMET)
                .fireproof()
                .rarity(Rarity.RARE));
        PANDA_HELMET = register("panda_helmet", (new Item.Settings())
                .armor(AeArmorMaterials.PANDA, EquipmentType.HELMET)
                .fireproof()
                .rarity(Rarity.UNCOMMON));

        CORE_CHESTPLATE = register("core_chestplate", (new Item.Settings())
                .armor(AeArmorMaterials.CORE, EquipmentType.CHESTPLATE)
                .fireproof()
                .rarity(Rarity.UNCOMMON));
        THREAD_CHESTPLATE = register("thread_chestplate", (new Item.Settings())
                .armor(AeArmorMaterials.THREAD, EquipmentType.CHESTPLATE)
                .fireproof()
                .rarity(Rarity.RARE));
        PANDA_CHESTPLATE = register("panda_chestplate", (new Item.Settings())
                .armor(AeArmorMaterials.PANDA, EquipmentType.CHESTPLATE)
                .fireproof()
                .rarity(Rarity.UNCOMMON));

        CORE_LEGGINGS = register("core_leggings", (new Item.Settings())
                .armor(AeArmorMaterials.CORE, EquipmentType.LEGGINGS)
                .fireproof()
                .rarity(Rarity.UNCOMMON));
        THREAD_LEGGINGS = register("thread_leggings", (new Item.Settings())
                .armor(AeArmorMaterials.THREAD, EquipmentType.LEGGINGS)
                .fireproof()
                .rarity(Rarity.RARE));
        PANDA_LEGGINGS = register("panda_leggings", (new Item.Settings())
                .armor(AeArmorMaterials.PANDA, EquipmentType.LEGGINGS)
                .fireproof()
                .rarity(Rarity.UNCOMMON));

        CORE_BOOTS = register("core_boots", (new Item.Settings())
                .armor(AeArmorMaterials.CORE, EquipmentType.BOOTS)
                .fireproof()
                .rarity(Rarity.UNCOMMON));
        THREAD_BOOTS = register("thread_boots", (new Item.Settings())
                .armor(AeArmorMaterials.THREAD, EquipmentType.BOOTS)
                .fireproof()
                .rarity(Rarity.RARE));
        PANDA_BOOTS = register("panda_boots", (new Item.Settings())
                .armor(AeArmorMaterials.PANDA, EquipmentType.BOOTS)
                .fireproof()
                .rarity(Rarity.UNCOMMON));

        BLACKSTONE_HELMET = register("blackstone_helmet", (new Item.Settings())
                .armor(AeArmorMaterials.BLACKSTONE, EquipmentType.HELMET)
                .fireproof()
                .rarity(Rarity.UNCOMMON));
        BLACKSTONE_CHESTPLATE = register("blackstone_chestplate", (new Item.Settings())
                .armor(AeArmorMaterials.BLACKSTONE, EquipmentType.CHESTPLATE)
                .fireproof()
                .rarity(Rarity.UNCOMMON));
        BLACKSTONE_BOOTS = register("blackstone_boots", (new Item.Settings())
              .armor(AeArmorMaterials.BLACKSTONE, EquipmentType.BOOTS)
              .fireproof()
              .rarity(Rarity.UNCOMMON));



        WILDFIRE_SPAWN_EGG = register((String)"wildfire_spawn_egg", (Function)((settings) -> new SpawnEggItem(AlphaEntityType.WILD_FIRE, (Item.Settings) settings)));

    }
    public static void registerModItems() {
        Ae.LOGGER.info("Registering Mod Items for Ae");
    }
}
