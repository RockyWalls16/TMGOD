package com.gp5.projettutore.core.main;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.gp5.projettutore.core.render.DrawSurface;
import com.gp5.projettutore.core.render.Textures;


public class MainActivity extends Activity
{
    private static MainActivity instance;

    private static DrawSurface openGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        instance = this;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Core.instance.initCore();
        openGLView = new DrawSurface(this);
        setContentView(openGLView);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (openGLView != null)
        {
            openGLView.onResume();
            Textures.init();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (openGLView != null)
        {
            openGLView.onPause();
        }
    }

    public static MainActivity getInstance()
    {
        return instance;
    }
}
