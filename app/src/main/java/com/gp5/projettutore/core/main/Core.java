package com.gp5.projettutore.core.main;

import com.gp5.projettutore.core.level.Level;
import com.gp5.projettutore.core.level.LevelUtil;
import com.gp5.projettutore.core.render.GUI.GUI;
import com.gp5.projettutore.core.render.GameRenderer;

public class Core
{
    public static final Core instance = new Core();
    public static float x;

    private static GUI currentGUI;
    private static Level level;

    /**
     * Called on activity creation
     */
    public void initCore()
    {
        try
        {
            level = LevelUtil.decodeLevel("");
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
        if(GameRenderer.shouldRotate)
        {
            x += 2F;
        }
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
}
