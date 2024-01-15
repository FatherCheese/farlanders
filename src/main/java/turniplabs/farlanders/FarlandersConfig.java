package turniplabs.farlanders;

import turniplabs.halplibe.util.ConfigUpdater;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class FarlandersConfig {
	public static ConfigUpdater updater = ConfigUpdater.fromProperties();
	private static final Toml properties = new Toml("Farlander's TOML Config");
	public static TomlConfigHandler cfg;
	static {
		properties.addCategory("Farlanders")
			.addEntry("enableEntities", true)
			.addEntry("farlanderID", 70)
			.addEntry("eyesID", 71)
			.addEntry("farlanderHealth", "Default is 120", 120)
			.addEntry("farlanderDamage", "This is plus the number based on their health. Default is 3.", 3);

		properties.addCategory("Item IDs")
			.addEntry("farlanderLens", 16600)
			.addEntry("farlanderGoggles", 16601);

		cfg = new TomlConfigHandler(updater, Farlanders.MOD_ID, properties);
	}
}
