package turniplabs.farlanders.entity.crafting;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.ItemStack;
import turniplabs.farlanders.Farlanders;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class FarlandersRecipes implements RecipeEntrypoint {
	public static final RecipeNamespace FARLANDERS = new RecipeNamespace();
	public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));

	@Override
	public void onRecipesReady() {
		FARLANDERS.register("workbench", WORKBENCH);
		Registries.RECIPES.register(Farlanders.MOD_ID, FARLANDERS);
		DataLoader.loadRecipes("/assets/farlanders/recipes/workbench.json");
	}
}
