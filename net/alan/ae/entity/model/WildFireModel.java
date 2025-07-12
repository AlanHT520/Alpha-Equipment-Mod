package net.alan.ae.entity.model;

import net.alan.ae.entity.entities.WildFireEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class WildFireModel extends SinglePartEntityModel<WildFireEntity> {
    private final ModelPart root;

    public WildFireModel(ModelPart root) {
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        // 这里添加模型部件定义
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    public void setAngles(WildFireEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // 示例动画逻辑：根据生物移动更新部件角度
        // 这里假设存在一个名为 "body" 的模型部件
        ModelPart body = root.getChild("body");
        body.pitch = MathHelper.sin(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void animateModel(WildFireEntity entity, float limbSwing, float limbSwingAmount, float tickDelta) {
        // 示例动画逻辑：根据生物状态更新部件位置
        // 这里假设存在一个名为 "flame" 的模型部件
        ModelPart flame = root.getChild("flame");
        if (entity.isAttacking()) {
            flame.pivotY = -1.0F;
        } else {
            flame.pivotY = 0.0F;
        }
    }
}
