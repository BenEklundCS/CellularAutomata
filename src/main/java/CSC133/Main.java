package CSC133;

import slRenderer.slSingleBatchRenderer;

public class Main {
    static int WIN_WIDTH = 1800, WIN_HEIGHT = 1200;

    public static void main(String[] args) {
        long window = slWindow.get(WIN_WIDTH, WIN_HEIGHT);
        slWindow.slWindowPrinter();
        new slSingleBatchRenderer(window, WIN_WIDTH, WIN_HEIGHT).render(); // This was a good idea and is typically correct, but the
                                                      // professor pointed out that this will lead to double context
                                                      // creation issues in the future. Well done!
        // Call some slWindow destroy method here??  YES BANG
        slWindow.destroyGLFWindow();
    } // public static void main(String[] args)
}
