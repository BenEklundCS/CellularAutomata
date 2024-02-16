package SlRenderer;

import CSC133.SlWindow;

public class SlGridOfSquares {
    private final int MAX_ROWS;
    private final int MAX_COLS;
    private final int VERTICES_PER_SQUARE = 4;
    private final int WIN_HEIGHT = SlWindow.getWinHeight();
    private final int WIN_WIDTH = SlWindow.getWinWidth();
    private final int RIGHT;
    private final int TOP;

    SlGridOfSquares(int maxRows, int maxCols, int RIGHT, int TOP) {
        this.MAX_ROWS = maxRows;
        this.MAX_COLS = maxCols;
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

        float xMin = offset;
        float xMax = xMin + length;
        float yMax = WIN_HEIGHT - offset;
        float yMin = yMax - length;
        int index = 0;

        float[] vertices = new float[VERTICES_PER_SQUARE * floatsPerVertex * MAX_ROWS * MAX_COLS];

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
                xMin = xMax + padding;
                xMax = xMin + length;
            }
            xMin = offset;
            xMax = xMin + length;
            yMax = yMin - padding;
            yMin = yMax - length;
        }
        return vertices;
    }

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
            vIndex += VERTICES_PER_SQUARE;
        }
        return indices;
    }
}
