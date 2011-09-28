package de.ubercode.gwtetris.client.gamelogic;

/**
 * A renderer that pretends to display the grid to the user in order to help
 * test the game logic.
 */
class MockRenderer implements Renderer {
    private Grid gameGrid;
    int timesGridUpdated;
    private Piece currentPiece;
    private Piece nextPiece;
    int timesPieceUpdated;
    boolean gameOver;

    /**
     * @return The last grid that was displayed.
     */
    Grid getGameGrid() {
        return gameGrid;
    }

    /**
     * @return The number of times the grid has been updated.
     */
    int getTimesGridUpdated() {
        return timesGridUpdated;
    }

    /**
     * @return The last piece that was displayed.
     */
    Piece getCurrentPiece() {
        return currentPiece;
    }

    /**
     * @return The next piece that is going to be displayed.
     */
    public Piece getNextPiece() {
        return nextPiece;
    }    

    /**
     * @return The number of times the current piece has been updated.
     */
    int getTimesPieceUpdated() {
        return timesPieceUpdated;
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
        timesGridUpdated = 0;
    }

    @Override
    public void updateGrid() {
        timesGridUpdated++;
    }

    @Override
    public void displayPiece(Piece piece) {
        currentPiece = piece;
        timesPieceUpdated = 0;
    }

    @Override
    public void displayNextPiece(Piece piece) {
        nextPiece = piece;
    }
    
    @Override
    public void updatePiece() {
        timesPieceUpdated++;
    }

    @Override
    public void displayGameOver() {
        gameOver = true;
    }
}
