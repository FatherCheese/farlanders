package cookie.farlanders.client.render;

import cookie.farlanders.Farlanders;
import cookie.farlanders.core.entity.EyesEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.helper.ModelHelper;

public class EyesRenderer extends LivingRenderer<EyesEntity> {

	public EyesRenderer(ModelBase modelbase) {
		super(modelbase, 0.7F);
		setRenderPassModel(ModelHelper.getOrCreateEntityModel(Farlanders.MOD_ID, "entity/eyes.json", EyesModel.class));
	}

	private boolean setEyeBrightness(EyesEntity eyes, int i, float f) {
		if (i == 0) {
			loadTexture("/assets/farlanders/textures/entity/eyes/eyes.png");
			float brightness = eyes.getBrightness(1.0f);
			if (Minecraft.getMinecraft(this).fullbright)
				brightness = 1.0f;

			if (LightmapHelper.isLightmapEnabled()) {
				LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
			}

			float f1 = (1.0f - brightness) * 0.5f;
			GL11.glEnable(3042);
			GL11.glDisable(3008);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
			return true;
		} else
			return false;
	}

	@Override
	protected boolean shouldRenderPass(EyesEntity entity, int renderPass, float renderPartialTicks) {
		return setEyeBrightness(entity, renderPass, renderPartialTicks);
	}
}
