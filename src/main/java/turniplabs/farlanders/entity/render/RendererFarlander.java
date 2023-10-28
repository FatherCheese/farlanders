package turniplabs.farlanders.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;
import turniplabs.farlanders.entity.EntityFarlander;

public class RendererFarlander extends LivingRenderer<EntityFarlander> {

    public RendererFarlander(ModelBase modelbase, float shadowSize) {
        super(modelbase, shadowSize);
        setRenderPassModel(new ModelFarlander());
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
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return true;
        } else
            return false;
    }

    @Override
    protected boolean shouldRenderPass(EntityFarlander entity, int renderPass, float renderPartialTicks) {
        return setEyeBrightness(entity, renderPass, renderPartialTicks);
    }
}
