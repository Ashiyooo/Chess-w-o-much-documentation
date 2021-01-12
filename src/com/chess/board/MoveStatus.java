package com.chess.board;

/*
* an enum class which basically tells if a
* move can be done meaning that the move is a legal move
* and not an illegal move that a piece cannot do
* or a move that cant be allowed to occur since it exposes the
* king
* */
public enum MoveStatus{
    DONE{
        @Override
        public boolean isDone() {
            return true;
        }
    },

    LEAVES_PLAYER_IN_CHECK{
        @Override
        public boolean isDone() {
            return false;
        }
    },
    ILLEGAL_MOVE{
        @Override
        public boolean isDone() {
            return false;
        }
    };



    public abstract boolean isDone();
}
