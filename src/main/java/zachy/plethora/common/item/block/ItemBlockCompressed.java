package zachy.plethora.common.item.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockCompressed extends ItemBlockWithMetadataAndName {

    Block block;

    public ItemBlockCompressed(Block block) {
        super(block);

        this.block = block;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List toolTip, boolean b) {
        toolTip.add((int) Math.pow(9, (stack.getItemDamage() + 1)) + " " + stack.getDisplayName());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return block.getRenderColor(stack.getItemDamage());
    }
}
