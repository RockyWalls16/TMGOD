package com.gp5.projettutore.game.render.GUI;

import com.gp5.projettutore.game.render.Textures;
import com.gp5.projettutore.game.render.shapes.Textured2DQuad;

import java.util.ArrayList;

public class FontRenderer
{
	private static final int GRID_SIZE = 16;
	private static final float CELL_SIZE = 1.0F / GRID_SIZE;

    /**
     * Prepare a new string for render
     * @param text The text you want to render
     * @return A StringQuadList (Contains a render list of each characters)
     */
	public static StringQuadList prepareStringRender(String text)
	{
		ArrayList<Textured2DQuad> quadList = new ArrayList<Textured2DQuad>();

		for(int i = 0; i < text.length();i++)
		{
			char character = text.charAt(i);
			if (character == '\u262F' && i < text.length())
			{
				FontColor newColor = FontColor.getColorForChar(text.charAt(i + 1));

				if (newColor != null)
				{
					i++;
                    continue;
				}
			}
			else
			{
				float tx = character % GRID_SIZE * CELL_SIZE;
				float ty = character / GRID_SIZE * CELL_SIZE;

                quadList.add(new Textured2DQuad(Textures.fontTexture, 16, 16, tx, ty, tx + 0.0625F, ty + 0.0625F));
			}
		}

		return new StringQuadList(quadList, text);
	}

    /**
     * Calculate the string size in pixel
     * @param str The text you want to determine the size
     * @return String width
     */
	public static int getStringLength(String str)
	{
		int charAmount = 0;
		for (int i = 0; i < str.length(); i++)
		{
			char character = str.charAt(i);
			if (character == '\u262F' && i < str.length())
			{
				i++;
			}
			else
			{
				charAmount++;
			}
		}
		return charAmount * GRID_SIZE;
	}
}
