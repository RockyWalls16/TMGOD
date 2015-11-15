package com.gp5.projettutore.game.render.shapes;

import android.opengl.GLES10;
import android.util.Log;

import com.gp5.projettutore.game.level.LevelUtil;
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
    private static float[] texCoords = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f
    };

    private FloatBuffer vertexBuffer;
    private FloatBuffer texBuffer;

    public Roof(int x, int z, int width, int height)
    {
        float[] vertexArray;
        float[] textureArray;

        int corner = 0;

        int wall = Core.instance.getCurrentLevel().getWallAt(x, z);
        if(LevelUtil.isWall(wall))
        {
            wall = LevelUtil.getWallDirection(wall);

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

        if(corner > 0)
        {
            textureArray = texCoords;

            switch(corner)
            {
                case 1:
                {
                    vertexArray = new float[]{x, 2.0F, z, x + width, 2.0F, z + height, x, 2.0F, z + height};
                    break;
                }
                case 2:
                {
                    vertexArray = new float[]{x, 2.0F, z, x + width, 2.0F, z, x, 2.0F, z + height};
                    break;
                }
                case 3:
                {
                    vertexArray = new float[]{x, 2.0F, z, x + width, 2.0F, z, x + width, 2.0F, z + height};
                    break;
                }
                default:
                {
                    vertexArray = new float[]{x, 2.0F, z + height, x + width, 2.0F, z, x + width, 2.0F, z + height};
                }
            }
        }
        else
        {
            vertexArray = new float[]{x, 2.0F, z, x + width, 2.0F, z, x, 2.0F, z + height, x + width, 2.0F, z + height};
            textureArray = new float[]{0.0F, 1.0F / (float) height, 1.0F / (float) width, 1.0F / (float) height, 0.0F, 0.0F, 1.0F / (float) width, 0.0F};
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(vertexArray.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(textureArray.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);
    }

    public void draw()
    {
        Textures.bindTexture(Textures.cellingTexture);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexBuffer);
        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texBuffer);

        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, vertexBuffer.capacity() == 12 ? 4 : 3);

        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
    }

    @Override
    public int getRenderID()
    {
        return -1;
    }
}