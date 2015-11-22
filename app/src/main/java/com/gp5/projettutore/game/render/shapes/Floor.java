package com.gp5.projettutore.game.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.game.render.LevelElement;
import com.gp5.projettutore.game.render.Texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Valentin on 13/09/2015.
 */
public class Floor extends LevelElement
{
    private FloatBuffer vertexBuffer;
    private FloatBuffer texBuffer;
    private Type type;

    public Floor(Type type, int x, int z, int width, int height)
    {
        this.type = type;
        float[] vertexArray = new float[]{x, 0.0F, z, x + width, 0.0F, z, x, 0.0F, z + height, x + width, 0.0F, z + height};
        float[] textureArray = new float[]{0.0F, height, width, height, 0.0F, 0.0F, width, 0.0F};

        ByteBuffer bb = ByteBuffer.allocateDirect(vertexArray.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(textureArray.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(textureArray);
        texBuffer.position(0);
    }

    public void draw()
    {
        Texture.bindTexture(type.texture.getTextureID());
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
        GRASS(Texture.floorTexture1),
        DIRT(Texture.floorTexture2),
        WOOD(Texture.floorTexture3),
        WATER(Texture.floorTexture4),
        POISON(Texture.floorTexture5),
        SAND(Texture.floorTexture6),
        ROCK(Texture.floorTexture7),
        SQUARE(Texture.floorTexture8),
        FLOWER_WHITE(Texture.floorTexture9),
        FLOWER_BLUE(Texture.floorTexture10),
        FLOWER_RED(Texture.floorTexture11),
        DEEP_WATER(Texture.floorTexture12);


        public final Texture texture;

        Type(Texture texture)
        {
            this.texture = texture;
        }
    }

    @Override
    public boolean isOpaque()
    {
        return true;
    }
}