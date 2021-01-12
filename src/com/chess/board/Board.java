package com.chess.board;

import com.chess.Color;
import com.chess.piece.*;
import com.chess.player.BlackPlayer;
import com.chess.player.Player;
import com.chess.player.WhitePlayer;

import java.util.*;

public class Board {
    //fields
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;

    //constructor
    private Board(final Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Color.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Color.BLACK);
        this.enPassantPawn = builder.enPassantPawn;
        final Collection<Move> whiteStandardLegalMove = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMove = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMove, blackStandardLegalMove);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMove, blackStandardLegalMove);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer,this.blackPlayer);
    }

    /*helper routine */
    public Player whitePlayer(){
        return this.whitePlayer;
    }

    public Player blackPlayer(){
        return this.blackPlayer;
    }

    public Player currentPlayer(){
        return this.currentPlayer;
    }

    public Pawn getEnPassantPawn(){return this.enPassantPawn;}

    public Collection<Piece> getBlackPieces(){
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces(){
        return this.whitePieces;
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece: pieces){
            legalMoves.addAll(piece.calculateLegalMove(this));
        }
        return legalMoves;
    }

    private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Color color) {
        final List<Piece> activePieces = new ArrayList<>();
        for(final Tile tile : gameBoard){
            if(tile.isTileOccupied()){
                final Piece piece = tile.getPiece();
                if(piece.getColor() == color){
                    activePieces.add(piece);
                }
            }
        }
        return activePieces;
    }

    public Tile getTile(final int tileCoordinate){
        return gameBoard.get(tileCoordinate);
    }

    private static List<Tile> createGameBoard(final Builder builder){
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return Arrays.asList(tiles);
    }

    //sets a standard chess board with pieces on their proper places
    public static Board createStandardBoard(){
        final Builder builder = new Builder();
        //Black Layout
        builder.setPiece(new Rook(0, Color.BLACK));
        builder.setPiece(new Knight(1, Color.BLACK));
        builder.setPiece(new Bishop(2, Color.BLACK));
        builder.setPiece(new Queen(3, Color.BLACK));
        builder.setPiece(new King(4, Color.BLACK, false));
        builder.setPiece(new Bishop(5, Color.BLACK));
        builder.setPiece(new Knight(6, Color.BLACK));
        builder.setPiece(new Rook(7, Color.BLACK));
        for(int i = 8; i < 16; i++){
            builder.setPiece(new Pawn(i, Color.BLACK));
        }

        //White Layout
        for(int i = 48; i < 56; i++){
            builder.setPiece(new Pawn(i, Color.WHITE));
        }
        builder.setPiece(new Rook(56, Color.WHITE));
        builder.setPiece(new Knight(57, Color.WHITE));
        builder.setPiece(new Bishop(58, Color.WHITE));
        builder.setPiece(new Queen(59, Color.WHITE));
        builder.setPiece(new King(60, Color.WHITE, false));
        builder.setPiece(new Bishop(61, Color.WHITE));
        builder.setPiece(new Knight(62, Color.WHITE));
        builder.setPiece(new Rook(63, Color.WHITE));

        //first move
        builder.setMoveMaker(Color.WHITE);

        return builder.build();
    }

    public Iterable<Move> getAllLegalMoves() {
        List<Move> allLegalMoves = new ArrayList<>();
        allLegalMoves.addAll(this.whitePlayer.getLegalMoves());
        allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
        return Collections.unmodifiableList(allLegalMoves);
    }

    //Builder class
    public static class Builder{

        Map<Integer, Piece> boardConfig;
        Color nextMoveMaker;
        Pawn enPassantPawn;

        //using hashmap for the board configuration
        public Builder(){
            this.boardConfig = new HashMap<>();
        }

        //set pieces on their proper initial tiles
        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        //sets who's turn it is
        public Builder setMoveMaker(final Color nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        //builds the board
        public Board build(){
            return new Board(this);
        }

        //to identify the enPassantPawn
        public void setEnPassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
        }
    }
}
