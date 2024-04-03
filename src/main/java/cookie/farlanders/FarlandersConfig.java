package cookie.farlanders;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class FarlandersConfig {
	private static final Toml properties = new Toml("Farlander's TOML Config");
	public static TomlConfigHandler cfg;

	static {
		properties.addCategory("Farlanders")
			.addEntry("enableEntities", true)
			.addEntry("farlanderID", 70)
			.addEntry("eyesID", 71)
			.addEntry("farlanderHealth", "Default health is 120", 120)
			.addEntry("farlanderDamage", "Damage is plus the number based on their health. Default is 2.", 2);

		properties.addCategory("IDs")
			.addEntry("farlanderLens", 16600)
			.addEntry("farlanderGoggles", 16601)
			.addEntry("farlanderSmoker", 16602);

		cfg = new TomlConfigHandler(Farlanders.MOD_ID, properties);
	}
}
