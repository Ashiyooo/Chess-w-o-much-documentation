//Code written by Joseph Victor Sumbong for CMSC 22

//package
package com;

//imports
import com.chess.board.Board;
import com.gui.Table;

public class JChess {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
        Table.get().show();
    }
}
