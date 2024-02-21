package SlGoLBoard;

public class Main {
    private static SlGoLBoardLive my_board;
    private static final int ROWS = 7, COLS = 9;
    public static void main(String[] args) {
        test_1(); // print TwoDegreeNeighbors(0,0)
        test_2(); // print TwoDegreeNeighbors(ROWS-1, COLS-1)
        test_3(); // print TwoDegreeNeighbors(ROWS/2, ROWS/2)
        test_4(); // print TwoDegreeNeighbors(0, ROWS/2)
        test_5(); // print the board and the updated board
    } // public static void main(String[] args) {

    // print TwoDegreeNeighbors(0,0)
    private static void test_1() {
        my_board = new SlGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        int my_row = 0, my_col = 0;
        System.out.println("TwoDegreeNeighbors(" + my_row + ", " + my_col + ") --> " +
                my_board.countLiveTwoDegreeNeighbors(my_row, my_col));
        System.out.println();
    } // private static void test_1() {

    // print TwoDegreeNeighbors(ROWS-1, COLS-1)
    private static void test_2() {
        my_board = new SlGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        int my_row = ROWS - 1, my_col = COLS - 1;
        System.out.println("TwoDegreeNeighbors(" + my_row + ", " + my_col + ") --> " +
                my_board.countLiveTwoDegreeNeighbors(my_row, my_col));
        System.out.println();
    } // private static void test_2() {

    // print TwoDegreeNeighbors((int)(ROWS/2), (int)(ROWS/2))
    private static void test_3() {
        my_board = new SlGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        int my_row = ROWS / 2, my_col = ROWS / 2;
        System.out.println("TwoDegreeNeighbors(" + my_row + ", " + my_col + ") --> " +
                my_board.countLiveTwoDegreeNeighbors(my_row, my_col));
        System.out.println();
    } // private static void test_3() {

    // print TwoDegreeNeighbors(0, (int)(ROWS/2))
    private static void test_4() {
        my_board = new SlGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        int my_row = 0, my_col = ROWS / 2;
        System.out.println("TwoDegreeNeighbors(" + my_row + ", " + my_col + ") --> " +
                my_board.countLiveTwoDegreeNeighbors(my_row, my_col));
        System.out.println();
    } // private static void test_4() {

    // print the board and the updated board
    private static void test_5() {
        my_board = new SlGoLBoardLive(ROWS, COLS);
        my_board.printGoLBoard();
        int updates = my_board.updateNextCellArray();
        int dashLength = COLS * 2;
        for (int i = 0; i < dashLength; i++) System.out.print("-");
        System.out.println();
        System.out.println("updateNextCellArray() --> Updated " + updates + " cells.");
        my_board.printGoLBoard();
    } // private static void test_5() {
} // public class Main {








