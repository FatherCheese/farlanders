package cookie.farlanders.entity.crafting;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.ItemStack;
import cookie.farlanders.Farlanders;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class FarlandersRecipes implements RecipeEntrypoint {
	public static RecipeNamespace FARLANDERS;
	public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));

	@Override
	public void onRecipesReady() {
		FARLANDERS.register("workbench", WORKBENCH);
		Registries.RECIPES.register(Farlanders.MOD_ID, FARLANDERS);
		DataLoader.loadRecipesFromFile("/assets/farlanders/recipes/workbench.json");
	}

	@Override
	public void initNamespaces() {
		FARLANDERS = new RecipeNamespace();
	}
}
