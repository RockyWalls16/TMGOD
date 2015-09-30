package com.gp5.projettutore.core.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.core.main.Core;
import com.gp5.projettutore.core.render.LevelElement;
import com.gp5.projettutore.core.render.Textures;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Outline implements LevelElement
{
    private FloatBuffer vertexBuffer;
    private FloatBuffer texBuffer;

    public Outline(int corner)
    {
        float[] vertices = null;
        float[] texCoords = null;

        int width = Core.instance.getCurrentLevel().getWidth();
        int height = Core.instance.getCurrentLevel().getHeight();
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
                        0.0F, 0.0F,
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
        Textures.bindTexture(Textures.cellingTexture);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexBuffer);
        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texBuffer);

        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, 4);

        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
    }
}