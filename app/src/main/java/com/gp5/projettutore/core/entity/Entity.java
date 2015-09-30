package com.gp5.projettutore.core.entity;

import com.gp5.projettutore.core.level.Level;

/**
 * Created by Valentin on 29/09/2015.
 */
public abstract class Entity
{
    private Level level;
    private float x;
    private float z;

    //Used only to fall
    private float yLayer;

    public Entity(Level level, float x, float z)
    {
        this.level = level;
        this.x = x;
        this.z = z;
    }

    public float getX()
    {
        return x;
    }

    public float getZ()
    {
        return z;
    }

    public float getYLayer()
    {
        return yLayer;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setZ(float z)
    {
        this.z = z;
    }
}
