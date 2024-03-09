package SlListeners;

import static org.lwjgl.glfw.GLFW.*;

public class SlKeyListener {
    private static SlKeyListener my_instance;
    private static final int MAX_KEYS = 400;
    private final boolean[] keyPressed = new boolean[MAX_KEYS]; // some "reasonable" number!

    private SlKeyListener() {

    }

    private static SlKeyListener get() {
        if (my_instance == null) {
            my_instance = new SlKeyListener();
        }
        return my_instance;
    }

    public static void keyCallback(long my_window, int key, int scancode,
                                   int action, int modifier_key) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE){
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode){
        if (keyCode < get().keyPressed.length) {
            return get().keyPressed[keyCode];
        } else {
            return false;
        }
    }

    // Call this function to receive one event for repeated presses:
    public static void resetKeypressEvent(int keyCode) {
        if (my_instance != null && keyCode < get().keyPressed.length) {
            my_instance.keyPressed[keyCode] = false;
        }
    }

    // Setting this key to GLFW_TRUE and calling int glfwGetKey	(...) will return positive
    // if the key was pressed, EVEN AFTER THE KEY HAS BEEN RELEASED - useful if we are only
    // interested in whether or not the key has been pressed and not in the order keys were
    // pressed
    public static void setStickyKeypressMode(long my_window, boolean b_status) {
        if (b_status) {
            glfwSetInputMode(my_window, GLFW_STICKY_KEYS, GLFW_TRUE);
        } else {
            glfwSetInputMode(my_window, GLFW_STICKY_KEYS, GLFW_FALSE);
        }
        return;
    }  //  public static void setStickyKeypressMode(boolean b_status)

}  //  public class slKeyListener
