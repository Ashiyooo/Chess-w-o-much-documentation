//Code written by Joseph Victor Sumbong for CMSC 22
//packages
package com.chess.piece;

//imports
import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;
import java.util.Collection;

/*
    Piece is a parent class for all the other piece types
    within it is also an enum for the piece types
*/
public abstract class Piece {
    //fields
    protected final PieceType pieceType;
    protected final int position;
    protected final Color pieceColor;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final PieceType pieceType, final int position, final Color pieceColor, final boolean isFirstMove){
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.position = position;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    //since the piece objects are immutable their hashcode will not change so we can call it once to get the hashcode
    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceColor.hashCode();
        result = 31 * result + position;
        result = 31 * result + (isFirstMove? 1: 0);
        return result;
    }

    //Override the equals method of java lang because it uses reference equality rather than object equality
    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return position == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType()
                            && pieceColor == otherPiece.getColor() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
    }

    //helper routines

    //gets the color of the piece
    public Color getColor(){
        return this.pieceColor;
    }

    //gets the piece type of the piece
    public PieceType getPieceType(){
        return this.pieceType;
    }

    //returns a boolean if the piece has already done its first move or not
    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    //gets the position of the piece
    public int getPiecePosition(){
        return this.position;
    }

    //gets the value of the piece
    public int getPieceValue(){return this.pieceType.getPieceValue();}

    //abstract methods
    public abstract Collection<Move> calculateLegalMove(final Board board);
    public abstract Piece movePiece(Move move);

    /*
        an enum class that specifies the piece type of each piece with values which
        will be needed by the minimax algorithm and other methods that will be needed
        in the program especially the Castling moves
    */
    public enum PieceType {
        PAWN("P",100){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N",300) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B",300) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R",500 ) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q", 900) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K", 10000) {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };
        final private String pieceName;
        final private int pieceValue;

        //constructor
        PieceType(final String pieceName, final int pieceValue){
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }
        @Override
        public String toString(){
            return this.pieceName;
        }
        public int getPieceValue(){return this.pieceValue;}
        public abstract boolean isKing();
        public abstract boolean isRook();
    }
}
