package cookie.farlanders.core.block.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockLogicSmoker extends BlockLogic {
	public final int ACTIVE = 0b0000_0001;

	public BlockLogicSmoker(Block<?> block) {
		super(block, Material.stone);
	}

	@Override
	public AABB getSelectedBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
		float pixel = 0.0625F;
		return AABB.getPermanentBB((float) x + pixel,
			y,
			(float) z + pixel,
			(float) (x + 1) - pixel,
			(float) y + (pixel * 3),
			(float) (z + 1) - pixel);
	}

	@Override
	public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
		float pixel = 0.0625F;
		return AABB.getPermanentBB((float) x + pixel,
			y,
			(float) z + pixel,
			(float) (x + 1) - pixel,
			(float) y + (pixel * 3),
			(float) (z + 1) - pixel);
	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		setActive(world, x, y, z, world.hasDirectSignal(x, y, z) || world.hasNeighborSignal(x, y, z));
	}

	public void setActive(@NotNull World world, int x, int y, int z, boolean active) {
		int meta = world.getBlockMetadata(x, y, z);
		if (active) world.setBlockMetadataWithNotify(x, y, z, meta | ACTIVE);
		else world.setBlockMetadataWithNotify(x, y, z, meta & ~ACTIVE);
	}

	@Override
	public void animationTick(World world, int x, int y, int z, Random rand) {
		if (world == null || world.isClientSide) return;
		int meta = world.getBlockMetadata(x, y, z);
		if ((meta & ACTIVE) == 1) return;

		for (int i = 0; i < 24; ++i) {
			double motX = rand.nextGaussian() * 0.04;
			double motY = rand.nextGaussian() * 0.15;
			double motZ = rand.nextGaussian() * 0.04;

			world.spawnParticle(
				"smoke",
				x + 0.5 + rand.nextFloat() * 0.5f - 0.25f,
				y + 0.5,
				z + 0.5 + rand.nextFloat() * 0.5f - 0.25f,
				motX,
				motY,
				motZ,
				0,
				64.0
			);
		}
	}
}
