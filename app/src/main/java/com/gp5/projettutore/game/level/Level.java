package com.gp5.projettutore.game.level;

import com.gp5.projettutore.game.entity.Entity;
import com.gp5.projettutore.game.entity.EntityPlayer;
import com.gp5.projettutore.game.entity.EntityPortalShoot;
import com.gp5.projettutore.game.render.Chunk;
import com.gp5.projettutore.game.render.GameRenderer;
import com.gp5.projettutore.game.render.shapes.Outline;
import com.gp5.projettutore.game.render.shapes.Wall;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Level class store everythings for level logic and rendering
 */
public class Level implements ContactListener
{
    private String mapName;
    private byte[][] mapData; //Map content (Walls, floor, Entities...)
    private int width;
    private int height;

    private Chunk[][] chunks;
    private ArrayList<Entity> entityList = new ArrayList<Entity>();

    private EntityPlayer player1;
    private EntityPlayer player2;
    private Outline[] levelOutline;

    private World world = new World(new Vec2(0.0F, 0.0F), false);

    private LinkedList<Body> bodyRemoveQueue = new LinkedList<Body>();

    public Level(String mapName, byte[][] mapData, int width, int height, int p1X, int p1Z, int p2X, int p2Z)
    {
        this.mapName = mapName;
        this.mapData = mapData;
        this.width = width;
        this.height = height;
        chunks = new Chunk[getChunkArrayWidth()][getChunkArrayHeight()];

        for(int x = 0; x < getChunkArrayWidth();x++)
        {
            for(int z = 0; z < getChunkArrayHeight();z++)
            {
                chunks[x][z] = new Chunk(x, z);
            }
        }

        levelOutline = new Outline[]{new Outline(this, 0), new Outline(this, 1), new Outline(this, 2), new Outline(this, 3)};

        this.player1 = new EntityPlayer(this, p1X, p1Z, true);
        this.player2 = new EntityPlayer(this, p2X, p2Z, false);

        getWorld().setContactListener(this);
    }

    public void tickLevel()
    {
        for(Body body : bodyRemoveQueue)
        {
            getWorld().destroyBody(body);
        }
        bodyRemoveQueue.clear();

        for(Entity entity : entityList)
        {
            entity.onUpdateTick();
        }
        world.step(1 / 60F, 8, 3);
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
        return x < 0 || z < 0 || x >= width || z >= height ? -1 : mapData[layer][x + z * width];
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

    public int getChunkArrayWidth()
    {
        return (getWidth() >> 2) + 1;
    }

    public int getChunkArrayHeight()
    {
        return (getHeight() >> 2) + 1;
    }

    public void initChunks()
    {
        LevelUtil.getRenderList(this);
    }

    public Chunk[][] getChunks()
    {
        return chunks;
    }

    public Outline[] getLevelOutline()
    {
        return levelOutline;
    }

    public String getName()
    {
        return mapName;
    }

    public ArrayList<Entity> getEntityList()
    {
        return entityList;
    }

    public void initEntityList(ArrayList<Entity> entityList)
    {
        player1.spawnEntity();
        player2.spawnEntity();
        GameRenderer.instance.setCamera(player1);

        for(Entity entity : entityList)
        {
            entity.spawnEntity();
        }
    }

    public EntityPlayer getPlayer2()
    {
        return player2;
    }

    public EntityPlayer getPlayer1()
    {
        return player1;
    }

    public World getWorld()
    {
        return world;
    }

    public EntityPlayer getWizard()
    {
        return getPlayer1().isAWizard() ? getPlayer1() : getPlayer2();
    }

    public EntityPlayer getFairy()
    {
        return !getPlayer1().isAWizard() ? getPlayer1() : getPlayer2();
    }


    @Override
    public void beginContact(Contact contact)
    {
        if(contact.isTouching())
        {
            if(contact.getFixtureA().m_userData instanceof EntityPortalShoot)
            {
                handlePortalContact(contact.getFixtureA(), contact.getFixtureB());
            }
            else if(contact.getFixtureB().m_userData instanceof EntityPortalShoot)
            {
                handlePortalContact(contact.getFixtureB(), contact.getFixtureA());
            }
            else if(contact.getFixtureA().m_userData instanceof EntityPlayer)
            {
                handleWallPlayerContact(contact.getFixtureA(), contact.getFixtureB());
            }
            else if(contact.getFixtureB().m_userData instanceof EntityPlayer)
            {
                handleWallPlayerContact(contact.getFixtureB(), contact.getFixtureA());
            }
        }
    }

    public void handlePortalContact(Fixture portalShoot, Fixture theWall)
    {
        entityList.remove(portalShoot.m_userData);

        if(theWall.m_userData instanceof Wall)
        {
            Wall wall = (Wall) theWall.m_userData;

            if(wall.getType() == Wall.Type.NORMAL && wall.getPortalId() == -1)
            {
                EntityPortalShoot shoot = (EntityPortalShoot) portalShoot.m_userData;
                wall.setPortalId(shoot.getColor());
            }
        }
        bodyRemoveQueue.add(portalShoot.getBody());
    }

    public void handleWallPlayerContact(Fixture thePlayer, Fixture theWall)
    {
        EntityPlayer player = (EntityPlayer) thePlayer.m_userData;
        Wall wall = (Wall) theWall.m_userData;
        if(wall.getPortalId() != -1)
        {
            if(wall.getPortalId() == 0 || wall.getPortalId() == 1)
            {
                EntityPlayer wizard = getWizard();

                if(wall.getPortalId() == 0 && wizard.getPortal2() != null)
                {
                    wizard.getPortal2().warpInFront(player);
                }
                else if(wizard.getPortal1() != null)
                {
                    wizard.getPortal1().warpInFront(player);
                }
            }
            else if(wall.getPortalId() == 2 || wall.getPortalId() == 3)
            {
                EntityPlayer fairy = getFairy();

                if(wall.getPortalId() == 2 && fairy.getPortal2() != null)
                {
                    fairy.getPortal2().warpInFront(player);
                }
                else if(fairy.getPortal1() != null)
                {
                    fairy.getPortal1().warpInFront(player);
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse){}
}
