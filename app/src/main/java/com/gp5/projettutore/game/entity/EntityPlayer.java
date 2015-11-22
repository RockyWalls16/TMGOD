package com.gp5.projettutore.game.entity;

import com.gp5.projettutore.game.control.JoyStick;
import com.gp5.projettutore.game.level.Level;
import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.network.DataManager;
import com.gp5.projettutore.game.render.GameRenderer;
import com.gp5.projettutore.game.render.SpriteAtlas;
import com.gp5.projettutore.game.render.Texture;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class EntityPlayer extends Entity
{
    private static final SpriteAtlas SPRITE_ATLAS1 = new SpriteAtlas(Texture.wizardTexture, 128, 128, 4, 3, 1, 2, false);
    private static final SpriteAtlas SPRITE_ATLAS2 = new SpriteAtlas(Texture.fairyTexture, 32, 32, 4, 3, 1, 2, false);

    private boolean shoot;
    private float shootAngle;
    private boolean portalColor;
    private boolean isAWizard;
    private float startX;
    private float startZ;
    private boolean isSlowed;

    public EntityPlayer(Level level, float x, float z, boolean isAWizard)
    {
        super(level, x, z);
        this.isAWizard = isAWizard;
        startX = x;
        startZ = z;
    }

    @Override
    public void onUpdateTick()
    {
        super.onUpdateTick();

        byte floor = getLevel().getTileAt(0, (int) x, (int) z);
        isSlowed = false;
        if(floor == 40 && this.isAWizard)//Water
        {
            isSlowed = true;
        }
        else if(floor == 41)//Is Poison
        {
            respawn();
        }
        else if(floor == 44 && !this.isAWizard)//Is slowing magic tile
        {
            isSlowed = true;
        }
        else if(floor == 48 && this.isAWizard)//Deep Water
        {
            respawn();
        }

        if(this == Core.instance.getPlayer())
        {
            DataManager.sendPlayerData(this);
        }
    }

    public void userControlTick()
    {
        if(shoot)
        {
            shoot = false;
            EntityPortalShoot shoot = new EntityPortalShoot(getLevel(), this, shootAngle, isAWizard ? (portalColor ? 0 : 1) : (portalColor ? 2 : 3));
            shoot.spawnEntity();
            portalColor = !portalColor;
        }

        if(JoyStick.getForce() > 40)
        {
            if(GameRenderer.instance.getScaleAmount() > 0)
            {
                GameRenderer.instance.setScaleAmount(0);
            }
            if(GameRenderer.instance.getCamera() == Core.instance.getFriend())
            {
                GameRenderer.instance.setCamera(Core.instance.getPlayer());
            }
            float fX = (float) Math.sin(Math.toRadians(JoyStick.getAngle() - GameRenderer.instance.getRotation())) * (isSlowed ? -2 : -8);
            float fZ = (float) Math.cos(Math.toRadians(JoyStick.getAngle() - GameRenderer.instance.getRotation())) * (isSlowed ? -2 : -8);
            body.setLinearVelocity(new Vec2(fX, fZ));
        }
        else
        {
            body.setLinearVelocity(new Vec2(0, 0));
        }
    }

    @Override
    public void render(float delta)
    {
        if(isAWizard)
        {
            SPRITE_ATLAS1.renderFrame(getRenderX(delta), getRenderZ(delta));
        }
        else
        {
            SPRITE_ATLAS2.renderFrame(getRenderX(delta), getRenderZ(delta));
        }
    }

    public void shoot(float shootAngle)
    {
        shoot = true;
        this.shootAngle = shootAngle;
    }

    public void createBox()
    {
        BodyDef boxDef = new BodyDef();
        boxDef.position.set(x, z);
        boxDef.type = BodyType.DYNAMIC;
        CircleShape boxShape = new CircleShape();
        boxShape.m_radius = 0.4F;
        body = getLevel().getWorld().createBody(boxDef);
        FixtureDef boxFixture = new FixtureDef();
        boxFixture.filter.categoryBits = 0x0004;
        boxFixture.filter.maskBits = 0x0001 | 0x0002 | 0x0003;//Walls, grid, normal entities
        boxFixture.density = 0.1f;
        boxFixture.shape = boxShape;
        body.createFixture(boxFixture);
    }

    public void respawn()
    {
        this.x = startX;
        this.z = startZ;
        this.body.setLinearVelocity(new Vec2(0, 0));
        this.body.setTransform(new Vec2(startX, startZ), body.getAngle());
    }

    public boolean isAWizard()
    {
        return isAWizard;
    }

    public void setIsAWizard(boolean isAWizard)
    {
        this.isAWizard = isAWizard;
    }
}
