package SlGoLBoard;

import CSC133.Spot;

import java.io.*;
import java.util.Arrays;

import static CSC133.Spot.SET_DIMENSIONS;

public class SlGoLBoardLive extends SlGoLBoard {

    private boolean[][] initialCellArray;

    public SlGoLBoardLive(int numRows, int numCols) {
        super(numRows, numCols);
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

    public void restart() {
        for (int row = 0; row < liveCellArray.length; ++row){
            System.arraycopy(initialCellArray[row], 0, liveCellArray[row], 0, liveCellArray[row].length);
        }
    }

    public void save(String file_name) {
        // Ensure the file ends with .ca
        if (!file_name.endsWith(".ca")) {
            file_name += ".ca";
        }
        // Create the file
        File f = new File(file_name);
        // Write to it
        try {
            if (f.createNewFile()) {
                System.out.println("File created.");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_name))) {
                    writer.write(String.valueOf(NUM_ROWS));
                    writer.newLine();
                    writer.write(String.valueOf(NUM_COLS));
                    writer.newLine();
                    for (boolean[] row : liveCellArray) {
                        for (int i = 0; i < row.length; i++) {
                            // Write '1' for true and '0' for false, separated by spaces
                            writer.write((row[i] ? '1' : '0') + (i < row.length - 1 ? " " : ""));
                        }
                        writer.newLine(); // Move to the next line after writing each row
                    }
                    System.out.println("Successfully wrote cellular automata to file.");
                } catch (Exception e) {
                    System.err.println("An error occurred while writing the cellular automata to file.");
                }
            }
            else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void load(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read the first two lines containing the number of rows and columns
            int numRows = Integer.parseInt(br.readLine().trim());
            int numCols = Integer.parseInt(br.readLine().trim());

            // Apply the new dimensions to the engine state
            SET_DIMENSIONS(numRows, numCols);
            // Re-initialize the game board to the new dimensions
            initializeBoard(numRows, numCols);

            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < numRows) {
                // Split by spaces to get cell states
                String[] values = line.trim().split("\\s+");
                if (values.length == numCols) {
                    for (int col = 0; col < numCols; col++) {
                        // Set the cell state based on the '1' or '0' value
                        liveCellArray[row][col] = "1".equals(values[col]);
                    }
                    row++;
                } else {
                    // Handle the error for incorrect number of columns
                    System.err.println("Incorrect number of columns in row " + row);
                    break;
                }
            }
            copyCellArray(liveCellArray, initialCellArray);
            System.out.println("Loaded cellular automata from file.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("An error occurred while reading the cellular automata file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("File format error: " + e.getMessage());
        }
    }


    private void copyCellArray(boolean[][] src, boolean[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
        }
    }

    public void setAllDead() {
        for (int i = 0; i < liveCellArray.length; i++) {
            for (int j = 0; j < liveCellArray[0].length; j++) {
                setCellDead(i, j);
            }
        }
    }

    public void initializeBoard(int numRows, int numCols) {
        this.NUM_ROWS = numRows;
        this.NUM_COLS = numCols;
        this.cellArrayA = new boolean[numRows][numCols];
        this.cellArrayB = new boolean[numRows][numCols];
        // Any other initialization logic that's shared between the constructors
        // and the load method can go here
        this.liveCellArray = cellArrayA;
        this.initialCellArray = liveCellArray;
        this.nextCellArray = cellArrayB;
    }


    private void SlGoLBoardLivePrinter() {
        System.out.println("Call to SlGoLBoardLive received.");
    }
} // public class SlGoLBoardLive extends SlGoLBoard {
