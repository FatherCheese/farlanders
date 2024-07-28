package cookie.farlanders.core;

import cookie.farlanders.FarlandersConfig;
import cookie.farlanders.client.render.EyesModel;
import cookie.farlanders.client.render.FarlanderModel;
import cookie.farlanders.client.render.FarlanderRenderer;
import cookie.farlanders.client.render.EyesRenderer;
import cookie.farlanders.core.entity.EyesEntity;
import cookie.farlanders.core.entity.FarlanderEntity;
import org.useless.dragonfly.helper.ModelHelper;
import turniplabs.halplibe.helper.EntityHelper;

import static cookie.farlanders.Farlanders.MOD_ID;

public class FarlanderEntities {

	public static void initializeCore() {
		EntityHelper.createEntity(FarlanderEntity.class,
			FarlandersConfig.cfg.getInt("Farlanders.farlanderID"),
			"Farlander",
			() -> new FarlanderRenderer(
				ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/farlander.json", FarlanderModel.class))
		);

		EntityHelper.createEntity(EyesEntity.class,
			FarlandersConfig.cfg.getInt("Farlanders.eyesID"),
			"Eyes",
			() -> new EyesRenderer(
				ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/eyes.json", EyesModel.class))
		);
	}
}
