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
    private Textured2DQuad pauseButton = new Textured2DQuad(Texture.guiTexture, 195, 90, 0, 0, 195, 90);
    private StringQuadList fps = FontRenderer.prepareStringRender("FPS: ☯2");
    private StringQuadList mapName = FontRenderer.prepareStringRender("Map : ");

    private boolean joystickLocked = false;
    private int joystickX;
    private int joystickY;
    private int joystickHalfRadius = 30;

    @Override
    public void onClick(int mouseX, int mouseY, int type)
    {
        if(type == 0 && mouseX < 195 && mouseY < 90)
        {
            Core.rotateAngle = (Core.rotateAngle + 1) % 8;
        }
        else if(type == 0 && mouseX < 250 && mouseY < 90)
        {
            Core.instance.getPlayer().setIsAWizard(!Core.instance.getPlayer().isAWizard());
            Core.instance.getFriend().setIsAWizard(!Core.instance.getFriend().isAWizard());
        }
        else if(type == 0 && mouseX < 305 && mouseY < 90)
        {
            GameRenderer.instance.setCamera(GameRenderer.instance.getCamera() == Core.instance.getPlayer() ? Core.instance.getFriend() : Core.instance.getPlayer());
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
        }
        else if(type == 0)
        {
            float deltaX = mouseX - GameRenderer.instance.getWidth() / 2F;
            float deltaY = mouseY - GameRenderer.instance.getHeight() / 2F;

            float angle = (float) (Math.atan2(deltaY, deltaX) * 180 / Math.PI); // Calculate the angle from x, y

            if(Core.instance.getPlayer() != null)
            {
                Core.instance.getPlayer().shoot(-angle - 90F - GameRenderer.instance.getRotation());
            }
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
            joystick.draw(10, getHeight() - 210);
            joystickTop.draw(joystickX + 110 - joystickHalfRadius, getHeight() - 110 + joystickY - joystickHalfRadius);
        }
    }
}
