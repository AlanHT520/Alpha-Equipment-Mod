package net.alan.ae.entity.render;

import net.alan.ae.Ae;
import net.alan.ae.entity.entities.WildFireEntity;
import net.alan.ae.entity.model.AlphaModelLayers;
import net.alan.ae.entity.model.WildFireModel;
import net.alan.ae.entity.state.WildFireRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class WildFireRender extends MobEntityRenderer<WildFireEntity, WildFireRenderState, WildFireModel> {
    private static final Identifier TEXTURE = Identifier.of(Ae.MOD_ID,"textures/entity/wild_fire.png");

    public WildFireRender(EntityRendererFactory.Context context) {
        super(context, new WildFireModel(context.getPart(AlphaModelLayers.WILD_FIRE)), 0.7F);
    }

    protected int getBlockLight(WildFireEntity wildfireEntity, BlockPos blockPos) {
        return 15;
    }

    public Identifier getTexture(WildFireRenderState wildFireRenderState) {
        return TEXTURE;
    }

    @Override
    public WildFireRenderState createRenderState() {
        return new WildFireRenderState();
    }

    @Override
    public void updateRenderState(WildFireEntity wildfireEntity, WildFireRenderState wildfireRenderState, float f) {
        super.updateRenderState(wildfireEntity, wildfireRenderState, f);
        wildfireRenderState.idleAnimationState.copyFrom(wildfireEntity.idleAnimationState);
    }
}
