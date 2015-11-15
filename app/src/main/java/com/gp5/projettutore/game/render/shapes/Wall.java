package com.gp5.projettutore.game.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.render.LevelElement;
import com.gp5.projettutore.game.render.Textures;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Valentin on 13/09/2015.
 */
public class Wall extends LevelElement
{
    private static float[] colors = {0.6F, 0.6F, 0.6F, 1.0F, 0.6F, 0.6F, 0.6F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};

    private static float[][] vertices = {

            //Direction 0
            {1.0F, 0.0F, 0.0F,
            0.0F, 0.0F, 0.0F,
            1.0F,  2.0F, 0.0F,
            0.0F,  2.0F, 0.0F},

            {1.0F, 0.0F,  1.0F,
            0.0F, 0.0F, 0.0F,
            1.0F,  2.0F,  1.0F,
            0.0F,  2.0F, 0.0F},

            //Direction 2
            {1.0F, 0.0F,  1.0F,
            1.0F, 0.0F, 0.0F,
            1.0F,  2.0F,  1.0F,
            1.0F,  2.0F, 0.0F},

            {0.0F, 0.0F,  1.0F,
            1.0F, 0.0F, 0.0F,
            0.0F,  2.0F,  1.0F,
            1.0F,  2.0F, 0.0F},

            //Direction 4
            {0.0F, 0.0F,  1.0F,
            1.0F, 0.0F,  1.0F,
            0.0F,  2.0F,  1.0F,
            1.0F,  2.0F,  1.0F},

            {0.0F, 0.0F,  0.0F,
            1.0F, 0.0F,  1.0F,
            0.0F,  2.0F,  0.0F,
            1.0F,  2.0F,  1.0F},

            //Direction 6

            {0.0F, 0.0F, 0.0F,
             0.0F, 0.0F,  1.0F,
             0.0F,  2.0F, 0.0F,
             0.0F,  2.0F,  1.0F},

            //Direction 7
            {1.0F, 0.0F, 0.0F,
            0.0F, 0.0F, 1.0F,
            1.0F,  2.0F, 0.0F,
            0.0F,  2.0F, 1.0F}

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

    private Type type;

    private int x;
    private int z;

    public Wall(Type type, int direction, int x, int z)
    {
        this.type = type;
        this.x = x;
        this.z = z;

        ByteBuffer bb = ByteBuffer.allocateDirect(12 * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices[direction]);
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

        BodyDef boxDef = new BodyDef();
        boxDef.position.set(x, z);
        boxDef.type = BodyType.STATIC;
        PolygonShape boxShape = new PolygonShape();

        if(direction == 0)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 0.0F), new Vec2(1.0F, 0.0F)}, 2);
        }
        else if(direction == 1)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 0.0F), new Vec2(1.0F, 1.0F)}, 2);
        }
        else if(direction == 2)
        {
            boxShape.set(new Vec2[]{new Vec2(1.0F, 0.0F), new Vec2(1.0F, 1.0F)}, 2);
        }
        else if(direction == 3)
        {
            boxShape.set(new Vec2[]{new Vec2(1.0F, 0.0F), new Vec2(0.0F, 1.0F)}, 2);
        }
        else if(direction == 4)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 1.0F), new Vec2(1.0F, 1.0F)}, 2);
        }
        else if(direction == 5)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 0.0F), new Vec2(1.0F, 1.0F)}, 2);
        }
        else if(direction == 6)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 0.0F), new Vec2(0.0F, 1.0F)}, 2);
        }
        else if(direction == 7)
        {
            boxShape.set(new Vec2[]{new Vec2(1.0F, 0.0F), new Vec2(0.0F, 1.0F)}, 2);
        }

        Body body = Core.instance.getCurrentLevel().getWorld().createBody(boxDef);
        FixtureDef boxFixture = new FixtureDef();
        boxFixture.density = 0.1f;
        boxFixture.shape = boxShape;
        body.createFixture(boxFixture);
    }

    public void draw()
    {
        Textures.bindTexture(type.texture);
        GLES10.glPushMatrix();
        GLES10.glTranslatef(x, 0, z);

        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexBuffer);
        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, colorBuffer);
        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texBuffer);

        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, 4);

        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);

        GLES10.glPopMatrix();
    }

    @Override
    public int getRenderID()
    {
        return type.ordinal();
    }

    public enum Type
    {
        NORMAL(Textures.wallTexture1),
        INSTABLE(Textures.wallTexture2),
        GRID(Textures.wallTexture3);

        public final int texture;

        Type(int texture)
        {
            this.texture = texture;
        }
    }
}
