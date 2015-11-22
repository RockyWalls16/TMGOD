package com.gp5.projettutore.game.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.game.level.Level;
import com.gp5.projettutore.game.render.LevelElement;
import com.gp5.projettutore.game.render.Texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Outline extends LevelElement
{
    private FloatBuffer vertexBuffer;
    private FloatBuffer texBuffer;

    public Outline(Level level, int corner)
    {
        float[] vertices = null;
        float[] texCoords = null;

        int width = level.getWidth();
        int height = level.getHeight();
        /*
        * Corner order : top left, top right, bottom right, bottom left
        */
        switch(corner)
        {
            case 0:
            {
                vertices = new float[]{
                        -50.0F, 2.0F, -50.0F,
                        width, 2.0F, -50.0F,
                        -50.0F, 2.0F, 0.0F,
                        width, 2.0F, 0.0F
                };
                texCoords = new float[]{
                        0.0F, 50.0F,
                        width + 50.0F, 50.0F,
                        0.0F, 0.0F,
                        width + 50.0F, 0.0F
                };
                break;
            }
            case 1:
            {
                vertices = new float[]{
                        width, 2.0F, -50.0F,
                        width + 50.0F, 2.0F, -50.0F,
                        width, 2.0F, height,
                        width + 50.0F, 2.0F, height
                };
                texCoords = new float[]{
                        0.0F, height + 50.0F,
                        50.0F, height + 50.0F,
                        0.0F, 0.0F,
                        50.0F, 0.0F
                };
                break;
            }
            case 2:
            {
                vertices = new float[]{
                        0.0F, 2.0F, height,
                        width + 50.0F, 2.0F, height,
                        0.0F, 2.0F, height + 50.0F,
                        width + 50.0F, 2.0F, height + 50.0F
                };
                texCoords = new float[]{
                        0.0F, 50.0F,
                        width + 50.0F, 50.0F,
                        0.0F, 0.0F,
                        width + 50.0F, 0.0F
                };
                break;
            }
            default:
            {
                vertices = new float[]{
                        -50.0F, 2.0F, 0.0F,
                        0.0F, 2.0F, 0.0F,
                        -50.0F, 2.0F, height + 50.0F,
                        0.0F, 2.0F, height + 50.0F
                };
                texCoords = new float[]{
                        0.0F, height + 50.0F,
                        50.0F, height + 50.0F,
                        0.0F, 0.0F,
                        50.0F, 0.0F
                };
                break;
            }
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);
    }

    public void draw()
    {
        Texture.bindTexture(Texture.cellingTexture.getTextureID());
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexBuffer);
        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texBuffer);

        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, 4);

        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
    }

    @Override
    public int getRenderID()
    {
        return -1;
    }

    @Override
    public boolean isOpaque()
    {
        return true;
    }
}