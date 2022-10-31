package tfar.davespotioneering.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.davespotioneering.Util;
import tfar.davespotioneering.init.ModEffects;

@Mixin(PotionItem.class)
public class PotionItemMixin {

    /**
     * @author Tfar
     * @reason to change potion drinking times
     * @param stack
     * @return
     */
    @Overwrite
    public int getUseDuration(ItemStack stack) {
        return 20;//half of 32
    }

    @Inject(method = "isFoil",at = @At("HEAD"),cancellable = true)
    private void removeGlintFromMilk(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (PotionUtils.getMobEffects(stack).stream().anyMatch(effectInstance -> effectInstance.getEffect() == ModEffects.MILK)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "finishUsingItem",at = @At("HEAD"))
    private void milkifyEffect(ItemStack potion, Level pLevel, LivingEntity entityLiving, CallbackInfoReturnable<ItemStack> cir) {
        if (Util.isMilkified(potion)) {
            entityLiving.removeAllEffects();
        }
    }
}
