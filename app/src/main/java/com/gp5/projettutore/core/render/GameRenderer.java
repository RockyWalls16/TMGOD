package com.gp5.projettutore.core.render;

import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLU;

import com.gp5.projettutore.core.main.Core;
import com.gp5.projettutore.core.render.GUI.DebugGUI;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Valentin on 09/09/2015.
 */
public class GameRenderer
{
    public static boolean shouldRotate = false;

    public static GameRenderer instance = new GameRenderer();
    private float ratio;
    private int width;
    private int height;

    private int[] textureCache;

    public void onRenderTick(float delta, GL10 gl)
    {
        switch3D(gl);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES10.glLoadIdentity();
        GLES10.glRotatef(45F, 1.0F, 0.0F, 0.0F);
        GLES10.glRotatef(Core.x, 0.0F, 1.0F, 0.0F);
        GLES10.glTranslatef(-2.5F, -10.0F, -12.5F);
        renderLevel(delta);

        GLES10.glLoadIdentity();
        switch2D();
        renderGUI(delta);
    }

    public void initOpenGL()
    {
        GLES20.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        GLES20.glClearDepthf(1.0F);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glHint(GLES10.GL_PERSPECTIVE_CORRECTION_HINT, GLES10.GL_NICEST);
        GLES10.glShadeModel(GLES10.GL_SMOOTH);
        GLES20.glCullFace(GLES20.GL_FRONT);
        GLES20.glDisable(GLES20.GL_DITHER);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        Textures.init();
        Core.instance.getCurrentLevel().initRenderList();
        Core.instance.displayGUI(new DebugGUI());
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
        if(Core.instance.getCurrentLevel() != null)
        {
            for (LevelElement element : Core.instance.getCurrentLevel().getRenderList())
            {
                element.draw();
            }
        }
    }

    public void renderEntities(float delta)
    {

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
}
