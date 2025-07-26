package cookie.farlanders.client.render.entity;

import cookie.farlanders.core.entity.MobEyes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererEyes extends MobRenderer<MobEyes> {
	public MobRendererEyes() {
		super(1.0F);
	}

	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NotNull MobEyes entity, float brightness, float partialTick, int layer) {
		if (layer == 1) {
			bindTexture("/assets/farlanders/textures/entity/eyes/eyes.png");
			if(LightmapHelper.isLightmapEnabled()) LightmapHelper.setLightmapCoord(LightmapHelper.getOverbrightLightmapCoord(15));

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		} else if (layer == 2) {
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			return null;
		}

		StaticEntityModel model = getModel("main");
		model.resetBones();

		final float bodyYaw = getBodyYaw(entity, partialTick);
		final float headYaw = getHeadYaw(entity, partialTick) - bodyYaw;
		final float headPitch = getHeadPitch(entity, partialTick);
		final float limbSwing = getLimbSwing(entity, partialTick);
		final float limbPitch = getLimbPitch(entity, partialTick);
		final float limbYaw = getLimbYaw(entity, partialTick);

		final BoneTransform head = model.getTransform("head");
		final BoneTransform armLeft = model.getTransform("armLeft");
		final BoneTransform armRight = model.getTransform("armRight");
		final BoneTransform legLeft1 = model.getTransform("legLeft1");
		final BoneTransform legLeft2 = model.getTransform("legLeft2");
		final BoneTransform legLeft3 = model.getTransform("legLeft3");
		final BoneTransform legLeft4 = model.getTransform("legLeft4");
		final BoneTransform legRight1 = model.getTransform("legRight1");
		final BoneTransform legRight2 = model.getTransform("legRight2");
		final BoneTransform legRight3 = model.getTransform("legRight3");
		final BoneTransform legRight4 = model.getTransform("legRight4");

		head.rotX = headPitch;
		head.rotY = headYaw;

		armRight.rotZ += MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
		armRight.rotX += MathHelper.sin(limbPitch * 0.067F) * 0.05F;
		armLeft.rotZ -= MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
		armLeft.rotX -= MathHelper.sin(limbPitch * 0.067F) * 0.05F;

		final double rZ = -45 * MathHelper.DEG_TO_RAD;
		legRight1.rotZ = -rZ;
		legLeft1.rotZ =  rZ;
		legRight2.rotZ = -rZ * 0.74F;
		legLeft2.rotZ =  rZ * 0.74F;
		legRight3.rotZ = -rZ * 0.74F;
		legLeft3.rotZ =  rZ * 0.74F;
		legRight4.rotZ = -rZ;
		legLeft4.rotZ =  rZ;

		final double ry = 22.5 * MathHelper.DEG_TO_RAD;
		legRight1.rotY =  ry * 2.0F;
		legLeft1.rotY = -ry * 2.0F;
		legRight2.rotY =  ry;
		legLeft2.rotY = -ry;
		legRight3.rotY = -ry;
		legLeft3.rotY =  ry;
		legRight4.rotY = -ry * 2.0;
		legLeft4.rotY =  ry * 2.0F;

		final double pair1Y = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbYaw;
		final double pair2Y = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + MathHelper.PI) * 0.4F) * limbYaw;
		final double pair3Y = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + MathHelper.PI / 2F) * 0.4F) * limbYaw;
		final double pair4Y = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + MathHelper.PI * 1.5F) * 0.4F) * limbYaw;
		final double pair1Z = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbYaw;
		final double pair2Z = Math.abs(MathHelper.sin(limbSwing * 0.6662F + MathHelper.PI) * 0.4F) * limbYaw;
		final double pair3Z = Math.abs(MathHelper.sin(limbSwing * 0.6662F + MathHelper.PI / 2F) * 0.4F) * limbYaw;
		final double pair4Z = Math.abs(MathHelper.sin(limbSwing * 0.6662F + MathHelper.PI * 1.5F) * 0.4F) * limbYaw;

		legRight1.rotY += pair1Y;
		legLeft1.rotY -= pair1Y;
		legRight2.rotY += pair2Y;
		legLeft2.rotY -= pair2Y;
		legRight3.rotY += pair3Y;
		legLeft3.rotY -= pair3Y;
		legRight4.rotY += pair4Y;
		legLeft4.rotY -= pair4Y;

		legRight1.rotZ += pair1Z;
		legLeft1.rotZ -= pair1Z;
		legRight2.rotZ += pair2Z;
		legLeft2.rotZ -= pair2Z;
		legRight3.rotZ += pair3Z;
		legLeft3.rotZ -= pair3Z;
		legRight4.rotZ += pair4Z;
		legLeft4.rotZ -= pair4Z;

		return model;
	}

	@Override
	protected int maxRenderLayer(@NotNull MobEyes entity) {
		return 2;
	}
}
