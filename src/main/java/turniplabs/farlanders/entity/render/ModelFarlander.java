package turniplabs.farlanders.entity.render;

import net.minecraft.core.util.helper.MathHelper;
import useless.dragonfly.model.entity.BenchEntityModel;

public class ModelFarlander extends BenchEntityModel {

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {
		super.setRotationAngles(limbSwing, limbYaw, ticksExisted, headYaw, headPitch, scale);

		if (this.getIndexBones().containsKey("head")) {
			this.getIndexBones().get("head")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("head")
				.rotateAngleX = -headPitch / (float) (180.0 / Math.PI);

			this.getIndexBones().get("head")
				.rotateAngleY = headYaw / ((float) (180.0 / Math.PI));
		}

		if (this.getIndexBones().containsKey("body"))
			this.getIndexBones().get("body")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

		if (this.getIndexBones().containsKey("armLeft")) {
			this.getIndexBones().get("armLeft")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("armLeft")
				.rotateAngleZ -= MathHelper.cos(ticksExisted * 0.09F) * 0.05F + 0.05F;
		}

		if (this.getIndexBones().containsKey("armRight")) {
			this.getIndexBones().get("armRight")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("armRight")
				.rotateAngleZ += MathHelper.cos(ticksExisted * 0.09F) * 0.05F + 0.05F;
		}

		if (this.getIndexBones().containsKey("legLeft"))
			this.getIndexBones().get("legLeft")
				.setRotationAngle(0.0F, 0.0F, -0.0625F);

		if (this.getIndexBones().containsKey("legRight"))
			this.getIndexBones().get("legRight")
				.setRotationAngle(0.0F, 0.0F, 0.0625F);
	}
}
