package SlGoLBoard;

public class SlGoLBoardLive extends SlGoLBoard {

    public SlGoLBoardLive(int numRows, int numCols) {
        super(numRows, numCols);
    }
    @Override
    public int countLiveTwoDegreeNeighbors(int row, int col) {
        boolean[][] liveCellArray = getLiveCellArray();

        int count = 0, start = -1;

        for (int i = start; i <= 1; i++) {
            for (int j = start; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    // Solution
                    int newRow = (row + i + NUM_ROWS) % NUM_ROWS;
                    int newCol = (col + j + NUM_COLS) % NUM_COLS;
                    if (newRow >= 0 && newRow < NUM_ROWS && newCol >= 0 && newCol < NUM_COLS) {
                        if (liveCellArray[newRow][newCol])
                            count++;
                    }
                }
            }
        }
        return count;
    } // public int countLiveTwoDegreeNeighbors(int row, int col) {

    // return how many live cells are in the updated board

     /*
        Rules:
        1. Live Two Degree Neighbors < 2 --> Kill
        2. Live Two Degree Neighbors == 2 || Live Neighbors == 3 --> Retain
        3. Live Two Degree Neighbors > 3 --> Kill
        4. Dead with Live Two Degree Neighbors == 3 --> Alive again
     */

    @Override
    public int updateNextCellArray() {
        boolean[][] liveCellArray = getLiveCellArray();
        boolean[][] nextCellArray = getNextCellArray();

        int retVal = 0;

        int nln = 0;  // Number Live Neighbors
        boolean ccs = true; // Current Cell Status
        for (int row = 0; row < NUM_ROWS; ++row){
            for (int col = 0; col < NUM_COLS; ++col) {
                ccs = liveCellArray[row][col];
                nln = countLiveTwoDegreeNeighbors(row, col);
                if (!ccs && nln == 3) {
                    nextCellArray[row][col] = true;
                    ++retVal;
                }
                else {
                    // Current Cell Status is true
                    if (nln < 2 || nln > 3) {
                        nextCellArray[row][col] = false;
                    }
                    else {
                        // nln == 2 || nln == 3
                        nextCellArray[row][col] = true;
                        ++retVal;
                    }
                }
            }  // for (int row = 0; ...)
        }  //  for (int col = 0; ...)

        boolean[][] tmp = this.liveCellArray;
        this.liveCellArray = nextCellArray;
        this.nextCellArray = tmp;

        return retVal;
    }  // public int updateNextCellArray()
    public boolean isAlive(int row, int col) {
        return liveCellArray[row][col];
    }
} // public class SlGoLBoardLive extends SlGoLBoard {
