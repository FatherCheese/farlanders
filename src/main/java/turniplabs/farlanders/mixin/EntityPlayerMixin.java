package turniplabs.farlanders.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.farlanders.Farlanders;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends Entity {


	@Unique
	private boolean toggledFullBright = false;
	@Unique
	private int durabilityTimer = 0;
	@Unique
	private Boolean gameSetFullbright = null;

	@Shadow
	public InventoryPlayer inventory = new InventoryPlayer((EntityPlayer)(Object)this);

	public EntityPlayerMixin(World world) {
		super(world);
	}

	@Unique
	public boolean hasNightVision() {
		return inventory.armorInventory[3] != null && inventory.armorInventory[3].itemID == Farlanders.itemArmorGoggles.id;
	}

	@Inject(method = "onLivingUpdate", at = @At("TAIL"))
	private void farlanders_nightVision(CallbackInfo ci) {
		if (!world.isClientSide){
			durabilityTimer++;
			if (hasNightVision()){
				if (durabilityTimer > 100) {
					durabilityTimer = 0;
					inventory.damageArmor(1, 3);
				}
			}
			return;
		}
		Minecraft mc = Minecraft.getMinecraft(this);
		if (gameSetFullbright == null){
			gameSetFullbright = mc.fullbright;
		}
		if (hasNightVision()){
			if (!toggledFullBright && mc.fullbright)
				gameSetFullbright = true;

			if (!toggledFullBright) {
				if (!mc.fullbright) {
					mc.fullbright = true;
					mc.renderGlobal.loadRenderers();
				}
				toggledFullBright = true;
			}

			if (!mc.fullbright) {
				gameSetFullbright = !gameSetFullbright;
				mc.fullbright = true;
				mc.renderGlobal.loadRenderers();
			}
		} else {
			if (toggledFullBright) {
				mc.fullbright = gameSetFullbright;
				toggledFullBright = false;
				mc.renderGlobal.loadRenderers();
			}
		}
	}
}
