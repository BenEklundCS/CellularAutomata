package CSC133;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

// put window stuff here
public class slWindow {
    private static int WIN_WIDTH, WIN_HEIGHT;
    private static int WIN_POS_X = 30, WIN_POS_Y = 90;
    //public long window;
    private static long window = NULL;
    private static GLFWKeyCallback keyCallback;
    private static GLFWFramebufferSizeCallback fbCallback;

    private slWindow() {

    }

    public static long get(int win_width, int win_height) {
        //WIN_POS_X = pos_x;
        //WIN_POS_Y = pos_y;
        WIN_WIDTH = win_width;
        WIN_HEIGHT = win_height;
        return get();
    }

    private static long get() {
        if (window == NULL) {
            initGLFWindow();
        }
        return window;
    }

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

        window = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT, "CSC 133", NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int
                    mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true);
            }
        });

        glfwSetFramebufferSizeCallback(window, fbCallback = new
                GLFWFramebufferSizeCallback() {
                    @Override
                    public void invoke(long window, int w, int h) {
                        if (w > 0 && h > 0) {
                            WIN_WIDTH = w;
                            WIN_HEIGHT = h;
                        }
                    }
                });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, WIN_POS_X, WIN_POS_Y);
        glfwMakeContextCurrent(window);
        int VSYNC_INTERVAL = 1;
        glfwSwapInterval(VSYNC_INTERVAL);
        glfwShowWindow(window);

    } // private void initGLFWindow()

    public static void destroyGLFWindow() {
        glfwDestroyWindow(window);
        keyCallback.free();
        fbCallback.free();
    }

    // I guess I can call this in my slWindow constructor
    static void slWindowPrinter() {
        System.out.println("Call to slWindow:: (width, height) == ("
                        + WIN_WIDTH + ", " + WIN_HEIGHT +") received!");
    }
}
