package turniplabs.farlanders.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;
import turniplabs.farlanders.Farlanders;
import turniplabs.farlanders.entity.EntityFarlander;
import useless.dragonfly.helper.ModelHelper;

public class RendererFarlander extends LivingRenderer<EntityFarlander> {

    public RendererFarlander(ModelBase modelbase) {
        super(modelbase, 0.4F);
		setRenderPassModel(ModelHelper.getOrCreateEntityModel(Farlanders.MOD_ID, "entity/farlander.json", ModelFarlander.class));
    }

    private boolean setEyeBrightness(EntityFarlander farlander, int i, float f) {
        if (i == 0) {
            loadTexture("/mob/farlander/farlander_eyes.png");
            float brightness = farlander.getBrightness(1.0f);
            if (Minecraft.getMinecraft(this).fullbright)
                brightness = 1.0f;

            float f1 = (1.0f - brightness) * 0.5f;
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.9F, 0.9F, 1.0F, f1);
            return true;
        } else
            return false;
    }

    @Override
    protected boolean shouldRenderPass(EntityFarlander entity, int renderPass, float renderPartialTicks) {
        return setEyeBrightness(entity, renderPass, renderPartialTicks);
    }
}
