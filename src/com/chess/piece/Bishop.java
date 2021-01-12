//Code written by Joseph Victor Sumbong for CMSC 22
//packages
package com.chess.piece;

//imports
import com.chess.Color;
import com.chess.board.*;
import com.chess.board.Move.MajorAttackMove;
import com.chess.board.Move.NonCaptureMove;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
 Bishop class is a child class of the Piece class
 It deals with the construction and movement of the Bishop piece
 on the board and other specific methods needed for the bishop piece
 */
public class Bishop extends Piece {
    //fields
    private final static int[] POSSIBLE_MOVE_TILE = {-9, -7, 7,9};

    //constructor
    public Bishop(final int position, final Color pieceColor) {
        super(PieceType.BISHOP, position, pieceColor,true);
    }

    //Convenience Constructor
    public Bishop(final int position, final Color pieceColor, final boolean isFirstMove){
        super(PieceType.BISHOP, position, pieceColor, isFirstMove);
    }

    //method the calculates the legal moves for the bishop
    @Override
    public Collection<Move> calculateLegalMove(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int possibleMove : POSSIBLE_MOVE_TILE) {
            int possibleTile = this.position;
            while (BoardUtils.isValidTile(possibleTile)) {
                if (isColA(possibleTile, possibleMove) ||
                        isColH(possibleTile, possibleMove)) {
                    break;
                }
                possibleTile += possibleMove;
                if (BoardUtils.isValidTile(possibleTile)) {
                    final Tile moveTile = board.getTile(possibleTile);
                    if (!moveTile.isTileOccupied()) {
                        legalMoves.add(new NonCaptureMove(board, this, possibleTile));
                    } else {
                        final Piece pieceAtTile = moveTile.getPiece();
                        final Color color = pieceAtTile.getColor();
                        if (this.pieceColor != color) {
                            legalMoves.add(new MajorAttackMove(board, this, possibleTile, pieceAtTile));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }


    //moves the bishop
    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getMovePosition(), move.getMovedPiece().getColor());
    }

    //to check if the position of the move/bishop is at column a
    private static boolean isColA(final int currentPosition, final int offset){
        return BoardUtils.COL_A[currentPosition] && ((offset == 7) || (offset == -9));
    }

    //to check if the position of the move/bishop is at column h
    private static boolean isColH(final int currentPosition, final int offset){
        return BoardUtils.COL_H[currentPosition] && ((offset == -7) || (offset == 9));
    }

    //to create a string output for the movelog
    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }
}
