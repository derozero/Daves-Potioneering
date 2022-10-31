package tfar.davespotioneering;

import net.minecraftforge.common.ForgeConfigSpec;
import tfar.davespotioneering.client.GauntletHUD;

public class ModConfig {

    public static class Client {

        //couldn't this be a resource pack?
      //  public static ForgeConfigSpec.BooleanValue play_block_brewing_stand_brew;

        public static ForgeConfigSpec.IntValue gauntlet_hud_x;
        public static ForgeConfigSpec.IntValue gauntlet_hud_y;
        public static ForgeConfigSpec.EnumValue<GauntletHUD.HudPresets> gauntlet_hud_preset;
        public static ForgeConfigSpec.IntValue particle_drip_rate;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("general");
         //   play_block_brewing_stand_brew = builder.define("play_block_brewing_stand_brew",true);
            gauntlet_hud_x = builder.comment("The X Position of the gauntlet hud (left top). You should be using the in-game gui to change this though").defineInRange("gauntlet_hud_x", -120, Integer.MIN_VALUE, Integer.MAX_VALUE);
            gauntlet_hud_y = builder.comment("The y Position of the gauntlet hud (left top). You should be using the in-game gui to change this though").defineInRange("gauntlet_hud_y", -92, Integer.MIN_VALUE, Integer.MAX_VALUE);
            gauntlet_hud_preset = builder.comment("You shouldn't change this. Just don't").defineEnum("gauntlet_hud_preset", GauntletHUD.HudPresets.FREE_MOVE);
            particle_drip_rate = builder.defineInRange("particle_drip_rate",10,1,Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static class Server {

        public static int potion_cooldown = 30;
        public static ForgeConfigSpec.BooleanValue milkification;
        public static ForgeConfigSpec.BooleanValue magic_protection;
        public static ForgeConfigSpec.IntValue gauntlet_cooldown;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            milkification = builder.define("milkification",false);
            magic_protection = builder.define("magic_protection",false);
            gauntlet_cooldown = builder.defineInRange("gauntlet_cooldown", 600, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }
}
