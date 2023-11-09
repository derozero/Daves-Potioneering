package tfar.davespotioneering.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tfar.davespotioneering.DavesPotioneering;
import tfar.davespotioneering.ModConfig;
import tfar.davespotioneering.Util;
import tfar.davespotioneering.blockentity.ReinforcedCauldronBlockEntity;
import tfar.davespotioneering.client.particle.FastDripParticle;
import tfar.davespotioneering.client.particle.TintedSplashParticle;
import tfar.davespotioneering.init.*;
import tfar.davespotioneering.mixin.ParticleManagerAccess;

public class ClientEvents {

    public static void particle(RegisterParticleProvidersEvent e) {

        ParticleEngine manager = Minecraft.getInstance().particleEngine;

        manager.register(ModParticleTypes.FAST_DRIPPING_WATER, FastDripParticle.DrippingWaterFactory::new);
        manager.register(ModParticleTypes.FAST_FALLING_WATER, FastDripParticle.FallingWaterFactory::new);
        manager.register(ModParticleTypes.TINTED_SPLASH, TintedSplashParticle.Factory::new);
    }

    public static void registerLoader(final ModelEvent.RegisterGeometryLoaders event) {
      //  event.register("fullbright", ModelLoader.INSTANCE);
    }


    public static void tooltips(ItemTooltipEvent e) {
        ItemStack stack = e.getItemStack();

        if (!PotionUtils.getMobEffects(stack).isEmpty()) {
            if (stack.getItem() instanceof TieredItem) {
                e.getToolTip().add(Component.literal("Coated with"));
                PotionUtils.addPotionTooltip(stack, e.getToolTip(), 0.125F);
                e.getToolTip().add(Component.literal("Uses: " + stack.getTag().getInt("uses")));
            } else if (stack.getItem().isEdible()) {
                PotionUtils.addPotionTooltip(stack, e.getToolTip(), 0.125F);
            }
        }
    }

    public static void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(ClientEvents::tooltips);
        MinecraftForge.EVENT_BUS.addListener(ClientEvents::playerTick);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.REINFORCED_WATER_CAULDRON,RenderType.translucent());

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, index) -> {
            if (pos != null) {
                BlockEntity blockEntity = reader.getBlockEntity(pos);
                if (blockEntity instanceof ReinforcedCauldronBlockEntity reinforced)
                    return reinforced.getColor();
            }
            return 0xffffff;
        }, ModBlocks.REINFORCED_WATER_CAULDRON);

    }

    public static void playerTick(TickEvent.PlayerTickEvent e) {
        Player player = e.player;
        
    }

    private static void spawnFluidParticle(ClientLevel world, Vec3 blockPosIn, ParticleOptions particleDataIn, int color) {
        // world.spawnParticle(new BlockPos(blockPosIn), particleDataIn, voxelshape, blockPosIn.getY() +.5);

        Particle particle = ((ParticleManagerAccess) Minecraft.getInstance().particleEngine).$makeParticle(particleDataIn, blockPosIn.x, blockPosIn.y, blockPosIn.z, 0, -.10, 0);

        float red = (color >> 16 & 0xff) / 255f;
        float green = (color >> 8 & 0xff) / 255f;
        float blue = (color & 0xff) / 255f;

        particle.setColor(red, green, blue);

        Minecraft.getInstance().particleEngine.add(particle);

        //world.addParticle(particleDataIn,blockPosIn.x,blockPosIn.y,blockPosIn.z,0,-.10,0);
    }
}
