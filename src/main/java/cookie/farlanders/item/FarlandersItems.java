package cookie.farlanders.item;

import cookie.farlanders.FarlandersConfig;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.ItemHelper;

import static cookie.farlanders.Farlanders.MOD_ID;

public class FarlandersItems {
	public static Item FARLANDER_LENS;
	public static Item FARLANDER_GOGGLES;
	public static Item FARLANDER_SMOKER;

	public static ArmorMaterial materialGoggles = ArmorHelper.createArmorMaterial(MOD_ID, "goggles", 64, 70.0f, 70.0f, 70.0f, 70.0f);

	public static void initializeItems() {
		FARLANDER_LENS = ItemHelper.createItem(MOD_ID,
			new Item("lens", FarlandersConfig.cfg.getInt("IDs.farlanderLens")),
			"lens.png");

		FARLANDER_GOGGLES = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.goggles", FarlandersConfig.cfg.getInt("IDs.farlanderGoggles"), materialGoggles, 0),
			"armor_goggles.png");

		FARLANDER_SMOKER = ItemHelper.createItem(MOD_ID,
			new ItemFarlanderSmoker("smoker", FarlandersConfig.cfg.getInt("IDs.farlanderSmoker")),
			"smoker.png");
	}
}
