package com.gp5.projettutore.game.render;

import java.util.ArrayList;

/**
 * Created by Valentin on 02/11/2015.
 */
public class Chunk
{
    private ArrayList<LevelElement> renderList = new ArrayList<LevelElement>();
    private int x;
    private int z;

    public Chunk(int x, int z)
    {
        this.x = x;
        this.z = z;
    }

    public int getZ()
    {
        return z;
    }

    public ArrayList<LevelElement> getRenderList()
    {
        return renderList;
    }

    public int getX()
    {
        return x;
    }
}
