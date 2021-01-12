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
 Kngiht class is a child class of the Piece class
 It deals with the construction and movement of the Knight piece
 on the board and other specific methods needed for the Knight piece
 */
public class Knight extends Piece {
    //fields
    private final static int[] POSSIBLE_MOVE_TILE = {-17,-15,-10,-6,6,10,15,17};

    public Knight(final int position, final Color pieceColor) {
        super(PieceType.KNIGHT, position, pieceColor, true);
    }

    //Convenience constructor
    public Knight(final int position, final Color pieceColor, final boolean isFirstMove){
        super(PieceType.KNIGHT, position, pieceColor, isFirstMove);
    }

    //calculates the legal moves of the knight
    @Override
    public Collection<Move> calculateLegalMove(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int possibleMove : POSSIBLE_MOVE_TILE){
            int possibleTile = this.position + possibleMove;
            if(BoardUtils.isValidTile(possibleTile)){
                if(isColA(this.position, possibleMove) || isColB(this.position, possibleMove)
                        || isColG(this.position, possibleMove) || isColH(this.position, possibleMove)){
                    continue;
                }
                final Tile moveTile = board.getTile(possibleTile);

                if(!moveTile.isTileOccupied()){
                    legalMoves.add(new NonCaptureMove(board, this, possibleTile));
                }
                else{
                    final Piece pieceAtTile = moveTile.getPiece();
                    final Color color = pieceAtTile.getColor();
                    if(this.pieceColor != color){
                        legalMoves.add(new MajorAttackMove(board, this, possibleTile, pieceAtTile));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    //moves the piece
    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getMovePosition(), move.getMovedPiece().getColor());
    }

    //checks if the position is at column a
    private static boolean isColA(final int currentPosition, final int offset){
        return BoardUtils.COL_A[currentPosition] && ((offset == -17) || (offset == -10) || (offset == 6) || (offset == 15));
    }

    //checks if the position is at column b
    private static boolean isColB(final int currentPosition, final int offset){
        return BoardUtils.COL_B[currentPosition] && ((offset == -10) || (offset == 6) );
    }

    ////checks if the position is at column g
    private static boolean isColG(final int currentPosition, final int offset){
        return BoardUtils.COL_G[currentPosition] && ((offset == -6) || (offset == 10));
    }

    //checks if the position is at column h
    private static boolean isColH(final int currentPosition, final int offset){
        return BoardUtils.COL_H[currentPosition] && ((offset == 17) || (offset == 10) || (offset == -6) || (offset == -15));
    }

    //creates a string for the movelog
    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }
}
