package cookie.farlanders.core.entity;

import cookie.farlanders.core.item.FarlandersItems;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;

public class ProjectileFarlanderPearl extends Projectile {
	public ProjectileFarlanderPearl(World world) {
		super(world);
		modelItem = FarlandersItems.FARLANDER_PEARL;
	}

	public ProjectileFarlanderPearl(World world, Mob owner) {
		super(world, owner);
		modelItem = FarlandersItems.FARLANDER_PEARL;
	}

	public ProjectileFarlanderPearl(World world, double x, double y, double z) {
		super(world, x, y, z);
		modelItem = FarlandersItems.FARLANDER_PEARL;
	}

	@Override
	public void onHit(HitResult hitResult) {
		if (world == null || world.isClientSide) super.onHit(hitResult);
		owner.setPos(x, y + 2, z);
		owner.hurt(null, 5, DamageType.GENERIC);

		world.playSoundAtEntity(owner, owner, "farlanders:mob.farlander.slip", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f - 1.0f);

		super.onHit(hitResult);
	}
}
