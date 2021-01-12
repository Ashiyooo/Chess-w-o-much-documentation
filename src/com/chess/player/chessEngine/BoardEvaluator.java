package com.chess.player.chessEngine;
import com.chess.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);
}
