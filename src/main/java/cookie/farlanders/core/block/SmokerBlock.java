package cookie.farlanders.core.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

import java.util.Random;

public class SmokerBlock extends Block {
	public SmokerBlock(String key, int id) {
		super(key, id, Material.stone);
		setTicking(true);
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		for(int i = 0; i < 20; ++i) {
			double motX = rand.nextGaussian() * 0.02;
			double motY = rand.nextGaussian() * 0.2;
			double motZ = rand.nextGaussian() * 0.02;

			world.spawnParticle(
				"smoke",
				x + 0.5,
				y + 0.5,
				z + 0.5,
				motX,
				motY,
				motZ,
				0,
				64.0
			);
		}
	}

	@Override
	public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
		float f = 0.0625F;
		return AABB.getBoundingBoxFromPool((float)x + f, y, (float)z + f, (float)(x + 1) - f, (float)y + 0.1875F, (float)(z + 1) - f);
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
