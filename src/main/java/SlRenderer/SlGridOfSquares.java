package SlRenderer;

import static CSC133.Spot.*;

public class SlGridOfSquares {
    private final int verticesPerSquare = 4;

    SlGridOfSquares() {

    } // SlGridOfSquares() {

    public float[] getVertices() {
        final int floatsPerVertex = 2;
        final int length = 30;

        final float xScale = WIDTH / WIN_WIDTH;
        final float yScale = HEIGHT / WIN_HEIGHT;

        float xMin = OFFSET;
        float xMax = xMin + length;
        float yMax = WIN_HEIGHT - OFFSET;
        float yMin = yMax - length;
        int index = 0;

        float[] vertices = new float[verticesPerSquare * floatsPerVertex * MAX_ROWS * MAX_COLS];

        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                vertices[index++] = xMin * xScale;
                vertices[index++] = yMin * yScale;
                vertices[index++] = xMax * xScale;
                vertices[index++] = yMin * yScale;
                vertices[index++] = xMax * xScale;
                vertices[index++] = yMax * yScale;
                vertices[index++] = xMin * xScale;
                vertices[index++] = yMax * yScale;
                xMin = xMax + PADDING;
                xMax = xMin + length;
            }
            xMin = OFFSET;
            xMax = xMin + length;
            yMax = yMin - PADDING;
            yMin = yMax - length;
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
