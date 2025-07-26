package cookie.farlanders.client;

import cookie.farlanders.core.Farlanders;
import cookie.farlanders.core.entity.MobFarlander;
import cookie.farlanders.core.item.FarlandersItems;
import cookie.farlanders.extra.FarlandersConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.sound.SoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;

@Environment(EnvType.CLIENT)
public class FarlandersClient implements ClientModInitializer, ClientStartEntrypoint {
	public static final String MOD_ID = "farlanders|client";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		try {
			TextureRegistry.initializeAllFiles(Farlanders.MOD_ID, TextureRegistry.blockAtlas, true);
			TextureRegistry.initializeAllFiles(Farlanders.MOD_ID, TextureRegistry.itemAtlas, true);
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}

		try {
			SoundRepository.registerNamespace(Farlanders.MOD_ID);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		LOGGER.info("Farlanders Client has been initialized.");
	}

	@Override
	public void beforeClientStart() {
	}

	@Override
	public void afterClientStart() {
		MobInfoRegistry.register(MobFarlander.class,
			"farlanders.guidebook.section.mob.farlander.name",
			"farlanders.guidebook.section.mob.farlander.desc",
			FarlandersConfig.cfg.getInt("Farlanders.farlanderHealth"),
			1000,
			new MobInfoRegistry.MobDrop[]{
				new MobInfoRegistry.MobDrop(FarlandersItems.FARLANDER_LENS.getDefaultStack(), 1.0F, 1, 2),
				new MobInfoRegistry.MobDrop(FarlandersItems.FARLANDER_PEARL.getDefaultStack(), 12.0F, 1, 1)
			});
	}
}
