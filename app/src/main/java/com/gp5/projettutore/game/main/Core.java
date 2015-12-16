package com.gp5.projettutore.game.main;

import android.util.Log;

import com.gp5.projettutore.game.entity.EntityPlayer;
import com.gp5.projettutore.game.level.Level;
import com.gp5.projettutore.game.level.LevelUtil;
import com.gp5.projettutore.game.render.GUI.GUI;

public class Core
{
    public static final Core instance = new Core();
    public static float rotateAngle;

    private static GUI currentGUI;
    private static Level level;

    private static EntityPlayer player;
    private static EntityPlayer friend;

    /**
     * Called on activity creation
     */
    public void initCore()
    {
        try
        {
            level = LevelUtil.decodeLevel(MainActivity.mapName);
            player = level.getPlayer1();
            friend = level.getPlayer2();
            Core.instance.getCurrentLevel().initChunks();
        }
        catch(Exception e)
        {
            Log.e("TEST", "Level loading failed !");
            Log.e("TEST", "Error :", e);
        }
    }

    /**
     * Called 20 times per seconds. Used to manage entities update
     */
    public void onLogicTick()
    {
        player.userControlTick();
        level.tickLevel();
    }

    /**
     * Display a new GUI on the screen
     * @param gui The gui you want to display
     */
    public void displayGUI(GUI gui)
    {
        if(gui != null)
        {
            gui.onGUIOpen();
        }
        else if(currentGUI != null)
        {
            currentGUI.onGUIClose();
        }
        currentGUI = gui;
    }

    public GUI getCurrentGUI()
    {
        return currentGUI;
    }

    public Level getCurrentLevel()
    {
        return level;
    }

    public EntityPlayer getPlayer()
    {
        return player;
    }

    public EntityPlayer getFriend()
    {
        return friend;
    }
}
