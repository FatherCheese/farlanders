package cookie.farlanders.extra.mixin;

import cookie.farlanders.core.item.FarlandersItems;
import cookie.farlanders.extra.interfaces.IPlayerSonar;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin extends Mob implements IPlayerSonar {

	@Shadow
	public ContainerInventory inventory;

	public PlayerMixin(@Nullable World world) {
		super(world);
	}

	@Unique
	private int durabilityTimer = 0;

	@Inject(method = "onLivingUpdate", at = @At("TAIL"))
	private void farlanders_onLivingUpdate(CallbackInfo ci) {
		if (world == null || world.isClientSide) return;

		if (farlanders$hasSonar()) {
			if (durabilityTimer++ >= 100) {
				durabilityTimer = 0;
				inventory.damageArmor(1, 3);
			}
		} else durabilityTimer = 0;
	}

	@Override
	public boolean farlanders$hasSonar() {
		return inventory.armorInventory[3] != null
			&& inventory.armorInventory[3].itemID == FarlandersItems.FARLANDER_GOGGLES.id;
	}
}
