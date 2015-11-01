package com.gp5.projettutore.game.main;

/**
 * Created by Valentin on 09/09/2015.
 */
public class TimeUtil
{
    private static int FPS;
    private static int currentFPS;
    private static long lastFPS = getTime();
    private static long lastFrame;

    /**
     * Called on activity create
     */
    public static void start()
    {
        getDelta();
        lastFPS = getTime();
    }

    /**
     * Fetch the time in milliseconds
     * @return the time
     */
    public static long getTime()
    {
        return System.currentTimeMillis();
    }

    /**
     * To put in a nutshell : time difference between last tick and this one
     * @return delta
     */
    public static int getDelta()
    {
        int delta = (int) (getTime() - lastFrame);
        lastFrame = getTime();
        return delta;
    }

    /**
     * Update fps to calculate frame rate (Frame per seconds)
     */
    public static void updateFPS()
    {
        if (getTime() - lastFPS > 1000)
        {
            FPS = currentFPS;
            currentFPS = 0;
            lastFPS += 1000;
        }
        currentFPS = currentFPS + 1;
    }

    public static int getFps()
    {
        return FPS;
    }
}
