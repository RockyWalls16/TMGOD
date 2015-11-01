package com.gp5.projettutore.game.control;

/**
 * Created by Valentin on 01/11/2015.
 */
public class JoyStick
{
    private static EnumDirection joystickDirection;

    public static EnumDirection getJoystickDirection()
    {
        return joystickDirection;
    }

    public static void calculateDirection(float jX, float jY)
    {
        //The joystick is topAligned
        if(jY < -0.2F)
        {
            if(jX > 0.7F)
            {
                joystickDirection = EnumDirection.NORTH_EAST;
                return;
            }
            else if(jX < -0.2F)
            {
                joystickDirection = EnumDirection.NORTH_WEST;
                return;
            }
            else
            {
                joystickDirection = EnumDirection.NORTH;
                return;
            }
        }
        //The joystick is bottom aligned
        else if(jY > 0.2F)
        {
            if(jX > 0.7F)
            {
                joystickDirection = EnumDirection.SOUTH_EAST;
                return;
            }
            else if(jX < -0.2F)
            {
                joystickDirection = EnumDirection.SOUTH_WEST;
                return;
            }
            else
            {
                joystickDirection = EnumDirection.SOUTH;
                return;
            }
        }
        //The joystick is left aligned
        else if(jX < -0.2F)
        {
            if(jY > 0.7F)
            {
                joystickDirection = EnumDirection.SOUTH_WEST;
                return;
            }
            else if(jY < -0.2F)
            {
                joystickDirection = EnumDirection.NORTH_WEST;
                return;
            }
            else
            {
                joystickDirection = EnumDirection.WEST;
                return;
            }
        }
        //The joystick is right aligned
        else if(jX > 0.2F)
        {
            if (jY > 0.7F)
            {
                joystickDirection = EnumDirection.SOUTH_EAST;
                return;
            } else if (jY < -0.2F)
            {
                joystickDirection = EnumDirection.NORTH_EAST;
                return;
            } else
            {
                joystickDirection = EnumDirection.EAST;
                return;
            }
        }
        joystickDirection = null;
    }
}
