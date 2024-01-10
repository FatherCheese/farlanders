package turniplabs.farlanders;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.farlanders.entity.EntityEyes;
import turniplabs.farlanders.entity.EntityFarlander;
import turniplabs.farlanders.entity.render.ModelEyes;
import turniplabs.farlanders.entity.render.ModelFarlander;
import turniplabs.farlanders.entity.render.RendererEyes;
import turniplabs.farlanders.entity.render.RendererFarlander;
import turniplabs.halplibe.helper.*;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import useless.dragonfly.helper.ModelHelper;


public class Farlanders implements GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "farlanders";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Item itemLens;
	public static Item itemArmorGoggles;
	public static ArmorMaterial materialGoggles;

	@Override
	public void beforeGameStart() {
		materialGoggles  = ArmorHelper.createArmorMaterial("goggles", 120, 70.0f, 70.0f, 70.0f, 70.0f);

		itemLens = ItemHelper.createItem(MOD_ID, new Item(FarlandersConfig.cfg.getInt("Item IDs.farlanderLens")), "lens", "lens.png");
		itemArmorGoggles = ItemHelper.createItem(MOD_ID, new ItemArmor("tools.goggles", FarlandersConfig.cfg.getInt("Item IDs.farlanderGoggles"), materialGoggles, 0),
			"armor.helmet.goggles", "armor_goggles.png");

		EntityHelper.Core.createEntity(EntityFarlander.class,FarlandersConfig.cfg.getInt("Farlanders.farlanderID"), "Farlander");
		EntityHelper.Core.createEntity(EntityEyes.class, FarlandersConfig.cfg.getInt("Farlanders.eyesID"), "Eyes");

		LOGGER.info("Farlanders initialized. Stay safe...");
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void beforeClientStart() {
		SoundHelper.Client.addSound(MOD_ID, "whispers.wav");
		SoundHelper.Client.addSound(MOD_ID, "fwoosh.wav");
		EntityHelper.Client.assignEntityRenderer(EntityFarlander.class,
			new RendererFarlander(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/farlander.json", ModelFarlander.class)));
		EntityHelper.Client.assignEntityRenderer(EntityEyes.class,
			new RendererEyes(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/eyes.json", ModelEyes.class)));
	}

	@Override
	public void afterClientStart() {

	}
}
