package com.gp5.projettutore.game.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.game.render.GameRenderer;
import com.gp5.projettutore.game.render.Textures;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TexturedCameraAlignedQuad
{
    private int textureId;

    private FloatBuffer vertexBuffer;
    private FloatBuffer texBuffer;

    public TexturedCameraAlignedQuad(int textureId, int sX, int sY, int tx, int ty, int tx2, int ty2)
    {
        this(textureId, sX, sY, tx / 256F, ty / 256F, tx2 / 256F, ty2 / 256F);
    }

    public TexturedCameraAlignedQuad(int textureId, int sX, int sY, float tx, float ty, float tx2, float ty2)
    {
        this.textureId = textureId;

        float x = (float) sX / 2F;
        float[] vertices = new float[]{-x, 0, 0, x, 0, 0, -x, sY, 0, x, sY, 0};
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

    public void draw(float x, float z)
    {
        Textures.bindTexture(textureId);
        GLES10.glPushMatrix();

        GLES10.glTranslatef(x, 0, z);
        GLES10.glRotatef(-GameRenderer.instance.getRotation() - 180F, 0.0F, 1.0F, 0.0F);
        //GLES10.glTranslatef(-0.5F, 0, -0.5F);

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
