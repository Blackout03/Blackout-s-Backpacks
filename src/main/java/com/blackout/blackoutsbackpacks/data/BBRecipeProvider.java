package com.blackout.blackoutsbackpacks.data;

import com.blackout.blackoutsbackpacks.BlackoutsBackpacks;
import com.blackout.blackoutsbackpacks.registry.BBItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class BBRecipeProvider extends RecipeProvider {
	public BBRecipeProvider(DataGenerator gen) {
		super(gen);
	}

	@SuppressWarnings("ALL")
	private static void crossRecipeItem(Consumer<IFinishedRecipe> consumer, IItemProvider pOutput, IItemProvider pSurroundMaterial, IItemProvider pItemMaterial) {
		ShapedRecipeBuilder.shaped(pOutput)
				.define('#', pSurroundMaterial)
				.define('X', pItemMaterial)
				.pattern(" # ")
				.pattern("#X#")
				.pattern(" # ")
				.unlockedBy("has_" + pSurroundMaterial.asItem(), has(pSurroundMaterial))
				.save(consumer, "blackoutsbackpacks:" + pOutput + "_from_" + pItemMaterial);
	}

	@SuppressWarnings("ALL")
	private static void crossRecipeItem(Consumer<IFinishedRecipe> consumer, IItemProvider pOutput, IItemProvider pSurroundMaterial, Tags.IOptionalNamedTag pItemMaterial) {
		ShapedRecipeBuilder.shaped(pOutput)
				.define('#', pSurroundMaterial)
				.define('X', pItemMaterial)
				.pattern(" # ")
				.pattern("#X#")
				.pattern(" # ")
				.unlockedBy("has_" + pSurroundMaterial.asItem(), has(pSurroundMaterial))
				.save(consumer, "blackoutsbackpacks:" + pOutput + "_from_" + pSurroundMaterial);
	}

	private static void backpackSmithing(Consumer<IFinishedRecipe> consumer, Item pOutput, Item pInput, Item pIngredient) {
		SmithingRecipeBuilder.smithing(Ingredient.of(pInput), Ingredient.of(pIngredient), pOutput)
				.unlocks("has_" + pIngredient, has(pIngredient))
				.save(consumer, "blackoutsbackpacks:" + pOutput + "_smithing");
	}

	public String getName() {
		return BlackoutsBackpacks.MODNAME + ": Recipes";
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		crossRecipeItem(consumer, BBItems.IRON_BACKPACK_UPGRADE.get(), Items.IRON_INGOT, Items.LEATHER);
		crossRecipeItem(consumer, BBItems.IRON_BACKPACK_UPGRADE.get(), Items.IRON_INGOT, Items.RABBIT_HIDE);
		crossRecipeItem(consumer, BBItems.GOLD_BACKPACK_UPGRADE.get(), Items.GOLD_INGOT, Items.IRON_INGOT);
		crossRecipeItem(consumer, BBItems.DIAMOND_BACKPACK_UPGRADE.get(), Items.DIAMOND, Items.GOLD_INGOT);
		crossRecipeItem(consumer, BBItems.NETHERITE_BACKPACK_UPGRADE.get(), Items.NETHERITE_SCRAP, Items.DIAMOND);
		crossRecipeItem(consumer, BBItems.EMERALD_BACKPACK_UPGRADE.get(), Items.EMERALD, Items.NETHERITE_SCRAP);

		crossRecipeItem(consumer, BBItems.LEATHER_BACKPACK.get(), Items.LEATHER, Tags.Items.CHESTS_WOODEN);
		crossRecipeItem(consumer, BBItems.LEATHER_BACKPACK.get(), Items.RABBIT_HIDE, Tags.Items.CHESTS_WOODEN);
		crossRecipeItem(consumer, BBItems.ENDER_BACKPACK.get(), Items.LEATHER, Tags.Items.CHESTS_ENDER);
		crossRecipeItem(consumer, BBItems.ENDER_BACKPACK.get(), Items.RABBIT_HIDE, Tags.Items.CHESTS_ENDER);

		backpackSmithing(consumer, BBItems.IRON_BACKPACK.get(), BBItems.LEATHER_BACKPACK.get(), BBItems.IRON_BACKPACK_UPGRADE.get());
		backpackSmithing(consumer, BBItems.GOLD_BACKPACK.get(), BBItems.IRON_BACKPACK.get(), BBItems.GOLD_BACKPACK_UPGRADE.get());
		backpackSmithing(consumer, BBItems.DIAMOND_BACKPACK.get(), BBItems.GOLD_BACKPACK.get(), BBItems.DIAMOND_BACKPACK_UPGRADE.get());
		backpackSmithing(consumer, BBItems.NETHERITE_BACKPACK.get(), BBItems.DIAMOND_BACKPACK.get(), BBItems.NETHERITE_BACKPACK_UPGRADE.get());
		backpackSmithing(consumer, BBItems.EMERALD_BACKPACK.get(), BBItems.NETHERITE_BACKPACK.get(), BBItems.EMERALD_BACKPACK_UPGRADE.get());
	}
}
