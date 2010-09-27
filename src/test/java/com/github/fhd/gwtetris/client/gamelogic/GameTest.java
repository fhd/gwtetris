package com.github.fhd.gwtetris.client.gamelogic;

import static org.junit.Assert.*;

import org.junit.*;

public class GameTest {
    MockRenderer mockRenderer;
    Game game;
    
    @Before
    public void setUp() {
        mockRenderer = new MockRenderer();
        game = new Game(mockRenderer);
    }
    
    @Test
    public void start() {
        assertTrue(!game.isRunning());
        assertNull(mockRenderer.gameGrid);
        game.start();
        assertTrue(game.isRunning());
        assertNotNull(mockRenderer.gameGrid);
    }
    
    @Test
    public void newPiece() {
        game.start();
        assertNull(mockRenderer.currentPiece);
        game.step();
        assertNotNull(mockRenderer.currentPiece);
    }
    
    @Test
    public void pieceMovesDown() {
        game.start();
        game.step();
        int previousPosition = mockRenderer.currentPiece.y;
        game.step();
        assertEquals(1, mockRenderer.currentPiece.y - previousPosition);
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
