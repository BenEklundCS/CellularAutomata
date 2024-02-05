package SlRenderer;

import CSC133.SlWindow;

public class SlGridOfSquares {
    private final int maxRows;
    private final int maxCols;
    private final int verticesPerSquare = 4;
    private final int WIN_HEIGHT = SlWindow.getWinHeight();

    SlGridOfSquares(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
    }
    public float[] getVertices() {
        final int floatsPerVertex = 2;
        final int length = 10;
        final int offset = 10;
        final int padding = 5;

        float xmin = offset;
        float xmax = xmin + length;
        float ymax = WIN_HEIGHT - offset;
        float ymin = ymax - length;
        int index = 0;

        float[] vertices = new float[verticesPerSquare * floatsPerVertex * maxRows * maxCols];

        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxCols; j++) {
                vertices[index++] = xmin;
                vertices[index++] = ymin;
                vertices[index++] = xmax;
                vertices[index++] = ymin;
                vertices[index++] = xmax;
                vertices[index++] = ymax;
                vertices[index++] = xmin;
                vertices[index++] = ymax;
                xmin = xmax + padding;
                xmax = xmin + length;
            }
            xmin = offset;
            xmax = xmin + length;
            ymax = ymin - padding;
            ymin = ymax - length;
        }
        return vertices;
    }
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
    }
}
