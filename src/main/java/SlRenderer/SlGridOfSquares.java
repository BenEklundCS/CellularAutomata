package SlRenderer;

import static CSC133.Spot.*;

public class SlGridOfSquares {
    private final int verticesPerSquare = 4;

    SlGridOfSquares() {

    } // SlGridOfSquares() {

    public float[] getVertices() {
        final int floatsPerVertex = 2;

        float xMin = OFFSET;
        float xMax = xMin + LENGTH;
        float yMax = WIN_HEIGHT - OFFSET;
        float yMin = yMax - LENGTH;
        int index = 0;

        float[] vertices = new float[verticesPerSquare * floatsPerVertex * MAX_ROWS * MAX_COLS];

        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                vertices[index++] = xMin;
                vertices[index++] = yMin;
                vertices[index++] = xMax;
                vertices[index++] = yMin;
                vertices[index++] = xMax;
                vertices[index++] = yMax;
                vertices[index++] = xMin;
                vertices[index++] = yMax;
                xMin = xMax + PADDING;
                xMax = xMin + LENGTH;
            }
            xMin = OFFSET;
            xMax = xMin + LENGTH;
            yMax = yMin - PADDING;
            yMin = yMax - LENGTH;
        }
        return vertices;
    } // public float[] getVertices() {

    public int[] getIndices() {
        final int indicesPerSquare = 6;

        int[] indices = new int[MAX_ROWS * MAX_COLS * indicesPerSquare];
        int vIndex = 0, i = 0;

        while (i < indices.length) {
            indices[i++] = vIndex;
            indices[i++] = vIndex + 1;
            indices[i++] = vIndex + 2;
            indices[i++] = vIndex;
            indices[i++] = vIndex + 2;
            indices[i++] = vIndex + 3;
            vIndex += verticesPerSquare;
        }
        return indices;
    } // public int[] getIndices() {
} // public class SlGridOfSquares {
