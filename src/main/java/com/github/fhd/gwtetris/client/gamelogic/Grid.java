package com.github.fhd.gwtetris.client.gamelogic;

/**
 * The grid which contains the pieces and represents the board of the game.
 */
class Grid {
    /**
     * The width of the grid in blocks.
     */
    public int width;

    /**
     * The height of the grid in blocks.
     */
    public int height;

    /**
     * Creates a new grid with the specified dimensions.
     * @param width The width of the grid in blocks.
     * @param height The height of the grid in blocks.
     */
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
