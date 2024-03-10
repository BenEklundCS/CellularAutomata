package SlListeners;

import static CSC133.Spot.*;
import static org.lwjgl.glfw.GLFW.*;

public class SlEventHandler {
    public SlEventHandler() {
        init();
    }
    public void processEvents() {
        if (SlKeyListener.isKeyPressed(GLFW_KEY_D)) {
            System.out.println(
                    (!DELAY) ? "Delay enabled." : "Delay disabled."
            );
            DELAY = !DELAY;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_D);
        }
        if (SlKeyListener.isKeyPressed(GLFW_KEY_H)) {
            HALT_RENDERING = !HALT_RENDERING;
            System.out.println(
                    (!HALT_RENDERING) ? "Render started." : "Render halted."
            );
            if (HALT_RENDERING) {
                RENDER_ONE_FRAME = true;
            }
            SlKeyListener.resetKeypressEvent(GLFW_KEY_H);
        }
        if (SlKeyListener.isKeyPressed(GLFW_KEY_F)) {
            System.out.println(
                    (!FPS) ? "Fps enabled." : "Fps disabled."
            );
            FPS = !FPS;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_F);
        }
        if (SlKeyListener.isKeyPressed(GLFW_KEY_R)) {
            System.out.println("Reset.");
            RESET = true;
            if (HALT_RENDERING) {
                RENDER_ONE_FRAME = true;
            }
            SlKeyListener.resetKeypressEvent(GLFW_KEY_R);
        }
        if (SlKeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            System.out.println("Restarted.");
            RESTART = true;
            if (HALT_RENDERING) {
                RENDER_ONE_FRAME = true;
            }
            SlKeyListener.resetKeypressEvent(GLFW_KEY_SPACE);
        }
        if (SlKeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
            System.out.println("Exiting.");
            glfwSetWindowShouldClose(WINDOW, true);
            SlKeyListener.resetKeypressEvent(GLFW_KEY_ESCAPE);
        }
        if (SlKeyListener.isKeyPressed(GLFW_KEY_SLASH) && SlKeyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            USAGE = true;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_SLASH);
            SlKeyListener.resetKeypressEvent(GLFW_KEY_LEFT_SHIFT);
        }
        if (SlKeyListener.isKeyPressed(GLFW_KEY_S)) {
            SAVE_TO_FILE = true;
            HALT_RENDERING = true;
            System.out.println("Render halted.");
        }
        if (SlKeyListener.isKeyPressed(GLFW_KEY_L)) {
            LOAD_FROM_FILE = true;
            HALT_RENDERING = true;
            System.out.println("Render halted.");
        }
    }

    private void init() {
        glfwSetKeyCallback(WINDOW, SlKeyListener::keyCallback);
    }
}
