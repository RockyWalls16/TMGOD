package com.gp5.projettutore.core.level;

import com.gp5.projettutore.core.main.MainActivity;
import com.gp5.projettutore.core.render.LevelElement;
import com.gp5.projettutore.core.render.shapes.Floor;
import com.gp5.projettutore.core.render.shapes.Outline;
import com.gp5.projettutore.core.render.shapes.Roof;
import com.gp5.projettutore.core.render.shapes.Wall;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
        BufferedReader reader = new BufferedReader(new InputStreamReader(MainActivity.getInstance().getResources().openRawResource(MainActivity.getInstance().getResources().getIdentifier("whale", "raw", MainActivity.getInstance().getPackageName()))));
        String mapName = reader.readLine().split(":")[1];
        int width = Integer.valueOf(reader.readLine().split(":")[1]);
        int height = Integer.valueOf(reader.readLine().split(":")[1]);
        byte[][] mapData = new byte[2][width * height];

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
            else
            {
                String[] data = line.split(",");

                for (String value : data)
                {
                    mapData[mode][index] = Byte.valueOf(value);
                    index++;
                }
            }
        }

        return new Level(mapName, mapData, width, height);
    }

    /**
     * Generate the render list to render each wall, floor, entities at the same time
     * @param level The level you want to use to create the render list
     * @return List of render elements
     */
    public static ArrayList<LevelElement> getRenderList(Level level)
    {
        ArrayList<LevelElement> renderList = new ArrayList<LevelElement>();
        renderList.add(new Outline(0));
        renderList.add(new Outline(1));
        renderList.add(new Outline(2));
        renderList.add(new Outline(3));
        for(int layer = 0; layer < 2; layer++)
        {
            for (int x = 0; x < level.getWidth(); x++)
            {
                for (int z = 0; z < level.getHeight(); z++)
                {
                    int id = level.getTileAt(layer, x, z);

                    if(id == 0 && layer == 0)
                    {
                        renderList.add(new Roof(x, z));
                    }
                    if (id > 0 && id <= 36)
                    {
                        int wallStyle = ((id - 1) / 12) + 1;
                        int wallType = (id - 1) % 12;

                        if(wallType < 8)
                        {
                            renderList.add(new Wall(Wall.Type.values()[wallStyle - 1], wallType, x, z));
                            if (id % 2 == 0)
                            {
                                renderList.add(new Roof(x, z));
                            }
                        }
                        //Corners
                        else if(wallType == 8)
                        {
                            renderList.add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle) - 1, x, z));
                            renderList.add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 3) - 1, x, z));
                        }
                        else if(wallType == 9)
                        {
                            renderList.add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 3) - 1, x, z));
                            renderList.add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 5) - 1, x, z));
                        }
                        else if(wallType == 10)
                        {
                            renderList.add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 5) - 1, x, z));
                            renderList.add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 7) - 1, x, z));
                        }
                        else if(wallType == 11)
                        {
                            renderList.add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle * 7) - 1, x, z));
                            renderList.add(new Wall(Wall.Type.values()[wallStyle - 1], (wallStyle) - 1, x, z));
                        }
                    }
                    if (id == 37)
                    {
                        renderList.add(new Floor(x, z));
                    }
                }
            }
        }
        return renderList;
    }
}
