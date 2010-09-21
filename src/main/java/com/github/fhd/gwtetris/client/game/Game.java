package com.github.fhd.gwtetris.client.game;

/**
 * The game's state.
 */
public class Game {
    private boolean paused;
    private boolean started;
    private boolean over;

    /**
     * Creates a new game.
     */
    public Game() {
        paused = false;
        started = false;
        over = false;
    }

    /**
     * @return <code>true</code> if the game is currently paused.
     */
    public boolean isPaused() {
        return paused;
    }
    
    /**
     * Pauses the game.
     */
    public void pause() {
        paused = true;
    }
    
    /**
     * Resumes the game.
     */
    public void resume() {
        paused = false;   
    }
    
    /**
     * Resumes the game if it was paused and pauses it otherwise.
     */
    public void togglePaused() {
        paused = !paused;
    }

    /**
     * @return <code>true</code> if the game has been started.
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * @return <code>true</code> if the game is over.
     */
    public boolean isOver() {
        return over;
    }
    
    /**
     * Starts a new game.
     */
    public void start() {
        started = true;
        over = false;   
    }

    /**
     * Ends the current game.
     */
    public void end() {
        over = true;
    }
}
