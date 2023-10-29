package turniplabs.farlanders.util;

import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class FarlanderUtils {

	public static double calculateDotProduct(Vec3d vec1, Vec3d vec2) {
		return vec1.xCoord * vec2.xCoord + vec1.yCoord * vec2.yCoord + vec1.zCoord * vec2.zCoord;
	}

	public static boolean isValidTPPos(World world, int x, int y, int z) {
		return world.isAirBlock(x, y, z) && world.isBlockOpaqueCube(z, y - 1, z);
	}

	public static boolean isStaredAt(EntityMonster monster, EntityPlayer player) {
		if (player != null) {
			Vec3d playerLookDirection = player.getViewVector(1.0f).normalize();
			Vec3d entityToPlayerDirection = Vec3d.createVector(monster.x - player.x,
				monster.bb.minY + (double) monster.bbHeight - player.y + (double) player.cameraPitch,
				monster.z - player.z);

			double entityToPlayerDistance = entityToPlayerDirection.lengthVector();

			entityToPlayerDirection = entityToPlayerDirection.normalize();

			double angleBetweenDirections = FarlanderUtils.calculateDotProduct(playerLookDirection, entityToPlayerDirection);
			double thresholdAngles = 1.0d - 0.025d / entityToPlayerDistance;

			return angleBetweenDirections > thresholdAngles && player.canEntityBeSeen(monster);
		}
		else return false;
	}
}
