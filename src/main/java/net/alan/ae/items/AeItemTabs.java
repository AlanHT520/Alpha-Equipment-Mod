package net.alan.ae.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.alan.ae.Ae;

public class AeItemTabs {
    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(Ae.MOD_ID,id));
    }
    public static final RegistryKey<ItemGroup> AE_TAB = register("ae_tab");

    public static void registerModItemGroup() {
        Registry.register(Registries.ITEM_GROUP, AE_TAB,
                ItemGroup.create(ItemGroup.Row.TOP, 7)
                        .displayName(Text.translatable("itemGroup.ae.ae_items"))
                        .icon(()-> new ItemStack(AeItems.CORE_SWORD))
                        .entries((displayContext, entries) -> {

                            entries.add(AeItems.CORE_SWORD);
                            entries.add(AeItems.THREAD_SWORD);
                            entries.add(AeItems.PANDA_SWORD);
                            entries.add(AeItems.WISH_SWORD);
                            entries.add(AeItems.WITHER_SWORD);
                            entries.add(AeItems.ENDER_SWORD);
                            entries.add(AeItems.ENDERED_SWORD);
                            entries.add(AeItems.SCULK_SWORD);
                            entries.add(AeItems.BLOOD_SWORD);


                            entries.add(AeItems.CORE_PICKAXE);
                            entries.add(AeItems.THREAD_PICKAXE);
                            entries.add(AeItems.PANDA_PICKAXE);
                            entries.add(AeItems.SCULK_PICKAXE);
                            //entries.add(AeItems.REDSTONE_CLAYMORE);


                            entries.add(AeItems.COMMAND_SWORD);
                            entries.add(AeItems.COMMAND_AXE);
                            entries.add(AeItems.COMMAND_PICKAXE);
                            entries.add(AeItems.COMMAND_SHOVEL);
                            entries.add(AeItems.COMMAND_HOE);

                            entries.add(AeItems.ICE_SWORD);
                            entries.add(AeItems.BLAZE_SWORD);

                            entries.add(AeItems.CORE_HELMET);
                            entries.add(AeItems.THREAD_HELMET);
                            entries.add(AeItems.PANDA_HELMET);
                            entries.add(AeItems.BLACKSTONE_HELMET);
                            entries.add(AeItems.CORE_CHESTPLATE);
                            entries.add(AeItems.THREAD_CHESTPLATE);
                            entries.add(AeItems.PANDA_CHESTPLATE);
                            entries.add(AeItems.BLACKSTONE_CHESTPLATE);
                            entries.add(AeItems.CORE_LEGGINGS);
                            entries.add(AeItems.THREAD_LEGGINGS);
                            entries.add(AeItems.PANDA_LEGGINGS);
                            entries.add(AeItems.CORE_BOOTS);
                            entries.add(AeItems.THREAD_BOOTS);
                            entries.add(AeItems.PANDA_BOOTS);
                            entries.add(AeItems.BLACKSTONE_BOOTS);


                            entries.add(AeItems.ICE_CORE);
                            entries.add(AeItems.BLAZE_CORE);
                            entries.add(AeItems.COMMAND_BOOK);

                            entries.add(AeItems.WILDFIRE_SPAWN_EGG);
                        }).build());
        Ae.LOGGER.info("AE Mod Items Group Start!");
    }
}
