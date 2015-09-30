package com.gp5.projettutore.core.render;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.gp5.projettutore.core.main.Core;
import com.gp5.projettutore.core.main.TimeUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Valentin on 09/09/2015.
 */
public class DrawSurface extends GLSurfaceView implements GLSurfaceView.Renderer
{
    private float lag = 0.0F;

    public DrawSurface(Context context)
    {
        super(context);
        setRenderer(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        TimeUtil.start();
        GameRenderer.instance.initOpenGL();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        GameRenderer.instance.resizeDisplay(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        TimeUtil.updateFPS();
        Log.d("FPS", TimeUtil.getFps() + "");
        int delta = TimeUtil.getDelta();

        lag += delta;
        while (lag >= 50)
        {
            Core.instance.onLogicTick();
            lag -= 50;
        }

        GameRenderer.instance.onRenderTick(delta, gl);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(Core.instance.getCurrentGUI() != null)
            {
                Core.instance.getCurrentGUI().onClick((int) event.getX(), (int) event.getY());
            }

        }
        return true;
    }
}
