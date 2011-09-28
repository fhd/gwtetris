package de.ubercode.gwtetris.client.gamelogic;


/**
 * The game logic.
 */
public class Game {
    private Renderer renderer;
    private RNG rng;
    private Grid gameGrid;
    private Piece currentPiece;
    private Piece nextPiece;

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
        nextPiece = Piece.createRandomPiece(rng, gameGrid);
    }

    /**
     * Proceeds the game for one step.
     */
    public void step() {
        if (currentPiece != null && currentPiece.moveDown())
            renderer.updatePiece();
        else {
            if (currentPiece != null) {
                gameGrid.insertPiece(currentPiece);
                renderer.updateGrid();
            }
            currentPiece = nextPiece;
            nextPiece = Piece.createRandomPiece(rng, gameGrid);
            renderer.displayPiece(currentPiece);
            renderer.displayNextPiece(nextPiece);
            if (gameGrid.isFilled())
                renderer.displayGameOver();
        }
    }

    /**
     * Proceed the game until the current piece has landed. 
     */
    public void fastStep() {
        Piece previousPiece = currentPiece;
        while (currentPiece == previousPiece)
            step();
    }
}
