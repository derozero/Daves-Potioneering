package tfar.davespotioneering.datagen.assets;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.davespotioneering.DavesPotioneering;
import tfar.davespotioneering.block.LayeredReinforcedCauldronBlock;
import tfar.davespotioneering.init.ModBlocks;

public class ModBlockstateProvider extends BlockStateProvider {
    public ModBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, DavesPotioneering.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        brewingStand();


        simpleBlock(ModBlocks.REINFORCED_CAULDRON,models().getExistingFile(modLoc("block/reinforced_cauldron_level0")));

        getVariantBuilder(ModBlocks.REINFORCED_WATER_CAULDRON).forAllStatesExcept(state -> {
            boolean dragon = state.getValue(LayeredReinforcedCauldronBlock.DRAGONS_BREATH);
            String s = dragon ? "swirling_" : "bubbling_";
            ModelFile modelFile = models().getExistingFile(modLoc("block/"+s+"reinforced_cauldron_level" + state.getValue(LayeredCauldronBlock.LEVEL)));

            return ConfiguredModel.builder().modelFile(modelFile).build();
        });
    }

    protected void blockstateFromExistingModel(Block block) {
        String s = Registry.BLOCK.getKey(block).getPath();
        ModelFile modelFile = models().getExistingFile(new ResourceLocation(DavesPotioneering.MODID, "block/" + s));
        getVariantBuilder(block).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(modelFile);
            if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
                switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                    case EAST -> builder.rotationY(90);
                    case SOUTH -> builder.rotationY(180);
                    case WEST -> builder.rotationY(270);
                    case NORTH -> builder.rotationY(0);
                }
            }
            return builder.build();
        });
    }
}
