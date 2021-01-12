package com.tests.chess.board;

import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Move;
import com.chess.board.MoveTransition;
import com.chess.player.chessEngine.MiniMax;
import com.chess.player.chessEngine.MoveStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CastlingTest {

    @Test
    public void kingSideCastleTest(){
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board,
                BoardUtils.getPositionAtTile("e2"), BoardUtils.getPositionAtTile("e4")));
        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t1.getBoard(),
                BoardUtils.getPositionAtTile("e7"), BoardUtils.getPositionAtTile("e5")));
        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t2.getBoard(),
                BoardUtils.getPositionAtTile("g1"), BoardUtils.getPositionAtTile("h3")));
        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t3.getBoard(),
                BoardUtils.getPositionAtTile("g8"), BoardUtils.getPositionAtTile("h6")));
        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t4.getBoard(),
                BoardUtils.getPositionAtTile("f1"), BoardUtils.getPositionAtTile("e2")));
        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t5.getBoard(),
                BoardUtils.getPositionAtTile("f8"), BoardUtils.getPositionAtTile("e7")));
        assertTrue(t6.getMoveStatus().isDone());

        final Move wm1 = Move.MoveFactory.createMove(t6.getBoard(),
                BoardUtils.getPositionAtTile("e1"), BoardUtils.getPositionAtTile("g1"));
        assertTrue(t6.getBoard().currentPlayer().getLegalMoves().contains(wm1));

        final MoveTransition t7 = t6.getBoard().currentPlayer().makeMove(wm1);
        assertTrue(t7.getMoveStatus().isDone());

        final Move bm1 = Move.MoveFactory.createMove(t7.getBoard(), BoardUtils.getPositionAtTile("e8"),
                BoardUtils.getPositionAtTile("g8"));
        assertTrue(t7.getBoard().currentPlayer().getLegalMoves().contains(bm1));

        final MoveTransition t8 = t7.getBoard().currentPlayer().makeMove(bm1);
        assertTrue(t8.getMoveStatus().isDone());
    }

    @Test
    public void queenSideCastleTest(){
        final Board board = Board.createStandardBoard();

        //white move
        final MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board,
                BoardUtils.getPositionAtTile("d2"), BoardUtils.getPositionAtTile("d4")));
        assertTrue(t1.getMoveStatus().isDone());

        //black move
        final MoveTransition t2 = t1.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t1.getBoard(),
                BoardUtils.getPositionAtTile("d7"), BoardUtils.getPositionAtTile("d5")));
        assertTrue(t2.getMoveStatus().isDone());

        //white move
        final MoveTransition t3 = t2.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t2.getBoard(),
                BoardUtils.getPositionAtTile("c1"), BoardUtils.getPositionAtTile("e3")));
        assertTrue(t3.getMoveStatus().isDone());

        //black move
        final MoveTransition t4 = t3.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t3.getBoard(),
                BoardUtils.getPositionAtTile("c8"), BoardUtils.getPositionAtTile("e6")));
        assertTrue(t4.getMoveStatus().isDone());

        //white move
        final MoveTransition t5 = t4.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t4.getBoard(),
                BoardUtils.getPositionAtTile("b1"), BoardUtils.getPositionAtTile("c3")));
        assertTrue(t5.getMoveStatus().isDone());

        //black move
        final MoveTransition t6 = t5.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t5.getBoard(),
                BoardUtils.getPositionAtTile("b8"), BoardUtils.getPositionAtTile("c6")));
        assertTrue(t6.getMoveStatus().isDone());

        //white move
        final MoveTransition t7 = t6.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t6.getBoard(),
                BoardUtils.getPositionAtTile("d1"), BoardUtils.getPositionAtTile("d2")));
        assertTrue(t7.getMoveStatus().isDone());

        //black move
        final MoveTransition t8 = t7.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t7.getBoard(),
                BoardUtils.getPositionAtTile("d8"), BoardUtils.getPositionAtTile("d7")));
        assertTrue(t8.getMoveStatus().isDone());

        //white move to castle
        final Move wm1 = Move.MoveFactory.createMove(t8.getBoard(),
                BoardUtils.getPositionAtTile("e1"), BoardUtils.getPositionAtTile("c1"));
        assertTrue(t8.getBoard().currentPlayer().getLegalMoves().contains(wm1));

        //check if the white queenside castle is done
        final MoveTransition t9 = t8.getBoard().currentPlayer().makeMove(wm1);
        assertTrue(t9.getMoveStatus().isDone());

        //black move to castle
        final Move bm1 = Move.MoveFactory.createMove(t9.getBoard(), BoardUtils.getPositionAtTile("e8"),
                BoardUtils.getPositionAtTile("c8"));
        assertTrue(t9.getBoard().currentPlayer().getLegalMoves().contains(bm1));

        //check if castling is done
        final MoveTransition t10 = t9.getBoard().currentPlayer().makeMove(bm1);
        assertTrue(t10.getMoveStatus().isDone());
    }
}
