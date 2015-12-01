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

    private ArrayList<Chunk> translucentChunks = new ArrayList<Chunk>();

    public void onRenderTick(float delta, GL10 gl)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        if(Core.instance.getCurrentLevel() != null && Core.instance.getPlayer() != null)
        {
            switch3D(gl);
            GLES10.glLoadIdentity();
            GLES10.glRotatef(45F, 1.0F, 0.0F, 0.0F);
            float x = 0.0F;
            if(Core.rotateAngle == 1 || Core.rotateAngle == 3)
            {
                x = 0.25F;
            }
            else if(Core.rotateAngle == 2)
            {
                x = 0.5F;
            }
            else if(Core.rotateAngle == 5 || Core.rotateAngle == 7)
            {
                x = -0.25F;
            }
            else if(Core.rotateAngle == 6)
            {
                x = -0.5F;
            }
            GLES10.glTranslatef(x, 0.0F, -8.5F - scaleAmount);
            GLES10.glRotatef(Core.rotateAngle * 45F, 0.0F, 1.0F, 0.0F);
            GLES10.glTranslatef(-camera.getRenderX(delta), -10.0F - scaleAmount, -camera.getRenderZ(delta) - 0.5F);
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
        GLES20.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        GLES20.glClearDepthf(1.0F);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glHint(GLES10.GL_PERSPECTIVE_CORRECTION_HINT, GLES10.GL_NICEST);
        GLES10.glShadeModel(GLES10.GL_SMOOTH);
        GLES20.glDisable(GLES20.GL_DITHER);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

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
                    if(Frustum.cubeInFrustum(chunk.getX() * 4, 0.0F, chunk.getZ() * 4, chunk.getX() * 4 + 4, 2.0F, chunk.getZ() * 4 + 4))
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
}
