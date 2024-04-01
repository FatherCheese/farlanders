package cookie.farlanders;

import cookie.farlanders.entity.EntityEyes;
import cookie.farlanders.entity.EntityFarlander;
import useless.spawneggs.SpawnEggsEntrypoint;
import useless.spawneggs.SpawnEggsMod;

import java.awt.*;

public class SpawnEggsPlugin implements SpawnEggsEntrypoint {
	@Override
	public void onLoad() {
		SpawnEggsMod.createSpawnEgg(EntityEyes.class, Color.BLACK, new Color(255, 160, 255));
		SpawnEggsMod.createSpawnEgg(EntityFarlander.class, Color.BLACK, new Color(160, 176, 255));
	}
}
