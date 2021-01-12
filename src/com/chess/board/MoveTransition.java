package com.chess.board;

public class MoveTransition {
    /*changes the board from one move to the next*/

    //fields
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    //constructor
    public MoveTransition(final Board transitionBoard, final Move move, final MoveStatus moveStatus){
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    //returns the movestatus specifically if the move can be done
    public MoveStatus getMoveStatus(){
        return this.moveStatus;
    }

    //returns the new board
    public Board getBoard() {
        return this.transitionBoard;
    }
}
