package com.gp5.projettutore.game.control;

/**
 * Created by Valentin on 01/11/2015.
 */
public class JoyStick
{
    private static float angle;
    private static float force = 0F;

    public static void calculateDirection(float jX, float jY)
    {
        angle = (float) (Math.atan2(jX, jY) * 180F / Math.PI + 180F);
        force = (float) (Math.min(100, Math.sqrt((jX * jX) + (jY * jY)) * 100F));
    }

    public static float getAngle()
    {
        return angle;
    }

    public static float getForce()
    {
        return force;
    }
}
