package com.gp5.projettutore.game.render;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.gp5.projettutore.game.main.Core;
import com.gp5.projettutore.game.main.TimeUtil;
import com.gp5.projettutore.game.render.shapes.AnimatedWall;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Valentin on 09/09/2015.
 */
public class DrawSurface extends GLSurfaceView implements GLSurfaceView.Renderer
{
    private float lag = 0.0F;
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleHandler;
    private boolean coreReady = false;
    private int time = 0;

    public DrawSurface(Context context)
    {
        super(context);
        setRenderer(this);
        scaleHandler = new ScaleGestureDetector(context, new ScaleHandler());
        gestureDetector = new GestureDetector(context, new GestureListener());
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
        if(!coreReady)
        {
            coreReady = true;
            Core.instance.initCore();
        }
        TimeUtil.updateFPS();

        int delta = TimeUtil.getDelta();
        lag += delta;
        while (lag >= 50)
        {
            time++;
            if(time % 3 == 0)
            {
                AnimatedWall.updateAnimations();
            }
            Core.instance.onLogicTick();
            lag -= 50;
        }

        GameRenderer.instance.onRenderTick(lag / 50.0F, gl);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getPointerCount() == 1)
        {
            if (Core.instance.getCurrentGUI() != null)
            {
                if(!Core.instance.getCurrentGUI().onClick((int) event.getX(), (int) event.getY(), event.getAction()))
                {
                    gestureDetector.onTouchEvent(event);
                }
            }
        }
        else
        {
            scaleHandler.onTouchEvent(event);
        }
        return true;
    }
}
class ScaleHandler extends ScaleGestureDetector.SimpleOnScaleGestureListener
{
    public boolean onScale(ScaleGestureDetector detector)
    {
        GameRenderer.instance.setScaleAmount(GameRenderer.instance.getScaleAmount() - (detector.getScaleFactor() - 1) * 5);
        return true;
    }
}
class GestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e)
    {
        if(GameRenderer.instance.getCamera() == Core.instance.getPlayer())
        {
            float x = e.getX();
            float y = e.getY();

            float deltaX = x - GameRenderer.instance.getWidth() / 2F;
            float deltaY = y - GameRenderer.instance.getHeight() / 2F;

            float angle = (float) (Math.atan2(deltaY, deltaX) * 180 / Math.PI); // Calculate the angle from x, y

            if (Core.instance.getPlayer() != null)
            {
                if(!GameRenderer.instance.isFirstPerson())
                {
                    Core.instance.getPlayer().shoot(-angle - 90F - GameRenderer.instance.getRotation());
                }
                else
                {
                    Core.instance.getPlayer().shoot(360 - GameRenderer.instance.getRotation());

                }
            }
        }
        return true;
    }
}