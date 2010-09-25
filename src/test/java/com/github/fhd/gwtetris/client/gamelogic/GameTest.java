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
    public void testMove() {
        Game game = new Game();
        game.start();
        List<Action> actions = game.move();
        assertEquals(1, actions.size());
        
        /*
         * What can happen?
         * - A new piece can arrive.
         * - The current piece moves down.
         * - The current piece can move to the left or to the right
         * - The current piece can land. If it does, it can complete one or
         *   more lines.
         */
    }
}
