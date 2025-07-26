package cookie.farlanders.client.render.entity;

import cookie.farlanders.core.entity.MobFarlander;
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
public class MobRendererFarlander extends MobRenderer<MobFarlander> {
	public MobRendererFarlander() {
		super(0.5F);
	}

	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NotNull MobFarlander entity, float brightness, float partialTick, int layer) {
		if (layer == 1) {
			bindTexture("/assets/farlanders/textures/entity/farlander/farlander_eyes.png");
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
		final float limbPitch = getLimbPitch(entity, partialTick);

		final BoneTransform head = model.getTransform("head");
		final BoneTransform armLeft = model.getTransform("armLeft");
		final BoneTransform armRight = model.getTransform("armRight");
		final BoneTransform legLeft = model.getTransform("legLeft");
		final BoneTransform legRight = model.getTransform("legRight");

		head.rotX = headPitch;
		head.rotY = headYaw;

		armRight.rotZ += MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
		armRight.rotX += MathHelper.sin(limbPitch * 0.067F) * 0.05F;
		armLeft.rotZ -= MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
		armLeft.rotX -= MathHelper.sin(limbPitch * 0.067F) * 0.05F;

		legLeft.rotZ = -2.5 * MathHelper.DEG_TO_RAD;
		legRight.rotZ = 2.5 * MathHelper.DEG_TO_RAD;

		return model;
	}

	@Override
	protected int maxRenderLayer(@NotNull MobFarlander entity) {
		return 2;
	}
}
