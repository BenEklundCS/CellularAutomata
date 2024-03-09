package CSC133;

import org.joml.Vector4f;

import static org.lwjgl.system.MemoryUtil.NULL;

// Single point of truth
public class Spot {
    public static int WIN_WIDTH = 1800, WIN_HEIGHT = 1200, WIN_POS_X = 30, WIN_POS_Y = 90; // (30, 90)
    public static long WINDOW = NULL;

    public static Vector4f deadColor = new Vector4f(0.0f, 0f, 0f, 1.0f);
    public static Vector4f liveColor = new Vector4f(0.0f, 1.0f, 1.0f, 1.0f);
}
