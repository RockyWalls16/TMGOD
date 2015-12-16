package com.gp5.projettutore.game.entity;

import com.gp5.projettutore.game.control.EnumDirection;
import com.gp5.projettutore.game.control.JoyStick;
import com.gp5.projettutore.game.level.Level;
import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.network.DataManager;
import com.gp5.projettutore.game.render.GameRenderer;
import com.gp5.projettutore.game.render.SpriteAtlas;
import com.gp5.projettutore.game.render.Texture;
import com.gp5.projettutore.game.render.shapes.Wall;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class EntityPlayer extends Entity
{
    private static final SpriteAtlas SPRITE_ATLAS1 = new SpriteAtlas(Texture.wizardTexture, 128, 256, 4, 3, 1, 2, false);
    private static final SpriteAtlas SPRITE_ATLAS2 = new SpriteAtlas(Texture.fairyTexture, 128, 256, 4, 3, 1, 2, false);

    private boolean shoot;
    private float shootAngle;
    private boolean portalColor;
    private boolean isAWizard;
    private float startX;
    private float startZ;
    private boolean isSlowed;

    private Wall portal1;
    private Wall portal2;

    private int portalCooldown = 0;

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

        if(portalCooldown > 0)
        {
            portalCooldown--;
        }

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

        byte wall = getLevel().getTileAt(1, (int) x, (int) z);
        if(wall >= 53 && wall <= 60)//Is Fire / Light
        {
            float x1 = ((int) x) + 0.5F;
            float z1 = ((int) z) + 0.5F;
            double distance = Math.sqrt((x1-x)*(x1-x) + (z1-z)*(z1-z));
            if(distance < 0.15F)
            {
                if(wall >= 53 && wall <= 56)
                {
                    if(!isAWizard())
                    {
                        respawn();
                    }
                }
                else
                {
                    resetPortalOne();
                    resetPortalTwo();
                }
            }
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
            togglePortal();
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

            int angle = 360 - ((int) (JoyStick.getAngle() + (Core.rotateAngle / 2) * 90) % 360);

            if((angle > 315 && angle <= 360) || (angle >= 0 && angle <= 45))
            {
                setDirection(EnumDirection.NORTH);
            }
            else if(angle > 45 && angle <= 135)
            {
                setDirection(EnumDirection.EAST);
            }
            else if(angle > 135 && angle <= 225)
            {
                setDirection(EnumDirection.SOUTH);
            }
            else
            {
                setDirection(EnumDirection.WEST);
            }

            body.setLinearVelocity(new Vec2(fX, fZ));
        } else
        {
            body.setLinearVelocity(new Vec2(0, 0));
        }
    }

    @Override
    public void render(float delta)
    {
        if(isAWizard)
        {
            SPRITE_ATLAS1.setFrame(getDirectionForRender().ordinal(), 0);
            SPRITE_ATLAS1.renderFrame(getRenderX(delta), getRenderZ(delta));
        } else
        {
            SPRITE_ATLAS2.setFrame(getDirectionForRender().ordinal(), 0);
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
        boxFixture.userData = this;
        body.createFixture(boxFixture);
    }

    public void respawn()
    {
        warp(startX, startZ);
    }

    public void resetPortalOne()
    {
        if(portal1 != null)
        {
            portal1.setPortalId(-1);
        }
    }

    public void resetPortalTwo()
    {
        if(portal2 != null)
        {
            portal2.setPortalId(-1);
        }
    }

    public void togglePortal()
    {
        portalColor = !portalColor;
    }

    public int getPortalColor()
    {
        return isAWizard ? portalColor ? 0 : 1 : portalColor ? 2 : 3;
    }

    public boolean isAWizard()
    {
        return isAWizard;
    }

    public void setIsAWizard(boolean isAWizard)
    {
        this.isAWizard = isAWizard;
    }

    public Wall getPortal2()
    {
        return portal2;
    }

    public void setPortal2(Wall portal2)
    {
        this.portal2 = portal2;
    }

    public Wall getPortal1()
    {
        return portal1;
    }

    public void setPortal1(Wall portal1)
    {
        this.portal1 = portal1;
    }

    public int getPortalCooldown()
    {
        return portalCooldown;
    }

    public void setPortalCooldown(int portalCooldown)
    {
        this.portalCooldown = portalCooldown;
    }
}
