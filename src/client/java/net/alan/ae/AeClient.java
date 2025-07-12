package net.alan.ae;

import net.alan.ae.entity.AlphaEntityType;
import net.alan.ae.entity.model.AlphaModelLayers;
import net.alan.ae.entity.model.WildFireModel;
import net.alan.ae.entity.render.WildFireRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class AeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(AlphaModelLayers.WILD_FIRE, WildFireModel::getTexturedModelData);
		EntityRendererRegistry.register(AlphaEntityType.WILD_FIRE, WildFireRender::new);
	}

}