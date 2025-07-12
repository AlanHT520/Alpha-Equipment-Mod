package net.alan.ae.entity.model;

import net.alan.ae.entity.state.WildFireRenderState;
import net.alan.ae.entity.animations.WildFireAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

public class WildFireModel extends EntityModel<WildFireRenderState> {
	public final ModelPart entity;
	public final ModelPart head;
	public final ModelPart bone;
	public final ModelPart alldun;
	public final ModelPart dun1;
	public final ModelPart dun2;
	public final ModelPart dun3;
	public final ModelPart dun4;

	//private final Animation idlingAnimation;
	//private final Animation shootingAnimation;

	public WildFireModel(ModelPart root) {
		super(root);
		this.entity = root.getChild("entity");
		this.head = this.entity.getChild("head");
		this.bone = this.entity.getChild("bone");
		this.alldun = root.getChild("alldun");
		this.dun1 = this.alldun.getChild("dun1");
		this.dun2 = this.alldun.getChild("dun2");
		this.dun3 = this.alldun.getChild("dun3");
		this.dun4 = this.alldun.getChild("dun4");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData entity = modelPartData.addChild("entity", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F));


		ModelPartData head = entity.addChild("head", ModelPartBuilder.create().uv(0, 19).cuboid(-4.0F, -3.25F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-4.0F, -6.25F, -4.0F, 8.0F, 11.0F, 8.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, -31.75F, 0.0F, 0.0F ,0.0F ,0.0F));

		ModelPartData bone = entity.addChild("bone", ModelPartBuilder.create().uv(32, 0).cuboid(-1.5F, -25.0F, -1.5F, 3.0F, 21.0F, 3.0F, new Dilation(0.0F)), ModelTransform.NONE);

		ModelPartData alldun = modelPartData.addChild("alldun", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData dun1 = alldun.addChild("dun1", ModelPartBuilder.create().uv(36, 40).cuboid(-5.0F, -1.0F, 0.0F, 10.0F, 17.0F, -1.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, -20.0F, -12.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData dun2 = alldun.addChild("dun2", ModelPartBuilder.create().uv(36, 40).cuboid(-5.0F, -1.0F, 0.0F, 10.0F, 17.0F, -1.0F, new Dilation(1.0F)), ModelTransform.of(12.0F, -20.0F, 0.0F, 0.0F, -1.5708F, -0.2182F));

		ModelPartData dun3 = alldun.addChild("dun3", ModelPartBuilder.create().uv(36, 40).cuboid(-5.0F, -1.0F, 0.0F, 10.0F, 17.0F, -1.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, -20.0F, 12.0F, 2.9234F, 0.0F, 3.1416F));

		ModelPartData dun4 = alldun.addChild("dun4", ModelPartBuilder.create().uv(36, 40).cuboid(-5.0F, -1.0F, 0.0F, 10.0F, 17.0F, -1.0F, new Dilation(1.0F)), ModelTransform.of(-12.0F, -20.0F, 0.0F, 0.0F, 1.5708F, 0.2182F));


		return TexturedModelData.of(modelData, 64, 64);
	}
	public ModelPart getPart() {
		return this.entity;
	}

	@Override
	public void setAngles(WildFireRenderState state) {
		super.setAngles(state);
		this.setHeadAngles(state.relativeHeadYaw, state.pitch);

		float rotationAngle = (state.age % 80) * (float)(2 * Math.PI) / 80;
		this.alldun.setAngles(0f, rotationAngle, 0f);

		float time = state.age;
		float amplitude = 2.5f;
		float speed = 0.125f;

		float group1Offset = MathHelper.sin(time * speed) * amplitude;
		float group2Offset = MathHelper.sin(time * speed + MathHelper.PI) * amplitude; // 半周期相位差

		this.dun1.originY = -20.0F + group1Offset; // dun1组
		this.dun3.originY = -20.0F + group1Offset;
		this.dun2.originY = -20.0F + group2Offset; // dun2组
		this.dun4.originY = -20.0F + group2Offset;

	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -45.0F, 45.0F);
		headPitch = MathHelper.clamp(headPitch, -45.0F, 45.0F);

		this.head.yaw = headYaw * 0.0137453292F;
		this.head.pitch = headPitch * 0.0137453292F;
	}
}
