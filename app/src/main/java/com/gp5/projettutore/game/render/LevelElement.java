package com.gp5.projettutore.game.render;

/**
 * Created by Valentin on 13/09/2015.
 */
public abstract class LevelElement implements Comparable<LevelElement>
{
    public abstract void draw();

    public abstract int getRenderID();

    public abstract boolean isOpaque();

    @Override
    public int compareTo(LevelElement another)
    {
        return Integer.valueOf(getRenderID()).compareTo(another.getRenderID());
    }
}
