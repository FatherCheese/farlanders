package cookie.farlanders.mixin;

import cookie.farlanders.Farlanders;
import cookie.farlanders.item.FarlandersItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLiving {

	@Unique
	private int durabilityTimer = 0;

	@Shadow
	public final InventoryPlayer inventory = new InventoryPlayer((EntityPlayer)(Object)this);

	public EntityPlayerMixin(World world) {
		super(world);
	}

	@Unique
	public boolean hasSonar() {
		return inventory.armorInventory[3] != null && inventory.armorInventory[3].itemID == FarlandersItems.FARLANDER_GOGGLES.id;
	}

	@Inject(method = "onLivingUpdate", at = @At("TAIL"))
	private void farlanders_nightVision(CallbackInfo ci) {
		if (!world.isClientSide){
            if (hasSonar() && durabilityTimer++ >= 100) {
                durabilityTimer = 0;
                inventory.damageArmor(1, 3);
            }
		}

		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		if (mc != null) {
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(32.0F, 16.0F, 32.0F));
			if (!list.isEmpty()) {
                for (Entity entity : list) {
					if (hasSonar()) {
						if (entity instanceof EntityLiving) {
							entity.entityBrightness = 1.0f;
						}
					} else {
						entity.entityBrightness = 0.0f;
					}
                }
			}
		}
	}
}
