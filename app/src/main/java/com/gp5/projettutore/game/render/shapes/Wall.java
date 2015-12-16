package com.gp5.projettutore.game.render.shapes;

import android.opengl.GLES10;

import com.gp5.projettutore.game.entity.EntityPlayer;
import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.render.LevelElement;
import com.gp5.projettutore.game.render.Texture;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

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

    private byte portalId = -1;
    private byte realDirection;
    private byte direction;

    public Wall(Type type, int direction, int x, int z)
    {
        this.direction = (byte) direction;
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

        realDirection = -1;

        if(direction == 0)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 0.0F), new Vec2(1.0F, 0.0F)}, 2);

            if(Core.instance.getCurrentLevel().getFloorAt(x, z - 1) <= 0)
            {
                realDirection = 0;
            }
            else if(Core.instance.getCurrentLevel().getFloorAt(x, z + 1) <= 0)
            {
                realDirection = 4;
            }
        }
        else if(direction == 1)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 0.0F), new Vec2(1.0F, 1.0F)}, 2);

            realDirection = 1;
        }
        else if(direction == 2)
        {
            boxShape.set(new Vec2[]{new Vec2(1.0F, 0.0F), new Vec2(1.0F, 1.0F)}, 2);

            if(Core.instance.getCurrentLevel().getFloorAt(x + 1, z) <= 0)
            {
                realDirection = 2;
            }
            else if(Core.instance.getCurrentLevel().getFloorAt(x - 1, z) <= 0)
            {
                realDirection = 6;
            }
        }
        else if(direction == 3)
        {
            boxShape.set(new Vec2[]{new Vec2(1.0F, 0.0F), new Vec2(0.0F, 1.0F)}, 2);

            realDirection = 3;
        }
        else if(direction == 4)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 1.0F), new Vec2(1.0F, 1.0F)}, 2);

            if(Core.instance.getCurrentLevel().getFloorAt(x, z + 1) <= 0)
            {
                realDirection = 4;
            }
            else if(Core.instance.getCurrentLevel().getFloorAt(x, z - 1) <= 0)
            {
                realDirection = 0;
            }
        }
        else if(direction == 5)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 0.0F), new Vec2(1.0F, 1.0F)}, 2);

            realDirection = 5;
        }
        else if(direction == 6)
        {
            boxShape.set(new Vec2[]{new Vec2(0.0F, 0.0F), new Vec2(0.0F, 1.0F)}, 2);

            if(Core.instance.getCurrentLevel().getFloorAt(x - 1, z) <= 0)
            {
                realDirection = 6;
            }
            else if(Core.instance.getCurrentLevel().getFloorAt(x + 1, z) <= 0)
            {
                realDirection = 2;
            }
        }
        else if(direction == 7)
        {
            boxShape.set(new Vec2[]{new Vec2(1.0F, 0.0F), new Vec2(0.0F, 1.0F)}, 2);

            realDirection = 7;
        }

        Body body = Core.instance.getCurrentLevel().getWorld().createBody(boxDef);
        FixtureDef boxFixture = new FixtureDef();
        boxFixture.filter.categoryBits = type == Type.GRID ? 0x0002 : 0x0001;
        boxFixture.density = 0.1f;
        boxFixture.shape = boxShape;
        boxFixture.userData = this;
        body.createFixture(boxFixture);
    }

    public void draw()
    {
        Texture.bindTexture(portalId >= 0 ? Texture.portals[portalId].getTextureID() : type.texture.getTextureID());
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

    public void setPortalId(int id)
    {
        portalId = (byte) id;
        
        if(id == 0 ||id == 1)//Red or blue
        {
            EntityPlayer wizard = Core.instance.getCurrentLevel().getWizard();

            if(id == 0)
            {
                wizard.resetPortalOne();
                wizard.setPortal1(this);
            }
            else
            {
                wizard.resetPortalTwo();
                wizard.setPortal2(this);
            }
        }
        else if(id == 2 || id == 3)//Green or Pink
        {
            EntityPlayer fairy = Core.instance.getCurrentLevel().getFairy();

            if(id == 2)
            {
                fairy.resetPortalOne();
                fairy.setPortal1(this);
            }
            else
            {
                fairy.resetPortalTwo();
                fairy.setPortal2(this);
            }
        }
    }

    public Type getType()
    {
        return type;
    }

    public int getPortalId()
    {
        return portalId;
    }

    public int getRealDirection()
    {
        return realDirection;
    }

    public void warpInFront(EntityPlayer player)
    {
        if(player.getPortalCooldown() > 0)
        {
            return;
        }
        player.setPortalCooldown(10);

        float nX = x;
        float nZ = z;
        float angle = Core.rotateAngle;

        switch(getRealDirection())
        {
            case 0:
            {
                nX += 0.5F;
                nZ += direction == 0 ? 0.5F : 1.5F;
                angle = 4;
                break;
            }
            case 1:
            {
                nX += 0.0F;
                nZ += 1.0F;
                angle = 5;
                break;
            }
            case 2:
            {
                nX += direction == 2 ? 0.5F : -0.5F;
                nZ += 0.5F;
                angle = 6;
                break;
            }
            case 3:
            {
                angle = 7;
                break;
            }
            case 4:
            {
                nX += 0.5F;
                nZ += direction == 4 ? 0.5F : -0.5F;
                angle = 0;
                break;
            }
            case 5:
            {
                nX += 0.5F;
                angle = 1;
                break;
            }
            case 6:
            {
                nX += direction == 6 ? 0.5F : 1.5F;
                nZ += 0.5F;
                angle = 2;
                break;
            }
            case 7:
            {
                nX += 1.0F;
                nZ += 1.0F;
                angle = 3;
                break;
            }
        }
        player.warp(nX, nZ);
        Core.rotateAngle = angle;
    }

    @Override
    public int getRenderID()
    {
        return isOpaque() ? type.ordinal() : 100;
    }

    @Override
    public boolean isOpaque()
    {
        return type != Type.GRID;
    }

    public enum Type
    {
        NORMAL(Texture.wallTexture1),
        INSTABLE(Texture.wallTexture2),
        GRID(Texture.wallTexture3);

        public final Texture texture;

        Type(Texture texture)
        {
            this.texture = texture;
        }
    }
}
