package com.gp5.projettutore.game.render.GUI;

import android.opengl.GLES10;

import com.gp5.projettutore.game.render.shapes.Textured2DQuad;

import java.util.ArrayList;

/**
 * Object containing each character render data to render it fast
 */
public class StringQuadList
{
    private ArrayList<Textured2DQuad> quads;
    private String text;

    public StringQuadList(ArrayList<Textured2DQuad> quads, String text)
    {
        this.quads = quads;
        this.text = text;
    }

    /**
     * Draw the string at coordinates
     * @param x explains by itself
     * @param y explains by itself
     */
    public void draw(int x, int y)
    {
        int lastCharIndex = 0;
        for(int i = 0;i < text.length() && lastCharIndex < quads.size();i++)
        {
            char character = text.charAt(i);

            if (character == '\u262F' && i < text.length())
            {
                FontColor newColor = FontColor.getColorForChar(text.charAt(i + 1));

                if (newColor != null)
                {
                    GLES10.glColor4f(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), 1.0F);
                    i++;
                }
            }
            else
            {
                quads.get(lastCharIndex).draw(x + lastCharIndex * 16, y);
                lastCharIndex++;
            }
        }
        GLES10.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Draw the string centered at coordinates
     * @param x explains by itself
     * @param y explains by itself
     */
    public void drawCentered(int x, int y)
    {
        this.draw(x - FontRenderer.getStringLength(text) / 2, y);
    }
    /**
     * Draw the string aligned on right at coordinates
     * @param x explains by itself
     * @param y explains by itself
     */
    public void drawRight(int x, int y)
    {
        this.draw(x - FontRenderer.getStringLength(text), y);
    }
}
