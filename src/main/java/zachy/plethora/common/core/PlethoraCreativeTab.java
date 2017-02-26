package zachy.plethora.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zachy.plethora.client.lib.LibResources;
import zachy.plethora.common.lib.LibMisc;

public class PlethoraCreativeTab extends CreativeTabs {

    public static PlethoraCreativeTab INSTANCE = new PlethoraCreativeTab();

    public PlethoraCreativeTab() {
        super(LibMisc.MOD_ID);

        setBackgroundImageName(LibResources.GUI_CREATIVE);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(Items.cauldron);
    }

    @Override
    public Item getTabIconItem() {
        return getIconItemStack().getItem();
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
