package cookie.farlanders.core.block.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockLogicSmoker extends BlockLogic {
	public static final int MASK_ACTIVE = 0b0000_1000;

	public BlockLogicSmoker(Block<?> block) {
		super(block, Material.stone);
	}

	@Override
	public void onBlockPlacedByMob(@NotNull World world, int x, int y, int z, @NotNull Side side, @NotNull Mob mob, double xPlaced, double yPlaced) {
		world.setBlockMetadataWithNotify(x, y, z, side.getOpposite().getId());
		world.scheduleBlockUpdate(x, y, z, block.id(), 0);
	}

	@Override
	public void onBlockPlacedOnSide(@NotNull World world, int x, int y, int z, @NotNull Side side, double xPlaced, double yPlaced) {
		world.setBlockMetadataWithNotify(x, y, z, side.getOpposite().getId());
		world.scheduleBlockUpdate(x, y, z, block.id(), 0);
	}

	@Override
	public AABB getSelectedBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
		return getBoundingBox(world, x, y, z);
	}

	@Override
	public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
		return getBoundingBox(world, x, y, z);
	}

	private AABB getBoundingBox(@NotNull WorldSource world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		Side side = Side.getSideById(meta & ~MASK_ACTIVE);
		float pixel = 0.0625F;

		switch (side) {
			case BOTTOM: // DOWN
				return AABB.getTemporaryBB(x,
					y,
					z,
					x + 1,
					y + (pixel * 3),
					z + 1
				);
			case TOP: // UP
				return AABB.getTemporaryBB(x,
					y + 1 - (pixel * 3),
					z,
					x + 1,
					y + 1,
					z + 1
				);
			case NORTH: // NORTH
				return AABB.getTemporaryBB(x,
					y,
					z,
					x + 1,
					y + 1,
					z + (pixel * 3)
				);
			case SOUTH: // SOUTH
				return AABB.getTemporaryBB(x,
					y,
					z + 1 - (pixel * 3),
					x + 1,
					y + 1,
					z + 1
				);
			case WEST: // WEST
				return AABB.getTemporaryBB(x,
					y,
					z,
					x + (pixel * 3),
					y + 1,
					z + 1
				);
			case EAST: // EAST
				return AABB.getTemporaryBB(x + 1 - (pixel * 3),
					y,
					z,
					x + 1,
					y + 1,
					z + 1
				);
			default:
				return AABB.getTemporaryBB(x,
					y,
					z,
					x + 1,
					y + (pixel * 3),
					z + 1
				);
		}
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
		if (active) world.setBlockMetadataWithNotify(x, y, z, meta | MASK_ACTIVE);
		else world.setBlockMetadataWithNotify(x, y, z, meta & ~MASK_ACTIVE);
	}

	@Override
	public void animationTick(World world, int x, int y, int z, Random rand) {
		if (world == null || world.isClientSide) return;

		int meta = world.getBlockMetadata(x, y, z);
		if ((meta & MASK_ACTIVE) != 0) return;

		Side side = Side.getSideById(meta & ~MASK_ACTIVE);
		for (int i = 0; i < 24; ++i) {
			double xPos = x + 0.5;
			double yPos = y + 0.5;
			double zPos = z + 0.5;
			double motX = 0.0;
			double motY = 0.0;
			double motZ = 0.0;

			switch (side) {
				case BOTTOM:
					xPos += (rand.nextFloat() - 0.5) * 0.8;
					yPos = y + 0.25; // Just below bottom face
					zPos += (rand.nextFloat() - 0.5) * 0.8;
					motX = (rand.nextFloat() - 0.5) * 0.02;
					motY = 0.08 - rand.nextFloat() * 0.05;
					motZ = (rand.nextFloat() - 0.5) * 0.02;
					break;
				case TOP:
					xPos += (rand.nextFloat() - 0.5) * 0.8;
					yPos = y + 0.75; // Just above top face
					zPos += (rand.nextFloat() - 0.5) * 0.8;
					motX = (rand.nextFloat() - 0.5) * 0.02;
					motY = -0.08 + rand.nextFloat() * 0.05;
					motZ = (rand.nextFloat() - 0.5) * 0.02;
					break;
				case NORTH:
					xPos += (rand.nextFloat() - 0.5) * 0.8;
					yPos += (rand.nextFloat() - 0.5) * 0.8;
					zPos = z + 0.25; // Just outside north face
					motX = (rand.nextFloat() - 0.5) * 0.02;
					motY = (rand.nextFloat() - 0.5) * 0.02;
					motZ = 0.08 + rand.nextFloat() * 0.05;
					break;
				case SOUTH:
					xPos += (rand.nextFloat() - 0.5) * 0.8;
					yPos += (rand.nextFloat() - 0.5) * 0.8;
					zPos = z + 0.75; // Just outside south face
					motX = (rand.nextFloat() - 0.5) * 0.02;
					motY = (rand.nextFloat() - 0.5) * 0.02;
					motZ = -0.08 + rand.nextFloat() * 0.05;
					break;
				case WEST:
					xPos = x + 0.25; // Just outside west face
					yPos += (rand.nextFloat() - 0.5) * 0.8;
					zPos += (rand.nextFloat() - 0.5) * 0.8;
					motX = 0.08 - rand.nextFloat() * 0.05;
					motY = (rand.nextFloat() - 0.5) * 0.02;
					motZ = (rand.nextFloat() - 0.5) * 0.02;
					break;
				case EAST:
					xPos = x + 0.75; // Just outside east face
					yPos += (rand.nextFloat() - 0.5) * 0.8;
					zPos += (rand.nextFloat() - 0.5) * 0.8;
					motX = -0.08 + rand.nextFloat() * 0.05;
					motY = (rand.nextFloat() - 0.5) * 0.02;
					motZ = (rand.nextFloat() - 0.5) * 0.02;
					break;
			}

			world.spawnParticle(
				"smoke",
				xPos,
				yPos,
				zPos,
				motX,
				motY,
				motZ,
				0,
				64.0
			);
		}
	}
}
