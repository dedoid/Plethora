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
    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean show) {
        String name;

        name = stack.getDisplayName();
        name = name.substring(15, name.length() - 7).replaceAll("-", " ");

        tooltip.add((int) Math.pow(9, (stack.getItemDamage() + 1)) + " " + name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return block.getRenderColor(stack.getItemDamage());
    }
}
