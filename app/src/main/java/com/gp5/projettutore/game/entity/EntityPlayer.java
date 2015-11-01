package com.gp5.projettutore.game.entity;

import android.util.Log;

import com.gp5.projettutore.game.control.EnumDirection;
import com.gp5.projettutore.game.control.JoyStick;
import com.gp5.projettutore.game.level.Level;
import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.render.SpriteAtlas;
import com.gp5.projettutore.game.render.Textures;

/**
 * Created by Valentin on 04/10/2015.
 */
public class EntityPlayer extends Entity
{
    private static final SpriteAtlas SPRITE_ATLAS = new SpriteAtlas(Textures.playerTexture, 32, 32, 4, 3, 1, 1);

    public EntityPlayer(Level level, float x, float z)
    {
        super(level, x, z);
    }

    public void onUpdateTick()
    {
        super.onUpdateTick();
    }

    public void userControlTick()
    {
        EnumDirection direction = JoyStick.getJoystickDirection();

        if(direction != null)
        {
            x += EnumDirection.values()[(direction.ordinal() + Core.rotateAngle) % 8].getX() * 0.2F;
            z += EnumDirection.values()[(direction.ordinal() + Core.rotateAngle) % 8].getY() * 0.2F;
        }
    }

    public void render(float delta)
    {
        SPRITE_ATLAS.renderFrame(getRenderX(delta), getRenderZ(delta));
    }
}
