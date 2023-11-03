package turniplabs.farlanders.entity.render;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelFarlander extends ModelBase {
	private final Cube head;
	private final Cube body;
	private final Cube armLeft;
	private final Cube armRight;
	private final Cube legLeft;
	private final Cube legRight;

	public ModelFarlander() {
		head = new Cube(0, 0);
		body = new Cube(4, 16);
		armLeft = new Cube(32, 6);
		armRight = new Cube(32, 6);
		legLeft = new Cube(40, 6);
		legRight = new Cube(40, 6);

		head.addBox(-4.0F, -56.0F, -4.0F, 8, 8, 8);
		body.addBox(-4.0F, -36.0F, -2.0F, 8, 12, 4);
		armLeft.addBox(-6.0F, -48.0F, -1.0F, 2, 24, 2);
		armRight.addBox(4.0F, -48.0F, -1.0F, 2, 24, 2);
		legLeft.addBox(-3.0F, -24.0F, -1.0F, 2, 24, 2);
		legRight.addBox(1.0F, -24.0F, -1.0F, 2, 24, 2);

		head.setRotationPoint(0, 36, 0);
		body.setRotationPoint(0, 24, 0);
		armLeft.setRotationPoint(0, 36, 0);
		armRight.setRotationPoint(0, 36, 0);
		legLeft.setRotationPoint(0, 24, 0);
		legRight.setRotationPoint(0, 24, 0);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {
		super.render(limbSwing, limbYaw, ticksExisted, headYaw, headPitch, scale);
		head.render(scale);
		body.render(scale);
		armLeft.render(scale);
		armRight.render(scale);
		legLeft.render(scale);
		legRight.render(scale);
	}
}
