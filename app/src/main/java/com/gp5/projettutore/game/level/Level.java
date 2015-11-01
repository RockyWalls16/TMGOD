package com.gp5.projettutore.game.level;

import com.gp5.projettutore.game.entity.Entity;
import com.gp5.projettutore.game.entity.EntityPlayer;
import com.gp5.projettutore.game.render.LevelElement;

import java.util.ArrayList;

/**
 * Level class store everythings for level logic and rendering
 */
public class Level
{
    private String mapName;
    private byte[][] mapData; //Map content (Walls, floor, Entities...)
    private int width;
    private int height;

    /**
     * Render list stores everything renderable in order to render everything at the same time. (Avoid lag)
     */
    private ArrayList<LevelElement> renderList;
    private ArrayList<Entity> entityList = new ArrayList<Entity>();

    private EntityPlayer player1;
    private EntityPlayer player2;

    public Level(String mapName, byte[][] mapData, int width, int height, int p1X, int p1Z, int p2X, int p2Z)
    {
        this.mapName = mapName;
        this.mapData = mapData;
        this.width = width;
        this.height = height;
        this.player1 = new EntityPlayer(this, p1X, p1Z);
        this.player2 = new EntityPlayer(this, p2X, p2Z);

        player1.spawnEntity();
        player2.spawnEntity();
    }

    public void tickLevel()
    {
        for(Entity entity : entityList)
        {
            entity.onUpdateTick();
        }
    }

    /**
     * Fetch the tile at x, z on a certain layer
     * @param layer layer of the tile (0 = Floor, 1 = Walls, 2 = Entities)
     * @param x of the tile
     * @param z of the tile
     * @return byte id of the tile
     */
    public byte getTileAt(int layer, int x, int z)
    {
        return x < 0 || z < 0 || x >= width || z >= height ? 0 : mapData[layer][x + z * width];
    }

    /**
     * Shortcut for getTileAt(int layer, int x, int z)
     */
    public byte getFloorAt(int x, int z)
    {
        return getTileAt(0, x, z);
    }
    /**
     * Shortcut for getTileAt(int layer, int x, int z)
     */
    public byte getWallAt(int x, int z)
    {
        return getTileAt(1, x, z);
    }

    /**
     * Fetch the width of the level (in tile)
     * @return width of the level
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Fetch the height of the level (in tile)
     * @return height of the level
     */
    public int getHeight()
    {
        return height;
    }

    public ArrayList<LevelElement> getRenderList()
    {
        return renderList;
    }

    /**
     * Init the render list. Must be done on level loading to create the appearance of the level
     */
    public void initRenderList()
    {
        renderList = LevelUtil.getRenderList(this);
    }

    public String getName()
    {
        return mapName;
    }

    public ArrayList<Entity> getEntityList()
    {
        return entityList;
    }

    public EntityPlayer getPlayer2()
    {
        return player2;
    }

    public EntityPlayer getPlayer1()
    {
        return player1;
    }
}
