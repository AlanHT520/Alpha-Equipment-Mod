package net.alan.ae.items.equipment;


import com.google.common.collect.Maps;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;

import java.util.Map;

public interface AeArmorMaterials {
        ArmorMaterial THREAD = new ArmorMaterial(
                15, createDefenseMap(
                        3,
                        7,
                        10,
                        5,
                        9),
                9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F, ItemTags.REPAIRS_IRON_ARMOR, AeEquipmentAssetKeys.THREAD);
        ArmorMaterial PANDA = new ArmorMaterial(
                15, createDefenseMap(
                        4,
                        10,
                        12,
                        6,
                        10),
                9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F, ItemTags.REPAIRS_IRON_ARMOR, AeEquipmentAssetKeys.PANDA);
        ArmorMaterial CORE = new ArmorMaterial(
                33, createDefenseMap(
                        3,
                        8,
                        11,
                        7,
                        11),
                10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, ItemTags.REPAIRS_DIAMOND_ARMOR, AeEquipmentAssetKeys.CORE);
        ArmorMaterial BLACKSTONE = new ArmorMaterial(
                37, createDefenseMap(
                        6,
                        16,
                        14,
                        9,
                        22),
                15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, ItemTags.REPAIRS_NETHERITE_ARMOR, AeEquipmentAssetKeys.BLACKSTONE);

        private static Map<EquipmentType, Integer> createDefenseMap(int bootsDefense, int leggingsDefense, int chestplateDefense, int helmetDefense, int bodyDefense) {
            return Maps.newEnumMap(Map.of(
                    EquipmentType.BOOTS, bootsDefense,
                    EquipmentType.LEGGINGS, leggingsDefense,
                    EquipmentType.CHESTPLATE, chestplateDefense,
                    EquipmentType.HELMET, helmetDefense,
                    EquipmentType.BODY, bodyDefense)
            );
        }
}