//Code written by Joseph Victor Sumbong for CMSC 22
//package
package com.gui;

//imports
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Move;
import com.chess.board.Tile;
import com.chess.piece.Piece;
import com.chess.board.MoveTransition;
import com.chess.player.chessEngine.MiniMax;
import com.chess.player.chessEngine.MoveStrategy;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table extends Observable{
    //fields
    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final BoardPanel boardPanel;
    private Board chessBoard;
    private final MoveLog moveLog;
    private final GameSetup gameSetup;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece playerMovedPiece;
    private BoardDirection boardDirection;
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
    private final static String defaultPieceImagePath = "Chess Icons/";
    private static final Table INSTANCE = new Table();

    private Table(){
        this.gameFrame= new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();
        this.gameHistoryPanel = new GameHistoryPanel();
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.gameSetup = new GameSetup(this.gameFrame, true);
        this.boardDirection = BoardDirection.NORMAL;
        this.addObserver(new TableGameWatcher());
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.setVisible(true);
    }

    public void show(){
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
    }

    //since it is a singleton we have to have a method to show the instance
    public static Table get(){
        return INSTANCE;
    }

    //creates a new game
    public void newGame(){
        this.chessBoard = Board.createStandardBoard();
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(this.chessBoard);
    }

    //gets the game setup
    private GameSetup getGameSetup(){
        return this.gameSetup;
    }

    //for the computer to know if it is their turn or not
    private void setupUpdate(final GameSetup gameSetup){
        setChanged();
        notifyObservers(gameSetup);
    }

    //get the game board
    private Board getGameBoard(){
        return this.chessBoard;
    }


    //create a menu bar
    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOption());
        return tableMenuBar;
    }

    //creates the file menu
    private JMenu createFileMenu(){
        final JMenu fileMenu = new JMenu("File");

        //Allows the user to create a new game
        final JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            newGame();
        });
        fileMenu.add(newGame);

        //allows the user to exit the application
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    //create the preference menu
    private JMenu createPreferencesMenu(){
        final JMenu preferenceMenu = new JMenu("Preferences");

        //allow the user to flip the board
        final JMenuItem flipBoardMenu = new JMenuItem("Flip Board");
        flipBoardMenu.addActionListener(e -> {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessBoard);
        });

        preferenceMenu.add(flipBoardMenu);
        return preferenceMenu;
    }

    //create the option menu
    private JMenu createOption(){
        final JMenu optionMenu = new JMenu("Options");

        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
        setupGameMenuItem.addActionListener(e -> {
            Table.get().getGameSetup().promptUser();
            Table.get().setupUpdate(Table.get().getGameSetup());
        });
        optionMenu.add(setupGameMenuItem);
        return optionMenu;
    }

    //BoardPanel extends the JPanel
    //this is for the whole board
    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtils.NUM_TILES; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        //draws the board
        public void drawBoard(final Board board){
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)){
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    /*
        MoveLog class stores the moves made by the player
    */
    public static class MoveLog{
        private final List<Move> moves;

        MoveLog(){
            this.moves = new ArrayList<>();
        }

        //gets the moves made by the players
        public List<Move> getMoves(){
            return this.moves;
        }

        //adds the move made to the move log
        public void addMove(final Move move){
            this.moves.add(move);
        }

        //returns the size of the move log
        public int size(){
            return this.moves.size();
        }

        //clears the move log
        public void clear(){
            this.moves.clear();
        }
    }

    /*
        TilePanel class extends the JPanel
        It is to create each tile
    */
    private class TilePanel extends JPanel{

        private final int tileID;

        TilePanel(final BoardPanel boardPanel, final int tileID){
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isRightMouseButton(e)){
                        sourceTile = null;
                        destinationTile = null;
                        playerMovedPiece = null;

                    } else if (isLeftMouseButton(e)){
                        //first click selecting the piece
                        if(sourceTile == null){
                            sourceTile = chessBoard.getTile(tileID);
                            playerMovedPiece = sourceTile.getPiece();
                            if(playerMovedPiece == null){
                                sourceTile = null;
                            }

                        //second click places the piece on a valid tile
                        }else{
                            destinationTile = chessBoard.getTile(tileID);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
                                                                          destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if(transition.getMoveStatus().isDone()){
                                chessBoard = transition.getBoard();
                                moveLog.addMove(move);
                            }
                            sourceTile = null;
                            destinationTile = null;
                            playerMovedPiece = null;
                        }
                        //GUI update
                        SwingUtilities.invokeLater(() -> {
                            gameHistoryPanel.redo(chessBoard, moveLog);
                            if(gameSetup.isAIPlayer(chessBoard.currentPlayer())){
                                Table.get().moveMadeUpdate(PlayerType.HUMAN);
                            }
                            boardPanel.drawBoard(chessBoard);
                        });
                    }

                }

                //the do nothing but still needed to be stated for the mouselistener
                @Override
                public void mousePressed(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                }
            });
            validate();
        }

        //assigns the Piece at the tile to show up for the gui
        private void assignTilePieceIcon(final Board board){
            this.removeAll();
            if(board.getTile(this.tileID).isTileOccupied()){
                try {
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagePath +
                            board.getTile(this.tileID).getPiece().getColor().toString().charAt(0) +
                            board.getTile(this.tileID).getPiece().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //method that assigns the tile if it is to be this color or the other
        private void assignTileColor() {
            boolean isLight = ((tileID + (tileID / 8)) % 2 == 0);
            setBackground(isLight ? Color.lightGray: Color.darkGray);
        }

        //draws the tile on the board
        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegals(board);
            validate();
            repaint();
        }

        //highlights the legal moves that can be done by the player
        private void highlightLegals(final Board board){
            for(final Move move : pieceLegalMoves(board)){
                   if(move.getMovePosition() == this.tileID){
                       try {
                           add(new JLabel(new ImageIcon(ImageIO.read(new File("Chess Icons/green_dot.png")))));
                       }catch(Exception e){
                           e.printStackTrace();
                       }
                   }
            }
        }

        //finds the legal mvoes of each piece
        private Collection<Move> pieceLegalMoves(Board board) {
            if(playerMovedPiece != null && playerMovedPiece.getColor() == board.currentPlayer().getColor()){
                return playerMovedPiece.calculateLegalMove(board);
            }
            return Collections.emptyList();
        }
    }

    //Chess engine makes move here
    private static class TableGameWatcher implements Observer{

        @Override
        public void update(Observable o, final Object arg) {
            if(Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer()) &&
                    !Table.get().getGameBoard().currentPlayer().isInCheckMate() &&
                    !Table.get().getGameBoard().currentPlayer().isInStaleMate()){
                final AIThinkTank thinkTank = new AIThinkTank();
                thinkTank.execute();
            }
            if(Table.get().getGameBoard().currentPlayer().isInCheckMate()){
                System.out.println("game over, " + Table.get().getGameBoard().currentPlayer() + " is in checkmate");
            }
            if(Table.get().getGameBoard().currentPlayer().isInStaleMate()){
                System.out.println("game over, " + Table.get().getGameBoard().currentPlayer() + " is in stalemate");
            }
        }
    }

    public void updateGameBoard(final Board board){
        this.chessBoard = board;
    }

    private MoveLog getMoveLog(){
        return this.moveLog;
    }

    private GameHistoryPanel getGameHistoryPanel(){
        return this.gameHistoryPanel;
    }

    private BoardPanel getBoardPanel(){
        return this.boardPanel;
    }

    private void moveMadeUpdate(final PlayerType playerType){
        setChanged();
        notifyObservers(playerType);
    }

    private static class AIThinkTank extends SwingWorker<Move, String>{
        private AIThinkTank(){

        }

        @Override
        protected Move doInBackground(){
            //staying with search depth 4 since depth 5 and higher takes too long
            final MoveStrategy miniMax = new MiniMax(4);
            return miniMax.execute(Table.get().getGameBoard());
        }

        @Override
        public void done(){
            try {
                final Move bestMove = get();
                Table.get().updateGameBoard(Table.get().getGameBoard().currentPlayer().makeMove(bestMove).getBoard());
                Table.get().getMoveLog().addMove(bestMove);
                Table.get().getGameHistoryPanel().redo(Table.get().getGameBoard(), Table.get().getMoveLog());
                Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
                Table.get().moveMadeUpdate(PlayerType.COMPUTER);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    //enum class for the BoardDirection which will be used for the preference of the player. If it should be from
    //white's perspective or black's perspective

    public enum BoardDirection{
        NORMAL{
            @Override
            public List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            public BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED{
            @Override
            public List<TilePanel> traverse(final List<TilePanel> boardTiles){
                for(int i = 0, j = boardTiles.size() - 1; i < j; i++) {
                    boardTiles.add(i, boardTiles.remove(j));
                }
                return boardTiles;
            }

            @Override
            public BoardDirection opposite() {
                return NORMAL;
            }
        };

        public abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        public abstract BoardDirection opposite();
    }

    //enum for player types
    enum PlayerType{
        HUMAN,
        COMPUTER
    }
}
