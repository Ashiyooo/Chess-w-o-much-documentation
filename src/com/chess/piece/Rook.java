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
 Rook class is a child class of the Piece class
 It deals with the construction and movement of the Rook piece
 on the board and other specific methods needed for the Rook piece
 */
public class Rook extends Piece {
    private static final int[] POSSIBLE_MOVE_TILE = {-8, -1, 1,8};

    public Rook(final int position,final Color pieceColor) {
        super(PieceType.ROOK, position, pieceColor, true);
    }
    public Rook(final int position,final Color pieceColor, final boolean isFirstMove) {
        super(PieceType.ROOK, position, pieceColor, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMove(Board board) {
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
    public Rook movePiece(final Move move) {
        return new Rook(move.getMovePosition(), move.getMovedPiece().getColor());
    }

    private static boolean isColA(int currentPosition, int offset) {
        return BoardUtils.COL_A[currentPosition] &&  (offset == -1);
    }

    private static boolean isColH(int currentPosition, int offset) {
        return BoardUtils.COL_H[currentPosition] &&  (offset == 1);
    }

    @Override
    public String toString(){
        return PieceType.ROOK.toString();
    }
}
