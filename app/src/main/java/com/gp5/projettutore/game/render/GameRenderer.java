package com.gp5.projettutore.game.render;

import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLU;

import com.gp5.projettutore.game.entity.Entity;
import com.gp5.projettutore.game.entity.EntityPlayer;
import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.render.GUI.IngameGUI;
import com.gp5.projettutore.game.render.shapes.Outline;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class GameRenderer
{
    public static GameRenderer instance = new GameRenderer();
    private EntityPlayer camera;
    private float ratio;
    private int width;
    private int height;

    private float rotation;
    private float scaleAmount;

    private boolean firstPerson;

    private ArrayList<Chunk> translucentChunks = new ArrayList<Chunk>();

    public void onRenderTick(float delta, GL10 gl)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        if(Core.instance.getCurrentLevel() != null && Core.instance.getPlayer() != null)
        {
            switch3D(gl);
            GLES10.glLoadIdentity();
            float x = 0.0F;
            float z = 0.0F;
            if(Core.rotateAngle == 1)
            {
                x = 0.35F;
                z = 0.15F;
            }
            else if(Core.rotateAngle == 2)
            {
                x = 0.5F;
                z = 0.5F;
            }
            else if(Core.rotateAngle == 3)
            {
                x = 0.35F;
                z = 0.85F;
            }
            else if(Core.rotateAngle == 4)
            {
                z = 1.0F;
            }
            else if(Core.rotateAngle == 5)
            {
                x = -0.35F;
                z = 0.85F;
            }
            else if(Core.rotateAngle == 6)
            {
                x = -0.5F;
                z = 0.5F;
            }
            else if(Core.rotateAngle == 7)
            {
                x = -0.35F;
                z = 0.15F;
            }
            if(!firstPerson)
            {
                GLES10.glDisable(GLES10.GL_FOG);
                GLES10.glRotatef(45F, 1.0F, 0.0F, 0.0F);
                GLES10.glTranslatef(x, 0.0F, -8.5F - scaleAmount - z);
                GLES10.glRotatef(Core.rotateAngle * 45F, 0.0F, 1.0F, 0.0F);
                GLES10.glTranslatef(-camera.getRenderX(delta), -10.0F - scaleAmount, -camera.getRenderZ(delta) - 0.5F);
            }
            else
            {
                GLES10.glEnable(GLES10.GL_FOG);
                GLES10.glRotatef(Core.rotateAngle * 45F, 0.0F, 1.0F, 0.0F);
                GLES10.glTranslatef(-camera.getRenderX(delta), -1.0F, -camera.getRenderZ(delta));
            }
            rotation = Core.rotateAngle * 45F;
            GLES10.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Frustum.calculateFrustum();
            renderLevel(delta);
        }

        GLES10.glLoadIdentity();
        switch2D();
        renderGUI(delta);
    }

    public void initOpenGL()
    {
        GLES20.glClearColor(0.8F, 0.95F, 1.0F, 1.0F);
        GLES20.glClearDepthf(1.0F);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glHint(GLES10.GL_PERSPECTIVE_CORRECTION_HINT, GLES10.GL_NICEST);
        GLES10.glShadeModel(GLES10.GL_SMOOTH);
        GLES20.glDisable(GLES20.GL_DITHER);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES10.glEnable(GLES10.GL_FOG);
        GLES10.glFogx(GLES10.GL_FOG_MODE, GLES10.GL_LINEAR);
        GLES10.glFogfv(GLES10.GL_FOG_COLOR, OpenGLUtil.createFloatBuffer(0.8F, 0.95F, 1.0F, 1.0F));
        GLES10.glFogf(GLES10.GL_FOG_START, 15.0F);
        GLES10.glFogf(GLES10.GL_FOG_END, 18.0F);

        Texture.init();
        Core.instance.displayGUI(new IngameGUI());
    }

    public void switch2D()
    {
        GLES10.glClearDepthf(1.0F);
        GLES10.glMatrixMode(GLES10.GL_PROJECTION);
        GLES10.glLoadIdentity();
        GLES10.glOrthof(0, width, height, 0, 1, -1);
        GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
        GLES10.glLoadIdentity();

        GLES10.glDisable(GLES10.GL_DEPTH_TEST);
    }

    public void switch3D(GL10 gl)
    {
        GLES10.glClearDepthf(1.0F);
        GLES10.glMatrixMode(GLES10.GL_PROJECTION);
        GLES10.glLoadIdentity();
        GLU.gluPerspective(gl, 45F, ratio, 0.1F, 100.0F);
        GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
        GLES10.glLoadIdentity();
        GLES10.glEnable(GLES10.GL_DEPTH_TEST);
    }


    public void resizeDisplay(GL10 gl, int width, int height)
    {
        this.width = width;
        this.height = height;
        ratio = (float)width / (float)height;
        GLES20.glViewport(0, 0, width, height);
        GLES10.glMatrixMode(GLES10.GL_PROJECTION);
        GLES10.glLoadIdentity();
        GLU.gluPerspective(gl, 45F, ratio, 0.1F, 100.0F);
        GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
        GLES10.glLoadIdentity();
    }

    public void renderLevel(float delta)
    {
        translucentChunks.clear();
        if(Core.instance.getCurrentLevel() != null)
        {
            for(Outline outline : Core.instance.getCurrentLevel().getLevelOutline())
            {
                outline.draw();
            }
            for(int x = 0; x < Core.instance.getCurrentLevel().getChunkArrayWidth();x++)
            {
                for(int z = 0; z < Core.instance.getCurrentLevel().getChunkArrayHeight();z++)
                {
                    Chunk chunk = Core.instance.getCurrentLevel().getChunks()[x][z];
                    float pX = Core.instance.getPlayer().getRenderX(delta);
                    float pZ = Core.instance.getPlayer().getRenderZ(delta);
                    int cX = x * 4 + 2;
                    int cZ = z * 4 + 2;
                    boolean inFrustum = Frustum.cubeInFrustum(chunk.getX() * 4, 0.0F, chunk.getZ() * 4, chunk.getX() * 4 + 4, 2.0F, chunk.getZ() * 4 + 4);
                    if((isFirstPerson() && Math.sqrt((pX-cX)*(pX-cX) + (pZ-cZ)*(pZ-cZ)) < 20 && inFrustum) || (!isFirstPerson() && inFrustum))
                    {
                        boolean isTranslucent = false;
                        for (LevelElement element : chunk.getRenderList())
                        {
                            if(element.isOpaque())
                            {
                                element.draw();
                            }
                            else if(!isTranslucent)
                            {
                                translucentChunks.add(chunk);
                                isTranslucent = true;
                            }
                        }
                    }
                }
            }

            GLES10.glEnable(GLES10.GL_ALPHA_TEST);
            GLES10.glAlphaFunc(GLES10.GL_GREATER, 0.5F);

            for (Entity entity : Core.instance.getCurrentLevel().getEntityList())
            {
                entity.render(delta);
            }

            for(Chunk chunk : translucentChunks)
            {
                int i = chunk.getRenderList().size() - 1;
                while(!chunk.getRenderList().get(i).isOpaque() && i >= 0)
                {
                    chunk.getRenderList().get(i).draw();
                    i--;
                }
            }

            GLES10.glDisable(GLES10.GL_ALPHA_TEST);
        }
    }

    public void renderGUI(float delta)
    {
        if(Core.instance.getCurrentGUI() != null)
        {
            Core.instance.getCurrentGUI().drawGUI(delta);
        }
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setScaleAmount(float scaleAmount)
    {
        this.scaleAmount = Math.max(-5.0F, Math.min(scaleAmount, 15.0F));
    }

    public float getScaleAmount()
    {
        return scaleAmount;
    }

    public EntityPlayer getCamera()
    {
        return camera;
    }

    public void setCamera(EntityPlayer camera)
    {
        this.camera = camera;
    }

    public boolean isFirstPerson()
    {
        return firstPerson;
    }

    public void setFirstPerson(boolean firstPerson)
    {
        this.firstPerson = firstPerson;
    }
}
