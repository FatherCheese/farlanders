package cookie.farlanders.core;

import cookie.farlanders.Farlanders;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class FarlandersRecipes implements RecipeEntrypoint {

	@Override
	public void onRecipesReady() {
		new RecipeBuilderShaped(Farlanders.MOD_ID, "111", "222")
			.addInput('1', FarlandersItems.FARLANDER_SMOKER)
			.addInput('2', "minecraft:cobblestones")
			.create("smoker", FarlandersBlocks.SMOKER.getDefaultStack());
	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(Farlanders.MOD_ID);
	}
}
