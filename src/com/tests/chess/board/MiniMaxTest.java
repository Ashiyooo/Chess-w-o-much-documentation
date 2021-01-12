package com.tests.chess.board;

import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Move;
import com.chess.board.MoveTransition;
import com.chess.player.chessEngine.MiniMax;
import com.chess.player.chessEngine.MoveStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MiniMaxTest {

    @Test
    public void testFoolsMate(){
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board,
                BoardUtils.getPositionAtTile("f2"), BoardUtils.getPositionAtTile("f3")));
        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t1.getBoard(),
                BoardUtils.getPositionAtTile("e7"), BoardUtils.getPositionAtTile("e5")));
        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t2.getBoard(),
                BoardUtils.getPositionAtTile("g2"), BoardUtils.getPositionAtTile("g4")));
        assertTrue(t3.getMoveStatus().isDone());

        final MoveStrategy strategy = new MiniMax(4);
        final Move engineMove = strategy.execute(t3.getBoard());
        final Move bestMove = Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getPositionAtTile("d8"),
                BoardUtils.getPositionAtTile("h4"));
        assertEquals(engineMove, bestMove);
    }

    @Test
    public void fourMovesCheckMateTest(){
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board,
                BoardUtils.getPositionAtTile("e2"), BoardUtils.getPositionAtTile("e4")));
        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t1.getBoard(),
                BoardUtils.getPositionAtTile("e7"), BoardUtils.getPositionAtTile("e5")));
        assertTrue(t2.getMoveStatus().isDone());

        //white move
        final MoveTransition t3 = t2.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t2.getBoard(),
                BoardUtils.getPositionAtTile("f1"), BoardUtils.getPositionAtTile("c4")));
        assertTrue(t3.getMoveStatus().isDone());

        //black move
        final MoveTransition t4 = t3.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t3.getBoard(),
                BoardUtils.getPositionAtTile("b8"), BoardUtils.getPositionAtTile("c6")));
        assertTrue(t4.getMoveStatus().isDone());

        //white move
        final MoveTransition t5 = t4.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t4.getBoard(),
                BoardUtils.getPositionAtTile("d1"), BoardUtils.getPositionAtTile("f3")));
        assertTrue(t5.getMoveStatus().isDone());

        //black move
        final MoveTransition t6 = t5.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t5.getBoard(),
                BoardUtils.getPositionAtTile("a7"), BoardUtils.getPositionAtTile("a5")));
        assertTrue(t6.getMoveStatus().isDone());

        final MoveStrategy strategy = new MiniMax(4);
        final Move engineMove = strategy.execute(t6.getBoard());
        final Move bestMove = Move.MoveFactory.createMove(t6.getBoard(), BoardUtils.getPositionAtTile("f3"),
                BoardUtils.getPositionAtTile("f7"));
        assertEquals(engineMove, bestMove);
    }
}