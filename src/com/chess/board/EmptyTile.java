//Code written by Joseph Victor Sumbong for CMSC 22
//package
package com.chess.board;

//import
import com.chess.piece.Piece;

/**
 * EmptyTile is a child class of the Tile class
 * Its purpose is to create an empty tile or a tile without a piece on it
 * The methods in this class are: isTileOccupied() and getPiece()
 * both of which is @Override from the parent class
 */
public final class EmptyTile extends Tile{

    //constructor for the EmptyTile
    public EmptyTile(int tileCoordinate) {
        super(tileCoordinate);
    }

    /**
     * isTileOccupied() is a boolean method that always return false for the case of the EmpytyTile
     * because by definition EmptyTile does nat have any piece on it therefore it is always not occupied
     * @return false always
     */
    @Override
    public boolean isTileOccupied() {
        return false;
    }

    /**
     * getPiece() is a method that always returns null because by definition an EmptyTile does not have a piece
     * on it
     * @return null always
     */
    @Override
    public Piece getPiece() {
        return null;
    }
}
