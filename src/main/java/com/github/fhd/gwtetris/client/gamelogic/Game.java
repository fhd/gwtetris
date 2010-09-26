package com.github.fhd.gwtetris.client.gamelogic;

import java.util.*;

/**
 * The game logic.
 */
class Game {
    private boolean running = false;
    private Piece currentPiece;
    
    /**
     * Starts the game.
     */
    void start() {
        running = true;
    }

    /**
     * @return <code>true</code> if the game has been started.
     */
    boolean isRunning() {
        return running;
    }

    /**
     * Proceeds the game for one step.
     * @return The actions that have occurred during this step.
     */
    List<Action> step() {
        List<Action> actions = new ArrayList<Action>();
        if (currentPiece == null) {
            currentPiece = new Piece();
            actions.add(Action.NEW_PIECE);
        } else 
            actions.add(Action.PIECE_MOVED_DOWN);
        return actions;
    }
}
