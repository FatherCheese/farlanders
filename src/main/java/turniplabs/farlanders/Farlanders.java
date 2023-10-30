package turniplabs.farlanders;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.farlanders.entity.EntityEyes;
import turniplabs.farlanders.entity.EntityFarlander;
import turniplabs.farlanders.entity.render.ModelEyes;
import turniplabs.farlanders.entity.render.ModelFarlander;
import turniplabs.farlanders.entity.render.RendererEyes;
import turniplabs.farlanders.entity.render.RendererFarlander;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.SoundHelper;


public class Farlanders implements ModInitializer {
    public static final String MOD_ID = "farlanders";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Farlanders initialized. Stay safe...");

		SoundHelper.addSound(MOD_ID, "whispers.wav");
		SoundHelper.addSound(MOD_ID, "fwoosh.wav");
		EntityHelper.createEntity(EntityFarlander.class, new RendererFarlander(new ModelFarlander(), 0.5f),200, "Farlander");
		EntityHelper.createEntity(EntityEyes.class, new RendererEyes(new ModelEyes(), 0.7f), 201, "Eyes");
    }
}
