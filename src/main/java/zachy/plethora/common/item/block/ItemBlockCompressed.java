package zachy.plethora.common.item.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockCompressed extends ItemBlockWithMetadataAndName {

    Block block;

    public ItemBlockCompressed(Block block) {
        super(block);

        this.block = block;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return block.getRenderColor(stack.getItemDamage());
    }
}
