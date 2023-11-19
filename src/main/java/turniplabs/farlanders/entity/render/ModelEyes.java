package turniplabs.farlanders.entity.render;

import useless.dragonfly.model.entity.BenchEntityModel;

public class ModelEyes extends BenchEntityModel {

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

		if (this.getIndexBones().containsKey("cepha"))
			this.getIndexBones().get("cepha")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

		if (this.getIndexBones().containsKey("abdomen")) {
			this.getIndexBones().get("abdomen")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("abdomen")
				.rotateAngleX = 0.15F;
		}

		if (this.getIndexBones().containsKey("legLeft1")) {
			this.getIndexBones().get("legLeft1")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("legLeft1")
				.rotateAngleX = 0.4F;

			this.getIndexBones().get("legLeft1")
				.rotateAngleY = -0.65F;

			this.getIndexBones().get("legLeft1")
				.rotateAngleZ = -0.10F;
		}

		if (this.getIndexBones().containsKey("legLeft2")) {
			this.getIndexBones().get("legLeft2")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("legLeft2")
				.rotateAngleX = 0.4F;

			this.getIndexBones().get("legLeft2")
				.rotateAngleY = -0.175F;

			this.getIndexBones().get("legLeft2")
				.rotateAngleZ = -0.10F;
		}

		if (this.getIndexBones().containsKey("legLeft3")) {
			this.getIndexBones().get("legLeft3")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("legLeft3")
				.rotateAngleX = -0.4F;

			this.getIndexBones().get("legLeft3")
				.rotateAngleY = 0.175F;

			this.getIndexBones().get("legLeft3")
				.rotateAngleZ = -0.10F;
		}

		if (this.getIndexBones().containsKey("legLeft4")) {
			this.getIndexBones().get("legLeft4")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("legLeft4")
				.rotateAngleX = -0.3F;

			this.getIndexBones().get("legLeft4")
				.rotateAngleY = 0.65F;

			this.getIndexBones().get("legLeft4")
				.rotateAngleZ = -0.10F;
		}

		if (this.getIndexBones().containsKey("legRight1")) {
			this.getIndexBones().get("legRight1")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("legRight1")
				.rotateAngleX = 0.4F;

			this.getIndexBones().get("legRight1")
				.rotateAngleY = 0.65F;

			this.getIndexBones().get("legRight1")
				.rotateAngleZ = 0.10F;
		}

		if (this.getIndexBones().containsKey("legRight2")) {
			this.getIndexBones().get("legRight2")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("legRight2")
				.rotateAngleX = 0.4F;

			this.getIndexBones().get("legRight2")
				.rotateAngleY = 0.175F;

			this.getIndexBones().get("legRight2")
				.rotateAngleZ = 0.10F;
		}

		if (this.getIndexBones().containsKey("legRight3")) {
			this.getIndexBones().get("legRight3")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("legRight3")
				.rotateAngleX = -0.4F;

			this.getIndexBones().get("legRight3")
				.rotateAngleY = -0.175F;

			this.getIndexBones().get("legRight3")
				.rotateAngleZ = 0.10F;
		}

		if (this.getIndexBones().containsKey("legRight4")) {
			this.getIndexBones().get("legRight4")
				.setRotationAngle(0.0F, 0.0F, 0.0F);

			this.getIndexBones().get("legRight4")
				.rotateAngleX = -0.4F;

			this.getIndexBones().get("legRight4")
				.rotateAngleY = -0.65F;

			this.getIndexBones().get("legRight4")
				.rotateAngleZ = 0.10F;
		}
	}
}
