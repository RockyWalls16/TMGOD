package com.gp5.projettutore.game.render.GUI;

import com.gp5.projettutore.game.control.JoyStick;
import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.main.TimeUtil;
import com.gp5.projettutore.game.render.GameRenderer;
import com.gp5.projettutore.game.render.Texture;
import com.gp5.projettutore.game.render.shapes.Textured2DQuad;

public class IngameGUI extends GUI
{
    private Textured2DQuad joystick = new Textured2DQuad(Texture.joystickTexture, 200, 200, 0, 0, 200, 200);
    private Textured2DQuad joystickTop = new Textured2DQuad(Texture.joystickTexture, 60, 60, 196, 196, 256, 256);
    private Textured2DQuad rotateButton = new Textured2DQuad(Texture.guiTexture, 96, 96, 0, 0, 96, 96);
    private Textured2DQuad portalButton = new Textured2DQuad(Texture.guiTexture, 96, 96, 0, 160, 96, 256);
    private Textured2DQuad[] portalColor = new Textured2DQuad[]{new Textured2DQuad(Texture.guiTexture, 32, 32, 96, 224, 128, 256), new Textured2DQuad(Texture.guiTexture, 32, 32, 128, 224, 160, 256), new Textured2DQuad(Texture.guiTexture, 32, 32, 160, 224, 192, 256), new Textured2DQuad(Texture.guiTexture, 32, 32, 192, 224, 224, 256)};
    private Textured2DQuad eye = new Textured2DQuad(Texture.guiTexture, 35, 32, 0, 96, 34, 127);
    private StringQuadList fps = FontRenderer.prepareStringRender("FPS: ☯2");
    private StringQuadList mapName = FontRenderer.prepareStringRender("Map : ");

    private boolean joystickLocked = false;
    private int joystickX;
    private int joystickY;
    private int joystickHalfRadius = 30;

    @Override
    public boolean onClick(int mouseX, int mouseY, int type)
    {
        if(type == 0 && mouseX > getWidth() - 98 && mouseY > getHeight() - 98)
        {
            Core.rotateAngle = (Core.rotateAngle + 1) % 8;

            return true;
        }
        else if(type == 0 && mouseX > getWidth() - 98 && mouseY > getHeight() - 196 && mouseY < getHeight() - 98)
        {
            Core.instance.getPlayer().togglePortal();

            return true;
        }
        else if(type == 0 && mouseX > getWidth() - 196 && mouseY > getHeight() - 98 && mouseX < getWidth() - 98)
        {
            if(GameRenderer.instance.getCamera() == Core.instance.getPlayer() && !GameRenderer.instance.isFirstPerson())
            {
                GameRenderer.instance.setCamera(Core.instance.getFriend());
            }
            else if(GameRenderer.instance.getCamera() == Core.instance.getFriend())
            {
                GameRenderer.instance.setCamera(Core.instance.getPlayer());
                GameRenderer.instance.setFirstPerson(true);
            }
            else
            {
                GameRenderer.instance.setFirstPerson(false);
            }
            return true;
        }
        else if(type == 0 && mouseX < 250 && mouseY < 90)
        {
            Core.instance.getPlayer().setIsAWizard(!Core.instance.getPlayer().isAWizard());
            Core.instance.getFriend().setIsAWizard(!Core.instance.getFriend().isAWizard());

            return true;
        }
        else if(type == 2 && joystickLocked)
        {
            mouseX = Math.max(10, Math.min(210, mouseX));
            mouseY = Math.max(getHeight() - 210, Math.min(getHeight() - 10, mouseY));

            float jX = ((float) (mouseX - 10) / 200F) * 2F - 1F;
            float jY = ((float) (mouseY - getHeight() + 210) / 200F) * 2F - 1F;
            joystickX = (int) (Math.sin(jX) * Math.cos(jY) * 115);
            joystickY = (int) (Math.cos(jX) * Math.sin(jY) * 115);
            JoyStick.calculateDirection(jX, jY);

            return true;
        }
        else if(type == 1)
        {
            joystickLocked = false;
            joystickX = 0;
            joystickY = 0;
            JoyStick.calculateDirection(0, 0);
        }
        else if(type == 0 && mouseX < 210 && mouseY > getHeight() - 210)
        {
            joystickLocked = true;
            onClick(mouseX, mouseY, 2);

            return true;
        }
        return false;
    }

    @Override
    public void drawGUI(float delta)
    {
        rotateButton.draw(getWidth() - 98, getHeight() - 98);
        portalButton.draw(getWidth() - 98, getHeight() - 196);
        portalButton.draw(getWidth() - 98, getHeight() - 196);
        portalButton.draw(getWidth() - 196, getHeight() - 98);
        eye.draw(getWidth() - 166, getHeight() - 65);
        portalColor[Core.instance.getPlayer().getPortalColor()].draw(getWidth() - 65, getHeight() - 163);
        fps = FontRenderer.prepareStringRender("FPS: ☯2" + TimeUtil.getFps());
        fps.draw(0, 0);

        if(Core.instance.getCurrentLevel() != null)
        {
            mapName = FontRenderer.prepareStringRender("Map:☯3 " + Core.instance.getCurrentLevel().getName());
            mapName.drawRight(getWidth(), 0);
            joystick.draw(10, getHeight() - 210);
            joystickTop.draw(joystickX + 110 - joystickHalfRadius, getHeight() - 110 + joystickY - joystickHalfRadius);
        }
    }
}
