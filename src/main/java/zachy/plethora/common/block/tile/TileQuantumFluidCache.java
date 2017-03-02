package zachy.plethora.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import zachy.plethora.common.block.ModBlocks;
import zachy.plethora.common.core.util.FluidUtil;
import zachy.plethora.common.core.util.Inventory;
import zachy.plethora.common.core.util.Tank;
import zachy.plethora.common.network.PacketHandler;

import java.util.List;

public class TileQuantumFluidCache extends TileMod implements IInventory, IFluidHandler {

    public int storage;

    public Inventory inventory;
    public Tank tank;

    public  TileQuantumFluidCache() {
        storage = Integer.MAX_VALUE;

        inventory = new Inventory(3, "tileQuantumFluidCache", 64, this);

        tank = new Tank("tileQuantumFluidCache", storage, this);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readFromNBTWithoutCoords(tag);
    }

    public void readFromNBTWithoutCoords(NBTTagCompound tag) {
        tank.readFromNBT(tag);
        inventory.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeToNBTWithoutCoords(tag);
    }

    public void writeToNBTWithoutCoords(NBTTagCompound tag) {
        tank.writeToNBT(tag);
        inventory.writeToNBT(tag);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag;

        tag = new NBTTagCompound();
        writeToNBT(tag);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!worldObj.isRemote) {
            FluidUtil.drainContainers(this, inventory, 0, 1);
            FluidUtil.fillContainers(this, inventory, 0, 1, tank.getFluidType());

            if (tank.getFluidType() != null && getStackInSlot(2) == null) {
                inventory.setInventorySlotContents(2, new ItemStack(tank.getFluidType().getBlock()));
            } else if (tank.getFluidType() == null && getStackInSlot(2) != null) {
                setInventorySlotContents(2, null);
            }

            tank.compareAndUpdate();
        }
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int ID, int count) {
        ItemStack stack;

        stack = inventory.decrStackSize(ID, count);
        syncWithAll();

        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int ID) {
        return inventory.getStackInSlotOnClosing(ID);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setInventorySlotContents(slot, stack);
        syncWithAll();
    }

    @Override
    public String getInventoryName() {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return inventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {
        inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return inventory.isItemValidForSlot(slot, stack);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int fill;

        fill = tank.fill(resource, doFill);
        tank.compareAndUpdate();

        return fill;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        FluidStack drain;

        drain = tank.drain(resource.amount, doDrain);
        tank.compareAndUpdate();

        return drain;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack drain;

        drain = tank.drain(maxDrain, doDrain);
        tank.compareAndUpdate();

        return drain;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return tank.getFluid() == null || tank.getFluid().getFluid() == fluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return tank.getFluid() == null || tank.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {
                tank.getInfo()
        };
    }

    public void syncWithAll() {
        if (!worldObj.isRemote) {
            PacketHandler.sendPacketToAllPlayers(getDescriptionPacket(), worldObj);
        }
    }

    public ItemStack getDropWithNBT() {
        NBTTagCompound tileEntity;
        ItemStack dropStack;

        tileEntity = new NBTTagCompound();
        dropStack = new ItemStack(ModBlocks.quantumFluidCache, 1);

        writeToNBTWithoutCoords(tileEntity);

        dropStack.setTagCompound(new NBTTagCompound());
        dropStack.getTagCompound().setTag("tileEntity", tileEntity);

        return dropStack;
    }

    @Override
    public void addWailaInfo(List<String> info) {
        if (tank.getFluid() != null) {
            info.add(tank.getFluid().getLocalizedName());
            info.add(tank.getFluidAmount() + "mb");
        } else {
            info.add("No Fluid Stored");
        }
    }
}
