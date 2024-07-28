package cookie.farlanders.core;

import cookie.farlanders.FarlandersConfig;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;

import static cookie.farlanders.Farlanders.MOD_ID;

public class FarlandersItems {
	private static int firstID = FarlandersConfig.cfg.getInt("IDs.startingItemID");
	private static int nextID() {
		return ++firstID;
	}

	public static Item FARLANDER_SMOKER;

	public static void initializeItems() {
		FARLANDER_SMOKER = new ItemBuilder(MOD_ID)
			.build(new Item("farlander_smoker", nextID()));
	}
}
