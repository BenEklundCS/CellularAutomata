package CSC133;

import org.joml.Vector4f;

import static org.lwjgl.system.MemoryUtil.NULL;

// Single point of truth
public class Spot {
    public static int WIN_WIDTH = 1800;
    public static int WIN_HEIGHT = 1200;
    public static int WIN_POS_X = 30, WIN_POS_Y = 90; // (30, 90)
    public static long WINDOW = NULL;
    public static int MAX_ROWS = 18; // 18
    public static int MAX_COLS = 20; // 20
    public final static int OFFSET = 10;
    public final static int PADDING = 15;
    public static float WIDTH = 2 * OFFSET + MAX_COLS * PADDING;
    public static float HEIGHT = 2 * OFFSET + MAX_ROWS * PADDING;
    public static Vector4f DEAD_COLOR = new Vector4f(1.0f, 0f, 0f, 1.0f);
    public static Vector4f LIVE_COLOR = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);

    // Settings
    public static boolean DELAY = false;
    public static boolean HALT_RENDERING = false;
}
