package cookie.farlanders;

import cookie.farlanders.core.FarlandersBlocks;
import cookie.farlanders.core.FarlanderEntities;
import cookie.farlanders.core.FarlandersItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class Farlanders implements GameStartEntrypoint {
    public static final String MOD_ID = "farlanders";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void beforeGameStart() {
		SoundHelper.addSound(MOD_ID, "whispers.wav");
		SoundHelper.addSound(MOD_ID, "slip.wav");

		FarlandersItems.initializeItems();
		FarlandersBlocks.initializeBlocks();
		FarlanderEntities.initializeCore();

		LOGGER.info("Farlanders has been initialized. Stay safe...");
	}

	@Override
	public void afterGameStart() {

	}
}
