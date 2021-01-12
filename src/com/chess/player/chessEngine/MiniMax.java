package com.chess.player.chessEngine;

import com.chess.board.Board;
import com.chess.board.Move;
import com.chess.board.MoveTransition;

public class MiniMax implements MoveStrategy{

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;

    public MiniMax(final int searchDepth){
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public String toString(){
        return "MiniMax";
    }

    @Override
    public Move execute(Board board) {
        final long startTime = System.currentTimeMillis();

        Move bestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer() + " thinking with depth = " + this.searchDepth);

        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                currentValue = board.currentPlayer().getColor().isWhite()? min(moveTransition.getBoard(), this.searchDepth -1):
                                max(moveTransition.getBoard(), this.searchDepth -1);

                if(board.currentPlayer().getColor().isWhite() && currentValue >= highestSeenValue){
                    highestSeenValue = currentValue;
                    bestMove = move;
                }else if(board.currentPlayer().getColor().isBlack() && currentValue <= lowestSeenValue){
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            }
        }

        final long executionTime = System.currentTimeMillis() - startTime;
        System.out.println(board.currentPlayer() + " took " + executionTime + "ms to execute the move");
        return bestMove;
    }

    private static boolean isEndGameScenario(final Board board){
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInStaleMate();
    }
    public int min(final Board board, final int depth){

        if(depth == 0 || isEndGameScenario(board)){
            return this.boardEvaluator.evaluate(board, depth);
        }

        int lowestSeenValue = Integer.MAX_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = max(moveTransition.getBoard(), depth -1);
                if(currentValue <= lowestSeenValue){
                    lowestSeenValue = currentValue;
                }
            }
        }

        return lowestSeenValue;
    }

    public int max(final Board board, final int depth){

        if(depth == 0 || isEndGameScenario(board)){
            return this.boardEvaluator.evaluate(board, depth);
        }

        int highestSeenValue = Integer.MIN_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = min(moveTransition.getBoard(), depth -1);
                if(currentValue >= highestSeenValue){
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }

}
