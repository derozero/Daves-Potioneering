package tfar.davespotioneering.datagen.assets;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;
import tfar.davespotioneering.DavesPotioneering;
import tfar.davespotioneering.init.ModBlocks;
import tfar.davespotioneering.init.ModEffects;
import tfar.davespotioneering.init.ModItems;
import tfar.davespotioneering.init.ModPotions;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(DataGenerator gen) {
        super(gen, DavesPotioneering.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addEffect(() -> ModEffects.MILK,"Milk");
        addPotions();
        addBlock(() -> ModBlocks.REINFORCED_CAULDRON,"Reinforced Cauldron");
        addBlock(() -> ModBlocks.REINFORCED_WATER_CAULDRON,"Reinforced Water Cauldron");

        addGroup(ModItems.tab,"Dave's Potioneering");

        detailedDescriptions();
    }

    public void detailedDescriptions() {
       
        addHoldSDesc(ModBlocks.REINFORCED_CAULDRON,"Summary: Hold [Shift]");
        addShiftDesc(ModBlocks.REINFORCED_CAULDRON,"A new cauldron that has some mechanical differences/benefits over the Vanilla one.");

        addHoldCDesc(ModBlocks.REINFORCED_CAULDRON,"Features: Hold [CTRL]");
        addCtrlDescs(ModBlocks.REINFORCED_CAULDRON,
                "- Water is not depleted when filling empty bottles.",
                "- Can be filled with potions. Don't mix different flavors!",
                "- Can be filled with milk"
        );

        addHoldADesc(ModBlocks.REINFORCED_CAULDRON,"Coating: Hold [Alt]");
        addAltDescs(ModBlocks.REINFORCED_CAULDRON,"1. Once the Reinforced Cauldron is filled with 3 similar potions and Dragon's Breath, a mixture is made.",
                "2. Next, throw the item/weapon/tool/arrows you would like to coat into the cauldron.",
                "3. The liquid will sizzle and evaporate until there is nothing left but the newly coated item.");

        addTooltip("coated_with","Coated with:");
        addTooltip("spiked_with","Spiked with:");
        add("key.davespotioneering.open_config","Open Config");
        add("key.categories."+DavesPotioneering.MODID,"Dave's Potioneering");
    }
    protected void addConfig(String value,String trans) {
        add("config."+value,trans);
    }
    protected void addTooltip(String code,String tip) {
        add(DavesPotioneering.MODID+"."+code+"."+"tooltip",tip);
    }
    protected void addDesc(ItemLike item, String desc) {
        add(item.asItem().getDescriptionId()+".desc",desc);
    }

    protected void addShiftDesc(ItemLike item, String desc) {
        add(item.asItem().getDescriptionId()+".shift.desc",desc);
    }

    protected void addShiftDescs(ItemLike item, String... descs) {
        for (int i = 0; i < descs.length; i++) {
            String desc = descs[i];
            add(item.asItem().getDescriptionId() + i+".shift.desc", desc);
        }
    }

    protected void addCtrlDesc(ItemLike item, String desc) {
        add(item.asItem().getDescriptionId()+".ctrl.desc",desc);
    }

    protected void addCtrlDescs(ItemLike item, String... descs) {
        for (int i = 0; i < descs.length; i++) {
            String desc = descs[i];
            add(item.asItem().getDescriptionId() + i+".ctrl.desc", desc);
        }
    }

    protected void addAltDesc(ItemLike item, String desc) {
        add(item.asItem().getDescriptionId()+".alt.desc",desc);
    }

    protected void addAltDescs(ItemLike item, String... descs) {
        for (int i = 0; i < descs.length; i++) {
            String desc = descs[i];
            add(item.asItem().getDescriptionId() + i+".alt.desc", desc);
        }
    }

    protected void addHoldSDesc(ItemLike item, String desc) {
        add(item.asItem().getDescriptionId()+".hold_shift.desc",desc);
    }

    protected void addHoldCDesc(ItemLike item, String desc) {
        add(item.asItem().getDescriptionId()+".hold_ctrl.desc",desc);
    }

    protected void addHoldADesc(ItemLike item, String desc) {
        add(item.asItem().getDescriptionId()+".hold_alt.desc",desc);
    }

    protected void addGroup(CreativeModeTab group,String name) {
        add(group.getDisplayName().getString(),name);
    }

    public void addPotions() {
        add(ModPotions.MILK.getName(Items.POTION.getDescriptionId() + ".effect."),"Milk Bottle");
        add(ModPotions.MILK.getName(Items.SPLASH_POTION.getDescriptionId() + ".effect."),"Splash Milk Bottle");
        add(ModPotions.MILK.getName(Items.LINGERING_POTION.getDescriptionId() + ".effect."),"Lingering Milk Bottle");
    }

    public static String getNameFromItem(Item item) {
        return StringUtils.capitaliseAllWords(item.getDescriptionId().split("\\.")[2].replace("_", " "));
    }
}
