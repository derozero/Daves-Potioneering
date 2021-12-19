package tfar.davespotioneering.client.gecko;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tfar.davespotioneering.DavesPotioneering;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class GeoItemStackRenderer<T extends IAnimatable> extends ItemStackTileEntityRenderer implements IGeoRenderer<T> {

    private final AnimatedGeoModel<T> modelProvider;
    protected ItemStack currentItemStack;
    protected final Function<ResourceLocation, RenderType> renderTypeGetter;
    private final T ianimatable;

    private static final Map<Item, GeoItemStackRenderer<?>> animatedRenderers = new ConcurrentHashMap<>();

    public GeoItemStackRenderer(AnimatedGeoModel<T> modelProvider, T ianimatable) {
        this(modelProvider, RenderType::getEntityCutout, ianimatable);
    }

    public GeoItemStackRenderer(AnimatedGeoModel<T> modelProvider, Function<ResourceLocation, RenderType> renderTypeGetter, T ianimatable) {
        this.modelProvider = modelProvider;
        this.renderTypeGetter = renderTypeGetter;
        this.ianimatable = ianimatable;
    }

    public static void registerAnimatedItem(Item item, GeoItemStackRenderer<?> renderer) {
        animatedRenderers.put(item, renderer);
    }

    static {
        AnimationController.addModelFetcher((IAnimatable object) -> {
            if (object instanceof GeoItemStackRenderer.DummyAnimations) {
                GeoItemStackRenderer<?> renderer = animatedRenderers.get(((DummyAnimations) object).itemSupplier.get());
                return renderer == null ? null : renderer.getGeoModelProvider();
            }
            return null;
        });
    }

    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrices, IRenderTypeBuffer bufferIn,
            int combinedLightIn,
            int p_239207_6_
    ) {
        if (transformType == ItemCameraTransforms.TransformType.GUI) {
            matrices.push();
            Minecraft mc = Minecraft.getInstance();
            IRenderTypeBuffer.Impl buffer = mc.getRenderTypeBuffers().getBufferSource();
            RenderHelper.setupGuiFlatDiffuseLighting();
            this.render(ianimatable, matrices, bufferIn, combinedLightIn, stack);
            buffer.finish();
            RenderSystem.enableDepthTest();
            RenderHelper.setupGui3DDiffuseLighting();
            matrices.pop();
        } else {
            this.render(ianimatable, matrices, bufferIn, combinedLightIn, stack);
        }
    }

    public void render(T animatable, MatrixStack matrices, IRenderTypeBuffer bufferIn, int packedLightIn, ItemStack itemStack) {
        this.currentItemStack = itemStack;
        GeoModel model = modelProvider.getModel(modelProvider.getModelLocation(animatable));
        Minecraft mc = Minecraft.getInstance();
        AnimationEvent<T> itemEvent = new AnimationEvent<>(animatable, 0, 0, mc.getRenderPartialTicks(),
                false, Collections.singletonList(itemStack));
        modelProvider.setLivingAnimations(animatable, this.getUniqueID(animatable), itemEvent);
        matrices.push();
        matrices.translate(0, 0.01f, 0);
        matrices.translate(0.5, 0.5, 0.5);

        mc.textureManager.bindTexture(getTextureLocation(animatable));
        Color renderColor = getRenderColor(animatable, 0, matrices, bufferIn, null, packedLightIn);
        RenderType renderType = getRenderType(animatable, 0, matrices, bufferIn, null, packedLightIn,
                getTextureLocation(animatable));

        // Our models often use single sided planes for fine detail
        RenderSystem.disableCull();

        // Get the Glint buffer if this item is enchanted
        IVertexBuilder ivertexbuilder = ItemRenderer.getEntityGlintVertexBuilder(bufferIn, renderType, true, currentItemStack.hasEffect());

        render(model, animatable, 0, renderType, matrices, null, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY,
                (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f,
                (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
        matrices.pop();
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return renderTypeGetter.apply(textureLocation);
    }

    @Override
    public AnimatedGeoModel<T> getGeoModelProvider() {
        return modelProvider;
    }

    @Override
    public ResourceLocation getTextureLocation(T instance) {
        return this.modelProvider.getTextureLocation(instance);
    }

    public static class GeoItemModel<T extends IAnimatable> extends AnimatedGeoModel<T> {

        protected final ResourceLocation animation;

        protected final ResourceLocation modelLoc;
        protected final ResourceLocation textureLoc;


        private static final ResourceLocation DUMMY = new ResourceLocation(DavesPotioneering.MODID, "animations/animation.dummy.json");

        public GeoItemModel(ResourceLocation item) {
            this(item, DUMMY);
        }

        public GeoItemModel(ResourceLocation item, ResourceLocation animation) {
            this.animation = animation;
            String name = item.getPath();
            modelLoc = new ResourceLocation(DavesPotioneering.MODID, "geo/item/" + name + ".geo.json");
            textureLoc = new ResourceLocation(DavesPotioneering.MODID, "textures/item/" + name + ".png");
        }

        @Override
        public ResourceLocation getModelLocation(T object) {
            return modelLoc;
        }

        @Override
        public ResourceLocation getTextureLocation(T object) {
            return textureLoc;
        }

        @Override
        public ResourceLocation getAnimationFileLocation(T animatable) {
            return animation;
        }
    }

    public static class DummyAnimations implements IAnimatable {

        AnimationFactory factory = new AnimationFactory(this);
        public final Supplier<Item> itemSupplier;

        public DummyAnimations(Supplier<Item> itemSupplier) {
            this.itemSupplier = itemSupplier;
        }

        @Override
        public void registerControllers(AnimationData data) {
            data.addAnimationController(
                    new AnimationController<>(this, "null", 0, this::predicate)
            );
        }

        protected <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
            return PlayState.STOP;
        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }
}
