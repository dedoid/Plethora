package zachy.plethora.common.integration.waila;

import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;
import zachy.plethora.common.block.tile.TileQuantumFluidCache;

public class IntegrationModuleWaila {

    public void init() {
        FMLInterModComms.sendMessage("Waila", "register", getClass().getName() + ".callbackRegister");
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaDataProvider(), TileQuantumFluidCache.class);
    }
}
