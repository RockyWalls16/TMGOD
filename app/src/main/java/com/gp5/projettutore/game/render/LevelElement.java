package com.gp5.projettutore.game.render;

/**
 * Created by Valentin on 13/09/2015.
 */
public abstract class LevelElement implements Comparable<LevelElement>
{
    public abstract void draw();

    public abstract int getRenderID();

    @Override
    public int compareTo(LevelElement another)
    {
        return Integer.valueOf(another.getRenderID()).compareTo(getRenderID());
    }
}
