package zachy.plethora.common.integration;

import cpw.mods.fml.common.Loader;
import zachy.plethora.common.integration.waila.IntegrationModuleWaila;

public class IntegrationManager {

    public static void init() {
        if (Loader.isModLoaded("Waila")) {
            new IntegrationModuleWaila().init();
        }
    }
}
