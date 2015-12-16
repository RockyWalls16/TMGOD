package com.gp5.projettutore.game.entity;

import com.gp5.projettutore.game.control.EnumDirection;
import com.gp5.projettutore.game.level.Level;
import com.gp5.projettutore.game.main.Core;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public abstract class Entity
{
    private Level level;
    private float lastX;
    private float lastZ;
    private float nextX;
    private float nextZ;//Used for warp
    private boolean warped = false;
    protected float x;
    protected float z;

    protected Body body;

    private EnumDirection direction = EnumDirection.NORTH;

    public Entity(Level level, float x, float z)
    {
        this.level = level;
        this.x = x;
        this.z = z;

        createBox();
    }

    public void onUpdateTick()
    {
        lastX = x;
        lastZ = z;

        if(!warped)
        {
            x = body.getPosition().x;
            z = body.getPosition().y;
        }
        else
        {
            warped = false;
            x = nextX;
            z = nextZ;

            this.body.setTransform(new Vec2(x, z), body.getAngle());
        }
    }

    public float getX()
    {
        return x;
    }

    public float getZ()
    {
        return z;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public float getRenderX(float delta)
    {
        return (x - lastX) * delta + lastX;
    }

    public float getRenderZ(float delta)
    {
        return (z - lastZ) * delta + lastZ;
    }

    public Level getLevel()
    {
        return level;
    }

    public void spawnEntity()
    {
        level.getEntityList().add(this);
    }

    public void render(float delta){}

    public void createBox()
    {
        BodyDef boxDef = new BodyDef();
        boxDef.position.set(x, z);
        boxDef.type = BodyType.DYNAMIC;
        CircleShape boxShape = new CircleShape();
        boxShape.m_radius = 0.4F;
        body = level.getWorld().createBody(boxDef);
        FixtureDef boxFixture = new FixtureDef();
        boxFixture.filter.categoryBits = 0x0003;
        boxFixture.filter.maskBits = 0x0001 | 0x0002;
        boxFixture.density = 0.1f;
        boxFixture.shape = boxShape;
        body.createFixture(boxFixture);
    }

    public void warp(float x, float z)
    {
        this.x = x;
        this.z = z;
        this.lastX = x;
        this.lastZ = z;
        this.nextX = x;
        this.nextZ = z;
        this.warped = true;
        this.body.setTransform(new Vec2(x, z), body.getAngle());
    }

    public EnumDirection getDirection()
    {
        return direction;
    }

    public void setDirection(EnumDirection direction)
    {
        this.direction = direction;
    }

    public EnumDirection getDirectionForRender()
    {
        return EnumDirection.values()[((direction.ordinal()) + (int) (Core.rotateAngle / 2)) % 4];
    }
}
