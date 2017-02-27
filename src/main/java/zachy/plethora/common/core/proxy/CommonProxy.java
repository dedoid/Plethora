package zachy.plethora.common.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import zachy.plethora.common.block.ModBlocks;
import zachy.plethora.common.core.handler.ConfigHandler;
import zachy.plethora.common.integration.IntegrationManager;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

        ModBlocks.init();
    }

    public void init(FMLInitializationEvent event) {
        IntegrationManager.init();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
