package CSC133;

import SlRenderer.SlSingleBatchRenderer;

public class Main {
    static int WIN_WIDTH = 1800, WIN_HEIGHT = 1200, WIN_POS_X = 30, WIN_POS_Y = 90;
    public static void main(String[] args) {
        long window = SlWindow.get(WIN_WIDTH, WIN_HEIGHT, WIN_POS_X, WIN_POS_Y);
        new SlSingleBatchRenderer(window).render();
        SlWindow.destroyGLFWindow();
    } // public static void main(String[] args)
} // public class Main {
