package CSC133;

import slRenderer.slSingleBatchRenderer;

public class Main {
    static int WIN_WIDTH = 1800, WIN_HEIGHT = 1200;

    public static void main(String[] args) {
        //new CSC133.slWindow().slWindow(WIN_WIDTH, WIN_HEIGHT);
        slWindow slWindow = new slWindow(WIN_WIDTH, WIN_HEIGHT);
        new slSingleBatchRenderer(slWindow).render(); // all logic is currently here
    } // public static void main(String[] args)
}
