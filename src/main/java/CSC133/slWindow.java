package CSC133;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

// put window stuff here
public class slWindow {
    public int WIN_WIDTH, WIN_HEIGHT;

    public long window;

    int WIN_POS_X = 30, WIN_POX_Y = 90;

    public GLFWErrorCallback errorCallback;
    public GLFWKeyCallback keyCallback;
    public GLFWFramebufferSizeCallback fbCallback;

    slWindow(int WIN_WIDTH, int WIN_HEIGHT) {
        this.WIN_WIDTH = WIN_WIDTH;
        this.WIN_HEIGHT = WIN_HEIGHT;
        slWindow(WIN_WIDTH, WIN_HEIGHT); // prints call to slWindow class
        initGLFWindow();
    }

    private void initGLFWindow() {

        final int MSAA = 8; // Extracted MSAA value to encapsulated variable

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
        glfwSetWindowPos(window, WIN_POS_X, WIN_POX_Y);
        glfwMakeContextCurrent(window);
        int VSYNC_INTERVAL = 1;
        glfwSwapInterval(VSYNC_INTERVAL);
        glfwShowWindow(window);

    } // private void initGLFWindow()

    // I guess I can call this in my slWindow constructor
    void slWindow(int win_width, int win_height) {
        System.out.println("Call to slWindow:: (width, height) == ("
                        + win_width + ", " + win_height +") received!");
    }
}
