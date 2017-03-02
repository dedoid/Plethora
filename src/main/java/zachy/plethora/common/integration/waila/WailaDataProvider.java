package zachy.plethora.common.integration.waila;

import cpw.mods.fml.common.Optional;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zachy.plethora.common.block.tile.TileQuantumFluidBuffer;

import java.util.ArrayList;
import java.util.List;

@Optional.Interface(iface = "mcp.mobius.waila.api.IWailaDataProvider", modid = "Waila")
public class WailaDataProvider implements IWailaDataProvider {

    private static List<String> quantumFluidCacheInfo;

    public WailaDataProvider() {
        quantumFluidCacheInfo = new ArrayList<String>();
    }

    @Override
    @Optional.Method(modid = "Waila")
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public List<String> getWailaHead(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currentTip;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public List<String> getWailaBody(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileQuantumFluidBuffer tileQuantumFluidBuffer;

        tileQuantumFluidBuffer = (TileQuantumFluidBuffer) accessor.getTileEntity();

        tileQuantumFluidBuffer.addWailaInfo(quantumFluidCacheInfo);

        currentTip.addAll(quantumFluidCacheInfo);
        quantumFluidCacheInfo.clear();

        return currentTip;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public List<String> getWailaTail(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currentTip;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tileEntity, NBTTagCompound tag, World world, int x, int y, int z) {
        return tag;
    }
}
