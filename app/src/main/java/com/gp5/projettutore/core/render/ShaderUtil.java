package com.gp5.projettutore.core.render;

import android.opengl.GLES20;

/**
 * Created by Valentin on 09/09/2015.
 */
public class ShaderUtil
{
    public static int loadShaderProgram(String vertexShaderCode, String fragmentShaderCode)
    {
        int vertexShaderId = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertexShaderId, vertexShaderCode);
        GLES20.glCompileShader(vertexShaderId);

        int fragmentShaderId = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShaderId, fragmentShaderCode);
        GLES20.glCompileShader(fragmentShaderId);

        int programId = GLES20.glCreateProgram();

        GLES20.glAttachShader(programId, vertexShaderId);
        GLES20.glAttachShader(programId, fragmentShaderId);
        GLES20.glLinkProgram(programId);

        return programId;
    }
}
