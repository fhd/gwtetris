package com.github.fhd.gwtetris.client.gamelogic;

/**
 * A renderer that pretends to display the grid to the user in order to help
 * test the game logic.
 */
class MockRenderer implements Renderer {
    Grid gameGrid;
    Piece currentPiece;

    @Override
    public void displayGrid(Grid grid) {
        gameGrid = grid;
    }
    
    @Override
    public void displayPiece(Piece piece) {
        currentPiece = piece;
    }
}
