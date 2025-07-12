package net.alan.ae.entity;

import com.google.common.collect.ImmutableSet;
import net.alan.ae.Ae;
import net.alan.ae.entity.entities.WildFireEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;


import java.util.Optional;


public class AlphaEntityType extends EntityType{

    public static final EntityType<WildFireEntity> WILD_FIRE;

    static {
        WILD_FIRE = register(
                "wild_fire",EntityType.Builder
                        .create(WildFireEntity::new, SpawnGroup.MONSTER)
                        .makeFireImmune()
                        .dimensions(1.2F, 2.3F)
                        .maxTrackingRange(10)
        );
    }



    public AlphaEntityType(EntityFactory factory, SpawnGroup spawnGroup, boolean saveable, boolean summonable, boolean fireImmune, boolean spawnableFarFromPlayer, ImmutableSet canSpawnInside, EntityDimensions dimensions, float spawnBoxScale, int maxTrackDistance, int trackTickInterval, String translationKey, Optional lootTable, FeatureSet requiredFeatures) {
        super(factory, spawnGroup, saveable, summonable, fireImmune, spawnableFarFromPlayer, canSpawnInside, dimensions, spawnBoxScale, maxTrackDistance, trackTickInterval, translationKey, lootTable, requiredFeatures);
    }

    public static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, Builder<T> type) {
        return (EntityType) Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    public static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Ae.MOD_ID,id));
    }

    public static <T extends Entity> EntityType<T> register(String id, Builder<T> type) {
        return register(keyOf(id), type);
    }
    public static void registerModEntities() {
        Ae.LOGGER.info("Registering Mod Entities for Ae");
    }
}