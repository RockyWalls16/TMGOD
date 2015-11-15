package com.gp5.projettutore.game.render;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.gp5.projettutore.R;
import com.gp5.projettutore.game.main.MainActivity;

import javax.microedition.khronos.opengles.GL10;

public class Textures
{
    private static int lastTextureId = -1;

    //World
    public static int wallTexture1;
    public static int wallTexture2;
    public static int wallTexture3;
    public static int floorTexture1;
    public static int floorTexture2;
    public static int floorTexture3;
    public static int floorTexture4;
    public static int floorTexture5;
    public static int cellingTexture;

    //Entities
    public static int playerTexture;

    //GUI
    public static int joystickTexture;
    public static int guiTexture;
    public static int fontTexture;

    private static int[] textureCache;

    public static void init()
    {
        wallTexture1 = loadTexture(R.drawable.wall_1);
        wallTexture2 = loadTexture(R.drawable.wall_2);
        wallTexture3 = loadTexture(R.drawable.wall_3);
        floorTexture1 = loadTexture(R.drawable.floor_1);
        floorTexture2 = loadTexture(R.drawable.floor_2);
        floorTexture3 = loadTexture(R.drawable.floor_3);
        floorTexture4 = loadTexture(R.drawable.floor_4);
        floorTexture5 = loadTexture(R.drawable.floor_5);
        cellingTexture = loadTexture(R.drawable.roof);

        playerTexture = loadTexture(R.drawable.player);

        joystickTexture = loadTexture(R.drawable.joystick);
        guiTexture = loadTexture(R.drawable.gui);
        fontTexture = loadTexture(R.drawable.font);
    }

    public static void bindTexture(int texId)
    {
        if(lastTextureId != texId)
        {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);

            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        }
    }

    public static int loadTexture(int id)
    {
        textureCache = new int[1];
        GLES20.glGenTextures(1, textureCache, 0);
        GLES20.glBindTexture(GL10.GL_TEXTURE_2D, textureCache[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.getInstance().getResources(), id, options);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return textureCache[0];
    }
}
