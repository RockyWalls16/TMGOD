package com.gp5.projettutore.game.entity;

import com.gp5.projettutore.game.control.JoyStick;
import com.gp5.projettutore.game.level.Level;
import com.gp5.projettutore.game.render.GameRenderer;
import com.gp5.projettutore.game.render.SpriteAtlas;
import com.gp5.projettutore.game.render.Textures;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class EntityPlayer extends Entity
{
    private static final SpriteAtlas SPRITE_ATLAS = new SpriteAtlas(Textures.cellingTexture, 32, 32, 4, 3, 1, 1);

    public EntityPlayer(Level level, float x, float z)
    {
        super(level, x, z);
    }

    public void onUpdateTick()
    {
        super.onUpdateTick();
        x = body.getPosition().x;
        z = body.getPosition().y;
    }

    public void userControlTick()
    {
        if(JoyStick.getForce() > 40)
        {
            GameRenderer.instance.setScaleAmount(0);
            float fX = (float) Math.sin(Math.toRadians(JoyStick.getAngle() - GameRenderer.instance.getRotation())) * -8;
            float fZ = (float) Math.cos(Math.toRadians(JoyStick.getAngle() - GameRenderer.instance.getRotation())) * -8;
            body.setLinearVelocity(new Vec2(fX, fZ));
        }
        else
        {
            body.setLinearVelocity(new Vec2(0, 0));
        }
    }

    public void render(float delta)
    {
        SPRITE_ATLAS.renderFrame(getRenderX(delta), getRenderZ(delta));
    }
}
