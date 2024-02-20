package SlGoLBoard;

import java.util.Random;
abstract class SlGoLBoard {
    protected int NUM_ROWS;
    protected int NUM_COLS;
    protected boolean[][] cellArrayA, cellArrayB, liveCellArray, nextCellArray;

    protected SlGoLBoard(int numRows, int numCols) {
        NUM_ROWS = numRows;
        NUM_COLS = numCols;
        cellArrayA = new boolean[NUM_ROWS][NUM_COLS];
        cellArrayB = new boolean[NUM_ROWS][NUM_COLS];

        Random myRandom = new Random();
        for (int row = 0; row < NUM_ROWS; ++row) {
            for (int col = 0; col < NUM_COLS; ++col) {
                cellArrayA[row][col] = myRandom.nextBoolean();
                cellArrayB[row][col] = myRandom.nextBoolean();
            }
        }
        liveCellArray = cellArrayA;
        nextCellArray = cellArrayB;
    }  //  public slGoLBoard(int numRows, int numCols)

    // Create a Board with a given number of cells alive - the alive cells
    // are placed randomly placed applying Durstenfeld-Knuth random shuffling
    private SlGoLBoard(int numRows, int numCols, int numAlive) {
        NUM_ROWS = numRows;
        NUM_COLS = numCols;
        boolean[] tmpArray = new boolean[NUM_ROWS * NUM_COLS];
        for (int i = 0; i < numAlive; ++i) {
            tmpArray[i] = true;
        }
        for (int i = numAlive; i < NUM_ROWS * NUM_COLS; ++i) {
            tmpArray[i] = false;
        }
        Random myRandom = new Random();
        // Durstenfeld-Knuth random shuffle:
        for (int i = 0; i < NUM_ROWS * NUM_COLS - 2; ++i) {
            int j = myRandom.nextInt(i, NUM_ROWS*NUM_COLS);
            boolean tmp = tmpArray[i];
            tmpArray[i] = tmpArray[j];
            tmpArray[j] = tmp;
        }

        cellArrayA = new boolean[NUM_ROWS][NUM_COLS];
        cellArrayB = new boolean[NUM_ROWS][NUM_COLS];
        int i = 0;
        for (int row = 0; row < NUM_ROWS; ++row) {
            for (int col = 0; col < NUM_COLS; ++col) {
                cellArrayA[row][col] = tmpArray[i];
                ++i;
                cellArrayB[row][col] = false;
            }
        }
        liveCellArray = cellArrayA;
        nextCellArray = cellArrayB;
    }  //  public slGoLBoard(int numRows, int numCols, int numAlive)


    protected boolean[][] getLiveCellArray() {
        return liveCellArray;
    } // protected boolean[][] getLiveCellArray() {
    protected boolean[][] getNextCellArray() {
        return nextCellArray;
    } // protected boolean[][] getNextCellArray() {

    private void setCellAlive(int row, int col) {
        liveCellArray[row][col] = true;
    } // private void setCellAlive(int row, int col) {

    private void setCellDead(int row, int col) {
        liveCellArray[row][col] = false;
    } // private void setCellDead(int row, int col) {

    private void setAllCells(boolean value) {
        for (boolean[] rows : liveCellArray) {
            for (boolean cell : rows) {
                cell = value;
            }
        }
    }  //  void setAllCells()

    protected void copyLiveToNext() {
        for (int row = 0; row < liveCellArray.length; ++row){
            for (int col = 0; col < liveCellArray[row].length; ++col) {
                nextCellArray[row][col] = liveCellArray[row][col];
            }
        }
        return;
    }  //  void copyLiveToNext()

    protected void printGoLBoard() {
        for (boolean[] my_row : liveCellArray) {
            for (boolean my_val : my_row) {
                if (my_val) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }  //  for (bool my_val : my_row)
            System.out.println();
        }  //  for (bool[] my_row : my_array)
    }  //  void printGoLBoard()

    protected abstract int countLiveTwoDegreeNeighbors(int row, int col);
    protected abstract int updateNextCellArray();

}  //  public class slGoLBoard

