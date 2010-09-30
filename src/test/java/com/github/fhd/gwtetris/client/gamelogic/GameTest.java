package com.github.fhd.gwtetris.client.gamelogic;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

/*
 * TODO: Test the following:
 * - The current piece lands and completes a line.
 * - The current piece lands and leads to game over.
 */

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
        assertThat(mockRenderer.getGameGrid(), nullValue());
        game.start();
        assertThat(mockRenderer.getGameGrid(), notNullValue());
    }
    
    @Test
    public void newPiece() {
        game.start();
        assertThat(mockRenderer.getCurrentPiece(), nullValue());
        game.step();
        assertThat(mockRenderer.getCurrentPiece(), notNullValue());
    }
    
    @Test
    public void pieceMovesDown() {
        game.start();
        game.step();
        int previousPosition = mockRenderer.getCurrentPiece().getY();
        game.step();
        assertThat(mockRenderer.getCurrentPiece().getY(),
                   is(previousPosition + 1));
    }

    @Test
    public void pieceMovesLeft() {
        game.start();
        mockRNG.setPiecePosition(1);
        game.step();
        Piece piece = mockRenderer.getCurrentPiece();
        assertThat(piece.move(-1, 0), is(true));
        assertThat(piece.getX(), is(0));
        assertThat(piece.move(-1, 0), is(false));
        assertThat(piece.getX(), is(0));
    }

    @Test
    public void pieceMovesRight() {
        game.start();
        mockRNG.setPiecePosition(0);
        game.step();
        Piece piece = mockRenderer.getCurrentPiece();
        int expectedPosition = mockRenderer.getGameGrid().getWidth()
                               - piece.getWidth();
        assertThat(piece.move(expectedPosition, 0), is(true));
        assertThat(piece.getX(), is(expectedPosition));
        assertThat(piece.move(1, 0), is(false));
        assertThat(piece.getX(), is(expectedPosition));
    }

    @Test
    public void pieceRotates() {
        game.start();
        mockRNG.setPiecePosition(10);
        mockRNG.setPieceType(Piece.Type.I);
        game.step();
        Piece p = mockRenderer.getCurrentPiece();
        int[][] originalMatrix = p.getMatrix(); 
        p.rotate();
        int[][] rotatedMatrix = p.getMatrix();
        assertThat(rotatedMatrix, is(new int[][] {{1},
                                                  {1},
                                                  {1},
                                                  {1}}));
        p.rotate();
        assertThat(p.getMatrix(), is(originalMatrix));
        p.rotate();
        assertThat(p.getMatrix(), is(rotatedMatrix));
    }
    
    @Test
    public void pieceLands() {
        game.start();
        game.step();
        Piece firstPiece = mockRenderer.getCurrentPiece();
        int numSteps = mockRenderer.getGameGrid().getHeight()
                       - mockRenderer.getCurrentPiece().getHeight();
        for (int i = 0; i < numSteps; i++)
            game.step();
        assertThat(mockRenderer.getCurrentPiece(), sameInstance(firstPiece));
        game.step();
        assertThat(mockRenderer.getCurrentPiece(),
                   not(sameInstance(firstPiece)));
        assertThat(mockRenderer.getGameGrid().getHeight(),
                   is(firstPiece.getY() + firstPiece.getHeight()));
    }
}
