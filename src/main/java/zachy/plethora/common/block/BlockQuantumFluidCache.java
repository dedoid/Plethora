package zachy.plethora.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zachy.plethora.client.core.handler.GuiHandler;
import zachy.plethora.client.lib.LibResources;
import zachy.plethora.common.Plethora;
import zachy.plethora.common.block.tile.TileQuantumFluidCache;
import zachy.plethora.common.lib.LibBlockNames;

public class BlockQuantumFluidCache extends BlockModContainer {

    IIcon iconFront;

    public BlockQuantumFluidCache() {
        super(Material.iron);

        setHardness(2);
        setBlockName(LibBlockNames.QUANTUM_FLUID_CACHE);
    }

    @Override
    public Block setBlockName(String name) {
        GameRegistry.registerBlock(this, name);

        return super.setBlockName(name);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        iconFront = register.registerIcon(LibResources.PREFIX_MOD + "quantumFluidCache_front");
        blockIcon = register.registerIcon(LibResources.PREFIX_MOD + "quantumFluidCache_icon");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 2 && meta == 2) {
            return iconFront;
        } else if (side == 5 && meta == 3) {
            return iconFront;
        } else if (side == 3 && meta == 0) {
            return iconFront;
        } else if (side == 4 && meta == 1) {
            return iconFront;
        } else {
            return blockIcon;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileQuantumFluidCache();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float offsetX, float offsetY, float offsetZ) {
        if (!player.isSneaking()) {

            if (!fillBlockWithFluid(world, x, y, z, player, player.getHeldItem())) {
                player.openGui(Plethora.instance, GuiHandler.quantumFluidCache, world, x, y, z);
            }

            return true;
        }

        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        TileEntity tileEntity;
        int facing;

        tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileQuantumFluidCache) {

            if (stack.getTagCompound() != null) {
                ((TileQuantumFluidCache) tileEntity).readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
            }
        }

        facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tileEntity;
        float xOffset, yOffset, zOffset;
        ItemStack stackNBT, stack;
        int amountToDrop;
        EntityItem entityItem;

        tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileQuantumFluidCache) {

            xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
            yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
            zOffset = world.rand.nextFloat() * 0.8F + 0.1F;

            if (((TileQuantumFluidCache) tileEntity).tank.getFluid() != null) {
                stackNBT = ((TileQuantumFluidCache) tileEntity).getDropWithNBT();

                amountToDrop = Math.min(world.rand.nextInt(21) + 10, stackNBT.stackSize);

                entityItem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, stackNBT.splitStack(amountToDrop));

                world.spawnEntityInWorld(entityItem);
            } else {
                stack = new ItemStack(ModBlocks.quantumFluidCache);

                entityItem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, stack);

                world.spawnEntityInWorld(entityItem);
            }
        }
    }

    public boolean fillBlockWithFluid(World world, int x, int y, int z, EntityPlayer player, ItemStack heldItem) {
        TileEntity tileEntity;
        boolean inserted;

        tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileQuantumFluidCache) {

            //inserted = FluidUtils.

            if (!world.isRemote) {
                ((TileQuantumFluidCache) tileEntity).syncWithAll();
            }

            //return inserted;
        }

        return false;
    }
}
