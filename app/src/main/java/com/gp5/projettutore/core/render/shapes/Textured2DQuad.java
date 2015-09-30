package com.gp5.projettutore.core.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.core.render.Textures;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Valentin on 09/09/2015.
 */
public class Textured2DQuad
{
    private int textureId;

    private FloatBuffer vertexBuffer;
    private FloatBuffer texBuffer;

    public Textured2DQuad(int textureId, int sX, int sY, int tx, int ty, int tx2, int ty2)
    {
        this(textureId, sX, sY, tx / 256F, ty / 256F, tx2 / 256F, ty2 / 256F);
    }

    public Textured2DQuad(int textureId, int sX, int sY, float tx, float ty, float tx2, float ty2)
    {
        this.textureId = textureId;

        float[] vertices = new float[]{0, 0, 0, sX, 0, 0, 0, sY, 0, sX, sY, 0};
        float[] texCoords = new float[]{tx, ty, tx2, ty, tx, ty2, tx2, ty2};

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

    public void draw(int x, int y)
    {
        Textures.bindTexture(textureId);
        GLES10.glPushMatrix();
        GLES10.glTranslatef(x, y, 0);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexBuffer);
        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texBuffer);

        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, 4);

        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glPopMatrix();
    }
}
