package zachy.plethora.client.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;

public class SlotFluid extends Slot {

    public SlotFluid(IInventory inventory, int x, int y, int z) {
        super(inventory, x, y, z);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return FluidContainerRegistry.isContainer(stack) || (stack != null && stack.getItem() instanceof IFluidContainerItem);
    }
}
