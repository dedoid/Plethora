package zachy.plethora.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import zachy.plethora.common.core.PlethoraCreativeTab;

public class BlockMod extends Block {

    public BlockMod(Material material) {
        super(material);

        setCreativeTab(PlethoraCreativeTab.INSTANCE);
    }
}
