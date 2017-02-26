package zachy.plethora.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import zachy.plethora.common.block.tile.TileQuantumFluidCache;
import zachy.plethora.common.core.handler.ConfigHandler;
import zachy.plethora.common.core.helper.CompressionHelper;
import zachy.plethora.common.lib.LibBlockNames;

public class ModBlocks {

    public static Block[] compressedBlock;
    public static Block quantumFluidCache;

    public static void init() {
        compressedBlock = new Block[CompressionHelper.VARIANT_NAME.size()];
        if (ConfigHandler.enableCompressedBlocks) {
            for (int i = 0; i < CompressionHelper.VARIANT_NAME.size(); i++) {
                compressedBlock[i] = new BlockCompressed(CompressionHelper.VARIANT_MOD_ID.get(i), CompressionHelper.VARIANT_NAME.get(i), CompressionHelper.VARIANT_TEXTURE_NAME.get(i));
            }
        }

        quantumFluidCache = new BlockQuantumFluidCache();

        initTileEntities();
    }

    public static void initTileEntities() {
        GameRegistry.registerTileEntity(TileQuantumFluidCache.class, LibBlockNames.TILE_QUANTUM_FLUID_CACHE);
    }
}
