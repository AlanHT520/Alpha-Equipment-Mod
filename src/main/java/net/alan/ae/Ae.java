package net.alan.ae;

import net.alan.ae.entity.AlphaEntityType;
import net.alan.ae.entity.entities.WildFireEntity;
import net.alan.ae.items.AeItemTabs;
import net.alan.ae.items.AeItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ae implements ModInitializer {
	public static final String MOD_ID = "ae";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		AeItems.registerModItems();
		AeItemTabs.registerModItemGroup();
		AlphaEntityType.registerModEntities();
		FabricDefaultAttributeRegistry.register(AlphaEntityType.WILD_FIRE, WildFireEntity.createWildFireAttributes());

		LOGGER.info("Hello Fabric world!");
	}
}