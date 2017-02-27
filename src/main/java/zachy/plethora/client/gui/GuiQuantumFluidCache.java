package zachy.plethora.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
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
        int xStart, yStart;

        this.mc.getTextureManager().bindTexture(new ResourceLocation(LibResources.GUI_QUANTUM_FLUID_CACHE));

        xStart = (this.width - this.xSize) / 2;
        yStart = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(xStart, yStart, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String name;

        name = "tile.quantumFluidCache.name";

        this.fontRendererObj.drawString(StatCollector.translateToLocal(name), this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

        if (tileQuantumFluidCache.tank.getFluid() != null) {
            this.fontRendererObj.drawString(tileQuantumFluidCache.tank.getFluid().getLocalizedName(), 10, 20, 16448255);
            this.fontRendererObj.drawString(tileQuantumFluidCache.tank.getFluidAmount() + "mb", 10, 30, 16448255);
        }
    }
}
