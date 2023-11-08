package tfar.davespotioneering.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import tfar.davespotioneering.DavesPotioneering;

public class ModSoundEvents {

    public static final SoundEvent BUBBLING_WATER_CAULDRON = createSound("bubbling_water_cauldron");

    private static SoundEvent createSound(String name) {
        return new SoundEvent(new ResourceLocation(DavesPotioneering.MODID, name));
    }
}
