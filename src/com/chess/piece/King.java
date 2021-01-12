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
 King class is a child class of the Piece class
 It deals with the construction and the movement of the King piece on the board
 and other specific methods needed for the King piece
 */
public class King extends Piece{
    //fields
    private final boolean isCastled;
    private final static int[] POSSIBLE_MOVE_TILE = {-9,-8,-7,-1,1,7,8,9};

    //constructor
    public King(final int position, final Color pieceColor, final boolean isCastled) {
        super(PieceType.KING, position, pieceColor,true);
        this.isCastled = false;
    }

    //Convenience constructor
    public King(final int position, final Color pieceColor, final boolean isFirstMove, final boolean isCastled){
        super(PieceType.KING, position, pieceColor, isFirstMove);
        this.isCastled = isCastled;
    }

    //calculates the legal moves of the King
    @Override
    public Collection<Move> calculateLegalMove(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int possibleMove : POSSIBLE_MOVE_TILE) {
            final int possibleTile = this.position + possibleMove;
            if (BoardUtils.isValidTile(possibleTile)) {
                if (isColA(this.position, possibleMove) || isColH(this.position, possibleMove)) {
                    continue;
                }
                final Tile moveTile = board.getTile(possibleTile);

                if (!moveTile.isTileOccupied()) {
                    legalMoves.add(new NonCaptureMove(board, this, possibleTile));
                } else {
                    final Piece pieceAtTile = moveTile.getPiece();
                    final Color color = pieceAtTile.getColor();
                    if (this.pieceColor != color) {
                        legalMoves.add(new MajorAttackMove(board, this, possibleTile, pieceAtTile));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    //moves the king
    @Override
    public King movePiece(final Move move) {
        return new King(move.getMovePosition(), move.getMovedPiece().getColor(), move.isCastlingMove());
    }

    //to check if the position of the king is at column a
    private static boolean isColA(final int currentPosition, final int offset){
        return BoardUtils.COL_A[currentPosition] &&  ((offset == -9) || (offset == -1) || (offset == 7));
    }

    //to check if the position of the king is at column h
    private static boolean isColH(final int currentPosition, final int offset){
        return BoardUtils.COL_H[currentPosition] && ((offset == 9) || (offset == 1) || (offset == -7));
    }

    //method to see if the king is castled or not
    public boolean isCastled(){
        return this.isCastled;
    }

    //creates a string for the movelog
    @Override
    public String toString(){
        return PieceType.KING.toString();
    }
}
