package CSC133;

import org.joml.Vector4f;

import static org.lwjgl.system.MemoryUtil.NULL;

// Single point of truth
public class Spot {
    public static int WIN_POS_X = 30, WIN_POS_Y = 90; // (30, 90)
    public static long WINDOW = NULL;
    public static int MAX_ROWS = 25; // 18
    public static int MAX_COLS = 50; // 20
    public final static int OFFSET = 40;
    public final static int PADDING = 15;
    public final static int LENGTH = 30; // 30
    public static int WIN_WIDTH = setWindowCalc(MAX_COLS);
    public static int WIN_HEIGHT = setWindowCalc(MAX_ROWS);
    public static Vector4f DEAD_COLOR = new Vector4f(1.0f, 0f, 0f, 1.0f);
    public static Vector4f LIVE_COLOR = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);

    // Methods

    public static int setWindowCalc(int x) {
        return (2 * OFFSET) + (x - 1) * PADDING + x * LENGTH;
    }

    // META Menu
    public static boolean DELAY = false;
    public static boolean HALT_RENDERING = false;
    public static boolean RENDER_ONE_FRAME = false;
    public static boolean FPS = false;
    public static boolean RESET = false;
    public static boolean RESTART = false;
    public static boolean USAGE = false;
    public static boolean SAVE_TO_FILE = false;
    public static boolean LOAD_FROM_FILE = false;
    public static boolean KILL = false;
}
