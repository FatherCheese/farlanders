package turniplabs.farlanders.entity.render;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelEyes extends ModelBase {
	private final Cube head;
	private final Cube torso;
	private final Cube leftArm;
	private final Cube rightArm;
	private final Cube cepha;
	private final Cube abdomen;

	private final Cube leftLeg1;
	private final Cube leftLeg2;
	private final Cube leftLeg3;
	private final Cube leftLeg4;

	private final Cube rightLeg1;
	private final Cube rightLeg2;
	private final Cube rightLeg3;
	private final Cube rightLeg4;

	public ModelEyes() {
		head = new Cube(0, 0);
		torso = new Cube(8, 16);
		leftArm = new Cube(0, 18);
		rightArm = new Cube(0, 18);
		cepha = new Cube(32, 5);
		abdomen = new Cube(20, 0);
		leftLeg1 = new Cube(28, 15);
		leftLeg2 = new Cube(28, 15);
		leftLeg3 = new Cube(28, 15);
		leftLeg4 = new Cube(28, 15);
		rightLeg1 = new Cube(28, 15);
		rightLeg2 = new Cube(28, 15);
		rightLeg3 = new Cube(28, 15);
		rightLeg4 = new Cube(28, 15);

		head.addBox(-4.0f, -26.0f, -7.0f, 8, 8, 8);
		torso.addBox(-4.0f, -18.0f, -5.0f, 8, 12, 4);
		leftArm.addBox(-1.0f, 24.0f, 0.0f, 2, 12, 2);
		rightArm.addBox(-1.0f, 24.0f, 0.0f, 2, 12, 2);
		cepha.addBox(-3.0f, -8.0f, -3.0f, 6, 6, 6);
		abdomen.addBox(-5.0f, 18.0f, -6.0f, 10, 8, 12);
		leftLeg1.addBox(0.0f, -1.0f, -1.0f, 16, 2, 2);
		leftLeg2.addBox(0.0f, -1.0f, -1.0f, 16, 2, 2);
		leftLeg3.addBox(0.0f, -1.0f, -1.0f, 16, 2, 2);
		leftLeg4.addBox(0.0f, -1.0f, -1.0f, 16, 2, 2);
		rightLeg1.addBox(-16.0f, -1.0f, -1.0f, 16, 2, 2);
		rightLeg2.addBox(-16.0f, -1.0f, -1.0f, 16, 2, 2);
		rightLeg3.addBox(-16.0f, -1.0f, -1.0f, 16, 2, 2);
		rightLeg4.addBox(-16.0f, -1.0f, -1.0f, 16, 2, 2);

		head.setRotationPoint(0.0f, 24.0f, 0.0f);
		torso.setRotationPoint(0.0f, 24.0f, 0.0f);
		leftArm.setRotationPoint(4.0f, -18.0f, -4.0f);
		rightArm.setRotationPoint(-4.0f, -18.0f, -4.0f);
		cepha.setRotationPoint(0.0f, 24.0f, 0.0f);
		abdomen.setRotationPoint(0.0F, -4.0F, 3.0F);
		leftLeg1.setRotationPoint(3.0f, 20.0f, 0.0f);
		leftLeg2.setRotationPoint(3.0f, 20.0f, 0.0f);
		leftLeg3.setRotationPoint(3.0f, 20.0f, 0.0f);
		leftLeg4.setRotationPoint(3.0f, 20.0f, 0.0f);
		rightLeg1.setRotationPoint(-3.0f, 20.0f, 0.0f);
		rightLeg2.setRotationPoint(-3.0f, 20.0f, 0.0f);
		rightLeg3.setRotationPoint(-3.0f, 20.0f, 0.0f);
		rightLeg4.setRotationPoint(-3.0f, 20.0f, 0.0f);

		leftArm.setRotationAngle(0.0F, 0.0F, -0.0436F);
		rightArm.setRotationAngle(0.0F, 0.0F, 0.0436F);
		abdomen.setRotationAngle(0.2618F, 0.0F, 0.0F);
		leftLeg1.setRotationAngle(-0.2618F, 0.5672F, 0.1745F);
		leftLeg2.setRotationAngle(-0.2618F, 0.3054F, 0.1745F);
		leftLeg3.setRotationAngle(0.2618F, -0.3054F, 0.1745F);
		leftLeg4.setRotationAngle(0.2618F, -0.5672F, 0.1745F);
		rightLeg1.setRotationAngle(0.2618F, -0.5672F, -0.1745F);
		rightLeg2.setRotationAngle(-0.2618F, -0.3054F, -0.1745F);
		rightLeg3.setRotationAngle(0.2618F, 0.5672F, -0.1745F);
		rightLeg4.setRotationAngle(0.2618F, 0.3054F, -0.1745F);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {

		head.render(scale);
		torso.render(scale);
		leftArm.render(scale);
		rightArm.render(scale);
		cepha.render(scale);
		abdomen.render(scale);
		leftLeg1.render(scale);
		leftLeg2.render(scale);
		leftLeg3.render(scale);
		leftLeg4.render(scale);
		rightLeg1.render(scale);
		rightLeg2.render(scale);
		rightLeg3.render(scale);
		rightLeg4.render(scale);
	}
}
