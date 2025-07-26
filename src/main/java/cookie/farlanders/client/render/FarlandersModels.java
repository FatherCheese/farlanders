package cookie.farlanders.client.render;

import cookie.farlanders.client.render.entity.MobRendererEyes;
import cookie.farlanders.client.render.entity.MobRendererFarlander;
import cookie.farlanders.core.Farlanders;
import cookie.farlanders.core.block.state.SmokerMetaState;
import cookie.farlanders.core.entity.ProjectileFarlanderPearl;
import cookie.farlanders.core.item.FarlandersItems;
import cookie.farlanders.core.block.FarlandersBlocks;
import cookie.farlanders.core.entity.MobEyes;
import cookie.farlanders.core.entity.MobFarlander;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import org.useless.DragonFly;
import org.useless.dragonfly.models.block.BlockModelDFJava;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

@Environment(EnvType.CLIENT)
public class FarlandersModels implements ModelEntrypoint {

	@Override
	public void initBlockModels(BlockModelDispatcher blockModelDispatcher) {
		ModelHelper.setBlockModel(FarlandersBlocks.SMOKER, () -> new BlockModelDFJava<>(FarlandersBlocks.SMOKER, DragonFly.loadBlockModel("farlanders:block/smoker/smoker_bottom"))
			.setStateInterpreter(new SmokerMetaState())
			.setStateData("farlanders:smoker"));
	}

	@Override
	public void initItemModels(ItemModelDispatcher itemModelDispatcher) {
		ModelHelper.setItemModel(FarlandersItems.FARLANDER_LENS, () -> {
			ItemModelStandard model = new ItemModelStandard(FarlandersItems.FARLANDER_LENS, Farlanders.MOD_ID);
			model.setIcon("farlanders:item/lens");
			model.setFullBright();

			return model;
		});

		ModelHelper.setItemModel(FarlandersItems.FARLANDER_PEARL, () -> {
			ItemModelStandard model = new ItemModelStandard(FarlandersItems.FARLANDER_PEARL, Farlanders.MOD_ID);
			model.setIcon("farlanders:item/pearl");

			return model;
		});

		ModelHelper.setItemModel(FarlandersItems.FARLANDER_GOGGLES, () -> {
			ItemModelStandard model = new ItemModelStandard(FarlandersItems.FARLANDER_GOGGLES, Farlanders.MOD_ID);
			model.setIcon("farlanders:item/goggles");

			return model;
		});
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher entityRenderDispatcher) {
		ModelHelper.setEntityModel(MobEyes.class, MobRendererEyes::new);
		ModelHelper.setEntityModel(MobFarlander.class, MobRendererFarlander::new);
		ModelHelper.setEntityModel(ProjectileFarlanderPearl.class, () -> new EntityRendererSprite<>(FarlandersItems.FARLANDER_PEARL));
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher tileEntityRenderDispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher blockColorDispatcher) {

	}
}
