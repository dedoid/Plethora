package zachy.plethora.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import zachy.plethora.client.container.ContainerQuantumFluidBuffer;
import zachy.plethora.client.lib.LibResources;
import zachy.plethora.common.block.tile.TileQuantumFluidBuffer;
import zachy.plethora.common.lib.LibBlockNames;

public class GuiQuantumFluidBuffer extends GuiContainer {

    private TileQuantumFluidBuffer tileQuantumFluidBuffer;

    public GuiQuantumFluidBuffer(EntityPlayer player, TileQuantumFluidBuffer tileQuantumFluidBuffer) {
        super(new ContainerQuantumFluidBuffer(player, tileQuantumFluidBuffer));

        this.xSize = 176;
        this.ySize = 167;
        this.tileQuantumFluidBuffer = tileQuantumFluidBuffer;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        int xStart, yStart;

        this.mc.getTextureManager().bindTexture(new ResourceLocation(LibResources.GUI_QUANTUM_FLUID_BUFFER));

        xStart = (this.width - this.xSize) / 2;
        yStart = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(xStart, yStart, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String name;

        name = "tile." + LibBlockNames.QUANTUM_FLUID_BUFFER +".name";

        this.fontRendererObj.drawString(StatCollector.translateToLocal(name), this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

        if (tileQuantumFluidBuffer.tank.getFluid() != null) {
            this.fontRendererObj.drawString(tileQuantumFluidBuffer.tank.getFluid().getLocalizedName(), 10, 20, 16448255);
            this.fontRendererObj.drawString(tileQuantumFluidBuffer.tank.getFluidAmount() + "mb", 10, 30, 16448255);
        } else {
            this.fontRendererObj.drawString("No Fluid Stored", 10, 20, 16448255);
        }
    }
}
