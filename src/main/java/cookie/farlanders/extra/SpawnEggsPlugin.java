package cookie.farlanders.extra;

import cookie.farlanders.core.entity.EyesEntity;
import cookie.farlanders.core.entity.FarlanderEntity;
import useless.spawneggs.SpawnEggsEntrypoint;
import useless.spawneggs.SpawnEggsMod;

import java.awt.*;

public class SpawnEggsPlugin implements SpawnEggsEntrypoint {
	@Override
	public void onLoad() {
		SpawnEggsMod.createSpawnEgg(EyesEntity.class, Color.BLACK, new Color(255, 160, 255));
		SpawnEggsMod.createSpawnEgg(FarlanderEntity.class, Color.BLACK, new Color(160, 176, 255));
	}
}
