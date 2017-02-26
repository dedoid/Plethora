package zachy.plethora.client.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import zachy.plethora.client.container.ContainerQuantumFluidCache;
import zachy.plethora.client.gui.GuiQuantumFluidCache;
import zachy.plethora.common.block.tile.TileQuantumFluidCache;

public class GuiHandler implements IGuiHandler {

    public static final int quantumFluidCache = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == quantumFluidCache) {
            return new ContainerQuantumFluidCache(player, (TileQuantumFluidCache) world.getTileEntity(x, y, z));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == quantumFluidCache) {
            return new GuiQuantumFluidCache(player, (TileQuantumFluidCache) world.getTileEntity(x, y, z));
        }

        return null;
    }
}
