package tfar.davespotioneering;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    public static class Client {
        
    }
    
    public static class Server {

        public static ForgeConfigSpec.BooleanValue milk;
        public static ForgeConfigSpec.IntValue coating_uses;

        public static ForgeConfigSpec.BooleanValue show_spiked_food;
        public static ForgeConfigSpec.BooleanValue coat_tools;
        public static ForgeConfigSpec.BooleanValue spike_food;
        public static ForgeConfigSpec.BooleanValue coat_all;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            milk = builder.define("milk",true);
            coat_tools = builder.define("coat_tools",false);
            spike_food = builder.define("spike_food",true);
            coat_all = builder.define("coat_anything",false);
            show_spiked_food = builder.define("show_spiked_food",false);

            coat_all = builder.comment("Allows all items to be coated").define("coat_all",false);
            coating_uses = builder.comment("Number of uses per coating").defineInRange("coating_uses",25,1,Integer.MAX_VALUE);

            builder.pop();
        }
    }
}
