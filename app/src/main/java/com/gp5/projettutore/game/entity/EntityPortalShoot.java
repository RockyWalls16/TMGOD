package com.gp5.projettutore.game.entity;

import com.gp5.projettutore.game.level.Level;
import com.gp5.projettutore.game.render.Texture;
import com.gp5.projettutore.game.render.shapes.TexturedCameraAlignedQuad;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class EntityPortalShoot extends Entity
{
    private static TexturedCameraAlignedQuad portal1 = new TexturedCameraAlignedQuad(Texture.projectileTexture, 0.1F, 0.1F, 0, 0, 16, 16, true);
    private static TexturedCameraAlignedQuad portal2 = new TexturedCameraAlignedQuad(Texture.projectileTexture, 0.1F, 0.1F, 16, 16, 32, 32, true);
    private static TexturedCameraAlignedQuad portal3 = new TexturedCameraAlignedQuad(Texture.projectileTexture, 0.1F, 0.1F, 32, 32, 48, 48, true);
    private static TexturedCameraAlignedQuad portal4 = new TexturedCameraAlignedQuad(Texture.projectileTexture, 0.1F, 0.1F, 48, 48, 64, 64, true);

    static
    {
        portal1.setHeight(1.0F);
        portal2.setHeight(1.0F);
        portal3.setHeight(1.0F);
        portal4.setHeight(1.0F);
    }

    private float motionX;
    private float motionZ;
    private int color;

    public EntityPortalShoot(Level level, EntityPlayer shooter, float angle, int color)
    {
        super(level, shooter.getX(), shooter.getZ());
        motionX = (float) Math.sin(Math.toRadians(angle)) * -60;
        motionZ = (float) Math.cos(Math.toRadians(angle)) * -60;
        this.color = color;
    }

    @Override
    public void onUpdateTick()
    {
        super.onUpdateTick();
        body.setLinearVelocity(new Vec2(motionX, motionZ));
    }

    @Override
    public void createBox()
    {
        BodyDef boxDef = new BodyDef();
        boxDef.position.set(x, z);
        boxDef.type = BodyType.DYNAMIC;
        CircleShape boxShape = new CircleShape();
        boxShape.m_radius = 0.05F;
        body = getLevel().getWorld().createBody(boxDef);
        FixtureDef boxFixture = new FixtureDef();
        boxFixture.filter.categoryBits = 0x0006;
        boxFixture.filter.maskBits = 0x0001 | 0x0008;//Only walls
        boxFixture.density = 0.1f;
        boxFixture.userData = this;
        boxFixture.shape = boxShape;
        body.createFixture(boxFixture);
    }

    @Override
    public void render(float delta)
    {
        if(color == 0)
        {
            portal1.draw(getRenderX(delta), getRenderZ(delta));
        }
        else if(color == 1)
        {
            portal2.draw(getRenderX(delta), getRenderZ(delta));
        }
        else if(color == 2)
        {
            portal3.draw(getRenderX(delta), getRenderZ(delta));
        }
        else if(color == 3)
        {
            portal4.draw(getRenderX(delta), getRenderZ(delta));
        }
    }

    public int getColor()
    {
        return color;
    }
}
