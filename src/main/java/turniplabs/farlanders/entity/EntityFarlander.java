package turniplabs.farlanders.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import turniplabs.farlanders.Farlanders;
import turniplabs.farlanders.util.FarlanderUtils;

public class EntityFarlander extends EntityMonster {
	private EntityPlayer player;
	private boolean angry = false;
	private int soundTicks = 0;
	private int ticksNotLooking = 0;
	private int teleportTime = 0;

	public EntityFarlander(World world) {
		super(world);
		skinName = "farlander";
		highestSkinVariant = 0;
		scoreValue = 1000;
		setSize(0.6f, 2.5f);
		health = 120;
		moveSpeed = 0;
	}

	private void smoke() {
		for(int j = 0; j < 20; ++j) {
			double motX = random.nextGaussian() * 0.02;
			double motY = random.nextGaussian() * 0.02;
			double motZ = random.nextGaussian() * 0.02;
			world.spawnParticle(
				"smoke",
				x + (double)(random.nextFloat() * bbWidth * 2.0F) - (double)bbWidth,
				y + (double)(random.nextFloat() * bbHeight),
				z + (double)(random.nextFloat() * bbWidth * 2.0F) - (double)bbWidth,
				motX,
				motY,
				motZ
			);
		}
	}

	public void randomTP(int x, int y, int z) {
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

		setPos(randX, randY, randZ);
		smoke();
		world.playSoundAtEntity(this, "farlanders.fwoosh", 1.0f, 1.0f);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		player = world.getClosestPlayerToEntity(this, 32.0);

		if (soundTicks > 0 && soundTicks <= 540)
            --soundTicks;

		if (teleportTime > 0 && teleportTime <= 200)
            --teleportTime;

		if (isInWater()) {
			damageEntity(1, DamageType.GENERIC);
			randomTP(32, 16, 32);
		}

		if (player != null && player.gamemode != Gamemode.creative) {
			if (FarlanderUtils.isStaredAt(this, player)) {
				angry = true;
				yRot = player.yRot + 180;
			}

			if (!FarlanderUtils.isStaredAt(this, player) && angry) {
				double diffX = player.x - x;
				double diffZ = player.z - z;

				++ticksNotLooking;

				if (soundTicks == 0) {
					world.playSoundAtEntity(player, "farlanders.whispers", 1.0f, 1.0f);
					soundTicks = 540;
				}

				if (teleportTime == 0) {
					setPos(player.x - diffX * random.nextDouble(), player.y, player.z - diffZ * random.nextDouble());
					smoke();
					world.playSoundAtEntity(player, "farlanders.fwoosh", 1.0f, 1.0f);
					teleportTime = 80;
				}

				if (ticksNotLooking == 200) {
					ticksNotLooking = 0;
					angry = !angry;
				}
			}
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

		setPos(player.x, player.y, player.z);
		smoke();
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

	@Override
	protected void dropFewItems() {
		int dropRand = random.nextInt(2);

		spawnAtLocation(Farlanders.itemLens.id, dropRand);
	}
}
