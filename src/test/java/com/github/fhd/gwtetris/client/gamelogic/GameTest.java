package com.github.fhd.gwtetris.client.gamelogic;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class GameTest {
    @Test
    public void testStart() {
        Game game = new Game();
        assertTrue(!game.isRunning());
        game.start();
        assertTrue(game.isRunning());
    }
    
    @Test
    public void testNewPiece() {
        Game game = new Game();
        game.start();
        List<Action> actions = game.step();
        assertEquals(1, actions.size());
        assertEquals(Action.NEW_PIECE, actions.get(0));
    }
    
    @Test
    public void testPieceMovesDown() {
        Game game = new Game();
        game.start();
        game.step();
        List<Action> actions = game.step();
        assertEquals(1, actions.size());
        assertEquals(Action.PIECE_MOVED_DOWN, actions.get(0));
    }
    
    /*
     * TODO: Write test cases to test the following:
     * - The current piece lands and nothing else happens.
     * - The current piece lands and completes a line.
     * - The current piece lands and leads to game over.
     * - The current piece moves to the left or to the right.
     * - The current piece rotates.
     */
}
