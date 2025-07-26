package cookie.farlanders.extra;

import cookie.farlanders.core.Farlanders;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.File;
import java.io.IOException;

public class FarlandersConfig {
	private static final Toml PROPERTIES = new Toml("Farlander's TOML Config");
	public static TomlConfigHandler cfg;

	static {
		PROPERTIES.addCategory("Farlanders")
			.addEntry("enableEntities", true)
			.addEntry("eyesChance", "Spawn chance of Eyes", 1)
			.addEntry("farlanderChance", "Spawn chance of Farlanders", 1)
			.addEntry("farlanderHealth", "Default health is 120", 120)
			.addEntry("farlanderDamage", "Damage is plus the number based on their health. Default is 2.", 2);

		PROPERTIES.addCategory("IDs")
			.addEntry("startingBlockID", 6600)
			.addEntry("startingItemID", 16600);

		cfg = new TomlConfigHandler(Farlanders.MOD_ID, PROPERTIES);

		File configFile = cfg.getConfigFile();
		if (cfg.getConfigFile().exists()) {
			cfg.loadConfig();
			cfg.setDefaults(cfg.getRawParsed());
		} else {
			cfg.setDefaults(PROPERTIES);
			try {
				//noinspection ResultOfMethodCallIgnored
				configFile.getParentFile().mkdirs();
				//noinspection ResultOfMethodCallIgnored
				configFile.createNewFile();
				cfg.writeConfig();
				cfg.loadConfig();
			} catch (IOException e) {
				throw new RuntimeException("Failed to generate config!", e);
			}
		}
	}
}
