package com.gp5.projettutore.game.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Valentin on 18/11/2015.
 */
public class OpenGLUtil
{
    public static FloatBuffer createFloatBuffer(float... values)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(values.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(values);
        fb.position(0);
        return fb;
    }
}
