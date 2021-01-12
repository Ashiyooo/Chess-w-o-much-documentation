//Code written by Joseph Victor Sumbong
//package
package com.chess.board;

//imports
import com.chess.board.Board.Builder;
import com.chess.piece.Pawn;
import com.chess.piece.Piece;
import com.chess.piece.Rook;

/**
 * Move is an abstract class that wherein the methods here will be fleshed out
 * in the subsequent child classes
 * The following methods are found here: hashCode(), equals(), getCurrentPosition(),
 * getMovePosition(), getMovedPiece(), isAttack(), isCastlingMove(), getAttackedPiece(),
 * getBoard(), and execute()
 */
public abstract class Move {
    //fields
    protected final Board board;
    protected final Piece movedPiece;
    protected final int movePosition;
    protected final boolean isFirstMove;

    //constructor
    private Move(final Board board,final Piece movedPiece, int movePosition) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.movePosition = movePosition;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    //convenience constructor
    private Move(final Board board, final int movePosition){
        this.board = board;
        this.movePosition = movePosition;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    /**
     * @Override the hashCode() of java.lang because the way the code is written keeps in mind immutability of the program
     * the hashCode of the object will not change so we can call it from here.
     * @return result which is an integer containing the hashcode
     */
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;

        result = prime * result + this.movePosition;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePosition();

        return result;
    }

