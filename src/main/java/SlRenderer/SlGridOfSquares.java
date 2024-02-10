package SlRenderer;

import CSC133.SlWindow;

public class SlGridOfSquares {
    private final int maxRows;
    private final int maxCols;
    private final int verticesPerSquare = 4;
    private final int WIN_HEIGHT = SlWindow.getWinHeight();
    private final int WIN_WIDTH = SlWindow.getWinWidth();
    private final int RIGHT;
    private final int TOP;

    SlGridOfSquares(int maxRows, int maxCols, int RIGHT, int TOP) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.RIGHT = RIGHT;
        this.TOP = TOP;
    }
    public float[] getVertices() {
        final int floatsPerVertex = 2;
        final int length = 10;
        final int offset = 10;
        final int padding = 5;

        float yScale = (float) TOP / WIN_HEIGHT;

        float xScale = (float) RIGHT / WIN_WIDTH;

        float xmin = offset;
        float xmax = xmin + length;
        float ymax = WIN_HEIGHT - offset;
        float ymin = ymax - length;
        int index = 0;

        float[] vertices = new float[verticesPerSquare * floatsPerVertex * maxRows * maxCols];

        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxCols; j++) {
                vertices[index++] = xmin * xScale;
                vertices[index++] = ymin * yScale;
                vertices[index++] = xmax * xScale;
                vertices[index++] = ymin * yScale;
                vertices[index++] = xmax * xScale;
                vertices[index++] = ymax * yScale;
                vertices[index++] = xmin * xScale;
                vertices[index++] = ymax * yScale;
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
