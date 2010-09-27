package com.github.fhd.gwtetris.client.gamelogic;

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
     * Displays the supplied piece.
     * @param piece The piece to display.
     */
    void displayPiece(Piece piece);
    
    /**
     * Updates the supplied piece.
     * @param piece The piece to update.
     */
    void updatePiece(Piece piece);
}
