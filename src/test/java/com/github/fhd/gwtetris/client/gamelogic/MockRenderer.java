package com.github.fhd.gwtetris.client.gamelogic;

/**
 * A renderer that pretends to display the grid to the user in order to help
 * test the game logic.
 */
class MockRenderer implements Renderer {
    private Grid gameGrid;
    private Piece currentPiece;
    boolean gameOver;

    /**
     * @return The last grid that was displayed.
     */
    Grid getGameGrid() {
        return gameGrid;
    }

    /**
     * @return The last piece that was displayed.
     */
    Piece getCurrentPiece() {
        return currentPiece;
    }

    /**
     * @return <code>true</code> if the game has ended.
     */
    public boolean isGameOver() {
        return gameOver;
    }
    
    @Override
    public void displayGrid(Grid grid) {
        gameGrid = grid;
    }
    
    @Override
    public void displayPiece(Piece piece) {
        currentPiece = piece;
    }
    
    @Override
    public void updatePiece(Piece piece) {
        currentPiece = piece;
    }

    @Override
    public void displayGameOver() {
        gameOver = true;
    }
}
