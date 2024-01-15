package turniplabs.farlanders.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.lwjgl.Sys;
import turniplabs.farlanders.Farlanders;
import turniplabs.farlanders.FarlandersConfig;
import turniplabs.farlanders.util.FarlanderUtils;

public class EntityFarlander extends EntityMonster {
	private EntityPlayer player;
	private boolean angry = false;
	private int soundTicks = 0;
	private int ticksNotLooking = 0;
	private int teleportTime = 0;
	private int randomTeleportTime;

	public EntityFarlander(World world) {
		super(world);
		skinName = "farlander";
		scoreValue = 1000;
		setSize(0.6f, 2.5f);
		health = FarlandersConfig.cfg.getInt("Farlanders.farlanderHealth");
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

	public void randomTP(int randPosX, int randPosY, int randPosZ) {
		int randX = 0;
		int randY = 0;
		int randZ = 0;

		// Check 5 times for a valid position
		for (int i = 0; i < 5; i++) {
			randX = (int) (random.nextInt(randPosX) + this.x);
			randY = (int) (random.nextInt(randPosY) + this.y);
			randZ = (int) (random.nextInt(randPosZ) + this.z);

			if (randY < 80 && world.isAirBlock(randX, (int) (randY + bb.minY), randZ)) {
				setPos(randX, randY, randZ);
				smoke();
				world.playSoundAtEntity(this, "farlanders.fwoosh", 1.0f, 1.0f);
			}
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		player = world.getClosestPlayerToEntity(this, 32.0);

		++randomTeleportTime;
		if (!angry && randomTeleportTime >= 600) {
			randomTP(16, 16, 16);
			randomTeleportTime = 0;
		}

		if (soundTicks > 0 && soundTicks <= 540)
            --soundTicks;

		if (teleportTime > 0 && teleportTime <= 200)
            --teleportTime;

		if (isInWater()) {
			this.hurt(null, 10, DamageType.GENERIC);
			randomTP(16, 16, 16);
		}

		if (player != null && player.gamemode != Gamemode.creative) {
			if (FarlanderUtils.isStaredAt(this, player)) {
				angry = true;
				faceEntity(player, 1.0F, 1.0F);
			} else
				if (!FarlanderUtils.isStaredAt(this, player) && angry) {
				double diffX = player.x - this.x;
				double diffY = player.y - this.y;
				double diffZ = player.z - this.z;

				++ticksNotLooking;

				if (soundTicks == 0) {
					world.playSoundAtEntity(player, "farlanders.whispers", 1.0f, 1.0f);
					soundTicks = 540;
				}

				if (teleportTime == 0) {
					setPos(player.x - diffX * random.nextDouble(), player.y - diffY, player.z - diffZ * random.nextDouble());
					smoke();
					world.playSoundAtEntity(player, "farlanders.fwoosh", 1.0f, 1.0f);
					teleportTime = 80;
				}

				if (ticksNotLooking == 600) {
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
		attackStrength += FarlandersConfig.cfg.getInt("Farlanders.farlanderDamage");
		attackTime += 1;
		if (damageType != DamageType.COMBAT)
			return;

		setPos(player.x + random.nextDouble(), player.y, player.z + random.nextDouble());
		smoke();
		world.playSoundAtEntity(player, "farlanders.fwoosh", 1.0f, 1.0f);
	}

	@Override
	public void knockBack(Entity entity, int i, double d, double d1) {
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

	@Override
	protected void jump() {
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}
}
