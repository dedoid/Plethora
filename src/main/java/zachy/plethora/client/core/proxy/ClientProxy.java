package zachy.plethora.client.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import zachy.plethora.client.core.handler.GuiHandler;
import zachy.plethora.common.Plethora;
import zachy.plethora.common.core.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {


        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Plethora.instance, new GuiHandler());

        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {


        super.postInit(event);
    }
}
