package com.github.fhd.gwtetris.client.gamelogic;

import static org.junit.Assert.*;

import org.junit.*;

public class GameTest {
    MockRenderer mockRenderer;
    MockRNG mockRNG;
    Game game;
    
    @Before
    public void setUp() {
        mockRenderer = new MockRenderer();
        mockRNG = new MockRNG();
        game = new Game(mockRenderer, mockRNG, 20, 30);
    }
    
    @Test
    public void start() {
        assertNull(mockRenderer.gameGrid);
        game.start();
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
        int previousPosition = mockRenderer.currentPiece.getY();
        game.step();
        assertEquals(1, mockRenderer.currentPiece.getY() - previousPosition);
    }
    
    @Test
    public void pieceLands() {
        game.start();
        game.step();
        Piece firstPiece = mockRenderer.currentPiece;
        int numSteps = mockRenderer.gameGrid.height
                       - mockRenderer.currentPiece.getHeight();
        for (int i = 0; i < numSteps; i++)
            game.step();
        assertSame(firstPiece, mockRenderer.currentPiece);
        game.step();
        assertNotSame(firstPiece, mockRenderer.currentPiece);
        assertEquals(firstPiece.getY() + firstPiece.getHeight(),
                     mockRenderer.gameGrid.height);
    }

    @Test
    public void pieceMovesLeft() {
        game.start();
        mockRNG.piecePosition = 1;
        game.step();
        Piece piece = mockRenderer.currentPiece;
        assertTrue(piece.move(-1, 0));
        assertEquals(0, piece.getX());
        assertFalse(piece.move(-1, 0));
        assertEquals(0, piece.getX());
    }

    @Test
    public void pieceMovesRight() {
        game.start();
        mockRNG.piecePosition = 0;
        game.step();
        Piece piece = mockRenderer.currentPiece;
        int expectedPosition = mockRenderer.gameGrid.width - piece.getWidth();
        assertTrue(piece.move(expectedPosition, 0));
        assertEquals(expectedPosition, piece.getX());
        assertFalse(piece.move(1, 0));
        assertEquals(expectedPosition, piece.getX());
    }

    /*
     * TODO: Write test cases to test the following:
     * - The current piece lands and completes a line.
     * - The current piece lands and leads to game over.
     * - The current piece rotates.
     */
}
