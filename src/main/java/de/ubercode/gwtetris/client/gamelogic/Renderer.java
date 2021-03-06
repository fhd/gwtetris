package de.ubercode.gwtetris.client.gamelogic;

/**
 * The renderer that will display the game's actions to the user.
 */
public interface Renderer {
    /**
     * Displays the supplied grid.
     * @param grid The grid to display.
     */
    void displayGrid(Grid grid);

    /**
     * Updates the grid.
     */
    void updateGrid();

    /**
     * Displays the supplied piece.
     * @param piece The piece to display.
     */
    void displayPiece(Piece piece);

    /**
     * Displays the next piece.
     * @param piece The piece that will be displayed after the current piece.
     */
    void displayNextPiece(Piece piece);

    /**
     * Updates the current piece.
     */
    void updatePiece();

    /**
     * Indicates to the user that the game has ended.
     */
    void displayGameOver();
}