    /**
     * @Override the equals() of java.lang because java.lang equals() tests the reference equality. We don't want that but
     * rather we want object equality so we override it to fit our need
     * @param other an object to be compared with
     * @return a boolean to see if the objects are equal
     */
    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }

        if(!(other instanceof Move)){
            return false;
        }

        final Move otherMove = (Move) other;
        return getMovePosition() == otherMove.getMovePosition()
                && getMovedPiece().equals(otherMove.getMovedPiece()) &&
                getCurrentPosition() == otherMove.getCurrentPosition();
    }

    /**
     * getCurrentPosition() is a method that returns the movedPiece position on the chessboard
     * It does not take any parameter
     * @return the position of the movedPiece which is an integer value
     */
    public int getCurrentPosition() {
        return this.getMovedPiece().getPiecePosition();
    }

    /**
     * getMovePosition() is a method that returns the resulting position if the move is made
     * @return the movePosition which is an integer value
     */
    public int getMovePosition(){
        return this.movePosition;
    }

    /**
     * getMovedPiece() is a method that lets the caller get the movedPiece
     * @return the movedPiece which is a piece object
     */
    public Piece getMovedPiece(){
        return this.movedPiece;
    }

    /**
     * isAttack() is a boolean method that returns false for the Move class because not all
     * moves in a chess game is an attacking or capture move so we can state it to be false always
     * but we will override it for specific child classes of move
     * @return boolean which is false
     */
    public boolean isAttack(){
        return false;
    }

    /**
     * isCastlingMove() is a boolean method that returns false for the Move class because most moves made is
     * not a castling move so we can state it to be false here but override it for the specific child class later on
     * @return a boolean which is false
     */
    public boolean isCastlingMove(){
        return false;
    }

    /**
     * getAttackedPiece() is a method that returns null here for the parent class but we will override it for the specific child classes
     * @return null always for the Move class
     */
    public Piece getAttackedPiece(){
        return null;
    }

    /**getBoard() is a method that returns the board for the caller
     * @return this.board which is a board object
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * execute() is a method that executes the move and returns a new board with the changed game state made by the move
     * @return a Board Object that is built by the builder class
     */
    public Board execute() {

        //create a new board to show the new change in the board to allow showing of past board states
        final Builder builder = new Builder();

        //set the pieces of the current player which is not the piece that has to be moved to their current locations
        for(final Piece piece : this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }

        //set the pieces current location of the opponent on the new board
        for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }

        //move the moved piece
        builder.setPiece(this.movedPiece.movePiece(this));

        //set the next turn as the opponents turn
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());

        //build the board
        return builder.build();
    }

    /**
     * Capture Move is a child class of Move.
     * This class is for all the attacking moves of the pieces
     * The methods found here are the following:
     * hashcode(), equals(), isAttack(), getAttackedPiece(),
     * and toString()
     */
    public static class CaptureMove extends Move {
        //field
        final Piece attackedPiece;

        //constructor
        public CaptureMove(final Board board, final Piece movedPiece, int movePosition, final Piece attackedPiece) {
            super(board, movedPiece, movePosition);
            this.attackedPiece = attackedPiece;
        }

        /**
         * As stated before since these are immutable objects their reference does not change so we can just call them
         * again
         * @return integer value of the hashcode
         */
        @Override
        public int hashCode(){
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        /**
         * As stated before, we want an Object equality rather than reference equality so we override the java.lang method
         * to achieve our need
         * @param other an object to be compared with
         * @return a boolean if the the objects are equal
         */
        @Override
        public boolean equals(final Object other){
            if(this == other){
                return true;
            }

            if(!(other instanceof CaptureMove)){
                return false;
            }

            final CaptureMove otherCaptureMove = (CaptureMove) other;
            return super.equals(otherCaptureMove) && getAttackedPiece().equals(otherCaptureMove.getAttackedPiece());
        }

        /**
         * Since this is a CaptureMove class it is defined to be an attacking move, capturing a piece of the opponent
         * therefore we override the isAttack() from the parent class and we return true
         * @return boolean true always since it is an attack move
         */
        @Override
        public boolean isAttack(){
            return true;
        }

        /**
         * getAttackedPiece() is an method that we override from the parent class because it is an attacking move
         * and an attacking move needs an attackedPiece so for CaptureMoves there is always an attacked piece so this
         * method allows the caller to get the attackedPiece
         * @return attackedPiece which is a Piece object
         */
        @Override
        public Piece getAttackedPiece(){
            return this.attackedPiece;
        }

        /**
         * For minor pieces to show a capture move, the chess notation is the tile the piece came from adding an x and
         * followed by the tile that it captured on. This will be used for the GameHistoryPanel to show the moves made
         * by the players
         * @return a String thats shows the move based on chess notation
         */
        @Override
        public String toString(){
            return this.movedPiece.toString().charAt(0) + "x"
                    + BoardUtils.getTileAtPosition(this.movePosition);
        }
    }

    /**
     * MajorAttackMove is a child class of Capture Move class
     * This MajorAttackMove is a capture move that is done by Major pieces which are the King, Queen, Knight, Bishop, and the Rook
     * The methods found here are the following: equals() and toString()
     */
    public static class MajorAttackMove extends CaptureMove{

        //Constructor
        public MajorAttackMove(final Board board, final Piece movedPiece, final int movePosition, final Piece attackedPiece) {
            super(board, movedPiece, movePosition, attackedPiece);
        }

        /**
         * As stated previously we want Object equality rather than Reference equality which is the default for the java.lang
         * so we override this method to fit with our need
         * @param other is an Object to be compared
         * @return a boolean to see if they are the same object
         */
        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof MajorAttackMove && super.equals(other);
        }

        /**
         * The toString() method is to show the move made which will be important for the GameHistoryPanel class wherein you can
         * see the moves made by the players and we use the standard chess notations
         * @return a String to show the move specifically when a Major piece captures another piece
         */
        @Override
        public String toString(){
            return movedPiece.getPieceType() + "x" + BoardUtils.getTileAtPosition(this.movePosition);
        }
    }

    /**
     * NonCaptureMove is a child class of move
     * It deals with Moves that just moves a piece from one tile to an unoccupied tile
     * The methods found here are: equals() and toString()
     */
    public static final class NonCaptureMove extends Move {
        //constructor
        public NonCaptureMove(final Board board, final Piece movedPiece, int movePosition) {
            super(board, movedPiece, movePosition);
        }

        /**
         * As stated before we want Object equality rather than reference equality which is the default for java.lang
         * so we override the equals() method to suit our needs
         * @param other an object to be compared with
         * @return a boolean based on the comparison of the objects if they are equal or not
         */
        @Override
        public boolean equals(final Object other){
            return  this == other || other instanceof NonCaptureMove && super.equals(other);
        }

        /**
         * The toString() method is to show the move made which will be important for the GameHistoryPanel class wherein you can
         * see the moves made by the players and we use the standard chess notations
         * @return a String to show the move specifically when a piece moves to another tile
         */
        @Override
        public String toString(){
            return movedPiece.getPieceType().toString() + BoardUtils.getTileAtPosition(this.movePosition);
        }

    }

    /**
     * PawnCaptureMove is a child class of Move
     * The methods found here are: equals() and toString()
     */
    public static final class PawnMove extends Move {
        //Constructor
        public PawnMove(final Board board, final Piece movedPiece, final int movePosition) {
            super(board, movedPiece, movePosition);
        }

        /**
         * As stated before we want Object equality rather than reference equality which is the default for java.lang
         * so we override the equals() method to suit our needs
         * @param other an object to be compared with
         * @return a boolean based on the comparison of the objects if they are equal or not
         */
        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof PawnMove && super.equals(other);
        }

        /**
         * The toString() method is to show the move made which will be important for the GameHistoryPanel class wherein you can
         * see the moves made by the players and we use the standard chess notations
         * @return a String to show the move specifically when a pawn piece moves a tile up
         */
        @Override
        public String toString(){
            return BoardUtils.getTileAtPosition(this.movePosition);
        }
    }

    /** PawnJump is a child class of Move
     * It deals with the special movement of the pawn in which if it is its first move it
     * can jump 2 tiles forward
     * The methods found here are: execute() and toString()
     */
    public static class PawnJump extends Move {
        public PawnJump(final Board board, final Piece movedPiece, final int movePosition) {
            super(board, movedPiece, movePosition);
        }

        /**
         * execute() method is an override method from the parent class to create a new board to show the new state of the
         * board and the difference here is that it sets the enPassantPawn as the pawn that has been moved
         * @return
         */
        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }

            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }

        /**
         * The toString() method is to show the move made which will be important for the GameHistoryPanel class wherein you can
         * see the moves made by the players and we use the standard chess notations
         * @return a String to show the move specifically when a pawn piece moves 2 tile up
         */
        @Override
        public String toString(){
            return BoardUtils.getTileAtPosition(this.movePosition);
        }
    }

    /**
     * PawnCaptureMove is a child class of the CaptureMove
     * It deals with the special capture move of the pawns
     * The methods found here are: equals() and toString()
     */
    public static class PawnCaptureMove extends CaptureMove {
        //Constructor
        public PawnCaptureMove(final Board board, final Piece movedPiece, final int movePosition, final Piece attackedPiece) {
            super(board, movedPiece, movePosition, attackedPiece);
        }

        /**
         * As stated before we want Object equality rather than reference equality which is the default for java.lang
         * so we override the equals() method to suit our needs
         * @param other an object to be compared with
         * @return a boolean based on the comparison of the objects if they are equal or not
         */
        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof PawnCaptureMove && super.equals(other);
        }

        /**
         * The toString() method is to show the move made which will be important for the GameHistoryPanel class wherein you can
         * see the moves made by the players and we use the standard chess notations
         * @return a String to show the move specifically when a pawn piece captures another piece
         */
        @Override
        public String toString(){
            return BoardUtils.getTileAtPosition(this.movedPiece.getPiecePosition()).charAt(0) + "x"
                    + BoardUtils.getTileAtPosition(this.movePosition);
        }
    }

    /**
     * PawnEnPassantMove is a child class of the PawnCaptureMove
     * It deals with the special capture move of the pawn when there is an enPassantPawn on the board
     * An enPassant Pawn in chess is when a Pawn jumps 2 tiles forward where there is an enemy pawn in one of its sides
     * The rules in chess allows this pawn to be captured
     * The methods found here are: equals(), execute() and toString()
     */
    public static class PawnEnPassantMove extends PawnCaptureMove {
        public PawnEnPassantMove(final Board board, final Piece movedPiece,
                                 final int movePosition, final Piece attackedPiece) {
            super(board, movedPiece, movePosition, attackedPiece);
        }

        /**
         * As stated before we want Object equality rather than reference equality which is the default for java.lang
         * so we override the equals() method to suit our needs
         * @param other an object to be compared with
         * @return a boolean based on the comparison of the objects if they are equal or not
         */
        @Override
        public boolean equals(final Object other){
            return  this == other || other instanceof  PawnEnPassantMove && super.equals(other);
        }

        /**
         * execute() method here is an override of the execute method in the Move class
         * It builds a new board that captures the enPassantPawn and sets the new position of the pawn
         * on the board
         * @return a Board object that is built
         */
        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece: this.board.currentPlayer().getActivePieces()){
                if (!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                if(!piece.equals(this.getAttackedPiece())){
                    builder.setPiece(piece);
                }
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }
    }


    /**
     * PawnPromotion class is a child class of Move
     * It follows the decorator design pattern to add functionality to the pawn
     */
    public static class PawnPromotion extends Move{
        //fields
        final Move decoratedMove;
        final Pawn promotedPawn;

        //Constructor
        public PawnPromotion(final Move decoratedMove) {
            super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getMovePosition());
            this.decoratedMove = decoratedMove;
            this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
        }

        /**
         * Override the hashCode() to allow us to call it anytime
         * @return integer value of the hashcode
         */
        @Override
        public int hashCode(){
            return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
        }

        /**
         * As stated before we want Object equality rather than reference equality which is the default for java.lang
         * so we override the equals() method to suit our needs
         * @param other an object to be compared with
         * @return a boolean based on the comparison of the objects if they are equal or not
         */
        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof PawnPromotion && super.equals(other);
        }

        /**
         * execute() method is an override method from the parent class to create a new board to show the new state of the
         * board and the difference here is that instead of a pawn in the promotion squares a selected major piece is found
         * on the tile where the pawn should be. Specifically in this program we automatically promote to a Queen since it
         * has the highest rate of piece to be promoted into
         * @return
         */
        @Override
        public Board execute(){
            final Board pawnMovedBoard = this.decoratedMove.execute();
            final Board.Builder builder = new Builder();
            for(final Piece piece : pawnMovedBoard.currentPlayer().getActivePieces()){
                if(!this.promotedPawn.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : pawnMovedBoard.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
            builder.setMoveMaker(pawnMovedBoard.currentPlayer().getColor());
            return builder.build();
        }

        /**
         * isAttack() is being override here to see if the move attacks another piece
         * @return a boolean value if the move is an capturing move or not
         */
        @Override
        public boolean isAttack(){
            return this.decoratedMove.isAttack();
        }

        /**
         * getAttackedPiece() is being override here in the case that it has capture a piece to get to the pawn
         * promotion square
         * @return piece being attacked if there is one
         */
        @Override
        public Piece getAttackedPiece(){
            return this.decoratedMove.getAttackedPiece();
        }

        /**
         * The toString() method is to show the move made which will be important for the GameHistoryPanel class wherein you can
         * see the moves made by the players and we use the standard chess notations
         * @return a String to show the move specifically when a pawn piece captures another piece
         */
        @Override
        public String toString(){
            return BoardUtils.getTileAtPosition(this.movePosition) + "Q";
        }
    }


    /**
     * CastleMove is a child class of Move
     * It is a special move made by the King piece
     * wherein both the King and the castleRook must have not made a move in the game already
     * The methods here are: getCastleRook(), isCastlingMove(), hashCode(), equals(), and execute()
     */
    abstract static class CastleMove extends Move{
        //fields
        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        //Constructor
        public CastleMove(final Board board, final Piece movedPiece, final int movePosition, final Rook castleRook,
                          final int castleRookStart, final int castleRookDestination){
            super(board, movedPiece, movePosition);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        /**
         * getCastleRook() is a method that allows the caller to get the castleRook
         * @return a Rook piece stored in castleRook
         */
        public Rook getCastleRook(){
            return this.castleRook;
        }

        /**
         * isCastlingMove() is a boolean method that override the same name method from the parent class
         * since this special move is a castling move
         * @return a boolean which is always true
         */
        @Override
        public boolean isCastlingMove(){
            return true;
        }

        /**
         * @Override the hashCode() of java.lang because the way the code is written keeps in mind immutability of the program
         * the hashCode of the object will not change so we can call it from here.
         * @return result which is an integer containing the hashcode
         */
        @Override
        public int hashCode(){
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            result = prime * result + this.castleRookDestination;
            return result;
        }

        /**
         * As stated before we want Object equality rather than reference equality which is the default for java.lang
         * so we override the equals() method to suit our needs
         * @param other an object to be compared with
         * @return a boolean based on the comparison of the objects if they are equal or not
         */
        @Override
        public boolean equals(final Object other){
            if(this == other){
                return true;
            }
            if(!(other instanceof CastleMove)){
                return false;
            }

            final CastleMove otherCastleMove = (CastleMove)other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }

        /**
         * execute() is a method to show the new board state using the Builder class and it override the execute method
         * from the parent class
         * @return a Board object
         */
        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getColor()));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }

    }

    /**
     * KingSideCastleMove is a child class of CastleMove
     * It is for the specific castle move in the kingside which chess players sometimes call short castle
     * The methods found here are: equals() and toString()
     */
    public static final class KingSideCastleMove extends CastleMove {
        //Constructor
        public KingSideCastleMove(Board board, Piece movedPiece, int movePosition, final Rook castleRook,
                                  final int castleRookStart, final int castleRookDestination) {
            super(board, movedPiece, movePosition, castleRook, castleRookStart, castleRookDestination);
        }

        /**
         * As stated before we want Object equality rather than reference equality which is the default for java.lang
         * so we override the equals() method to suit our needs
         * @param other an object to be compared with
         * @return a boolean based on the comparison of the objects if they are equal or not
         */
        @Override
        public boolean equals(final Object other){
            return  this == other || other instanceof  KingSideCastleMove && super.equals(other);
        }

        /**
         * The toString() method is to show the move made which will be important for the GameHistoryPanel class wherein you can
         * see the moves made by the players and we use the standard chess notations
         * @return a String to show the move specifically when a KingSideCastle occurs
         */
        @Override
        public String toString(){
            return "O-O";
        }
    }

    /**
     * QueenSideCastleMove is a child class of CastleMove
     * It is for the specific castle move in the queenside which chess players sometimes call long castle
     * The methods found here are: equals() and toString()
     */
    public static class QueenSideCastleMove extends CastleMove{
        //Constructor
        public QueenSideCastleMove(final Board board, final Piece movedPiece, final int movePosition, final Rook castleRook,
                                   final int castleRookStart, final int castleRookDestination) {
            super(board, movedPiece, movePosition,  castleRook, castleRookStart, castleRookDestination);
        }

        /**
         * As stated before we want Object equality rather than reference equality which is the default for java.lang
         * so we override the equals() method to suit our needs
         * @param other an object to be compared with
         * @return a boolean based on the comparison of the objects if they are equal or not
         */
        @Override
        public boolean equals(final Object other){
            return  this == other || other instanceof  QueenSideCastleMove && super.equals(other);
        }

        /**
         * The toString() method is to show the move made which will be important for the GameHistoryPanel class wherein you can
         * see the moves made by the players and we use the standard chess notations
         * @return a String to show the move specifically when a QueenSideCastle occurs
         */
        @Override
        public String toString(){
            return "O-O-O";
        }
    }

    /**Null move is a child class of the Move class
     * Null Object Design Pattern is applied to represent a null object rather than to return just a null
     */
    public static class NullMove extends Move{
        //Constructor
        public NullMove() {
            super(null, 65);
        }

        /**
         * Overrides the execute() method from the parent class because a null move
         * should not be executed that is why it throws a RunTimeException
         * @return a RunTimeException
         */
        @Override
        public Board execute(){
            throw new RuntimeException("cannot execute null move");
        }

        /**
         * overrides the getCurrentPosition() method from the parent class
         * @return -1 since it should on a valid tile
         */
        @Override
        public int getCurrentPosition(){
            return -1;
        }
    }

    /*
    Factory Method design pattern
    creates an unspecified move and checks if it is
    * */
    public static class MoveFactory{
        private static final Move NULL_MOVE = new NullMove();
        private MoveFactory(){
            throw new RuntimeException("Not instantiable");
        }

        public static Move createMove(final Board board, final int currentPosition, final int movePosition){
            for(final Move move : board.getAllLegalMoves()){
                if(move.getCurrentPosition() == currentPosition && move.getMovePosition() == movePosition){
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
