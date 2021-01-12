//Code written by Joseph Victor Sumbong
//package
package com.chess.player;

//imports
import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.Move;
import com.chess.board.MoveStatus;
import com.chess.board.MoveTransition;
import com.chess.piece.King;
import com.chess.piece.Piece;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
    Player is a parent class
    It has methods that is applicable for both players black and white
*/
public abstract class Player {
    //fields
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    //constructor
    Player(final Board board, final Collection<Move> playerLegalMoves, final Collection<Move> oppLegalMoves){
        this.board = board;
        this.playerKing = establishKing();
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), oppLegalMoves).isEmpty();
        playerLegalMoves.addAll(calculateKingCastles(playerLegalMoves, oppLegalMoves));
        this.legalMoves = Collections.unmodifiableCollection(playerLegalMoves);
    }

    //gets the king of the player
    public King getPlayerKing(){
        return this.playerKing;
    }

    //gets the legal moves of the player
    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    //calculates the attack on the tile
    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves){
            if(piecePosition == move.getMovePosition()){
                attackMoves.add(move);
            }
        }
        return Collections.unmodifiableList(attackMoves);
    }

    //A chess game can only be played if there is a king piece on the board for both players
    //so this method establishes the king of the player
    protected King establishKing(){
        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Not a valid board");
    }

    //boolean method to check if the player is in check
    public boolean isInCheck(){
        return this.isInCheck;
    }

    //boolean method to check if the player is in checkmate
    public boolean isInCheckMate(){
        return this.isInCheck && !hasEscapeMoves();
    }

    //boolean method to check if the player is in stalemate
    public boolean isInStaleMate(){
        return !this.isInCheck && !hasEscapeMoves();
    }

    //boolean method to check if the king has moves that can defend or move his king out of harm's way
    protected boolean hasEscapeMoves(){
        return this.legalMoves.stream().anyMatch(move -> makeMove(move).getMoveStatus().isDone());
    }

    //boolean method to check if the player has castled
    public boolean isCastled(){
        return this.playerKing.isCastled();
    }

    /*
        method that checks if the move made is legal
        if it is not then it will not build the new board
        if it is legal then it will execute the move and make the new
        board that shows the new move
    */
    public MoveTransition makeMove(final Move move){
        if(!this.legalMoves.contains(move)){
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    //abstract methods
    public abstract Collection<Piece> getActivePieces();
    public abstract Color getColor();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals);
}
