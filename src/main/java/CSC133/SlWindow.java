package CSC133;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import static CSC133.Spot.*;

// put window stuff here
public class SlWindow {
    private static GLFWKeyCallback keyCallback;
    private static GLFWFramebufferSizeCallback fbCallback;

    public static long get(int win_width, int win_height, int pos_x, int pos_y) {
        WIN_WIDTH = win_width;
        WIN_HEIGHT = win_height;
        WIN_POS_X = pos_x;
        WIN_POS_Y = pos_y;
        return get();
    } // public static long get(int win_width, int win_height)

    public static long get() {
        if (WINDOW == NULL) {
            initGLFWindow(); // Prevents a lot of problems with singleton pattern :)
        }
        slWindowPrinter();
        return WINDOW;
    } // private static long get() {

    private static void initGLFWindow() {

        final int MSAA = 8; // Extracted MSAA value to encapsulated variable

        GLFWErrorCallback errorCallback;
        glfwSetErrorCallback(errorCallback =
                GLFWErrorCallback.createPrint(System.err));

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, MSAA);

        // This can never happen twice
        WINDOW = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT, "CSC 133", NULL, NULL);

        if (WINDOW == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(WINDOW, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int
                    mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true);
            }
        });

        glfwSetFramebufferSizeCallback(WINDOW, fbCallback = new
                GLFWFramebufferSizeCallback() {
                    @Override
                    public void invoke(long window, int w, int h) {
                        if (w > 0 && h > 0) {
                            WIN_WIDTH = w;
                            WIN_HEIGHT = h;
                            glViewport(0, 0, WIN_WIDTH, WIN_HEIGHT); // Update the OpenGL viewport here
                        }
                    }
                });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(WINDOW, WIN_POS_X, WIN_POS_Y);
        glfwMakeContextCurrent(WINDOW);
        int VSYNC_INTERVAL = 1;
        glfwSwapInterval(VSYNC_INTERVAL);
        glfwShowWindow(WINDOW);
    } // private void initGLFWindow()

    public static void resizeWindow(int width, int height) {
        if (WINDOW != NULL) {
            glfwSetWindowSize(WINDOW, width, height);
        }
    }

    public static void destroyGLFWindow() {
        glfwDestroyWindow(WINDOW);
        keyCallback.free();
        fbCallback.free();
    } // public static void destroyGLFWindow() {

    // I guess I can call this in my slWindow constructor
    private static void slWindowPrinter() {
        System.out.println("Call to slWindow:: (width, height) == ("
                        + WIN_WIDTH + ", " + WIN_HEIGHT +") received!");
    } // static void slWindowPrinter()
} // public class SlWindow {
