package zachy.plethora.common.core.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class Inventory implements IInventory {

    private final ItemStack[] contents;
    private final String name;
    private final int limit;
    private TileEntity tileEntity;
    public boolean changed = false;

    public Inventory(int size, String name, int limit, TileEntity tileEntity) {
        contents = new ItemStack[size];
        this.name = name;
        this.limit = limit;
        this.tileEntity = tileEntity;
    }

    @Override
    public int getSizeInventory() {
        return contents.length;
    }

    @Override
    public ItemStack getStackInSlot(int ID) {
        return contents[ID];
    }

    @Override
    public ItemStack decrStackSize(int ID, int count) {
        ItemStack result, stack;

        if (ID < contents.length && contents[ID] != null) {
            if (contents[ID].stackSize > count) {
                result = contents[ID].splitStack(count);
                markDirty();
                changed = true;
                return result;
            }

            stack = contents[ID];
            setInventorySlotContents(ID, null);
            changed = true;
            return stack;
        }

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int ID) {
        return null;
        //return contents[ID];
    }

    @Override
    public void setInventorySlotContents(int ID, ItemStack stack) {
        if (ID >= contents.length) {
            return;
        }

        contents[ID] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        markDirty();
        changed = true;
    }

    @Override
    public String getInventoryName() {
        return name;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return limit;
    }

    @Override
    public void markDirty() {
        tileEntity.markDirty();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        return true;
    }

    public void readFromNBT(NBTTagCompound data) {
        readFromNBT(data, "Items");
    }

    public void readFromNBT(NBTTagCompound data, String tag) {
        NBTTagList nbtTagList;
        NBTTagCompound slot;
        int index;

        nbtTagList = data.getTagList(tag, Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            slot = nbtTagList.getCompoundTagAt(i);

            if (slot.hasKey("index")) {
                index = slot.getInteger("index");
            } else {
                index = slot.getByte("Slot");
            }

            if (index >= 0 && index < contents.length) {
                setInventorySlotContents(index, ItemStack.loadItemStackFromNBT(slot));
            }
        }

        changed = true;
    }

    public void writeToNBT(NBTTagCompound data) {
        writeToNBT(data, "Items");
    }

    public void writeToNBT(NBTTagCompound data, String tag) {
        NBTTagList slots;
        NBTTagCompound slot;

        slots = new NBTTagList();

        for (byte i = 0; i < contents.length; i++) {
            if (contents[i] != null && contents[i].stackSize > 0) {
                slot = new NBTTagCompound();
                slots.appendTag(slot);
                slot.setByte("Slot", i);
                contents[i].writeToNBT(slot);
            }
        }

        data.setTag(tag, slots);
    }

    public void setTileEntity(TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    public ItemStack[] getStacks() {
        return contents;
    }
}
