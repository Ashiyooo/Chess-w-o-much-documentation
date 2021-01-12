package com.chess.board;
import com.chess.piece.Piece;

//child class of Tile
public final class OccupiedTile extends Tile{
    private final Piece pieceOnTile;

    //constructor that creates an occupied tile
    protected OccupiedTile(int tileCoordinate, final Piece pieceOnTile){
        super(tileCoordinate);
        this.pieceOnTile = pieceOnTile;
    }

    //method returns true since an occupied tile can only exist if there is a piece on it
    @Override
    public boolean isTileOccupied(){
        return true;
    }

    //method returns the piece on the tile
    @Override
    public Piece getPiece(){
        return this.pieceOnTile;
    }
}
