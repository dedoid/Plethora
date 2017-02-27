package zachy.plethora.common.core.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import zachy.plethora.common.network.PacketHandler;

public class Tank extends FluidTank {

    private String name;
    private FluidStack lastStack;

    public Tank(String name, int capacity, TileEntity tileEntity) {
        super(capacity);

        this.name = name;
        this.tile = tileEntity;

        lastStack = null;
    }

    public boolean isEmpty() {
        return getFluid() == null || getFluid().amount <= 0;
    }

    public boolean isFull() {
        return getFluid() != null && getFluid().amount >= getCapacity();
    }

    public Fluid getFluidType() {
        return getFluid() != null ? getFluid().getFluid() : null;
    }

    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound tankData;

        tankData = new NBTTagCompound();
        super.writeToNBT(tankData);

        nbt.setTag(name, tankData);

        return nbt;
    }

    @Override
    public final FluidTank readFromNBT(NBTTagCompound nbt) {
        NBTTagCompound tankData;

        if (nbt.hasKey(name)) {
            setFluid(null);

            tankData = nbt.getCompoundTag(name);
            super.readFromNBT(tankData);
        }

        return this;
    }

    public void compareAndUpdate() {
        FluidStack current;

        if (tile.getWorldObj().isRemote) {
            return;
        }

        current = this.getFluid();

        if (current != null) {
            if (lastStack != null) {
                if (Math.abs(current.amount - lastStack.amount) >= 500) {
                    PacketHandler.sendPacketToAllPlayers(tile.getDescriptionPacket(), tile.getWorldObj());
                    lastStack = current.copy();
                } else if (lastStack.amount < this.getCapacity() && current.amount == this.getCapacity() || lastStack.amount == this.getCapacity() && current.amount < this.getCapacity()) {
                    PacketHandler.sendPacketToAllPlayers(tile.getDescriptionPacket(), tile.getWorldObj());
                    lastStack = current.copy();
                }
            } else {
                PacketHandler.sendPacketToAllPlayers(tile.getDescriptionPacket(), tile.getWorldObj());
                lastStack = current.copy();
            }
        } else if (lastStack != null) {
            PacketHandler.sendPacketToAllPlayers(tile.getDescriptionPacket(), tile.getWorldObj());
            lastStack = null;
        }
    }
}
