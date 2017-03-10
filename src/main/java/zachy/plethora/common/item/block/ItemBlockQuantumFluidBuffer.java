package zachy.plethora.common.item.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockQuantumFluidBuffer extends ItemBlockMod {

    public ItemBlockQuantumFluidBuffer(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean show) {
        if (stack.getTagCompound() != null) {
            if (stack.getTagCompound().getString("fluid") != null) {
                tooltip.add(stack.getTagCompound().getString("fluid"));
            }

            if (stack.getTagCompound().getString("fluidAmount") != null) {
                tooltip.add(stack.getTagCompound().getString("fluidAmount"));
            }
        } else {
            tooltip.add("No Fluid Stored");
        }
    }
}
