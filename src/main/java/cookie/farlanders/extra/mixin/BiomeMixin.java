package cookie.farlanders.extra.mixin;

import cookie.farlanders.client.FarlandersClient;
import cookie.farlanders.core.Farlanders;
import cookie.farlanders.extra.FarlandersConfig;
import cookie.farlanders.core.entity.MobEyes;
import cookie.farlanders.core.entity.MobFarlander;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = Biome.class, remap = false)
public class BiomeMixin {

	@Shadow
	protected List<SpawnListEntry> spawnableMonsterList;

	@Inject(method = "<init>", remap = false, at = @At("TAIL"))
	private void farlanders_addMobs(CallbackInfo ci) {
		if (FarlandersConfig.cfg.getBoolean("Farlanders.enableEntities")) {
			spawnableMonsterList.add(new SpawnListEntry(MobFarlander.class, FarlandersConfig.cfg.getInt("Farlanders.farlanderChance")));
			spawnableMonsterList.add(new SpawnListEntry(MobEyes.class, FarlandersConfig.cfg.getInt("Farlanders.eyesChance")));
		}
	}
}
