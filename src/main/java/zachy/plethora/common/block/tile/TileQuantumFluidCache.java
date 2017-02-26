package zachy.plethora.common.block.tile;

import zachy.plethora.common.core.util.Inventory;

public class TileQuantumFluidCache extends TileMod {

    public Inventory inventory;

    public  TileQuantumFluidCache() {
        inventory = new Inventory(3, "TileQuantumFluidCache", 64, this);
    }
}
