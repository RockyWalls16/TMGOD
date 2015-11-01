package com.gp5.projettutore.game.entity;

import com.gp5.projettutore.game.level.Level;

/**
 * Created by Valentin on 29/09/2015.
 */
public abstract class Entity
{
    private Level level;
    private float lastX;
    private float lastZ;
    protected float x;
    protected float z;

    private float motionX;
    private float motionZ;

    //Used only to fall
    private float lastY;
    private float y;

    public Entity(Level level, float x, float z)
    {
        this.level = level;
        this.x = x;
        this.z = z;
    }

    public void onUpdateTick()
    {
        lastX = x;
        lastY = y;
        lastZ = z;
    }

    public float getX()
    {
        return x;
    }

    public float getZ()
    {
        return z;
    }

    public float getY()
    {
        return y;
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

    public float getRenderY(float delta)
    {
        return (y - lastY) * delta + lastY;
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
}
