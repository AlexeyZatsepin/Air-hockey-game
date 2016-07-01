package study.example.azatsepin.airhockey.programs;

import android.content.Context;

import study.example.azatsepin.airhockey.utils.ResourceReader;
import study.example.azatsepin.airhockey.utils.ShaderHelper;

import static android.opengl.GLES20.glUseProgram;


public class ShaderProgram {
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    // Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected static final String U_COLOR = "u_Color";

    // Shader program
    protected final int program;
    protected ShaderProgram(Context context, int vertexShaderResourceId,
                            int fragmentShaderResourceId) {
// Compile the shaders and link the program.
        program = ShaderHelper.buildProgram(
                ResourceReader.readRawResources(
                        context, vertexShaderResourceId),
                ResourceReader.readRawResources(
                        context, fragmentShaderResourceId));
    }
    public void useProgram() {
// Set the current OpenGL shader program to this program.
        glUseProgram(program);
    }
}
