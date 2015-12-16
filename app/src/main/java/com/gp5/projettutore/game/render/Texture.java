package com.gp5.projettutore.game.render;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.gp5.projettutore.R;
import com.gp5.projettutore.game.main.MainActivity;

import javax.microedition.khronos.opengles.GL10;

public class Texture
{
    private static int lastTextureId = -1;
    private static int[] textureCache;

    //World
    public static Texture wallTexture1;
    public static Texture wallTexture2;
    public static Texture wallTexture3;
    public static Texture floorTexture1;
    public static Texture floorTexture2;
    public static Texture floorTexture3;
    public static Texture floorTexture4;
    public static Texture floorTexture5;
    public static Texture floorTexture6;
    public static Texture floorTexture7;
    public static Texture floorTexture8;
    public static Texture floorTexture9;
    public static Texture floorTexture10;
    public static Texture floorTexture11;
    public static Texture floorTexture12;
    public static Texture cellingTexture;

    public static Texture[] fireWall;
    public static Texture[] lightWall;
    public static Texture[] portals;

    //Entities
    public static Texture wizardTexture;
    public static Texture fairyTexture;
    public static Texture projectileTexture;

    //GUI
    public static Texture joystickTexture;
    public static Texture guiTexture;
    public static Texture fontTexture;



    private int textureID;
    private int width;
    private int height;

    public Texture(int rId)
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
            options.inPreferQualityOverSpeed = true;

            Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.getInstance().getResources(), rId, options);
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        textureID = textureCache[0];
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getTextureID()
    {
        return textureID;
    }

    public static void init()
    {
        wallTexture1 = new Texture(R.drawable.wall_1);
        wallTexture2 = new Texture(R.drawable.wall_2);
        wallTexture3 = new Texture(R.drawable.wall_3);
        floorTexture1 = new Texture(R.drawable.floor_1);
        floorTexture2 = new Texture(R.drawable.floor_2);
        floorTexture3 = new Texture(R.drawable.floor_3);
        floorTexture4 = new Texture(R.drawable.floor_4);
        floorTexture5 = new Texture(R.drawable.floor_5);
        floorTexture6 = new Texture(R.drawable.floor_6);
        floorTexture7 = new Texture(R.drawable.floor_7);
        floorTexture8 = new Texture(R.drawable.floor_8);
        floorTexture9 = new Texture(R.drawable.floor_9);
        floorTexture10 = new Texture(R.drawable.floor_10);
        floorTexture11 = new Texture(R.drawable.floor_11);
        floorTexture12 = new Texture(R.drawable.floor_12);
        cellingTexture = new Texture(R.drawable.roof);

        fireWall = new Texture[]{new Texture(R.drawable.fire_wall_0), new Texture(R.drawable.fire_wall_1), new Texture(R.drawable.fire_wall_2), new Texture(R.drawable.fire_wall_3)};
        lightWall = new Texture[]{new Texture(R.drawable.light_wall_0), new Texture(R.drawable.light_wall_1), new Texture(R.drawable.light_wall_2), new Texture(R.drawable.light_wall_3), new Texture(R.drawable.light_wall_4), new Texture(R.drawable.light_wall_5)};
        portals = new Texture[]{new Texture(R.drawable.portal_0), new Texture(R.drawable.portal_1), new Texture(R.drawable.portal_2), new Texture(R.drawable.portal_3)};

        wizardTexture = new Texture(R.drawable.wizard);
        fairyTexture = new Texture(R.drawable.fairy);
        projectileTexture = new Texture(R.drawable.projectiles);

        joystickTexture = new Texture(R.drawable.joystick);
        guiTexture = new Texture(R.drawable.gui);
        guiTexture = new Texture(R.drawable.gui);
        fontTexture = new Texture(R.drawable.font);
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
}
