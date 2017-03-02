package zachy.plethora.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import zachy.plethora.client.slot.SlotFake;
import zachy.plethora.client.slot.SlotFluid;
import zachy.plethora.client.slot.SlotOutput;
import zachy.plethora.common.block.tile.TileQuantumFluidBuffer;

public class ContainerQuantumFluidBuffer extends Container {

    EntityPlayer player;
    TileQuantumFluidBuffer tileQuantumFluidBuffer;

    public ContainerQuantumFluidBuffer(EntityPlayer player, TileQuantumFluidBuffer tileQuantumFluidBuffer) {
        this.player = player;
        this.tileQuantumFluidBuffer = tileQuantumFluidBuffer;

        this.addSlotToContainer(new SlotFluid(tileQuantumFluidBuffer.inventory, 0, 116, 17));
        this.addSlotToContainer(new SlotOutput(tileQuantumFluidBuffer.inventory, 1, 116, 53));
        this.addSlotToContainer(new SlotFake(tileQuantumFluidBuffer.inventory, 2, 95, 51, false, false, 1));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack original, inSlot;
        Slot slot;
        int slots;

        original = null;
        slot = (Slot) inventorySlots.get(index);
        slots = inventorySlots.size();

        if (slot != null && slot.getHasStack()) {
            inSlot = slot.getStack();
            original = inSlot.copy();

            if (index >= slots - 9 * 4 && tryShiftItem(inSlot, slots)) {
                // noop
            } else if (index >= slots - 9 * 4 && index < slots - 9) {
                if (!shiftStack(inSlot, slots - 9, slots)) {
                    return null;
                }
            } else if (index >= slots - 9 && index < slots) {
                if (!shiftStack(inSlot, slots - 9 * 4, slots - 9)) {
                    return null;
                }
            } else if (!shiftStack(inSlot, slots - 9 * 4, slots)) {
                return null;
            }

            slot.onSlotChange(inSlot, original);

            if (inSlot.stackSize <= 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (inSlot.stackSize == original.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, inSlot);
        }

        return original;
    }

    protected boolean shiftStack(ItemStack toShift, int start, int end) {
        boolean changed;
        Slot slot;
        ItemStack inSlot;
        int resultingSize, max;

        changed = false;

        if (toShift.isStackable()) {
            for (int i = start; toShift.stackSize > 0 && i < end; i++) {
                slot = (Slot) inventorySlots.get(i);
                inSlot = slot.getStack();

                if (inSlot != null && canStacksMerge(inSlot, toShift)) {
                    resultingSize = inSlot.stackSize + toShift.stackSize;
                    max = Math.min(toShift.getMaxStackSize(), slot.getSlotStackLimit());

                    if (resultingSize <= max) {
                        toShift.stackSize = 0;
                        inSlot.stackSize = resultingSize;
                        slot.onSlotChanged();
                        changed = true;
                    } else if (inSlot.stackSize < max) {
                        toShift.stackSize -= max - inSlot.stackSize;
                        inSlot.stackSize = max;
                        slot.onSlotChanged();
                        changed = true;
                    }
                }
            }
        }

        return changed;
    }

    private boolean tryShiftItem(ItemStack toShift, int slots) {
        Slot slot;

        for (int i = 0; i < slots - 9 * 4; i++) {
            slot = (Slot) inventorySlots.get(i);

            if (slot instanceof SlotFake) {
                continue;
            }

            if (!slot.isItemValid(toShift)) {
                continue;
            }

            if (shiftStack(toShift, i, i + 1)) {
                return true;
            }
        }

        return false;
    }

    public static boolean canStacksMerge(ItemStack stackA, ItemStack stackB) {
        if (stackA == null || stackB == null) {
            return false;
        }

        if (!stackA.isItemEqual(stackB)) {
            return false;
        }

        if (!ItemStack.areItemStackTagsEqual(stackA, stackB)) {
            return false;
        }

        return true;
    }
}
