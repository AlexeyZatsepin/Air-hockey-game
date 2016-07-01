package study.example.azatsepin.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import study.example.azatsepin.airhockey.models.Mallet;
import study.example.azatsepin.airhockey.models.Puck;
import study.example.azatsepin.airhockey.models.Table;
import study.example.azatsepin.airhockey.programs.ColorShaderProgram;
import study.example.azatsepin.airhockey.programs.TextureShaderProgram;
import study.example.azatsepin.airhockey.utils.MatrixHelper;

import study.example.azatsepin.airhockey.utils.TextureHelper;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;


public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    private final Context context;
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private Table table;
    private Mallet mallet;
    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;
    private int texture;
    public AirHockeyRenderer(Context context) {
        this.context = context;
    }
    private Puck puck;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                / (float) height, 1f, 10f);
        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);

        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();
// Draw the mallets.
        positionObjectInScene(0f, mallet.height / 2f, -0.4f);
        colorProgram.useProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mallet.bindData(colorProgram);
        mallet.draw();

        positionObjectInScene(0f, mallet.height / 2f, 0.4f);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
// Note that we don't have to define the object data twice -- we just
// draw the same mallet again but in a different position and with a
// different color.
        mallet.draw();
// Draw the puck.
        positionObjectInScene(0f, puck.height / 2f, 0f);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        puck.bindData(colorProgram);
        puck.draw();
    }

    private void positionTableInScene() {
// The table is defined in terms of X & Y coordinates, so we rotate it
// 90 degrees to lie flat on the XZ plane.
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }
    private void positionObjectInScene(float x, float y, float z) {
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, x, y, z);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }
}
