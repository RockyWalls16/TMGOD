package com.gp5.projettutore.game.main;

import com.gp5.projettutore.game.entity.EntityPlayer;
import com.gp5.projettutore.game.level.Level;
import com.gp5.projettutore.game.level.LevelUtil;
import com.gp5.projettutore.game.render.GUI.GUI;
import com.gp5.projettutore.game.render.GameRenderer;

public class Core
{
    public static final Core instance = new Core();
    public static int rotateAngle;

    private static GUI currentGUI;
    private static Level level;

    private static EntityPlayer player;

    /**
     * Called on activity creation
     */
    public void initCore()
    {
        try
        {
            level = LevelUtil.decodeLevel("test_big");
            player = level.getPlayer1();
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
}
