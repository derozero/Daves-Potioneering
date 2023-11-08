package tfar.davespotioneering.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import tfar.davespotioneering.blockentity.ReinforcedCauldronBlockEntity;

public class ModBlockEntityTypes {

    public static final BlockEntityType<ReinforcedCauldronBlockEntity> REINFORCED_CAULDRON = BlockEntityType.Builder.of(ReinforcedCauldronBlockEntity::new,ModBlocks.REINFORCED_WATER_CAULDRON).build(null);
}
