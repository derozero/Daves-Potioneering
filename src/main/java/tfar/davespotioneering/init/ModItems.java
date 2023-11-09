package tfar.davespotioneering.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import tfar.davespotioneering.DavesPotioneering;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModItems {

    private static List<Item> MOD_ITEMS;

    public static final CreativeModeTab tab = new CreativeModeTab(DavesPotioneering.MODID) {
        @Override
        public ItemStack makeIcon() {
        }
    };
    
    public static final Item REINFORCED_CAULDRON = new BlockItem(ModBlocks.REINFORCED_CAULDRON,new Item.Properties().tab(tab));

    public static final TagKey<Item> BLACKLISTED = ItemTags.create(new ResourceLocation(DavesPotioneering.MODID,"blacklisted"));
    public static final TagKey<Item> WHITELISTED = ItemTags.create(new ResourceLocation(DavesPotioneering.MODID,"whitelisted"));

    public static List<Item> getAllItems() {
        if (MOD_ITEMS == null) {
            MOD_ITEMS = Arrays.stream(ModItems.class.getFields()).map(field -> {
                try {
                    return field.get(null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }).filter(Item.class::isInstance).map(Item.class::cast).collect(Collectors.toList());
        }
        return MOD_ITEMS;
    }

}
