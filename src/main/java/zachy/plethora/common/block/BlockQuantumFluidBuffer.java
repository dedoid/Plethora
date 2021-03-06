package zachy.plethora.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import zachy.plethora.client.lib.LibGuiIDs;
import zachy.plethora.client.lib.LibResources;
import zachy.plethora.common.Plethora;
import zachy.plethora.common.block.tile.TileQuantumFluidBuffer;
import zachy.plethora.common.core.util.FluidUtil;
import zachy.plethora.common.core.util.ItemUtil;
import zachy.plethora.common.item.block.ItemBlockQuantumFluidBuffer;
import zachy.plethora.common.lib.LibBlockNames;

public class BlockQuantumFluidBuffer extends BlockModContainer {

    IIcon iconFront;

    public BlockQuantumFluidBuffer() {
        super(Material.iron);

        setHardness(2);
        setBlockName(LibBlockNames.QUANTUM_FLUID_BUFFER);
    }

    @Override
    public Block setBlockName(String name) {
        GameRegistry.registerBlock(this, ItemBlockQuantumFluidBuffer.class, name);

        return super.setBlockName(name);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        iconFront = register.registerIcon(LibResources.PREFIX_MOD + LibBlockNames.QUANTUM_FLUID_BUFFER + "_front");
        blockIcon = register.registerIcon(LibResources.PREFIX_MOD + LibBlockNames.QUANTUM_FLUID_BUFFER + "_icon");
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
        return new TileQuantumFluidBuffer();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float offsetX, float offsetY, float offsetZ) {
        if (!player.isSneaking()) {

            if (!fillBlockWithFluid(world, x, y, z, player, player.getHeldItem()) && !emptyBlockFromFluid(world, x, y, z, player, player.getHeldItem())) {
                player.openGui(Plethora.instance, LibGuiIDs.QUANTUM_FLUID_BUFFER, world, x, y, z);
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

        if (tileEntity instanceof TileQuantumFluidBuffer) {

            if (stack.getTagCompound() != null) {
                //((TileQuantumFluidBuffer) tileEntity).readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
                tileEntity.readFromNBT(stack.getTagCompound().getCompoundTag("tileEntity"));
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

        if (tileEntity instanceof TileQuantumFluidBuffer) {

            xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
            yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
            zOffset = world.rand.nextFloat() * 0.8F + 0.1F;

            if (((TileQuantumFluidBuffer) tileEntity).tank.getFluid() != null) {
                stackNBT = ((TileQuantumFluidBuffer) tileEntity).getDropWithNBT();

                amountToDrop = Math.min(world.rand.nextInt(21) + 10, stackNBT.stackSize);

                entityItem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, stackNBT.splitStack(amountToDrop));

                world.spawnEntityInWorld(entityItem);
            } else {
                stack = new ItemStack(ModBlocks.quantumFluidBuffer);

                entityItem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, stack);

                world.spawnEntityInWorld(entityItem);
            }
        }
    }

    public boolean fillBlockWithFluid(World world, int x, int y, int z, EntityPlayer player, ItemStack held) {
        TileEntity tileEntity;
        FluidStack fluid;
        int filled;

        tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileQuantumFluidBuffer) {

            if (held != null) {
                fluid = FluidUtil.getFluidFromItem(held);
                if (fluid != null) {
                    filled = ((TileQuantumFluidBuffer) tileEntity).fill(ForgeDirection.UP, fluid, false);

                    if (filled >= fluid.amount) {
                        ((TileQuantumFluidBuffer) tileEntity).fill(ForgeDirection.UP, fluid, true);

                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemUtil.consumeItem(held));
                        }

                        return true;
                    }
                }
            }

            if (!world.isRemote) {
                ((TileQuantumFluidBuffer) tileEntity).syncWithAll();
            }
        }

        return false;
    }

    public boolean emptyBlockFromFluid(World world, int x, int y, int z, EntityPlayer player, ItemStack held) {
        TileEntity tileEntity;
        FluidStack available, filled;
        ItemStack stack;
        FluidContainerRegistry.FluidContainerData[] datas;

        tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileQuantumFluidBuffer) {

            available = ((TileQuantumFluidBuffer) tileEntity).tank.getFluid();
            if (available != null) {
                stack = FluidContainerRegistry.fillFluidContainer(available.copy(), held);
                filled = FluidContainerRegistry.getFluidForFilledItem(stack);

                if (filled == null) {
                    datas = FluidContainerRegistry.getRegisteredFluidContainerData();
                    for (FluidContainerRegistry.FluidContainerData data : datas) {
                        if (data.fluid.getFluid().getName().equals(available.getFluid().getName()) && data.emptyContainer.isItemEqual(held)) {
                            stack = data.filledContainer.copy();
                            filled = FluidContainerRegistry.getFluidForFilledItem(stack);
                        }
                    }
                }

                if (filled != null) {
                    ((TileQuantumFluidBuffer) tileEntity).drain(ForgeDirection.DOWN, filled, true);

                    if (held.stackSize > 1) {
                        held.stackSize--;

                        player.inventory.setInventorySlotContents(player.inventory.currentItem, held);

                        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                            if (player.inventory.mainInventory[i] == null) {
                                player.inventory.setInventorySlotContents(i, stack);
                                return true;
                            }
                        }

                        if (!world.isRemote) {
                            ItemUtil.dropItems(world, stack, x, y, z);
                            ((TileQuantumFluidBuffer) tileEntity).syncWithAll();
                        }
                    } else {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
                    }

                    return true;
                }
            }
        }

        return false;
    }
}
