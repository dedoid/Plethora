package zachy.plethora.client.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFake extends Slot {

    public boolean canInsert;
    public boolean canStack;
    public int maxStackSize = 127;

    public SlotFake(IInventory inventory, int x, int y, int z, boolean canInsert, boolean canStack, int maxStackSize) {
        super(inventory, x, y, z);

        this.canInsert = canInsert;
        this.canStack = canStack;
        this.maxStackSize = maxStackSize;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return this.canInsert;
    }

    @Override
    public int getSlotStackLimit() {
        return this.maxStackSize;
    }

    @Override
    public boolean getHasStack() {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int amt) {
        return !this.canStack ? null : super.decrStackSize(amt);
    }
}
