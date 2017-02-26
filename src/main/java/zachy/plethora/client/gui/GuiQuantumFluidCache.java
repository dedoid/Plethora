package zachy.plethora.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import zachy.plethora.client.container.ContainerQuantumFluidCache;
import zachy.plethora.client.lib.LibResources;
import zachy.plethora.common.block.tile.TileQuantumFluidCache;

public class GuiQuantumFluidCache extends GuiContainer {

    private TileQuantumFluidCache tileQuantumFluidCache;

    public GuiQuantumFluidCache(EntityPlayer player, TileQuantumFluidCache tileQuantumFluidCache) {
        super(new ContainerQuantumFluidCache(player, tileQuantumFluidCache));

        this.xSize = 176;
        this.ySize = 167;
        this.tileQuantumFluidCache = tileQuantumFluidCache;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        int k, l;

        this.mc.getTextureManager().bindTexture(new ResourceLocation(LibResources.GUI_QUANTUM_FLUID_CACHE));

        k = (this.width - this.xSize) / 2;
        l = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String name;

    }
}
