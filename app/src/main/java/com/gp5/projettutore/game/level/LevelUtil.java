package com.gp5.projettutore.game.level;

import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.main.MainActivity;
import com.gp5.projettutore.game.render.Chunk;
import com.gp5.projettutore.game.render.shapes.Floor;
import com.gp5.projettutore.game.render.shapes.Roof;
import com.gp5.projettutore.game.render.shapes.Wall;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;

/**
 * Created by Valentin on 10/09/2015.
 */
public class LevelUtil
{
    /**
     * Decode the level file and store everything in memory
     * @param levelName name of the level file
     * @return new instance of Level
     * @throws Exception
     */
    public static Level decodeLevel(String levelName) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(MainActivity.getInstance().getResources().openRawResource(MainActivity.getInstance().getResources().getIdentifier(levelName, "raw", MainActivity.getInstance().getPackageName()))));
        String mapName = reader.readLine().split(":")[1];
        int width = Integer.valueOf(reader.readLine().split(":")[1]);
        int height = Integer.valueOf(reader.readLine().split(":")[1]);
        byte[][] mapData = new byte[2][width * height];

        int p1X = 0;
        int p1Z = 0;
        int p2X = 0;
        int p2Z = 0;

        String line;
        int mode = -1;
        int index = 0;

        while((line = reader.readLine()) != null)
        {
            if (line.equals("floor:"))
            {
                mode = 0;
                index = 0;
            }
            else if (line.equals("walls:"))
            {
                mode = 1;
                index = 0;
            }
            else if (line.equals("entities:"))
            {
                mode = 2;
                index = 0;
            }
            else if(mode < 2)
            {
                String[] data = line.split(",");

                for (String value : data)
                {
                    mapData[mode][index] = Byte.valueOf(value);
                    index++;
                }
            }
            else //Entity Mode
            {
                if(line.startsWith("player1"))
                {
                    String[] coords = line.split(" ")[1].split(",");

                    p1X = Integer.valueOf(coords[0]);
                    p1Z = Integer.valueOf(coords[1]);
                }
                else if(line.startsWith("player2"))
                {
                    String[] coords = line.split(" ")[1].split(",");

                    p2X = Integer.valueOf(coords[0]);
                    p2Z = Integer.valueOf(coords[1]);
                }
            }
        }
        return new Level(mapName, mapData, width, height, p1X, p1Z, p2X, p2Z);
    }

    /**
     * Generate the render list to render each wall, floor, entities at the same time
     * @param level The level you want to use to create the render list
     * @return List of render elements
     */
    public static void getRenderList(Level level)
    {
        for(int layer = 0; layer < 2; layer++)
        {
            for (int cX = 0; cX < level.getChunkArrayWidth(); cX++)
            {
                for (int cZ = 0; cZ < level.getChunkArrayHeight(); cZ++)
                {
                    Chunk chunk = level.getChunks()[cX][cZ];

                    boolean[][][] mask = new boolean[4][4][2];
                    for(int x = 0;x < 4;x++)
                    {
                        for(int z = 0;z < 4;z++)
                        {
                            int rX = chunk.getX() * 4 + x;
                            int rZ = chunk.getZ() * 4 + z;

                            int id = getTileOnChunkAt(chunk, layer, x, z);

                            if(id == 0 && layer == 0 && !mask[x][z][0])
                            {
                                int width = 0;
                                int height = 0;

                                mainLoop: for (int x2 = x; x2 < 4; x2++)
                                {
                                    if (x2 == x)
                                    {
                                        for (int z2 = z; z2 < 4; z2++)
                                        {
                                            if (!mask[x2][z2][0] && getTileOnChunkAt(chunk, layer, x2, z2) == 0)
                                            {
                                                width++;
                                                mask[x2][z2][0] = true;
                                            }
                                            else
                                            {
                                                break;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        for (int z2 = z; z2 < z + width; z2++)
                                        {
                                            if (mask[x2][z2][0] || getTileOnChunkAt(chunk, layer, x2, z2) != 0)
                                            {
                                                break mainLoop;
                                            }
                                        }
                                        for (int z2 = z; z2 < z + width; z2++)
                                        {
                                            mask[x2][z2][0] = true;
                                        }
                                    }
                                    height++;
                                }
                                chunk.getRenderList().add(new Roof(rX, rZ, height, width));
                            }
                            if (layer == 1 && isWall(id))
                            {
                                handleWallRender(chunk, id, rX, rZ);
                            }
                            if(layer == 0 && isFloor(id) && !mask[x][z][1])
                            {
                                int width = 0;
                                int height = 0;

                                mainLoop: for (int x2 = x; x2 < 4; x2++)
                                {
                                    if (x2 == x)
                                    {
                                        for (int z2 = z; z2 < 4; z2++)
                                        {
                                            if (!mask[x2][z2][1] && isFloor(getTileOnChunkAt(chunk, layer, x2, z2)) && getFloorType(id) == getFloorType(getTileOnChunkAt(chunk, layer, x2, z2)))
                                            {
                                                width++;
                                                mask[x2][z2][1] = true;
                                            }
                                            else
                                            {
                                                break;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        for (int z2 = z; z2 < z + width; z2++)
                                        {
                                            if (mask[x2][z2][1] || !isFloor(getTileOnChunkAt(chunk, layer, x2, z2)) || getFloorType(id) != getFloorType(getTileOnChunkAt(chunk, layer, x2, z2)))
                                            {
                                                break mainLoop;
                                            }
                                        }
                                        for (int z2 = z; z2 < z + width; z2++)
                                        {
                                            mask[x2][z2][1] = true;
                                        }
                                    }
                                    height++;
                                }
                                chunk.getRenderList().add(new Floor(getFloorType(id), rX, rZ, height, width));
                            }
                        }
                    }
                }
            }
        }
        for(int x = 0; x < Core.instance.getCurrentLevel().getChunkArrayWidth();x++)
        {
            for (int z = 0; z < Core.instance.getCurrentLevel().getChunkArrayHeight(); z++)
            {
                Chunk chunk = Core.instance.getCurrentLevel().getChunks()[x][z];
                Collections.sort(chunk.getRenderList());
            }
        }
    }

    private static byte getTileOnChunkAt(Chunk ch, int layer, int x, int z)
    {
        return Core.instance.getCurrentLevel().getTileAt(layer, ch.getX() * 4 + x, ch.getZ() * 4 + z);
    }

    private static void handleWallRender(Chunk chunk, int id, int x, int z)
    {
        int wallStyle = getWallStyle(id);
        int wallDirection = getWallDirection(id);

        if(wallDirection < 8)
        {
            chunk.getRenderList().add(new Wall(Wall.Type.values()[wallStyle - 1], wallDirection, x, z));
            if (isWallNotAligned(id))
            {
                chunk.getRenderList().add(new Roof(x, z, 1, 1));
            }
        }
        //Corners
        else if(wallDirection == 8)
        {
            chunk.getRenderList().add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle) - 1, x, z));
            chunk.getRenderList().add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 3) - 1, x, z));
        }
        else if(wallDirection == 9)
        {
            chunk.getRenderList().add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 3) - 1, x, z));
            chunk.getRenderList().add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 5) - 1, x, z));
        }
        else if(wallDirection == 10)
        {
            chunk.getRenderList().add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 5) - 1, x, z));
            chunk.getRenderList().add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 7) - 1, x, z));
        }
        else if(wallDirection == 11)
        {
            chunk.getRenderList().add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 7) - 1, x, z));
            chunk.getRenderList().add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle) - 1, x, z));
        }
    }

    public static boolean isWall(int id)
    {
        return id > 0 && id <= 36;
    }

    public static int getWallDirection(int id)
    {
        return (id - 1) % 12;
    }

    public static int getWallStyle(int id)
    {
        return ((id - 1) / 12) + 1;
    }

    public static boolean isWallNotAligned(int id)
    {
        return id % 2 == 0;
    }

    public static boolean isFloor(int id)
    {
        return id >= 37 && id <= 48;
    }

    public static Floor.Type getFloorType(int id)
    {
        return Floor.Type.values()[id - 37];
    }
}
