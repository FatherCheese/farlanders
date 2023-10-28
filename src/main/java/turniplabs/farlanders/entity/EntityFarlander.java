package turniplabs.farlanders.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import turniplabs.farlanders.util.FarlanderUtils;

public class EntityFarlander extends EntityMonster {
	private final EntityPlayer player = Minecraft.getMinecraft(this).thePlayer;

	private boolean angry = false;
	private int soundTicks = 0;
	private int ticksNotLooking = 0;
	private int teleportTime = 0;

	private int trailerSound = 0;

	public EntityFarlander(World world) {
		super(world);
		skinName = "farlander";
		highestSkinVariant = 0;
		scoreValue = 1000;
		setSize(0.6f, 2.5f);
		health = 80;
		moveSpeed = 0;
	}

	private void randomTP(int x, int y, int z) {
		int randX = 0;
		int randY = 0;
		int randZ = 0;

		// Check 5 times for a valid position
		for (int i = 0; i < 5; i++) {
			randX = (int) (random.nextInt(x + x) - x + this.x);
			randY = (int) (random.nextInt(y + y) - y + this.y);
			randZ = (int) (random.nextInt(z + z) - z + this.z);

			if (FarlanderUtils.isValidTPPos(world, randX, randY, randZ))
				break;
		}
		if (!FarlanderUtils.isValidTPPos(world, randX, randY, randZ))
			return;

		world.playSoundAtEntity(this, "farlanders.fwoosh", 1.0f, 1.0f);
		setPos(randX, randY, randZ);
	}

	private boolean isLookingAtFarlander(EntityPlayer player) {
		if (player != null) {
			Vec3d playerLookDirection = player.getViewVector(1.0f).normalize();
			Vec3d entityToPlayerDirection = Vec3d.createVector(x - player.x,
				bb.minY + (double) bbHeight - player.y + (double) player.cameraPitch,
				z - player.z);

			double entityToPlayerDistance = entityToPlayerDirection.lengthVector();

			entityToPlayerDirection = entityToPlayerDirection.normalize();

			double angleBetweenDirections = FarlanderUtils.calculateDotProduct(playerLookDirection, entityToPlayerDirection);
			double thresholdAngles = 1.0d - 0.025d / entityToPlayerDistance;

			return angleBetweenDirections > thresholdAngles && player.canEntityBeSeen(this);
		}
		else return false;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (soundTicks > 0 && soundTicks <= 540)
            --soundTicks;

		if (teleportTime > 0 && teleportTime <= 200)
            --teleportTime;

		if (isInWater()) {
			damageEntity(1, DamageType.GENERIC);
			randomTP(32, 16, 32);
		}

		if (isLookingAtFarlander(player)) {
			angry = true;
			yRot = player.yRot + 180;

			if (trailerSound == 0) {
				world.playSoundAtEntity(player, "ambient.cave.cave", 1.0f, 1.0f);
				trailerSound = 1;
			}
		}

		if (!isLookingAtFarlander(player) && angry) {
			double diffX = player.x - x;
			double diffZ = player.z - z;

            ++ticksNotLooking;

			if (soundTicks == 0) {
				world.playSoundAtEntity(player, "farlanders.whispers", 1.0f, 1.0f);
				soundTicks = 540;
			}

			if (teleportTime == 0) {
				setPos(player.x - diffX * random.nextDouble(), player.y, player.z - diffZ * random.nextDouble());
				world.playSoundAtEntity(player, "farlanders.fwoosh", 1.0f, 1.0f);
				teleportTime = 80;
			}

			if (ticksNotLooking % 40 != 0)
				return;

			ticksNotLooking = 0;
			angry = !angry;
		}
	}

	@Override
	protected void damageEntity(int i, DamageType damageType) {
		super.damageEntity(i, damageType);

		health -= i;
		attackStrength += 3;
		attackTime += 1;
		if (damageType != DamageType.COMBAT)
			return;

		EntityPlayer player = Minecraft.getMinecraft(this).thePlayer;
		setPos(player.x, player.y, player.z);
		world.playSoundAtEntity(player, "farlanders.fwoosh", 1.0f, 1.0f);
	}

	@Override
	public void knockBack(Entity entity, int i, double d, double d1) {
	}

	@Override
	protected String getLivingSound() {
		return null;
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	protected Entity findPlayerToAttack() {
		return angry ? player : null;
	}
}
