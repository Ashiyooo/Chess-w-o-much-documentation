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
 Queen class is a child class of the Piece class
 It deals with the construction and movement of the Queen piece
 on the board and other specific methods needed for the Queen piece
 */
public class Queen extends Piece{
    private final static int[] POSSIBLE_MOVE_TILE = {-1,1,-8,8,-9,-7,7,9};
    public Queen(final int position, final Color pieceColor) {
        super(PieceType.QUEEN, position, pieceColor,true);
    }

    //Convenience constructor
    public Queen(final int position, final Color pieceColor, final boolean isFirstMove){
        super(PieceType.QUEEN, position, pieceColor, isFirstMove);
    }
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

    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getMovePosition(), move.getMovedPiece().getColor());
    }

    private static boolean isColA(final int currentPosition, final int offset){
        return BoardUtils.COL_A[currentPosition] &&  ((offset == 7) || (offset == -1) || (offset == -9));
    }

    private static boolean isColH(final int currentPosition, final int offset){
        return BoardUtils.COL_H[currentPosition] && ((offset == -7) || (offset == 1) || (offset == 9));
    }

    @Override
    public String toString(){
        return PieceType.QUEEN.toString();
    }
}
