package turniplabs.farlanders.util;

import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class FarlanderUtils {

	public static double calculateDotProduct(Vec3d vec1, Vec3d vec2) {
		return vec1.xCoord * vec2.xCoord + vec1.yCoord * vec2.yCoord + vec1.zCoord * vec2.zCoord;
	}

	public static boolean isValidTPPos(World world, int x, int y, int z) {
		return world.isAirBlock(x, y, z) && world.isBlockOpaqueCube(z, y - 1, z);
	}
}
