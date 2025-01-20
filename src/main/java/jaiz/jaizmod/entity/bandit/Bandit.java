package jaiz.jaizmod.entity.bandit;


import jaiz.jaizmod.entity.animation.DunesAndDroughtAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

public class Bandit extends EntityModel<BanditRenderState> implements ModelWithArms {


	private final ModelPart bandit;
	private final ModelPart head;
	private final ModelPart hands;

	protected Bandit(ModelPart root) {
		super(root);
		this.bandit = root.getChild("bandit");
		this.hands = root.getChild("bandit").getChild("body").getChild("arms").getChild("item");
		this.head = bandit.getChild("body").getChild("head");
	}


	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bandit = modelPartData.addChild("bandit", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData leg_l = bandit.addChild("leg_l", ModelPartBuilder.create().uv(28, 36).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -12.0F, 0.0F));

		ModelPartData leg_r = bandit.addChild("leg_r", ModelPartBuilder.create().uv(44, 36).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -12.0F, 0.0F));

		ModelPartData body = bandit.addChild("body", ModelPartBuilder.create().uv(0, 36).cuboid(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-4.0F, -12.0F, -3.0F, 8.0F, 20.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(20, 18).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F))
				.uv(22, 0).cuboid(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(28, 0).cuboid(-4.0F, -9.25F, -4.0F, 8.0F, 7.0F, 8.0F, new Dilation(0.51F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));


		ModelPartData arms = body.addChild("arms", ModelPartBuilder.create().uv(44, 15).cuboid(-4.0F, 2.7F, -2.55F, 8.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(24, 52).cuboid(-8.0F, -1.3F, -2.55F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F))
				.uv(52, 23).cuboid(4.0F, -1.3F, -2.55F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.25F, -0.5F, -0.7418F, 0.0F, 0.0F));

		ModelPartData item = arms.addChild("item", ModelPartBuilder.create(), ModelTransform.of(0.0F, 3.0F, 3.0F, -19.9F, 0.0F, 0.0F));


		return TexturedModelData.of(modelData, 80, 80);
	}

	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bandit.render(matrices, vertexConsumer, light, overlay);
	}


	@Override
	public void setAngles(BanditRenderState entity) {
		super.setAngles(entity);
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.head.pitch = entity.pitch * (float) (Math.PI / 180.0);
		this.head.yaw = entity.yawDegrees * (float) (Math.PI / 180.0);
		this.animateWalking(DunesAndDroughtAnimations.BANDIT_WALK, entity.limbFrequency, entity.limbAmplitudeMultiplier, 3.0F, 5.0F);
		this.animate(entity.attackAnimationState, DunesAndDroughtAnimations.BANDIT_ATTACK, entity.age, 1f);
		this.animate(entity.tradeAnimationState, DunesAndDroughtAnimations.BANDIT_ATTACK, entity.age, 1f);
	}

	public ModelPart getPart() {
		return bandit;
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		this.hands.rotate(matrices);
	}
}