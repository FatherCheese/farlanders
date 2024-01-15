package turniplabs.farlanders.mixin;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.farlanders.FarlandersConfig;
import turniplabs.farlanders.entity.EntityEyes;
import turniplabs.farlanders.entity.EntityFarlander;

import java.util.List;

@Mixin(value = Biome.class, remap = false)
public class BiomeMixin {

	@Shadow
	protected List<SpawnListEntry> spawnableMonsterList;

	@Inject(method = "<init>", remap = false, at = @At("TAIL"))
	private void farlanders_addMobs(CallbackInfo ci) {
		if (FarlandersConfig.cfg.getBoolean("Farlanders.enableEntities")){
			spawnableMonsterList.add(new SpawnListEntry(EntityFarlander.class, 1));
			spawnableMonsterList.add(new SpawnListEntry(EntityEyes.class, 1));
		}
	}
}
