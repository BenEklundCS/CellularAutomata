package SlRenderer;

import CSC133.SlCamera;
import CSC133.SlWindow;
import SlGoLBoard.SlGoLBoardLive;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniform3f;

import static CSC133.Spot.*;

public class SlSingleBatchRenderer {
    private static final int OGL_MATRIX_SIZE = 16;
    private final FloatBuffer myFloatBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
    private int vpMatLocation = 0;
    private int renderColorLocation = 0;

    public SlSingleBatchRenderer() {
        slSingleBatchPrinter();
    }

    public void render() {
        WINDOW = SlWindow.get(); // render should grab the current window
        try {
            renderLoop();
        } finally {
            SlWindow.destroyGLFWindow();
            glfwTerminate();
            Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        }
    } // public void render()

    private void renderLoop() {
        glfwPollEvents();
        initOpenGL();
        renderObjects();
        /* Process window messages in the main thread */
        while (!glfwWindowShouldClose(WINDOW)) {
            glfwWaitEvents();
        }
    } // void renderLoop()
    private void initOpenGL() {

        final float BG_RED = 0.0f;
        final float BG_GREEN = 0.0f;
        final float BG_BLUE = 0.3f;
        final float BG_ALPHA = 1.0f;

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glViewport(0, 0, WIN_WIDTH, WIN_HEIGHT);

        glClearColor(BG_RED, BG_GREEN, BG_BLUE, BG_ALPHA); // background color

        // call glCreateProgram() here - we have no gl-context here

        int shader_program = glCreateProgram();
        int vs = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vs,
                "uniform mat4 viewProjMatrix;" +
                        "void main(void) {" +
                        " gl_Position = viewProjMatrix * gl_Vertex;" +
                        "}");

        glCompileShader(vs);
        glAttachShader(shader_program, vs);
        int fs = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fs,
                "uniform vec3 color;" +
                        "void main(void) {" +
                        // This guy sets the shape color :)
                        " gl_FragColor = vec4(color, 1.0f);" + //" gl_FragColor = vec4(0.7f, 0.5f, 0.1f, 1.0f);"
                        "}");

        glCompileShader(fs);
        glAttachShader(shader_program, fs);
        glLinkProgram(shader_program);
        glUseProgram(shader_program);
        vpMatLocation = glGetUniformLocation(shader_program, "viewProjMatrix");
        renderColorLocation = glGetUniformLocation(shader_program, "color");
    } // void initOpenGL()

    private void renderObjects() {

        // Generate GoL board and rows and cols of the grid

        int rows = 50;
        int cols = 50;
        SlGoLBoardLive GoLBoard = new SlGoLBoardLive(rows, cols);

        //
        // Camera handling
        //  - Make a camera and then get the right and top properties to scale the Grid Of Squares

        SlCamera camera = new SlCamera(); // Initialize camera here to use right/top for SlGridOfSquares scaling
        final float[] ortho = camera.getOrtho();
        final float right = ortho[1], top = ortho[3];

        //
        // Vertices / Indices generation
        //  - The squares do not move, so we can generate their indices and vertices once.

        SlGridOfSquares grid = new SlGridOfSquares(rows, cols, right, top);
        float[] vertices = grid.getVertices();
        int[] indices = grid.getIndices();

        //
        //  Begin rendering while loop
        //

        while (!glfwWindowShouldClose(WINDOW)) {

            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            int vbo = glGenBuffers();
            int ibo = glGenBuffers();

            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.
                    createFloatBuffer(vertices.length).
                    put(vertices).flip(), GL_STATIC_DRAW);
            glEnableClientState(GL_VERTEX_ARRAY);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils.
                    createIntBuffer(indices.length).
                    put(indices).flip(), GL_STATIC_DRAW);

            final int SIZE = 2;

            glVertexPointer(SIZE, GL_FLOAT, 0, 0L);

            //
            // Use the camera to setProjectionOrtho and generate a viewProjMatrix
            //

            camera.setProjectionOrtho();
            Matrix4f viewProjMatrix = camera.getProjectionMatrix();

            glUniformMatrix4fv(vpMatLocation, false,
                    viewProjMatrix.get(myFloatBuffer));

            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

            //
            // Color squares using GoL rules
            //

            int ibps = 24;
            int dvps = 6;

            for (int ci = 0; ci < rows * cols; ++ci) {
                int currRow = ci / cols;
                int currCol = ci % cols;

                if (GoLBoard.isAlive(currRow, currCol)) {
                    glUniform3f(renderColorLocation, liveColor.x, liveColor.y, liveColor.z);
                } else {
                    glUniform3f(renderColorLocation, deadColor.x, deadColor.y, deadColor.z);
                }
                glDrawElements(GL_TRIANGLES, dvps, GL_UNSIGNED_INT, ibps*ci);
                GoLBoard.updateNextCellArray();
            }  //  for (int ci = 0; ci < NUM_POLY_ROWS * NUM_POLY_COLS; ++ci)
            glfwSwapBuffers(WINDOW);
        }
    } // renderObjects

    private void slSingleBatchPrinter() {
        System.out.println("Call to slSingleBatchRenderer:: () == received!");
    }
} // public class SlSingleBatchRenderer {
