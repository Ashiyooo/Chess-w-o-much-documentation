package com.chess.player.chessEngine;

import com.chess.board.Board;
import com.chess.board.Move;

public interface MoveStrategy {

    Move execute(Board board);
}
