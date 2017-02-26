package zachy.plethora.common.core.handler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import zachy.plethora.common.core.helper.CompressionHelper;
import zachy.plethora.common.lib.LibMisc;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    public static String[] compressedVariants = {"minecraft:cobblestone", "minecraft:sand"};

    public static boolean enableCompressedBlocks = true;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);

        config.load();
        load();

        FMLCommonHandler.instance().bus().register(new ChangeListener());
    }

    public static void load() {
        String desc;

        desc = "Blocks currently being \"compressed\". Add or remove a block to \"compress\" or \"decompress\" it.\nFormat: modid:blockName";
        compressedVariants = loadStringList("compressed.variants", desc, compressedVariants);
        CompressionHelper.getVariant(compressedVariants);

        desc = "Set this to false to disable \"compressed\" of the above blocks";
        enableCompressedBlocks = loadBool("compressedBlocks.enabled", desc, enableCompressedBlocks);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static boolean loadBool(String name, String desc, boolean default_) {
        Property prop;

        prop = config.get(Configuration.CATEGORY_GENERAL, name, default_);
        prop.comment = desc;

        return prop.getBoolean(default_);
    }

    public static String[] loadStringList(String name, String desc, String[] default_) {
        Property prop;

        prop = config.get(Configuration.CATEGORY_GENERAL, name, default_);
        prop.comment = desc;

        return config.getStringList(name, "general", default_, desc);
    }


    public static class ChangeListener {

        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.modID.equals(LibMisc.MOD_ID)) {
                load();
            }
        }
    }
}
