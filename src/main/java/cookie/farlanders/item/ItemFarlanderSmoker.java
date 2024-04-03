package cookie.farlanders.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemFarlanderSmoker extends Item {

	public ItemFarlanderSmoker(String name, int id) {
		super(name, id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		for(int i = 0; i < 20; ++i) {
			double motX = itemRand.nextGaussian() * 0.02;
			double motY = itemRand.nextGaussian() * 0.02;
			double motZ = itemRand.nextGaussian() * 0.02;

			world.spawnParticle(
				"smoke",
				player.x + (double) (itemRand.nextFloat() * player.bbWidth * 2.0F) - (double) player.bbWidth,
				player.y + (double) (itemRand.nextFloat() * player.bbHeight),
				player.z + (double) (itemRand.nextFloat() * player.bbWidth * 2.0F) - (double) player.bbWidth,
				motX,
				motY,
				motZ
			);
		}

		return itemstack;
	}
}
