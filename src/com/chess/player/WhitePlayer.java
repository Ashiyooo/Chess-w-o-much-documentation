//Code written by Joseph Victor Sumbong for CMSC 22

//packages
package com.chess.player;

//imports
import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;
import com.chess.piece.King;
import com.chess.piece.Piece;
import com.chess.piece.Rook;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static com.chess.board.Move.*;
import static com.chess.piece.Piece.PieceType.ROOK;

/*
    WhitePlayer class is a child class of player
    This class contains methods that can only be done specifically by
    the black player
*/
public class WhitePlayer extends Player {

    //constructor
    public WhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMove, final Collection<Move> blackStandardLegalMove) {
        super(board, whiteStandardLegalMove, blackStandardLegalMove);
    }

    //A chess game cannot be played if there are no Kings
    //so this method establishes the white king
    @Override
    protected King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here " + this.getColor() + " king could not be established");
    }

    //gets the active pieces of the white player
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    //gets the color of the player which is white
    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    //gets the opponent of the white player which is black
    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    //method that calculates if the king can castle or not on both sides
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 60 && !this.isInCheck()) {
            //whites king side castle
            if(this.board.getTile(61).getPiece() == null && this.board.getTile(62).getPiece() == null) {
                final Piece kingSideRook = this.board.getTile(63).getPiece();
                if(kingSideRook != null && kingSideRook.isFirstMove()) {
                    if(Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                            kingSideRook.getPieceType() == ROOK) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook) kingSideRook, kingSideRook.getPiecePosition(), 61));
                    }
                }
            }
            //whites queen side castle
            if(this.board.getTile(59).getPiece() == null && this.board.getTile(58).getPiece() == null &&
                    this.board.getTile(57).getPiece() == null) {
                final Piece queenSideRook = this.board.getTile(56).getPiece();
                if(queenSideRook != null && queenSideRook.isFirstMove()) {
                    if(Player.calculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(59, opponentLegals).isEmpty() && queenSideRook.getPieceType() == ROOK) {
                            kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) queenSideRook, queenSideRook.getPiecePosition(), 59));
                    }
                }
            }
        }
        //returns an immutable collection of castling moves for the white player
        return Collections.unmodifiableList(kingCastles);
    }
}