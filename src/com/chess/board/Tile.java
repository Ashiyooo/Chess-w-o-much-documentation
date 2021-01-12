package com.chess.board;

import com.chess.piece.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    protected final int tileCoordinate;
    private static final Map<Integer, EmptyTile> EMPTY_TILE = createAllPossibleEmptyTiles(); //field that stores all the possible empty tiles that can occur

    //creates all the possible empty tiles
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for( int i= 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    //constructor
    protected Tile(final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    //creates a tile
    //if a tile to create has a piece it will make an occupied tile
    //if a tile to create does not have a piece it will take a tile from the EMPTY_TILE Map
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null? new OccupiedTile(tileCoordinate, piece): EMPTY_TILE.get(tileCoordinate);
    }

    public int getTileCoordinate(){
        return this.tileCoordinate;
    }

    //abstract methods to be specified in the child classes
    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();

}
