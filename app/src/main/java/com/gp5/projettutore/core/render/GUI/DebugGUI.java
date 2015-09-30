package com.gp5.projettutore.core.render.GUI;

import com.gp5.projettutore.core.main.Core;
import com.gp5.projettutore.core.main.TimeUtil;
import com.gp5.projettutore.core.render.GameRenderer;
import com.gp5.projettutore.core.render.Textures;
import com.gp5.projettutore.core.render.shapes.Textured2DQuad;

public class DebugGUI extends GUI
{
    private Textured2DQuad pauseButton = new Textured2DQuad(Textures.guiTexture, 195, 90, 0, 0, 195, 90);
    private StringQuadList fps = FontRenderer.prepareStringRender("FPS: ☯2");
    private StringQuadList mapName = FontRenderer.prepareStringRender("Map : ");
    private StringQuadList renderListSize = FontRenderer.prepareStringRender("Triangles : ");

    @Override
    public void onClick(int mouseX, int mouseY)
    {
        if(mouseX < 195 && mouseY < 90)
        {
            GameRenderer.shouldRotate = !GameRenderer.shouldRotate;
        }
    }

    @Override
    public void drawGUI(float delta)
    {
        pauseButton.draw(0, 0);
        fps = FontRenderer.prepareStringRender("FPS: ☯2" + TimeUtil.getFps());
        fps.drawCentered(getWidth() / 2, 0);

        if(Core.instance.getCurrentLevel() != null)
        {
            mapName = FontRenderer.prepareStringRender("Map:☯3 " + Core.instance.getCurrentLevel().getName());
            mapName.drawRight(getWidth(), 0);
            renderListSize = FontRenderer.prepareStringRender("Triangles : ☯6" + Core.instance.getCurrentLevel().getRenderList().size() * 2);
            renderListSize.draw(0, getHeight() - 16);
        }
    }
}
