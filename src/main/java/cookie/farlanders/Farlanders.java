package cookie.farlanders;

import cookie.farlanders.entity.FarlanderEntities;
import cookie.farlanders.item.FarlandersItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class Farlanders implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "farlanders";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// IDEAS
	// The Slip
	// A whole new dimension; the Farlanders' home.
	// Gets whiter the deeper you are, but closer the fog?

	@Override
	public void onInitialize() {
		LOGGER.info("Farlanders has been initialized. Stay safe...");
	}

	@Override
	public void beforeGameStart() {
		FarlandersItems.initializeItems();
		FarlanderEntities.initializeCore();
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void beforeClientStart() {
		SoundHelper.Client.addSound(MOD_ID, "whispers.wav");
		SoundHelper.Client.addSound(MOD_ID, "slip.wav");

		FarlanderEntities.initializeClient();
	}

	@Override
	public void afterClientStart() {

	}
}
