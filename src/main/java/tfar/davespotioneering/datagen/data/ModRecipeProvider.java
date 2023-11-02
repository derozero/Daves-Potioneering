package tfar.davespotioneering.datagen.data;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import tfar.davespotioneering.init.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.REINFORCED_CAULDRON)
                .define('a', Blocks.CAULDRON)
                .define('b', Items.GOLD_INGOT)
                .pattern("b b").pattern("bab").pattern("b b")
                .unlockedBy("has_gold", has(Items.GOLD_INGOT)).save(consumer);
    }
}
