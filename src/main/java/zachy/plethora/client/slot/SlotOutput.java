package zachy.plethora.client.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot {

    public SlotOutput(IInventory inventory, int x, int y, int z) {
        super(inventory, x, y, z);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 64;
    }
}
