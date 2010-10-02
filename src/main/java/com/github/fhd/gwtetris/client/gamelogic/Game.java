package com.github.fhd.gwtetris.client.gamelogic;


/**
 * The game logic.
 */
class Game {
    private Renderer renderer;
    private RNG rng;
    private Grid gameGrid;
    private Piece currentPiece;
    
    /**
     * Creates a new game.
     * @param renderer The renderer that will be used to render all actions
     *                 within the game.
     * @param rng The random number generator that will be used to create all
     *            required random numbers.
     * @param width The game grid's width in blocks.
     * @param height The game grid's height in blocks.
     */
    Game(Renderer renderer, RNG rng, int width, int height) {
        this.renderer = renderer;
        this.rng = rng;
        this.gameGrid = new Grid(width, height);
    }
    
    /**
     * Starts the game.
     */
    void start() {
        renderer.displayGrid(gameGrid);
    }

    /**
     * Proceeds the game for one step.
     */
    void step() {
        if (currentPiece != null && currentPiece.move(0, 1))
            renderer.updatePiece(currentPiece);
        else {
            if (currentPiece != null)
                gameGrid.insertPiece(currentPiece);
            renderer.displayPiece(
                    currentPiece = Piece.createRandomPiece(rng, gameGrid));
        }
    }
}
