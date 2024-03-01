package SlRenderer;

import java.util.Random;

public class SlRenderUtils {
    private final static Random random = new Random();
    public static float randomRGB() {
        return random.nextFloat();
    }
}
