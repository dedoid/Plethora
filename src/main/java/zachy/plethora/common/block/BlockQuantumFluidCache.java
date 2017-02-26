package zachy.plethora.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import zachy.plethora.client.core.handler.GuiHandler;
import zachy.plethora.common.Plethora;
import zachy.plethora.common.block.tile.TileQuantumFluidCache;
import zachy.plethora.common.lib.LibBlockNames;

public class BlockQuantumFluidCache extends BlockModContainer {

    IIcon iconFront;
    IIcon iconSide;
    IIcon iconTop;
    IIcon iconBottom;

    public BlockQuantumFluidCache() {
        super(Material.iron);

        setHardness(2);
        setBlockName(LibBlockNames.QUANTUM_FLUID_CACHE);
    }

    @Override
    public Block setBlockName(String name) {
        GameRegistry.registerBlock(this, name);

        return super.setBlockName(name);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        //iconFront = register.registerIcon();
        //iconSide = register.registerIcon();
        //iconTop = register.registerIcon();
        //iconBottom = register.registerIcon();
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        switch(side) {
            case 0:
                return iconBottom;
            case 1:
                return iconTop;
            case 2:
                return iconFront;
            default:
                return iconSide;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileQuantumFluidCache();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float offsetX, float offsetY, float offsetZ) {
        if (!player.isSneaking()) {
            player.openGui(Plethora.instance, GuiHandler.quantumFluidCache, world, x, y, z);
        }

        return true;
    }
}
