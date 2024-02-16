package CSC133;
public class Main {
    private static slGoLBoardLive my_board;
    private static final int ROWS = 7, COLS = 9;
    public static void main(String[] args) {
        test_1();
        test_2();
        test_3();
        test_4();
        test_5();
    }

    // print TwoDegreeNeighbors(0,0)
    private static void test_1() {
        my_board = new slGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        int my_row = 0, my_col = 0;
        System.out.println("TwoDegreeNeighbors(" + my_row +", " + my_col + ") --> " +
                my_board.countLiveTwoDegreeNeighbors(my_row, my_col));
        System.out.println();
    }

    // print TwoDegreeNeighbors(ROWS-1, COLS-1)
    private static void test_2() {
        my_board = new slGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        int my_row = ROWS - 1, my_col = COLS - 1;
        System.out.println("TwoDegreeNeighbors(" + my_row +", " + my_col + ") --> " +
                my_board.countLiveTwoDegreeNeighbors(my_row, my_col));
        System.out.println();
    }

    // print TwoDegreeNeighbors((int)(ROWS/2), (int)(ROWS/2))
    private static void test_3() {
        my_board = new slGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        int my_row = (int)ROWS / 2, my_col = (int)ROWS / 2;
        System.out.println("TwoDegreeNeighbors(" + my_row +", " + my_col + ") --> " +
                my_board.countLiveTwoDegreeNeighbors(my_row, my_col));
        System.out.println();
    }

    // print TwoDegreeNeighbors(0, (int)(ROWS/2))
    private static void test_4() {
        my_board = new slGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        int my_row = 0, my_col = (int)ROWS / 2;
        System.out.println("TwoDegreeNeighbors(" + my_row +", " + my_col + ") --> " +
                my_board.countLiveTwoDegreeNeighbors(my_row, my_col));
        System.out.println();
    }

    // print the board and the updated board
    private static void test_5() {
        my_board = new slGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        my_board.updateNextCellArray();
        System.out.println("-----------------");
        my_board.printGoLBoard();
    }

}








