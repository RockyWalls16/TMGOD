package com.gp5.projettutore.core.render.GUI;

import com.gp5.projettutore.core.render.GameRenderer;

public abstract class GUI
{
	/**
	 * Called each render tick. Every draw functions must be here !
	 * @param delta
	 */
	public void drawGUI(float delta)
	{}

    /**
     * Called when gui open
     */
	public void onGUIOpen()
    {

    }

    /**
     * Called when gui close
     */
	public void onGUIClose()
	{}

	public void onClick(int mouseX, int mouseY)
	{}

    /**
     * Fetch width of the screen (Shortcut)
     * @return width of the screen
     */
	public int getWidth()
	{
		return (int) GameRenderer.instance.getWidth();
	}

    /**
     * Fetch height of the screen (Shortcut)
     * @return height of the screen
     */
	public int getHeight()
	{
		return (int) GameRenderer.instance.getHeight();
	}
}
