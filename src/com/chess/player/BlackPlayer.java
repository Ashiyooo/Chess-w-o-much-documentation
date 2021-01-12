//Code written by Joseph Victor Sumbong for CMSC 22

//packages
package com.chess.player;

//imports
import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;
import com.chess.board.Tile;
import com.chess.piece.King;
import com.chess.piece.Piece;
import com.chess.piece.Rook;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static com.chess.board.Move.*;

/*
    BlackPlayer is a child class of the Player Class
    This class contains methods that can only be done specifically by
    the black player
*/
public class BlackPlayer extends Player{
    public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMove, final Collection<Move> blackStandardLegalMove) {
        super(board, blackStandardLegalMove, whiteStandardLegalMove);
    }

    //A chess game in any point in time can only be played if there is still both kings on the board
    //so we establish the black king using this method
    @Override
    protected King establishKing() {
        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here " +this.getColor() +" king could not be established");
    }

    //returns the current pieces of the black player
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    //returns the color of the player
    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    //returns the opponent of the black player which is the white player
    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    //Applying castle rules in chess to the code
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            //black kingside castle
            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
                       rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6, (Rook)rookTile.getPiece(),
                                rookTile.getTileCoordinate(), 5));
                    }
                }

            }

            //black queenside castle
            if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied()
                    && !this.board.getTile(3).isTileOccupied()){
                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(1, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(2, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(3, opponentsLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()){
                    kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook)rookTile.getPiece(),
                            rookTile.getTileCoordinate(), 3));
                    }
                }
            }
        }
        //returns an immutable list of castling moves
        return Collections.unmodifiableList(kingCastles);
    }
}
