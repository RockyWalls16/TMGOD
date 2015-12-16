package com.gp5.projettutore.game.entity;

import com.gp5.projettutore.game.level.Level;

/**
 * Created by Valentin on 16/12/2015.
 */
public class EntityElectric extends Entity
{
    private int timer = 0;
    private boolean isEnabled;

    public EntityElectric(Level level, float x, float z)
    {
        super(level, x, z);
    }

    @Override
    public void onUpdateTick()
    {
        super.onUpdateTick();

        if(timer > 0)
        {
            timer--;
        }
        else if(timer == 0)
        {
            isEnabled = false;
        }
    }

    public int getTimer()
    {
        return timer;
    }

    public boolean isEnabled()
    {
        return isEnabled;
    }
}
