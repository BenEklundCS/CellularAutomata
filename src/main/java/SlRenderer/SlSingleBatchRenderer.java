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

import static SlGoL.Spot.*;

public class SlSingleBatchRenderer {
    private static final int OGL_MATRIX_SIZE = 16;
    private final FloatBuffer myFloatBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
    private int vpMatLocation = 0;

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
        // og: 0.0f, 0.0f, 0.3f, 1.0f;
        // new: 0.8f, 0.8f, 0.8f, 1.0f
        final float BG_RED = 0.2f;
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
                        " gl_FrontColor = gl_Color;" +
                        "}");

        glCompileShader(vs);
        glAttachShader(shader_program, vs);
        int fs = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fs,
                "void main(void) {" +
                        // This guy sets the shape color :)
                        " gl_FragColor = gl_Color;" +
                        "}");

        glCompileShader(fs);
        glAttachShader(shader_program, fs);
        glLinkProgram(shader_program);
        glUseProgram(shader_program);
        vpMatLocation = glGetUniformLocation(shader_program, "viewProjMatrix");
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

        //
        // Vertices / Indices generator - CREATE ONCE OUTSIDE LOOP
        //

        SlRenderable renderable = new SlGridOfSquares(MAX_ROWS, MAX_COLS);
        float[] vertices = renderable.getVertices();
        int[] indices = renderable.getIndices();

        int vbo = glGenBuffers();
        int ibo = glGenBuffers();
        int colorVbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.
                createFloatBuffer(vertices.length).
                put(vertices).flip(), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.
                createIntBuffer(indices.length).
                put(indices).flip(), GL_STATIC_DRAW);

        final int SIZE = 2;

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

                    // Regenerate buffers when dimensions change
                    glDeleteBuffers(colorVbo);
                    glDeleteBuffers(ibo);
                    glDeleteBuffers(vbo);

                    renderable = new SlGridOfSquares(MAX_ROWS, MAX_COLS);
                    vertices = renderable.getVertices();
                    indices = renderable.getIndices();

                    vbo = glGenBuffers();
                    ibo = glGenBuffers();
                    colorVbo = glGenBuffers();

                    glBindBuffer(GL_ARRAY_BUFFER, vbo);
                    glBufferData(GL_ARRAY_BUFFER, BufferUtils.
                            createFloatBuffer(vertices.length).
                            put(vertices).flip(), GL_STATIC_DRAW);

                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
                    glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.
                            createIntBuffer(indices.length).
                            put(indices).flip(), GL_STATIC_DRAW);
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

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

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

            int verticesPerSquare = 4;
            float[] colors = new float[MAX_ROWS * MAX_COLS * verticesPerSquare * 3];

            for (int i = 0; i < MAX_ROWS * MAX_COLS; ++i) {
                int currRow = i / MAX_COLS;
                int currCol = i % MAX_COLS;

                float r, g, b;
                if (goLBoard.isAlive(currRow, currCol)) {
                    r = LIVE_COLOR.x;
                    g = LIVE_COLOR.y;
                    b = LIVE_COLOR.z;
                } else {
                    r = DEAD_COLOR.x;
                    g = DEAD_COLOR.y;
                    b = DEAD_COLOR.z;
                }

                for (int v = 0; v < verticesPerSquare; v++) {
                    int idx = (i * verticesPerSquare + v) * 3;
                    colors[idx] = r;
                    colors[idx + 1] = g;
                    colors[idx + 2] = b;
                }
            }  //  for (int i = 0; i < NUM_POLY_ROWS * NUM_POLY_COLS; ++i)

            glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
            glBufferData(GL_ARRAY_BUFFER, BufferUtils.
                    createFloatBuffer(colors.length).
                    put(colors).flip(), GL_DYNAMIC_DRAW);

            glEnableClientState(GL_COLOR_ARRAY);
            glColorPointer(3, GL_FLOAT, 0, 0L);

            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glEnableClientState(GL_VERTEX_ARRAY);
            glVertexPointer(SIZE, GL_FLOAT, 0, 0L);

            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0L);

            glfwSwapBuffers(WINDOW);

            end_render_time = System.currentTimeMillis();

            if (FPS) {
                SlMetaUI.fps(start_render_time, end_render_time);
            }
        }

        // Cleanup buffers after render loop ends
        glDeleteBuffers(colorVbo);
        glDeleteBuffers(ibo);
        glDeleteBuffers(vbo);
    } // renderObjects

    private void slSingleBatchPrinter() {
        System.out.println("Call to slSingleBatchRenderer:: () == received!");
    }
} // public class SlSingleBatchRenderer {