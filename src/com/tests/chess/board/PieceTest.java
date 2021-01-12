package com.tests.chess.board;

import com.chess.Color;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Move;
import com.chess.board.MoveTransition;
import com.chess.piece.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    public void queenOnMiddle(){
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(57, Color.WHITE, false));
        builder.setPiece(new Queen(35, Color.WHITE));
        builder.setPiece(new King(6, Color.BLACK, false));
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
        assertEquals(whiteMoves.size(), 32);
        assertEquals(blackMoves.size(), 5);

        //checking random Queen Moves
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("a4"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("c5"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("e5"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("g1"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("h8"))));
    }

    @Test
    public void queenOnCorners(){
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(50, Color.WHITE, false));
        builder.setPiece(new Queen(56, Color.WHITE));
        builder.setPiece(new King(6, Color.BLACK, false));
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
        assertEquals(whiteMoves.size(), 29);
        assertEquals(blackMoves.size(), 5);

        //checking random Queen Moves
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("a1"),
                BoardUtils.getPositionAtTile("a4"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("a1"),
                BoardUtils.getPositionAtTile("d4"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("a1"),
                BoardUtils.getPositionAtTile("e1"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("a1"),
                BoardUtils.getPositionAtTile("g1"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("a1"),
                BoardUtils.getPositionAtTile("b8"))));
    }

    @Test
    public void kingOnMiddle(){
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(35, Color.WHITE, false));
        builder.setPiece(new King(6, Color.BLACK, false));
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
        assertEquals(whiteMoves.size(), 8);
        assertEquals(blackMoves.size(), 5);

        //checking random King Moves
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("d5"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("c5"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("e5"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("g1"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("h8"))));
    }

    @Test
    public void kingOnCorners(){
            final Board.Builder builder = new Board.Builder();
            builder.setPiece(new King(35, Color.WHITE, false));
            builder.setPiece(new King(7, Color.BLACK, false));
            builder.setMoveMaker(Color.WHITE);

            final Board board = builder.build();
            final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
            final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
            assertEquals(whiteMoves.size(), 8);
            assertEquals(blackMoves.size(), 3);

            //checking random King Moves
            assertTrue(blackMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h8"),
                    BoardUtils.getPositionAtTile("g8"))));
            assertTrue(blackMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h8"),
                    BoardUtils.getPositionAtTile("h7"))));
            assertTrue(blackMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h8"),
                    BoardUtils.getPositionAtTile("g7"))));
            assertFalse(blackMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h8"),
                    BoardUtils.getPositionAtTile("a1"))));
            assertFalse(blackMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h8"),
                    BoardUtils.getPositionAtTile("b7"))));
    }

    @Test
    public void rookOnMiddle(){
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(57, Color.WHITE, false));
        builder.setPiece(new Rook(35, Color.WHITE));
        builder.setPiece(new King(6, Color.BLACK, false));
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
        assertEquals(whiteMoves.size(), 19);
        assertEquals(blackMoves.size(), 5);

        //checking random rook Moves
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("a4"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("d1"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("d8"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("h4"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("h8"))));
    }

    @Test
    public void rookOnCorners(){
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(50, Color.WHITE, false));
        builder.setPiece(new Rook(63, Color.WHITE));
        builder.setPiece(new King(6, Color.BLACK, false));
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
        assertEquals(whiteMoves.size(), 22);
        assertEquals(blackMoves.size(), 5);

        //checking random Rook Moves
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("h4"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("a1"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("d1"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("d4"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("b8"))));

    }

    @Test
    public void bishopOnMiddle(){
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(57, Color.WHITE, false));
        builder.setPiece(new Bishop(35, Color.WHITE));
        builder.setPiece(new King(6, Color.BLACK, false));
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
        assertEquals(whiteMoves.size(), 18);
        assertEquals(blackMoves.size(), 5);

        //checking random Bishop Moves
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("a1"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("h8"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("a7"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("g1"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("e4"))));
    }

    @Test
    public void bishopOnCorners(){
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(50, Color.WHITE, false));
        builder.setPiece(new Bishop(63, Color.WHITE));
        builder.setPiece(new King(6, Color.BLACK, false));
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
        assertEquals(whiteMoves.size(), 15);
        assertEquals(blackMoves.size(), 5);

        //checking random Bishop Moves
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("a8"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("b7"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("g2"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("g1"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("h8"))));

    }

    @Test
    public void knightOnMiddle(){
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(57, Color.WHITE, false));
        builder.setPiece(new Knight(35, Color.WHITE));
        builder.setPiece(new King(6, Color.BLACK, false));
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
        assertEquals(whiteMoves.size(), 13);
        assertEquals(blackMoves.size(), 5);

        //checking random Bishop Moves
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("c2"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("b3"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("e6"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("g1"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("d4"),
                BoardUtils.getPositionAtTile("e4"))));
    }

    @Test
    public void knightOnCorners(){
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(50, Color.WHITE, false));
        builder.setPiece(new Knight(63, Color.WHITE));
        builder.setPiece(new King(6, Color.BLACK, false));
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final Collection<Move> whiteMoves = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackMoves = board.blackPlayer().getLegalMoves();
        assertEquals(whiteMoves.size(), 10);
        assertEquals(blackMoves.size(), 5);

        //checking random Knight Moves
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("f2"))));
        assertTrue(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("g3"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("g2"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("d4"))));
        assertFalse(whiteMoves.contains(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("b8"))));

    }

    @Test
    public void pawnPromotion(){
        final Board.Builder builder = new Board.Builder();

        builder.setPiece(new King( 52, Color.WHITE, false));
        builder.setPiece(new Pawn(8, Color.WHITE));

        builder.setPiece(new King(50, Color.BLACK, false));
        builder.setPiece(new Pawn(55, Color.BLACK));

        builder.setMoveMaker(Color.WHITE);
        final Board board = builder.build();
        //white promotion move
        final Move wpm = Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("a7"), BoardUtils.getPositionAtTile("a8"));
        final MoveTransition t1 = board.currentPlayer().makeMove(wpm);
        assertTrue(t1.getMoveStatus().isDone());

        //black promotion move
        final Move bpm = Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getPositionAtTile("h2"), BoardUtils.getPositionAtTile("h1"));
        final MoveTransition t2 = t1.getBoard().currentPlayer().makeMove(bpm);
        assertTrue(t2.getMoveStatus().isDone());

        //check if it is queen
        final MoveTransition t3 = t2.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getPositionAtTile("a8")
        , BoardUtils.getPositionAtTile("h8")));
        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getPositionAtTile("h1"),
                BoardUtils.getPositionAtTile("a1")));
        assertTrue(t4.getMoveStatus().isDone());
    }

    @Test
    public void enPassantPawn(){
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getPositionAtTile("g2"),
                BoardUtils.getPositionAtTile("g4")));
        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t1.getBoard(),
                BoardUtils.getPositionAtTile("a7"), BoardUtils.getPositionAtTile("a6")));
        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t2.getBoard(),
                BoardUtils.getPositionAtTile("g4"), BoardUtils.getPositionAtTile("g5")));
        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t3.getBoard(),
                BoardUtils.getPositionAtTile("h7"), BoardUtils.getPositionAtTile("h5")));
        assertTrue(t4.getMoveStatus().isDone());

        final Collection<Move> whiteMoves = t4.getBoard().whitePlayer().getLegalMoves();
        //en passant move
        final Move epm = Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getPositionAtTile("g5"),
                BoardUtils.getPositionAtTile("h6"));
        assertTrue(whiteMoves.contains(epm));
        final MoveTransition t5 = t4.getBoard().currentPlayer().makeMove(epm);
        assertTrue(t5.getMoveStatus().isDone());
    }
}