package cookie.farlanders.extra;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FarlanderUtils {

	public static double calculateDotProduct(@NotNull Vec3 vec1, @NotNull Vec3 vec2) {
		return vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
	}

	public static boolean isStaredAt(Mob mob, Player player) {
        if (player == null) return false;

		Vec3 playerLookDirection = player.getViewVector(1.0f).normalize();
		Vec3 entityToPlayerDirection = Vec3.getTempVec3(mob.x - player.x,
			mob.bb.minY + (double) mob.bbHeight - player.y + (double) player.cameraPitch,
			mob.z - player.z);

		double entityToPlayerDistance = entityToPlayerDirection.length();

		double angleBetweenDirections = FarlanderUtils.calculateDotProduct(playerLookDirection, entityToPlayerDirection);
		double thresholdAngles = 1.0 - 0.025 / entityToPlayerDistance;

		return angleBetweenDirections > thresholdAngles && player.canEntityBeSeen(mob);
	}
}
