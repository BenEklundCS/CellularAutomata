package CSC133;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SlCamera {
    private final Matrix4f viewProjMatrix = new Matrix4f();
    private final Matrix4f viewMatrix = new Matrix4f();
    private float f_left = 0;
    private float f_right = 200;
    private float f_bottom = 0;
    private float f_top = 200;
    private float f_near = 0;
    private float f_far = 10;
    public Vector3f defaultLookFrom = new Vector3f();
    public Vector3f defaultLookAt = new Vector3f();
    public Vector3f defaultUpVector = new Vector3f();
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

    public float getRight() {
        return f_right;
    }

    public float getTop() {
        return f_top;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return viewProjMatrix;
    }

    private void slCameraPrinter() {
        System.out.println("Call to slCamera:: () == received!");
    }
}
