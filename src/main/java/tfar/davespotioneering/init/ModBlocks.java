package tfar.davespotioneering.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import tfar.davespotioneering.block.*;

import java.util.List;

public class ModBlocks {

    private static List<Block> MOD_BLOCKS;

    public static final Block REINFORCED_CAULDRON = new ReinforcedCauldronBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.STONE)
            .requiresCorrectToolForDrops().strength(0.5F).lightLevel(state -> 1).noOcclusion(),ModCauldronInteractions.EMPTY);

    public static final Block REINFORCED_WATER_CAULDRON = new LayeredReinforcedCauldronBlock(BlockBehaviour.Properties.copy(REINFORCED_CAULDRON));

}
