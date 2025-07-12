package net.alan.ae.items.equipment;


import net.alan.ae.Ae;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public interface AeEquipmentAssetKeys {
    public static final RegistryKey<EquipmentAsset> CORE = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Ae.MOD_ID, "core"));
    public static final RegistryKey<EquipmentAsset> THREAD = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Ae.MOD_ID, "thread"));
    public static final RegistryKey<EquipmentAsset> PANDA = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Ae.MOD_ID, "panda"));
    public static final RegistryKey<EquipmentAsset> BLACKSTONE = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Ae.MOD_ID, "blackstone"));
}