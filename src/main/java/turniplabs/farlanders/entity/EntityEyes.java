package turniplabs.farlanders.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import turniplabs.farlanders.util.FarlanderUtils;

public class EntityEyes extends EntityMonster {
	private int stareTimer = 0;
	private int soundTimer = 0;
	private boolean found = false;

	public EntityEyes(World world) {
		super(world);
		skinName = "eyes";
		highestSkinVariant = 0;
		scoreValue = 0;
		setSize(1.4f, 1.4f);
		health = 1000;
		moveSpeed = 0;
		attackStrength = 0;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0);

		if (player != null) {
			faceEntity(player, 1.0F, 1.0F);

			if (found)
				++stareTimer;

			if (FarlanderUtils.isStaredAt(this, player) && player.getGamemode().areMobsHostile) {
				found = true;

				if (soundTimer == 0) {
					world.playSoundAtEntity(this, "ambient.cave.cave", 1.0f, 1.0f);
					soundTimer = 1;
				}

				if (stareTimer > 25)
					this.remove();
			}
		}
	}

	private boolean validWorldType() {
        return world.worldType != WorldTypes.FLAT ||
			world.worldType != WorldTypes.EMPTY ||
			world.worldType != WorldTypes.PARADISE_DEFAULT;
    }

	@Override
	protected Entity findPlayerToAttack() {
		return null;
	}

	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere() && validWorldType() && !(y <= (double) world.getHeightBlocks() / 3);
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
	protected void jump() {
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}
}
