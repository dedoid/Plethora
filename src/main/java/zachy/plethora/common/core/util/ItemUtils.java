package zachy.plethora.common.core.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtils {

    public static boolean isItemEqual(final ItemStack stackA, final ItemStack stackB, final boolean matchDamage, final boolean matchNBT) {
        if (stackA == null || stackB == null) {
            return false;
        }

        if (stackA.getItem() != stackB.getItem()) {
            return false;
        }

        if (matchNBT && !ItemStack.areItemStackTagsEqual(stackA, stackB)) {
            return false;
        }

        if (matchDamage && stackA.getHasSubtypes()) {
            if (isWildCard(stackA) || isWildCard(stackB)) {
                return true;
            }

            if (stackA.getItemDamage() != stackB.getItemDamage()) {
                return false;
            }
        }

        return true;
    }

    public static boolean isItemEqual(ItemStack stackA, ItemStack stackB, boolean matchDamage, boolean matchNBT, boolean useOreDict) {
        if (isItemEqual(stackA, stackB, matchDamage, matchNBT)) {
            return true;
        }

        if (stackA == null || stackB == null) {
            return false;
        }

        if (useOreDict) {
            for (int i : OreDictionary.getOreIDs(stackA)) {
                for (int j : OreDictionary.getOreIDs(stackB)) {
                    if (i == j) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean isWildCard(ItemStack stack) {
        return isWildCard(stack.getItemDamage());
    }

    public static boolean isWildCard(int damage) {
        return damage == -1 || damage == OreDictionary.WILDCARD_VALUE;
    }

    public static void writeInvToNBT(IInventory inventory, String tag, NBTTagCompound data) {
        NBTTagList list;
        ItemStack stack;
        NBTTagCompound itemTag;

        list = new NBTTagList();

        for (byte i = 0; i < inventory.getSizeInventory(); i++) {
            stack = inventory.getStackInSlot(i);

            if (stack != null) {
                itemTag = new NBTTagCompound();

                itemTag.setByte("Slot", i);
                writeItemToNBT(stack, itemTag);
                list.appendTag(itemTag);
            }
        }

        data.setTag(tag, list);
    }

    public static void readInvFromNBT(IInventory inventory, String tag, NBTTagCompound data) {
        NBTTagList list;
        NBTTagCompound itemTag;
        int slot;
        ItemStack stack;

        list = data.getTagList(tag, 10);
        for (byte i = 0; i < list.tagCount(); i++) {
            itemTag = list.getCompoundTagAt(i);

            slot = itemTag.getByte("Slot");
            if (slot >= 0 && slot < inventory.getSizeInventory()) {
                stack = readItemFromNBT(itemTag);
                inventory.setInventorySlotContents(slot, stack);
            }
        }
    }

    public static void writeItemToNBT(ItemStack stack, NBTTagCompound data) {
        if (stack == null || stack.stackSize <= 0) {
            return;
        }

        if (stack.stackSize > 127) {
            stack.stackSize = 127;
        }

        stack.writeToNBT(data);
    }

    public static ItemStack readItemFromNBT(NBTTagCompound data) {
        return ItemStack.loadItemStackFromNBT(data);
    }
}
