package com.github.fhd.gwtetris.client.gamelogic;

/**
 * The grid which contains the pieces and represents the board of the game.
 */
class Grid {
    private int width;
    private int height;

    /**
     * Creates a new grid with the specified dimensions.
     * @param width The width of the grid in blocks.
     * @param height The height of the grid in blocks.
     */
    Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return The width of the grid in blocks.
     */
    int getWidth() {
        return width;
    }

    /**
     * @return The height of the grid in blocks.
     */
    int getHeight() {
        return height;
    }
}
