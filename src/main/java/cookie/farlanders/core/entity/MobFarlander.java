package cookie.farlanders.core.entity;

import com.mojang.nbt.tags.CompoundTag;
import cookie.farlanders.core.Farlanders;
import cookie.farlanders.extra.FarlandersConfig;
import cookie.farlanders.core.item.FarlandersItems;
import cookie.farlanders.extra.FarlanderUtils;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

public class MobFarlander extends MobMonster {
	private Player player;
	private boolean isAngry = false;
	private int soundTicks;
	private int ticksNotLooking;
	private int slipTimer;
	private int randomSlipTimer;

	public MobFarlander(World world) {
		super(world);
		textureIdentifier = NamespaceID.getPermanent(Farlanders.MOD_ID, "farlander");
		scoreValue = 1000;
		setSize(0.6f, 2.5f);
		heartsHalvesLife = FarlandersConfig.cfg.getInt("Farlanders.farlanderHealth");
		moveSpeed = 0;
		mobDrops.add(new WeightedRandomLootObject(FarlandersItems.FARLANDER_LENS.getDefaultStack(), 0, 2));
	}

	private void smoke() {
		if (world == null || world.isClientSide) return;

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
		if (world == null || world.isClientSide) return;

		int randX;
		int randY;
		int randZ;

		// Check 5 times for a valid position
		if (!dead) {
			for (int i = 0; i < 5; i++) {
				randX = random.nextInt(randPosX) + MathHelper.floor(this.x);
				randY = random.nextInt(randPosY) + MathHelper.floor(this.y);
				randZ = random.nextInt(randPosZ) + MathHelper.floor(this.z);

				if (randY < 80 && world.isAirBlock(randX, MathHelper.floor(randY + bb.minY), randZ) && !(world.getBlockLightValue(randX, randY, randZ) > 7)) {
					world.playSoundAtEntity(player, this, "farlanders:mob.farlander.slip", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);
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

		if (world == null || world.isClientSide) return;
		player = world.getClosestPlayerToEntity(this, 64.0);

		// If it isn't angry and the random teleport timer surpasses 600, reset timer and slip away randomly.
		if (!isAngry && randomSlipTimer++ >= 600) {
			randomlySlip(16, 16, 16);
			randomSlipTimer = 0;
		}

		// Checks the light level in a 3x3 area around the Farlander.
		// If it's above light level 7, then do damage and randomly slip away.
		for (double _x = x - 1; _x <= x + 1; _x++) {
			for (double _z = z - 1; _z <= z + 1; _z++) {
				if (world.getBlockLightValue(MathHelper.floor(_x), MathHelper.floor(y), MathHelper.floor(_z)) > 7) {
					hurt(null, 5, DamageType.GENERIC);
					randomlySlip(16, 16, 16);
				}
			}
		}

		if ((player != null) && player.gamemode.areMobsHostile()) {
			if (isAngry) {
				lookAt(player, 30.0F, 30.0F);
				setTarget(player);
			}

			// A check to see if the Farlander is stared at.
			// If it is, then face the entity and set to angry.
			// Otherwise, it teleports a random distance closer and starts a 'not looking' timer.
			if (FarlanderUtils.isStaredAt(this, player)) isAngry = true;
			else if (!FarlanderUtils.isStaredAt(this, player) && isAngry && !dead) {
				++ticksNotLooking;

				if (soundTicks <= 0) {
					world.playSoundAtEntity(player, player, "farlanders:mob.farlander.whispering", 1.0f, 1.0f);
					soundTicks = 540;
				}

				double newX = (x + player.x) / 2.0;
				double newZ = (z + player.z) / 2.0;
				double newY = world.getHeightValue(MathHelper.floor(newX), MathHelper.floor(newZ)) + 0.5;

				// If the 'slip' timer hits 0, then this tries to spawn an item, spawn smoke particles,
				// teleport, and then reset the timer back to 80 ticks.
				if (slipTimer <= 0) {
					smoke();

					// This should only spawn pearls if the random int is 0.
					if (random.nextInt(12) == 0) {
						dropItem(FarlandersItems.FARLANDER_PEARL.id, 1);
					}

					setPos(newX, newY, newZ);
					smoke();
					world.playSoundAtEntity(player, player, "farlanders:mob.farlander.slip", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);
					slipTimer = 30;
				}
			}
		}

		// A check to see if the 'ticksNotLooking' int is above or equal to 600.
		// If it is, then set angry to 0 and reset the int back to 0.
		if (target != null) {
			if (slipTimer > 0) slipTimer--;
			if (soundTicks > 0) soundTicks--;

			if (distanceToSqr(target) <= 8.0) {
				target.hurt(this, 3, DamageType.COMBAT);
				attackTime = 20;
			}

			if (ticksNotLooking >= 600 && isAngry && distanceToSqr(target) >= 256.0) {
				ticksNotLooking = 0;
				isAngry = false;
			}
		}

		// Stuck prevention, in case they slip into blocks.
		if (world.getBlock(MathHelper.floor(x), MathHelper.floor(bb.minY + bb.maxY), MathHelper.floor(z)) != null)
			setPos(x, bb.minY + 1, z);
	}

	@Override
	public boolean hurt(Entity attacker, int i, DamageType type) {
		if (world == null || world.isClientSide) return false;
		if (super.hurt(attacker, i, type)) {

			// If the damage type is 'combat'...
			// Then teleport closer to the attacker by a random amount and play whispers.
			if (type == DamageType.COMBAT) {
				attackStrength += FarlandersConfig.cfg.getInt("Farlanders.farlanderDamage");
			}

			if (attacker != null && !dead) {
				double newX = random.nextFloat() * (attacker.x - x) + attacker.x;
				double newZ = random.nextFloat() * (attacker.z - z) + player.z;
				double newY = world.getHeightValue(MathHelper.floor(newX), MathHelper.floor(newZ)) + 0.5;

				if (random.nextInt(8) == 0) {
					world.playSoundAtEntity(player, this, "mob.chickenplop", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);
					dropItem(FarlandersItems.FARLANDER_PEARL.id, 1);
				}

				smoke();
				setPos(newX, newY, newZ);
				smoke();

				world.playSoundAtEntity(player, attacker, "farlanders:mob.farlander.slip", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);
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
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putByte("isAngry", isAngry() ? (byte) 1 : (byte) 0);
		tag.putInt("TicksNotLooking", ticksNotLooking);
		tag.putInt("SlipTimer", slipTimer);
		tag.putInt("RandomSlipTimer", randomSlipTimer);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setAngry(tag.getByte("IsAngry") == (byte) 1);
		ticksNotLooking = tag.getInteger("TicksNotLooking");
		slipTimer = tag.getInteger("SlipTimer");
		randomSlipTimer = tag.getInteger("RandomSlipTimer");
	}

	public boolean isAngry() {
		return isAngry;
	}

	public void setAngry(boolean angry) {
		isAngry = angry;
	}
}
