package com.gp5.projettutore.core.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.core.main.Core;
import com.gp5.projettutore.core.render.LevelElement;
import com.gp5.projettutore.core.render.Textures;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Valentin on 13/09/2015.
 */
public class Floor implements LevelElement
{
    private static float[][] colors = {
            {1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F},

            //Direction 0 - OK
            {0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F},

            //Direction 1 - OK
            {0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F},

            //Direction 2 - OK
            {1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F},

            //Direction 3 - OK
            {1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F},

            //Direction 4 - OK
            {1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F},

            //Direction 5 - OK
            {0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F},

            //Direction 6 - OK
            {0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F},

            //Direction 7 - OK
            {0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F},

            //Square Corner 1 - OK
            {0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F},

            //Square Corner 2 - OK
            {1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F},

            //Square Corner 3 - OK
            {0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F},

            //Square Corner 4 - OK
            {0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F},

            //Corner 1
            {1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F},

            //Corner 2
            {1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F},

            //Corner 3
            {1.0F, 1.0F, 1.0F, 1.0F, 0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F},

            //Corner 4
            {0.7F, 0.7F, 0.7F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F}

    };

    private static float[][] vertices = {
            {0.0F, 0.0F, 0.0F,
            1.0F, 0.0F, 0.0F,
            0.0F, 0.0F,  1.0F,
            1.0F, 0.0F,  1.0F},

            //1
            {0.0F, 0.0F, 0.0F,
            1.0F, 0.0F, 1.0F,
            0.0F, 0.0F, 1.0F},

            //2
            {0.0F, 0.0F, 0.0F,
            1.0F, 0.0F, 0.0F,
            0.0F, 0.0F, 1.0F},

            //3
            {0.0F, 0.0F, 0.0F,
            1.0F, 0.0F, 0.0F,
            1.0F, 0.0F, 1.0F},

            //4
            {0.0F, 0.0F, 1.0F,
            1.0F, 0.0F, 0.0F,
            1.0F, 0.0F, 1.0F},
    };

    private static float[] texCoords = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f
    };

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer texBuffer;

    private int x;
    private int z;
    private int corner;
    private int colorIndex;

    public Floor(int x, int z)
    {
        int wall = Core.instance.getCurrentLevel().getWallAt(x, z);

        if(wall > 0 && wall <= 36)
        {
            wall = (wall - 1) % 12;

            if(wall < 12)
            {
                colorIndex = wall + 1;
            }

            if(wall == 1)
            {
                corner = 1;
            }
            else if(wall == 3)
            {
                corner = 2;
            }
            else if(wall == 5)
            {
                corner = 3;
            }
            else if(wall == 7)
            {
                corner = 4;
            }
        }
        else if(wall == 0)
        {
            int right = Core.instance.getCurrentLevel().getWallAt(x + 1, z);
            int left = Core.instance.getCurrentLevel().getWallAt(x - 1, z);
            int down = Core.instance.getCurrentLevel().getWallAt(x, z + 1);
            int top = Core.instance.getCurrentLevel().getWallAt(x, z - 1);

            if((right == 1 || right == 2) && (top == 2 || top == 3 || top == 9))
            {
                colorIndex = 13;
            }
            else if((right == 4 || right == 5) && (down == 3 || down == 4 || down == 10))
            {
                colorIndex = 14;
            }
            else if((left == 5 || left == 6) && (down == 6 || down == 7 || down == 1))
            {
                colorIndex = 15;
            }
            else if((left == 1 || left == 8) && (top == 7 || top == 8 || top == 2))
            {
                colorIndex = 16;
            }
        }

        this.x = x;
        this.z = z;

        ByteBuffer bb = ByteBuffer.allocateDirect(vertices[corner].length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices[corner]);
        vertexBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors[colorIndex].length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors[colorIndex]);
        colorBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);
    }

    public void draw()
    {
        Textures.bindTexture(Textures.floorTexture1);
        GLES10.glPushMatrix();
        GLES10.glTranslatef(x, 0, z);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexBuffer);
        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, colorBuffer);
        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texBuffer);


        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, corner == 0 ? 4 : 3);
        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glPopMatrix();
    }

    public enum Type
    {
        GRASS(Textures.floorTexture1),
        DIRT(Textures.wallTexture2),
        WOOD(Textures.wallTexture3);

        public final int texture;

        Type(int texture)
        {
            this.texture = texture;
        }
    }
}