package zachy.plethora.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import zachy.plethora.client.lib.LibResources;

public class ItemBlockMod extends ItemBlock {

    public ItemBlockMod(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack stack) {
        return getUnlocalizedNameInefficiently_(stack).replaceAll("tile.", "tile." + LibResources.PREFIX_MOD);
    }

    public String getUnlocalizedNameInefficiently_(ItemStack stack) {
        return super.getUnlocalizedNameInefficiently(stack);
    }
}
