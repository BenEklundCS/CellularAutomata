package SlRenderer;

import static CSC133.Spot.*;

public class SlGridOfSquares {
    private final int maxRows;
    private final int maxCols;
    private final float right;
    private final float top;
    private final int verticesPerSquare = 4;

    SlGridOfSquares(int maxRows, int maxCols, float[] ortho) {
        final int right = 1;
        final int top = 3;
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.right = ortho[right];
        this.top = ortho[top];
    } // SlGridOfSquares(int maxRows, int maxCols) {

    public float[] getVertices() {
        final int floatsPerVertex = 2;
        final int length = 30;
        final int offset = 10;
        final int padding = 15;

        float yScale = top / WIN_HEIGHT;
        float xScale = right / WIN_WIDTH;

        float xMin = offset;
        float xMax = xMin + length;
        float yMax = WIN_HEIGHT - offset;
        float yMin = yMax - length;
        int index = 0;

        float[] vertices = new float[verticesPerSquare * floatsPerVertex * maxRows * maxCols];

        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxCols; j++) {
                vertices[index++] = xMin * xScale;
                vertices[index++] = yMin * yScale;
                vertices[index++] = xMax * xScale;
                vertices[index++] = yMin * yScale;
                vertices[index++] = xMax * xScale;
                vertices[index++] = yMax * yScale;
                vertices[index++] = xMin * xScale;
                vertices[index++] = yMax * yScale;
                xMin = xMax + padding;
                xMax = xMin + length;
            }
            xMin = offset;
            xMax = xMin + length;
            yMax = yMin - padding;
            yMin = yMax - length;
        }
        return vertices;
    } // public float[] getVertices() {

    public int[] getIndices() {
        final int indicesPerSquare = 6;

        int[] indices = new int[maxRows * maxCols * indicesPerSquare];
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
