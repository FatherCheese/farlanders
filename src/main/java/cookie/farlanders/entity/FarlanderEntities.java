package cookie.farlanders.entity;

import cookie.farlanders.FarlandersConfig;
import cookie.farlanders.entity.render.ModelEyes;
import cookie.farlanders.entity.render.ModelFarlander;
import cookie.farlanders.entity.render.RendererEyes;
import cookie.farlanders.entity.render.RendererFarlander;
import turniplabs.halplibe.helper.EntityHelper;
import useless.dragonfly.helper.ModelHelper;

import static cookie.farlanders.Farlanders.MOD_ID;

public class FarlanderEntities {

	public static void initializeCore() {
		EntityHelper.Core.createEntity(EntityFarlander.class, FarlandersConfig.cfg.getInt("Farlanders.farlanderID"), "Farlander");
		EntityHelper.Core.createEntity(EntityEyes.class, FarlandersConfig.cfg.getInt("Farlanders.eyesID"), "Eyes");
	}

	public static void initializeClient() {
		EntityHelper.Client.assignEntityRenderer(EntityFarlander.class,
			new RendererFarlander(ModelHelper.getOrCreateEntityModel(MOD_ID,
				"entity/farlander.json",
				ModelFarlander.class)));

		EntityHelper.Client.assignEntityRenderer(EntityEyes.class,
			new RendererEyes(ModelHelper.getOrCreateEntityModel(MOD_ID,
				"entity/eyes.json",
				ModelEyes.class)));
	}
}
