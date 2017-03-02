package zachy.plethora.client.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import zachy.plethora.client.container.ContainerQuantumFluidBuffer;
import zachy.plethora.client.gui.GuiQuantumFluidBuffer;
import zachy.plethora.client.lib.LibGuiIDs;
import zachy.plethora.common.block.tile.TileQuantumFluidBuffer;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == LibGuiIDs.QUANTUM_FLUID_BUFFER) {
            return new ContainerQuantumFluidBuffer(player, (TileQuantumFluidBuffer) world.getTileEntity(x, y, z));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == LibGuiIDs.QUANTUM_FLUID_BUFFER) {
            return new GuiQuantumFluidBuffer(player, (TileQuantumFluidBuffer) world.getTileEntity(x, y, z));
        }

        return null;
    }
}
