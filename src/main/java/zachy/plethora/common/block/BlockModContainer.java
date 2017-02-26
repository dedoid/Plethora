package zachy.plethora.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zachy.plethora.common.core.PlethoraCreativeTab;

public abstract class BlockModContainer<T extends TileEntity> extends BlockContainer {

    public BlockModContainer(Material material) {
        super(material);

        setCreativeTab(PlethoraCreativeTab.INSTANCE);
    }

    @Override
    public abstract T createNewTileEntity(World world, int meta);
}
