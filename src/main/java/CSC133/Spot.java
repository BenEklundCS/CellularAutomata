package CSC133;

import SlGoLBoard.SlGoLBoardLive;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

// Single point of truth
public class Spot {
    // Application settings and properties
    public static int WIN_POS_X = 30, WIN_POS_Y = 90; // (30, 90)
    public static long WINDOW = NULL; // NULL
    public static int MAX_ROWS = 60; // 18
    public static int MAX_COLS = 60; // 20
    public static void SET_DIMENSIONS(int rows, int cols) {
        MAX_ROWS = rows;
        MAX_COLS = cols;
        WIN_WIDTH = setWindowCalc(MAX_COLS); // Recalculate window width
        WIN_HEIGHT = setWindowCalc(MAX_ROWS); // Recalculate window height
        // I'm loading in ANY scope of rows and cols :)
        if(WINDOW != NULL) {
            // Resize the GLFW window to match the new dimensions
            SlWindow.resizeWindow(WIN_WIDTH, WIN_HEIGHT);
            glViewport(0, 0, WIN_WIDTH, WIN_HEIGHT); // Update the OpenGL viewport here
        }
    }
    public final static int OFFSET = 40;
    public final static int PADDING = 5; // 15
    public final static int LENGTH = 15; // 30
    public static int WIN_WIDTH = setWindowCalc(MAX_COLS); // MAX_COLS
    public static int WIN_HEIGHT = setWindowCalc(MAX_ROWS); // MAX_ROWS

    public static Vector4f DEAD_COLOR = new Vector4f(0.0f, 0f, 1.0f, 1.0f); // 1 0 0 1
    public static Vector4f LIVE_COLOR = new Vector4f(0.0f, 1.0f, 0.5f, 1.0f); // 0 1 0 1

    public static void printSpot() {
        System.out.println(WIN_WIDTH);
        System.out.println(WIN_HEIGHT);
        System.out.println(MAX_ROWS);
        System.out.println(MAX_COLS);
    }

    // Methods

    public static int setWindowCalc(int x) {
        return (2 * OFFSET) + (x - 1) * PADDING + (x * LENGTH);
    }

    // META Menu Flags

    public static boolean DELAY = false;
    public static boolean HALT_RENDERING = false;
    public static boolean FPS = false;
    public static boolean RESET = false;
    public static boolean RESTART = false;
    public static boolean USAGE = false;
    public static boolean SAVE_TO_FILE = false;
    public static boolean LOAD_FROM_FILE = false;
}
