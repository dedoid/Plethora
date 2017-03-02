package zachy.plethora.common.core.util;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class FluidUtil {

    public static boolean drainContainers(IFluidHandler fluidHandler, IInventory inventory, int inputSlot, int outputSlot) {
        ItemStack input, output, emptyItem;
        FluidStack inContainer;
        int used;

        input = inventory.getStackInSlot(inputSlot);
        output = inventory.getStackInSlot(outputSlot);

        if (input != null) {
            inContainer = getFluidStackInContainer(input);
            emptyItem = input.getItem().getContainerItem(input);

            if (inContainer != null && (emptyItem == null || output == null || (output.stackSize < output.getMaxStackSize() && ItemUtil.isItemEqual(output, emptyItem, true, true)))) {
                used = fluidHandler.fill(null, inContainer, false);

                if (used >= inContainer.amount && fluidHandler.canFill(ForgeDirection.UP, inContainer.getFluid())) {
                    fluidHandler.fill(null, inContainer, true);

                    if (emptyItem != null) {
                        if (output == null) {
                            inventory.setInventorySlotContents(outputSlot, emptyItem);
                        } else {
                            output.stackSize++;
                        }

                        inventory.decrStackSize(inputSlot, 1);

                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean fillContainers(IFluidHandler fluidHandler, IInventory inventory, int inputSlot, int outputSlot, Fluid toFill) {
        ItemStack input, output, filled;
        FluidStack inContainer, drain;

        input = inventory.getStackInSlot(inputSlot);
        output = inventory.getStackInSlot(outputSlot);
        filled = getFilledContainer(toFill, input);

        if (filled != null && (output == null || (output.stackSize < output.getMaxStackSize() && ItemUtil.isItemEqual(filled, output, true, true)))) {
            inContainer = getFluidStackInContainer(filled);
            drain = fluidHandler.drain(null, inContainer, false);

            if (drain != null && drain.amount == inContainer.amount) {
                fluidHandler.drain(null, inContainer, true);

                if (output == null) {
                    inventory.setInventorySlotContents(outputSlot, filled);
                } else {
                    output.stackSize++;
                }

                inventory.decrStackSize(inputSlot, 1);

                return true;
            }
        }

        return false;
    }

    public static FluidStack getFluidStackInContainer(ItemStack stack) {
        return FluidContainerRegistry.getFluidForFilledItem(stack);
    }

    public static ItemStack getFilledContainer(Fluid fluid, ItemStack empty) {
        if (fluid == null || empty == null) {
            return null;
        }

        return FluidContainerRegistry.fillFluidContainer(new FluidStack(fluid, Integer.MAX_VALUE), empty);
    }

    public static FluidStack getFluidFromItem(ItemStack item) {
        FluidStack fluid;

        fluid = FluidContainerRegistry.getFluidForFilledItem(item);

        if (fluid == null) {
            if (item.getItem() == Items.water_bucket) {
                fluid = new FluidStack(FluidRegistry.WATER, 1000);
            } else if (item.getItem() == Items.lava_bucket) {
                fluid = new FluidStack(FluidRegistry.LAVA, 1000);
            }
        }

        return fluid;
    }

}
