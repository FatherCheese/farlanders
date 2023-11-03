package turniplabs.farlanders.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;
import turniplabs.farlanders.entity.EntityEyes;

public class RendererEyes extends LivingRenderer<EntityEyes> {

	public RendererEyes(ModelBase modelbase, float shadowSize) {
		super(modelbase, shadowSize);
		setRenderPassModel(new ModelEyes());
	}

	private boolean setEyeBrightness(EntityEyes eyes, int i, float f) {
		if (i == 0) {
			loadTexture("/mob/eyes/eyes.png");
			float brightness = eyes.getBrightness(1.0f);
			if (Minecraft.getMinecraft(this).fullbright)
				brightness = 1.0f;

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
	protected boolean shouldRenderPass(EntityEyes entity, int renderPass, float renderPartialTicks) {
		return setEyeBrightness(entity, renderPass, renderPartialTicks);
	}
}
