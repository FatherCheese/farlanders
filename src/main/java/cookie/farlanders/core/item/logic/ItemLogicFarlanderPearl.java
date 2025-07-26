package cookie.farlanders.core.item.logic;

import cookie.farlanders.core.entity.ProjectileFarlanderPearl;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.ProjectileEgg;
import net.minecraft.core.item.IDispensable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ItemLogicFarlanderPearl extends Item implements IDispensable {
	public ItemLogicFarlanderPearl(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
		setMaxStackSize(8);
	}

	@Override
	public ItemStack onUseItem(@NotNull ItemStack stack, @NotNull World world, Player player) {
		stack.consumeItem(player);
		world.playSoundAtEntity(player, player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isClientSide) {
			world.entityJoinedWorld(new ProjectileFarlanderPearl(world, player));
		}

		return stack;
	}

	@Override
	public void onUseByActivator(@NotNull ItemStack stack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, @NotNull Direction direction) {
		ProjectileFarlanderPearl projectile = new ProjectileFarlanderPearl(world, (double)blockX + offX, (double)blockY + offY, (double)blockZ + offZ);
		projectile.setHeading((double)direction.getOffsetX() * 0.6, direction.getOffsetY() == 0 ? 0.1 : (double)direction.getOffsetY() * 0.6, (float)direction.getOffsetZ() * 0.6, 1.1F, 6.0F);
		world.entityJoinedWorld(projectile);
		--stack.stackSize;
	}

	@Override
	public void onDispensed(ItemStack stack, World world, double x, double y, double z, int xOffset, int yOffset, int zOffset, Random random) {
		ProjectileFarlanderPearl projectile = new ProjectileFarlanderPearl(world, x, y, z);
		projectile.setHeading(xOffset, (double)yOffset + 0.1, zOffset, 1.1F, 6.0F);
		world.entityJoinedWorld(projectile);
	}
}
