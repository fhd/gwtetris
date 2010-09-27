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
     */
    Game(Renderer renderer) {
        this.renderer = renderer;
    }
    
    /**
     * Starts the game.
     */
    void start() {
        running = true;
        gameGrid = new Grid();
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
        if (currentPiece == null)
            currentPiece = new Piece();
        currentPiece.y++;
        renderer.displayPiece(currentPiece);
    }
}
