//Code written by Joseph Victor Sumbong for CMSC 22
//packages
package com.chess.piece;

//imports
import com.chess.Color;
import com.chess.board.*;
import com.chess.board.Move.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
 Pawn class is a child class of the Piece class
 It deals with the construction and movement of the Bishop piece
 on the board and other specific methods needed for the pawn piece
 */
public class Pawn extends Piece {
    private final static int[] POSSIBLE_MOVE_TILE = {7,8,9,16};
    public Pawn(final int position, final Color pieceColor) {
        super(PieceType.PAWN, position, pieceColor,true);
    }

    //Convenience constructor
    public Pawn(final int position, final Color pieceColor, final boolean isFirstMove){
        super(PieceType.PAWN, position, pieceColor, isFirstMove);
    }
    @Override
    public Collection<Move> calculateLegalMove(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int possibleMove : POSSIBLE_MOVE_TILE) {
            int possibleTile = this.position + (this.pieceColor.getDirection() * possibleMove);
            if (!BoardUtils.isValidTile(possibleTile)) {
                continue;
            }
            //simple pawn move up 1 tile
            if (possibleMove == 8 && !board.getTile(possibleTile).isTileOccupied()) {
                //pawn promotion
                if(this.pieceColor.isPawnPromotionSquare(possibleTile)){
                    legalMoves.add(new PawnPromotion(new PawnMove(board, this, possibleTile)));
                }else {
                    //normal pawn move
                    legalMoves.add(new PawnMove(board, this, possibleTile));
                }
            //pawn moves up 2 tiles if it is for the pawn's first move
            } else if (possibleMove == 16 && this.isFirstMove() && ((BoardUtils.ROW_7[this.position] && this.getColor().isBlack())
                    || (BoardUtils.ROW_2[this.position] && this.getColor().isWhite()))) {
                final int behindPossibleTile = this.position + (this.pieceColor.getDirection() * 8);
                if (!board.getTile(behindPossibleTile).isTileOccupied() && !board.getTile(possibleTile).isTileOccupied()) {
                    legalMoves.add(new PawnJump(board, this, possibleTile));
                }
            //pawn capture to its right
            } else if (possibleMove == 7 && !((BoardUtils.COL_H[this.position] && this.pieceColor.isWhite()) ||
                            (BoardUtils.COL_A[this.position] && this.pieceColor.isBlack()))) {
                if (board.getTile(possibleTile).isTileOccupied()) {
                    final Piece pieceAtTile = board.getTile(possibleTile).getPiece();
                    if (this.pieceColor != pieceAtTile.getColor()) {
                        //pawn captures to its left and it is at the 8th row or promotion row
                        if(this.pieceColor.isPawnPromotionSquare(possibleTile)){
                            legalMoves.add(new PawnPromotion(new PawnCaptureMove(board, this, possibleTile, pieceAtTile)));
                        }else {
                            //normal pawn capture to its right
                            legalMoves.add(new PawnCaptureMove(board, this, possibleTile, pieceAtTile));
                        }
                    }
                //enpassant capture to its right
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.position + (this.pieceColor.getOppositeDirection()))) {
                        final Piece pieceAtTile = board.getEnPassantPawn();
                        if (this.pieceColor != pieceAtTile.getColor()) {
                            legalMoves.add(new PawnEnPassantMove(board, this, possibleTile, pieceAtTile));
                    }
                }
            //pawn capture to its left
            } else if (possibleMove == 9 && !((BoardUtils.COL_A[this.position] && this.pieceColor.isWhite()) ||
                            (BoardUtils.COL_H[this.position] && this.pieceColor.isBlack()))) {
                if (board.getTile(possibleTile).isTileOccupied()) {
                    final Piece pieceAtTile = board.getTile(possibleTile).getPiece();
                    if (this.pieceColor != pieceAtTile.getColor()) {
                        //pawn capture to its left and it is in the 8th row or the promotion row
                        if(this.pieceColor.isPawnPromotionSquare(possibleTile)){
                            legalMoves.add(new PawnPromotion(new PawnCaptureMove(board, this, possibleTile, pieceAtTile)));
                        }else {
                            legalMoves.add(new PawnCaptureMove(board, this, possibleTile, pieceAtTile));
                        }
                    }
                //en passant capture to its left
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.position - (this.pieceColor.getOppositeDirection()))) {
                        final Piece pieceAtTile = board.getEnPassantPawn();
                        if (this.pieceColor != pieceAtTile.getColor()) {
                            legalMoves.add(new PawnEnPassantMove(board, this, possibleTile, pieceAtTile));

                    }
                }
            }
        }
        //return an immutable list
        return Collections.unmodifiableList(legalMoves);
    }

    //move the pawn
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getMovePosition(), move.getMovedPiece().getColor());
    }

    //creates a string for the movelog
    @Override
    public String toString(){
        return PieceType.PAWN.toString();
    }

    public Piece getPromotionPiece(){
        return new Queen(this.position, this.pieceColor,false);
    }
}
