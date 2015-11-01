package com.gp5.projettutore.game.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.render.LevelElement;
import com.gp5.projettutore.game.render.Textures;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Valentin on 13/09/2015.
 */
public class Roof extends LevelElement
{
    private static float[] colors = {1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};

    private static float[][] vertices = {
            {0.0F, 2.0F, 0.0F,
            1.0F, 2.0F, 0.0F,
            0.0F, 2.0F,  1.0F,
            1.0F, 2.0F,  1.0F},

            //1
            {0.0F, 2.0F, 0.0F,
            1.0F, 2.0F, 1.0F,
            0.0F, 2.0F, 1.0F},

            //2
            {0.0F, 2.0F, 0.0F,
            1.0F, 2.0F, 0.0F,
            0.0F, 2.0F, 1.0F},

            //3
            {0.0F, 2.0F, 0.0F,
            1.0F, 2.0F, 0.0F,
            1.0F, 2.0F, 1.0F},

            //4
            {0.0F, 2.0F, 1.0F,
            1.0F, 2.0F, 0.0F,
            1.0F, 2.0F, 1.0F},
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

    public Roof(int x, int z)
    {
        int wall = Core.instance.getCurrentLevel().getWallAt(x, z);

        if(wall > 0 && wall <= 36)
        {
            wall = (wall - 1) % 12;

            if(wall == 1)
            {
                corner = 3;
            }
            else if(wall == 3)
            {
                corner = 4;
            }
            else if(wall == 5)
            {
                corner = 1;
            }
            else if(wall == 7)
            {
                corner = 2;
            }
        }

        this.x = x;
        this.z = z;

        ByteBuffer bb = ByteBuffer.allocateDirect(vertices[corner].length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices[corner]);
        vertexBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);
    }

    public void draw()
    {
        Textures.bindTexture(Textures.cellingTexture);
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

    @Override
    public int getRenderID()
    {
        return -1;
    }
}