package com.gp5.projettutore.game.render.shapes;

import android.opengl.GLES10;

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
public class Floor extends LevelElement
{
    private static float[] texCoords = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f
    };

    private FloatBuffer vertexBuffer;
    private FloatBuffer texBuffer;
    private Type type;

    public Floor(Type type, int x, int z, int width, int height)
    {
        this.type = type;
        float[] vertexArray = new float[]{x, 0.0F, z, x + width, 0.0F, z, x, 0.0F, z + height, x + width, 0.0F, z + height};
        float[] textureArray = new float[]{0.0F, 1.0F / (float) height, 1.0F / (float) width, 1.0F / (float) height, 0.0F, 0.0F, 1.0F / (float) width, 0.0F};

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
        Textures.bindTexture(type.texture);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexBuffer);
        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texBuffer);

        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, 4);

        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
    }

    @Override
    public int getRenderID()
    {
        return type.ordinal() + 3;
    }

    public enum Type
    {
        GRASS(Textures.floorTexture1),
        DIRT(Textures.floorTexture2),
        WOOD(Textures.floorTexture3),
        WATER(Textures.floorTexture4),
        POISON(Textures.floorTexture5);


        public final int texture;

        Type(int texture)
        {
            this.texture = texture;
        }
    }
}