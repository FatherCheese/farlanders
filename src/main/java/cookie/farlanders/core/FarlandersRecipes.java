package cookie.farlanders.core;

import cookie.farlanders.core.block.FarlandersBlocks;
import cookie.farlanders.core.item.FarlandersItems;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class FarlandersRecipes implements RecipeEntrypoint {

	@Override
	public void onRecipesReady() {
		RecipeBuilder.Shaped(Farlanders.MOD_ID)
			.setShape("111", "212")
			.addInput('1', Items.INGOT_GOLD)
			.addInput('2', FarlandersItems.FARLANDER_LENS)
			.create("goggles", FarlandersItems.FARLANDER_GOGGLES);

		RecipeBuilder.Shaped(Farlanders.MOD_ID)
			.setShape("121")
			.addInput('1', "minecraft:cobblestones")
			.addInput('2', FarlandersItems.FARLANDER_PEARL)
			.create("smoker", FarlandersBlocks.SMOKER);
	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(Farlanders.MOD_ID);
	}
}
