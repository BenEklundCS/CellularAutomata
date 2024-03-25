package SlRenderer;

import SlGoL.SlCamera;
import SlGoL.SlMetaUI;
import SlGoL.SlWindow;
import SlGoLBoard.SlGoLBoardLive;
import SlListeners.SlEventHandler;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.File;

import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniform3f;

import static SlGoL.Spot.*;

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

        //
        // Generate GoL board from rows and cols of the grid
        //

        SlGoLBoardLive goLBoard = new SlGoLBoardLive(MAX_ROWS, MAX_COLS);

        //
        // Set up event handler and register callbacks
        //

        SlEventHandler eventHandler = new SlEventHandler();

        SlCamera camera = new SlCamera();

        long start_render_time;
        long end_render_time;

        //
        //  Begin rendering while loop
        //

        while (!glfwWindowShouldClose(WINDOW)) {

            start_render_time = System.currentTimeMillis();

            glfwPollEvents(); // sends events from the GLFW window

            eventHandler.processEvents();

            // When processing a file save action, we do the following here:
                // - render the scene to reflect the CURRENT game state
                // - get a file name from the user

            if (SAVE_TO_FILE) {
                String file_name = SlMetaUI.getFileName();
                if (file_name != null) {
                    goLBoard.save(file_name); // save to the file
                }
                SAVE_TO_FILE = false;
            }

            // Load from file will get a file from the user and then render the scene with the new file

            if (LOAD_FROM_FILE) {
                File file = SlMetaUI.getFile();
                if (file != null) {
                    //GoLBoard.setAllDead(); // allow loading of smaller boards onto larger spaces
                    goLBoard.setAllDead();
                    goLBoard.load(file); // load GoLBoard from the file
                    int numRows = goLBoard.getNumRows();
                    int numCols = goLBoard.getNumCols();
                    // Apply the new dimensions to the engine state
                        // MUST BE CALLED
                    SET_DIMENSIONS(numRows, numCols);
                }
                LOAD_FROM_FILE = false;
                // Render the scene a few times to ensure GL catches up with the new Game State
                glViewport(0, 0, WIN_WIDTH, WIN_HEIGHT); // Update the OpenGL viewport here
            }

            // If the RESET flag is set, the user expects the GoLBoard to reset once

            if (RESET) {
                goLBoard = new SlGoLBoardLive(MAX_ROWS, MAX_COLS); // create a new randomized GoLBoard
                RESET = false;
            }

            if (RESTART) {
                goLBoard.restart();
                RESTART = false;
            }

            // If delay is set we will sleep while polling for events to remain responsive

            if (DELAY) {
                long delayEnd = System.currentTimeMillis() + 500;
                while (System.currentTimeMillis() < delayEnd) {
                    try {
                        // Sleep for a short period to maintain responsiveness
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle interrupted exception
                    }
                    glfwPollEvents(); // Poll for events during the delay
                    eventHandler.processEvents(); // process any incoming events during the delay polls
                }
            }

            // If USAGE is set the user expects the Meta UI to print the usage

            if (USAGE) {
                SlMetaUI.printUsage(); // Allow the UI to print its usage
                USAGE = false; // Toggle back to false
            }

            // Render call is now encapsulated in renderScene
            if (!HALT_RENDERING) {
                goLBoard.updateNextCellArray(); // never update to the next cell array unless the renderer is un-halted
            }

            else {
                // wait for events with a responsive timeout
                glfwWaitEventsTimeout(0.1);
            }

            //
            // Vertices / Indices generator
            //

            SlGridOfSquares grid = new SlGridOfSquares(MAX_ROWS, MAX_COLS);

            float[] vertices = grid.getVertices();
            int[] indices = grid.getIndices();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            int vbo = glGenBuffers();
            int ibo = glGenBuffers();

            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, BufferUtils.
                    createFloatBuffer(vertices.length).
                    put(vertices).flip(), GL_STATIC_DRAW);
            glEnableClientState(GL_VERTEX_ARRAY);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.
                    createIntBuffer(indices.length).
                    put(indices).flip(), GL_STATIC_DRAW);

            final int SIZE = 2;

            glVertexPointer(SIZE, GL_FLOAT, 0, 0L);

            //
            // Use the camera to setProjectionOrtho and generate a viewProjMatrix
            //
            camera.setProjectionOrtho(0, WIN_WIDTH, 0, WIN_HEIGHT, 0, 10);
            Matrix4f viewProjMatrix = camera.getProjectionMatrix();

            glUniformMatrix4fv(vpMatLocation, false,
                    viewProjMatrix.get(myFloatBuffer));
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

            //
            // Color squares using GoL rules
            //

            int ibps = 24;
            int dvps = 6;

            for (int i = 0; i < MAX_ROWS * MAX_COLS; ++i) {
                int currRow = i / MAX_COLS;
                int currCol = i % MAX_COLS;

                if (goLBoard.isAlive(currRow, currCol)) {
                    glUniform3f(renderColorLocation, LIVE_COLOR.x, LIVE_COLOR.y, LIVE_COLOR.z);
                } else {
                    glUniform3f(renderColorLocation, DEAD_COLOR.x, DEAD_COLOR.y, DEAD_COLOR.z);
                }
                glDrawElements(GL_TRIANGLES, dvps, GL_UNSIGNED_INT, (long) ibps * i);
            }  //  for (int i = 0; i < NUM_POLY_ROWS * NUM_POLY_COLS; ++i)
            glfwSwapBuffers(WINDOW);

            glDeleteBuffers(ibo);
            glDeleteBuffers(vbo);

            end_render_time = System.currentTimeMillis();

            if (FPS) {
                SlMetaUI.fps(start_render_time, end_render_time);
            }
        }
    } // renderObjects
    private void slSingleBatchPrinter() {
        System.out.println("Call to slSingleBatchRenderer:: () == received!");
    }
} // public class SlSingleBatchRenderer {
