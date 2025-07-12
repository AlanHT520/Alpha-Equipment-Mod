package net.alan.ae.entity.model;

import net.alan.ae.Ae;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class AlphaModelLayers {
    public static final EntityModelLayer WILD_FIRE =
            new EntityModelLayer(Identifier.of(Ae.MOD_ID, "wild_fire"),"wild_fire");
}
