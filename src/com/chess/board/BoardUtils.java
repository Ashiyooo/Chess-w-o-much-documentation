//Code written by Joseph Victor Sumbong for CMSC 22
//package
package com.chess.board;

//import
import java.util.*;

/**
* BoardUtils is a class for miscellaneous things needed for
* the logic of the game to work properly\
 *
 * The methods that can be found here are the following:
 * isValidTile(), initColumn(), initRow(), getPositionAtTile(), getTileAtPosition()
* */
public class BoardUtils {
    //fields

    //Constant fields
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    //These are the columns that affects the pieces and how they move
    //setting tiles in the arrays so that we can take which tiles are in the said column
    public static final boolean[] COL_A = initColumn(0);
    public static final boolean[] COL_B = initColumn(1);
    public static final boolean[] COL_G = initColumn(6);
    public static final boolean[] COL_H = initColumn(7);
    //same thing as the columns
    public static final boolean[] ROW_8 = initRow(0);
    public static final boolean[] ROW_7 = initRow(8);
    public static final boolean[] ROW_2 = initRow(48);
    public static final boolean[] ROW_1 = initRow(56);

    //create a standard chess notation for the tiles
    public static final List<String> ALGEBRAIC_NOTATION = initAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_TILE = initPositionToTileMap();

    //constructor for the class throws a runtime exception because we dont want it to be instantiated
    private BoardUtils(){
        throw new RuntimeException("Not applicable for instantiation");
    }

    /**
     * isValidTile() is a method that checks if the integer is between 0-64 including 0 and excluding 64
     * @param tile which is the tile coordinate
     * @return a boolean if the int value of the tile variable is between the stated values above
     */
    public static boolean isValidTile(final int tile) {
        return tile >= 0 && tile < 64;
    }

    /**
     * initColumn() is a method to return a boolean array in which each tile is given a boolean value so
     * that we can say that they are in the same column. Uses the constants NUM_TILES_PER_ROW which is 8 and
     * NUM_TILES which is 64.
     * @param columnNumber the first tile coordinate for the column
     * @return the boolean array for the column
     */
    private static boolean[] initColumn(int columnNumber){
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        }while(columnNumber < NUM_TILES);
        return column;
    }

    /**
     * initColumn() is a method to return a boolean array in which each tile is given a boolean value so
     * that we can say that they are in the same row. Uses the constants NUM_TILES_PER_ROW which is 8 and
     * NUM_TILES which is 64.
     * @param rowNumber the first tile coordinate for the row
     * @return the boolean array for the row
     */
    private static boolean[] initRow(int rowNumber){
        final boolean[] row = new boolean[NUM_TILES];
        do{
            row[rowNumber] = true;
            rowNumber ++;
        }
        while((rowNumber % NUM_TILES_PER_ROW) != 0);
        return row;
    }

    /**
     * initializes the algebraic notation for a standard chess board
     * @return an immutable list of with the values stated below
     */
    private static List<String> initAlgebraicNotation() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }

    /**
     * initPositionToTileMap() method does not have a parameter
     * Its purpose is to add the specific algebraic notation for each tile
     * @return an immutable list of the Map with the variable name positionToCoordinate
     */
    private static Map<String, Integer> initPositionToTileMap() {

        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for(int i = 0; i < NUM_TILES; i++){
            positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return Collections.unmodifiableMap(positionToCoordinate);
    }

    /**
     * getPositionAtTile() is a method that gets the tile coordinate which is an integer value given the
     * algebraic notation by the caller
     * @param position is one of the strings from the ALGEBRAIC_NOTATION
     * @return an integer with the coordinate of the tile
     */
    public static int getPositionAtTile(final String position) {
        return POSITION_TO_TILE.get(position);
    }

    /**
     * getTileAtPosition() is a method that gives the specific algebraic notation for the tile
     * @param position is the tile coordinate which is an integer value
     * @return a string of the specific algebraic notation of the tile coordinate
     */
    public static String getTileAtPosition(final int position){
        return ALGEBRAIC_NOTATION.get(position);
    }
}
