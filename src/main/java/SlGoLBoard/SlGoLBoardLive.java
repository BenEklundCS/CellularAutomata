package SlGoLBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SlGoLBoardLive extends SlGoLBoard {

    private final boolean[][] initialCellArray;

    public SlGoLBoardLive(int numRows, int numCols) {
        super(numRows, numCols);
        SlGoLBoardLivePrinter();
        initialCellArray = new boolean[numRows][numCols];
        copyCellArray(liveCellArray, initialCellArray);
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

        int nln;  // Number Live Neighbors
        boolean ccs; // Current Cell Status
        for (int row = 0; row < NUM_ROWS; ++row){
            for (int col = 0; col < NUM_COLS; ++col) {
                ccs = liveCellArray[row][col];
                nln = countLiveTwoDegreeNeighbors(row, col);
                if (!ccs && nln == 3) {
                    nextCellArray[row][col] = true;
                    ++retVal;
                }
                else {
                    // 1. Live Neighbors < 2 --> Kill
                    if (nln < 2) {
                        nextCellArray[row][col] = false;
                    }
                    // 2. Live Neighbors == 2 || Live Neighbors == 3 --> Retain
                    // 4. Dead with Live Neighbors == 3 --> Alive again
                    else if (nln == 2 || nln == 3) {
                        nextCellArray[row][col] = true;
                    }
                    // 3. Live Neighbors > 3 --> Kill
                    else { // nln > 3
                        nextCellArray[row][col] = false;
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

    public void reset() {
        for (int row = 0; row < liveCellArray.length; ++row){
            System.arraycopy(initialCellArray[row], 0, liveCellArray[row], 0, liveCellArray[row].length);
        }
    }
    public void save(String file_name) {
        if (!file_name.endsWith(".ca")) {
            file_name += ".ca";
        }
        File f = new File(file_name);
        try {
            if (f.createNewFile()) {
                System.out.println("File created.");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_name))) {
                    for (boolean[] row : liveCellArray) {
                        for (boolean value : row) {
                            // Write '1' for true and '0' for false
                            writer.write(value ? '1' : '0');
                        }
                        writer.newLine(); // Move to the next line after writing each row
                    }
                    System.out.println("Successfully wrote cellular automata to file.");
                } catch (Exception e) {
                    System.err.println("An error occurred while writing the cellular automata to file.");
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public void load(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < NUM_ROWS) {
                for (int col = 0; col < line.length() && col < NUM_COLS; col++) {
                    liveCellArray[row][col] = (line.charAt(col) == '1');
                }
                row++;
            }
            System.out.println("Loaded cellular automata from file.");
        } catch (Exception e) {
            System.err.println("An error occurred while reading the cellular automata file.");
            e.printStackTrace();
        }
    }




    private void copyCellArray(boolean[][] src, boolean[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
        }
    }

    private void SlGoLBoardLivePrinter() {
        System.out.println("Call to SlGoLBoardLive received.");
    }
} // public class SlGoLBoardLive extends SlGoLBoard {
