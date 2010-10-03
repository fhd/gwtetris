package com.github.fhd.gwtetris.client.gamelogic;


/**
 * The game logic.
 */
public class Game {
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
    public Game(Renderer renderer, RNG rng, int width, int height) {
        this.renderer = renderer;
        this.rng = rng;
        this.gameGrid = new Grid(width, height);
    }

    /**
     * Starts the game.
     */
    public void start() {
        renderer.displayGrid(gameGrid);
    }

    /**
     * Proceeds the game for one step.
     */
    public void step() {
        if (currentPiece != null && currentPiece.move(0, 1))
            renderer.updatePiece();
        else {
            if (currentPiece != null) {
                gameGrid.insertPiece(currentPiece);
                renderer.updateGrid();
            }
            currentPiece = Piece.createRandomPiece(rng, gameGrid);
            renderer.displayPiece(currentPiece);
            if (gameGrid.isFilled())
                renderer.displayGameOver();
        }
    }
}
