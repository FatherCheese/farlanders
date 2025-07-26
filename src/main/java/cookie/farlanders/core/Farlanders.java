package cookie.farlanders.core;

import cookie.farlanders.core.entity.MobEyes;
import cookie.farlanders.core.entity.MobFarlander;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.guidebook.mobs.GuidebookPageMob;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.core.util.collection.NamespaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;
import turniplabs.halplibe.util.GameStartEntrypoint;

public class Farlanders implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "farlanders";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		EntityHelper.createEntity(MobFarlander.class, NamespaceID.getPermanent(MOD_ID, "farlander"), "Farlander");
		EntityHelper.createEntity(MobEyes.class, NamespaceID.getPermanent(MOD_ID, "eyes"), "Eyes");

		LOGGER.info("Farlanders has been initialized.");
	}

	@Override
	public void beforeGameStart() {
	}

	@Override
	public void afterGameStart() {

	}
}
