package com.gp5.projettutore.game.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.game.render.GameRenderer;
import com.gp5.projettutore.game.render.Texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TexturedCameraAlignedQuad
{
    private Texture texture;

    private FloatBuffer vertexBuffer;
    private FloatBuffer texBuffer;

    private boolean alignX = true;
    private float height = 0.01F;

    public TexturedCameraAlignedQuad(Texture texture, float sX, float sY, int tx, int ty, int tx2, int ty2, boolean alignX)
    {
        this(texture, sX, sY, (float) tx / (float) texture.getWidth(), (float) ty / (float) texture.getHeight(), (float) tx2 / (float) texture.getWidth(), (float) ty2 / (float) texture.getHeight(), alignX);
    }

    public TexturedCameraAlignedQuad(Texture texture, float sX, float sY, float tx, float ty, float tx2, float ty2, boolean alignX)
    {
        this.texture = texture;
        this.alignX = alignX;

        float x = sX / 2F;
        float[] vertices = new float[]{-x, 0, 0, x, 0, 0, -x, sY, 0, x, sY, 0};
        float[] texCoords = new float[]{tx2, ty2, tx, ty2, tx2, ty, tx, ty};

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
        Texture.bindTexture(texture.getTextureID());
        GLES10.glPushMatrix();

        if(!alignX)
        {
            GLES10.glTranslatef(x, 0, z);
            GLES10.glRotatef(-GameRenderer.instance.getRotation() - 180F, 0.0F, 1.0F, 0.0F);
        }
        else
        {
            GLES10.glTranslatef(x, height, z);
            GLES10.glRotatef(-GameRenderer.instance.getRotation() - 180F, 0.0F, 1.0F, 0.0F);
            GLES10.glRotatef(90F, 1.0F, 0.0F, 0.0F);
        }

        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexBuffer);
        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texBuffer);

        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, 4);

        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glPopMatrix();
    }

    public void setHeight(float height)
    {
        this.height = height;
    }
}
