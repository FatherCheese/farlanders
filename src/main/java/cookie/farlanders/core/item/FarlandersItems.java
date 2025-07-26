package cookie.farlanders.core.item;

import cookie.farlanders.core.Farlanders;
import cookie.farlanders.core.item.logic.ItemLogicFarlanderPearl;
import cookie.farlanders.extra.FarlandersConfig;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.util.ItemInitEntrypoint;

public class FarlandersItems implements ItemInitEntrypoint {
	private static int baseID = FarlandersConfig.cfg.getInt("IDs.startingItemID");
	private static final ArmorMaterial material = new ArmorMaterial(NamespaceID.getPermanent(
		Farlanders.MOD_ID, "goggles"),
		240);

	public static final Item FARLANDER_LENS = new ItemBuilder(Farlanders.MOD_ID)
		.build(new Item("lens", "farlanders:item/lens", baseID++));

	public static final Item FARLANDER_PEARL = new ItemBuilder(Farlanders.MOD_ID)
		.build(new ItemLogicFarlanderPearl("pearl", "farlanders:item/pearl", baseID++));

	public static final Item FARLANDER_GOGGLES = new ItemBuilder(Farlanders.MOD_ID)
		.build(new ItemArmor("armor.helmet.goggles", "farlanders:item/goggles", baseID++, material, 3));

	@Override
	public void afterItemInit() {

	}
}
