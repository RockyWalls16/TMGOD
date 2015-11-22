package com.gp5.projettutore.game.render.GUI;

import java.util.HashMap;

public class FontColor
{
	private static HashMap<Character, FontColor> colorMap = new HashMap<Character, FontColor>();

	public static final FontColor WHITE = new FontColor(1.0F, 1.0F, 1.0F, '0');
	public static final FontColor BLACK = new FontColor(0.0F, 0.0F, 0.0F, '1');
	public static final FontColor RED = new FontColor(1.0F, 0.0F, 0.0F, '2');
	public static final FontColor GREEN = new FontColor(0.0F, 1.0F, 0.0F, '3');
	public static final FontColor BLUE = new FontColor(0.0F, 0.0F, 1.0F, '4');
	public static final FontColor PURPLE = new FontColor(1.0F, 0.5F, 1.0F, '5');
	public static final FontColor ORANGE = new FontColor(1.0F, 0.5F, 0.0F, '6');

	private final float red;
	private final float green;
	private final float blue;
	private final char specialChar;

	public FontColor(float r, float g, float b, char specialChar)
	{
		red = r;
		green = g;
		blue = b;
		this.specialChar = specialChar;
		colorMap.put(specialChar, this);
	}

	public static FontColor getColorForChar(char specialChar)
	{
		return colorMap.get(specialChar);
	}

	public float getRed()
	{
		return red;
	}

	public float getGreen()
	{
		return green;
	}

	public float getBlue()
	{
		return blue;
	}

	public char getSpecialChar()
	{
		return specialChar;
	}

	@Override
	public String toString()
	{
		return "☯" + specialChar;
	}
}
