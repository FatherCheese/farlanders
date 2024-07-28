package cookie.farlanders.core.entity;

import com.mojang.nbt.CompoundTag;
import cookie.farlanders.Farlanders;
import cookie.farlanders.FarlandersConfig;
import cookie.farlanders.core.FarlandersItems;
import cookie.farlanders.extra.FarlanderUtils;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class FarlanderEntity extends EntityMonster {
	private EntityPlayer player;
	private boolean isAngry = false;
	private int soundTicks;
	private int ticksNotLooking;
	private int slipTimer;
	private int randomSlipTimer;

	public FarlanderEntity(World world) {
		super(world);
		textureIdentifier = new NamespaceID(Farlanders.MOD_ID, "farlander");
		scoreValue = 1000;
		setSize(0.6f, 2.5f);
		heartsHalvesLife = FarlandersConfig.cfg.getInt("Farlanders.farlanderHealth");
		moveSpeed = 0;
	}

	private void smoke() {
		for(int i = 0; i < 20; ++i) {
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
				motZ,
				0
			);
		}
	}

	public void randomlySlip(int randPosX, int randPosY, int randPosZ) {

		int randX;
		int randY;
		int randZ;

		// Check 5 times for a valid position
		if (!dead) {
			for (int i = 0; i < 5; i++) {
				randX = (int) (random.nextInt(randPosX) + this.x);
				randY = (int) (random.nextInt(randPosY) + this.y);
				randZ = (int) (random.nextInt(randPosZ) + this.z);

				if (randY < 80 && world.isAirBlock(randX, (int) (randY + bb.minY), randZ) && !(world.getBlockLightValue(randX, randY, randZ) > 7)) {
					world.playSoundAtEntity(player, this, "farlanders.slip", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);
					smoke();
					setPos(randX, randY, randZ);
					smoke();
				}
			}
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		player = world.getClosestPlayerToEntity(this, 32.0);

		// If it isn't angry and the random teleport timer surpasses 600, reset timer and TP.
		if (!isAngry && randomSlipTimer++ >= 600) {
			randomlySlip(16, 16, 16);
			randomSlipTimer = 0;
		}

		// Checks the light level in a 3x3 area around the Farlander.
		// If it's above light level 7 then do damage and randomly slip away.
		for (double _x = x - 1; _x <= x + 1; _x++) {
			for (double _z = z - 1; _z <= z + 1; _z++) {
				if (world.getBlockLightValue((int) _x, (int) y, (int) _z) > 7) {
					hurt(null, 5, DamageType.GENERIC);
					randomlySlip(16, 16, 16);
				}
			}
		}

		if (player != null && player.gamemode != Gamemode.creative) {
			// A check to see if the Farlander is stared at.
			// If it is then face the entity and set to angry.
			// Otherwise, it teleports a random distance closer and starts a 'not looking' timer.
			if (FarlanderUtils.isStaredAt(this, player)) {
				isAngry = true;
			} else if (!FarlanderUtils.isStaredAt(this, player) && isAngry && !dead) {
				++ticksNotLooking;

				if (soundTicks-- <= 0) {
					world.playSoundAtEntity(player, player, "farlanders.whispers", 1.0f, 1.0f);
					soundTicks = 540;
				}

				double newX = random.nextFloat() * (player.x - x) + player.x;
				double newY = y + (double) (random.nextFloat() * bbHeight);
				double newZ = random.nextFloat() * (player.z - z) + player.z;

				// If the 'slip' timer hits 0 then try to spawn an item, spawn smoke particles,
				// teleport, and then reset the timer back to 80 ticks.
				if (slipTimer-- <= 0) {
					smoke();

					// This should only spawn the smoker if the random int is 0.
					if (random.nextInt(12) == 0) {
						world.playSoundAtEntity(null, this, "mob.chickenplop", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);
						spawnAtLocation(FarlandersItems.FARLANDER_SMOKER.id, 1);
					}

					setPos(newX, newY, newZ);
					smoke();
					world.playSoundAtEntity(player, player, "farlanders.slip", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);
					slipTimer = 80;
				}
			}

			if (isAngry) faceEntity(player, 30.0F, 30.0F);
		}

		// A check to see if the 'ticksNotLooking' int is above or equal to 600.
		// If it is then set angry to 0 and reset the int back to 0.
		if (ticksNotLooking >= 600 && isAngry) {
			ticksNotLooking = 0;
			isAngry = false;
		}

		// Stuck prevention, in-case they slip into blocks.
		if (world.getBlock((int) x, (int) bb.minY, (int) z) != null && world.isBlockOpaqueCube((int) x, (int) bb.minY, (int) z))
			setPos(x, bb.minY + 1, z);
	}

	@Override
	public boolean hurt(Entity attacker, int i, DamageType type) {
		if (super.hurt(attacker, i, type)) {

			// If the damage type is 'combat'...
			// Add to the 'new attack strength' int and set attack time to 0, or instantly.
			// Then teleport closer to the attacker by a random amount and play whispers.
			if (type == DamageType.COMBAT) {
				attackStrength += FarlandersConfig.cfg.getInt("Farlanders.farlanderDamage");
				attackTime = 0;
			}

			if (attacker != null && !dead) {
				double newX = random.nextFloat() * (attacker.x - x) + attacker.x;
				double newY = y + (double) (random.nextFloat() * bbHeight);
				double newZ = random.nextFloat() * (attacker.z - z) + player.z;

				if (random.nextInt(6) == 0) {
					world.playSoundAtEntity(player, this, "mob.chickenplop", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);
					spawnAtLocation(FarlandersItems.FARLANDER_SMOKER.id, 1);
				}

				smoke();
				setPos(newX, newY, newZ);
				smoke();

				world.playSoundAtEntity(player, attacker, "farlanders.slip", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);
			}

            return true;

        } else {
			return false;
		}
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
		return isAngry ? player : null;
	}

	@Override
	protected void jump() {
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("IsAngry", isAngry);
		tag.putInt("TicksNotLooking", ticksNotLooking);
		tag.putInt("SlipTimer", slipTimer);
		tag.putInt("RandomSlipTimer", randomSlipTimer);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		isAngry = tag.getBoolean("IsAngry");
		ticksNotLooking = tag.getInteger("TicksNotLooking");
		slipTimer = tag.getInteger("SlipTimer");
		randomSlipTimer = tag.getInteger("RandomSlipTimer");
	}
}
