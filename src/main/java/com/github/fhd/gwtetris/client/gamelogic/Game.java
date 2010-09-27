package com.github.fhd.gwtetris.client.gamelogic;


/**
 * The game logic.
 */
class Game {
    private Renderer renderer;
    private boolean running = false;
    private Grid gameGrid;
    private Piece currentPiece;
    
    /**
     * Creates a new game.
     * @param renderer The renderer that will be used to render all actions
     *                 within the game.
     * @param width The game grid's width in blocks.
     * @param height The game grid's height in blocks.
     */
    Game(Renderer renderer, int width, int height) {
        this.renderer = renderer;
        this.gameGrid = new Grid(width, height);
    }
    
    /**
     * Starts the game.
     */
    void start() {
        running = true;
        renderer.displayGrid(gameGrid);
    }

    /**
     * @return <code>true</code> if the game has been started.
     */
    boolean isRunning() {
        return running;
    }

    /**
     * Proceeds the game for one step.
     */
    void step() {
        if (currentPiece != null && currentPiece.move(0, 1))
            renderer.updatePiece(currentPiece);
        else
            renderer.displayPiece(
                    currentPiece = Piece.createRandomPiece(gameGrid));
    }
}
