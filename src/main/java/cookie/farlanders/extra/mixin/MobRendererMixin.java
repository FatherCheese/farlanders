package cookie.farlanders.extra.mixin;

import cookie.farlanders.extra.interfaces.IPlayerSonar;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRenderer.class, remap = false)
public abstract class MobRendererMixin<T extends Mob> {
	@Shadow
	protected ModelBase mainModel;

	@Shadow
	protected abstract float limbSway(T entity, float partialTick);

	@Shadow
	public abstract void loadEntityTexture(T entity);

	@Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At("HEAD"))
	private void farlanders_render(Tessellator tessellator, T entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci) {
		if (farlanders_shouldRenderOutline(entity)) {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

			loadEntityTexture(entity);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

			// Outline color
			GL11.glColor4f(0.0F, 0.5F, 1.0F, Math.max(0.2F, (float) entity.getHealth() / entity.getMaxHealth()));

			GL11.glTranslated(x, y + 1.5, z);
			GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);

			// Multiple render passes with slight offsets
			float offset = 0.05f;
			int passes = 8;

			for (int i = 0; i < passes; i++) {
				GL11.glPushMatrix();

				// Create an offset in different directions
				float angle = (float)(i * 2 * Math.PI / passes);
				float offsetX = (float)(Math.cos(angle) * offset);
				float offsetZ = (float)(Math.sin(angle) * offset);

				GL11.glTranslatef(offsetX, 0, offsetZ);

				// Now render animations
				float bodyYaw = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTick;
				float headYaw = entity.yRotO + (entity.yRot - entity.yRotO) * partialTick;
				float headPitch = entity.xRotO + (entity.xRot - entity.xRotO) * partialTick;
				float limbSway = limbSway(entity, partialTick);
				float walkSpeed = entity.walkAnimSpeedO + (entity.walkAnimSpeed - entity.walkAnimSpeedO) * partialTick;
				float walkProgress = entity.walkAnimPos - entity.walkAnimSpeed * (1.0F - partialTick);

				GL11.glRotatef(bodyYaw, 0.0f, 1.0f, 0.0f);

				this.mainModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, 0.0625F);

				GL11.glPopMatrix();
			}

			// Restore GL state
			GL11.glPopAttrib();
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
	}

	@Unique
	private boolean farlanders_shouldRenderOutline(T entity) {
		Player player = Minecraft.getMinecraft().thePlayer;
		if (player == null || entity == null) return false;

		boolean hasSonar = ((IPlayerSonar) player).farlanders$hasSonar();
		if (!hasSonar) return false;

		double dist = player.distanceToSqr(entity);
		return dist <= 1024.0;
	}
}
