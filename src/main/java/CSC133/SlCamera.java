package CSC133;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static CSC133.Spot.*;

public class SlCamera {
    private final Matrix4f viewProjMatrix = new Matrix4f().identity();
    private final Matrix4f viewMatrix = new Matrix4f().identity();
    private float f_left = 0;
    private float f_right = WIN_WIDTH;
    private float f_bottom = 0;
    private float f_top = WIN_HEIGHT;
    private float f_near = 0;
    private float f_far = 10;
    public final Vector3f defaultLookFrom = new Vector3f(0f, 0f, 10f);
    public final Vector3f defaultLookAt = new Vector3f(0f, 0f, -1.0f);
    public final Vector3f defaultUpVector = new Vector3f(0f, 1.0f, 0f);
    private final Vector3f curLookFrom = new Vector3f();
    private final Vector3f curLookAt = new Vector3f();
    private final Vector3f curUpVector = new Vector3f();

    private void setCamera() {

    }

    public SlCamera(Vector3f camera_position) {

    }

    public SlCamera() {

    }

    public void setProjectionOrtho() {
        // Ensure to set the Matrix to identity before calling setOrtho on it
        viewProjMatrix.identity();
        viewProjMatrix.setOrtho(f_left, f_right, f_bottom, f_top, f_near, f_far);
    }

    public void setProjectionOrtho(float left, float right, float bottom, float top, float near, float far) {
        f_left = left;
        f_right = right;
        f_bottom = bottom;
        f_top = top;
        f_near = near;
        f_far = far;
        setProjectionOrtho();
    }

    public float[] getOrtho() { // custom method to get all the Ortho coordinates
        return new float[] {f_left, f_right, f_bottom, f_top, f_near, f_far};
    }

    public Matrix4f getViewMatrix() {
        viewMatrix.identity();
        viewMatrix.lookAt(curLookFrom, curLookAt.add(defaultLookFrom), curUpVector);
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return viewProjMatrix;
    }

    private void slCameraPrinter() {
        System.out.println("Call to slCamera:: () == received!");
    }
}
