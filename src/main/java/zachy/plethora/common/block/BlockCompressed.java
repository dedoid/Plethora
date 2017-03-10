package zachy.plethora.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zachy.plethora.common.item.block.ItemBlockCompressed;
import zachy.plethora.common.lib.LibBlockNames;

import java.util.List;

public class BlockCompressed extends BlockMod {

    String modID;
    String blockName;
    String textureName;

    IIcon[] icons;

    public BlockCompressed(String modID, String blockName, String textureName) {
        super(Material.rock);

        this.modID = modID;
        this.blockName = blockName;
        this.textureName = textureName;

        setResistance(25F); // temp value for now
        setStepSound(soundTypeStone);
        setBlockName(LibBlockNames.COMPRESSED + blockName);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);

        return (float) Math.pow((meta + 1), 2);
    }

    @Override
    public Block setBlockName(String name) {
        GameRegistry.registerBlock(this, ItemBlockCompressed.class, name);

        return super.setBlockName(name);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 8; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[8];

        for (int i = 0; i < 8; i++) {
            icons[i] = register.registerIcon(modID + ":" + textureName);
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[meta];
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        int meta = blockAccess.getBlockMetadata(x, y, z);

        return 0xe3e3e3 * (meta + 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(int meta) {
        return 0xe3e3e3 * (meta + 1);
    }
}
