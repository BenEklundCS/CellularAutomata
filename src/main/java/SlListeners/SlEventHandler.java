package SlListeners;

import org.lwjgl.opengl.GL;

import static SlGoL.Spot.*;
import static org.lwjgl.glfw.GLFW.*;

public class SlEventHandler {
    public SlEventHandler() {
        init();
    }
    public void processEvents() {

        boolean key_pressed = false;

        if (SlKeyListener.isKeyPressed(GLFW_KEY_D)) {
            System.out.println(
                    (!DELAY) ? "Delay enabled." : "Delay disabled."
            );
            DELAY = !DELAY;
            key_pressed = true;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_D);
        }

        if (SlKeyListener.isKeyPressed(GLFW_KEY_H)) {
            key_pressed = true;
            // Make sure to render the last frame to catch up - we do this here because the HALT_RENDERING flag isn't a toggle
            SlKeyListener.resetKeypressEvent(GLFW_KEY_H);
        }

        if (SlKeyListener.isKeyPressed(GLFW_KEY_F)) {
            System.out.println(
                    (!FPS) ? "Fps enabled." : "Fps disabled."
            );
            FPS = !FPS;
            key_pressed = true;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_F);
        }

        if (SlKeyListener.isKeyPressed(GLFW_KEY_R)) {
            System.out.println("Reset.");
            RESET = true;
            key_pressed = true;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_R);
        }

        if (SlKeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            if (HALT_RENDERING) {
                System.out.println("Render started.");
                HALT_RENDERING = false;
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
            key_pressed = true;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_SLASH);
            SlKeyListener.resetKeypressEvent(GLFW_KEY_LEFT_SHIFT);
        }

        if (SlKeyListener.isKeyPressed(GLFW_KEY_S)) {
            SAVE_TO_FILE = true;
            key_pressed = true;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_S);
        }

        if (SlKeyListener.isKeyPressed(GLFW_KEY_L)) {
            LOAD_FROM_FILE = true;
            key_pressed = true;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_L);
        }

        if (SlKeyListener.isKeyPressed(GLFW_KEY_C)) {
            RESTART = true;
            System.out.println("Render restarted.");
            key_pressed = true;
            SlKeyListener.resetKeypressEvent(GLFW_KEY_C);
        }

        // If key_pressed then we halt until the user resumes, we don't need to do this if the app is already halted though
        if (key_pressed) {
            // If a key event takes place, always render one frame
            if (!HALT_RENDERING) {
                System.out.println("Render halted.");
                HALT_RENDERING = true;
                // Always render one frame when halting so render matches game state
            }
        }
    }

    private void init() {
        glfwSetKeyCallback(WINDOW, SlKeyListener::keyCallback);
    }
}
