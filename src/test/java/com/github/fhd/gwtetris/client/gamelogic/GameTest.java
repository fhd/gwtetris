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
        assertThat(mockRenderer.gameGrid, nullValue());
        game.start();
        assertThat(mockRenderer.gameGrid, notNullValue());
    }
    
    @Test
    public void newPiece() {
        game.start();
        assertThat(mockRenderer.currentPiece, nullValue());
        game.step();
        assertThat(mockRenderer.currentPiece, notNullValue());
    }
    
    @Test
    public void pieceMovesDown() {
        game.start();
        game.step();
        int previousPosition = mockRenderer.currentPiece.getY();
        game.step();
        assertThat(mockRenderer.currentPiece.getY(), is(previousPosition + 1));
    }

    @Test
    public void pieceMovesLeft() {
        game.start();
        mockRNG.piecePosition = 1;
        game.step();
        Piece piece = mockRenderer.currentPiece;
        assertThat(piece.move(-1, 0), is(true));
        assertThat(piece.getX(), is(0));
        assertThat(piece.move(-1, 0), is(false));
        assertThat(piece.getX(), is(0));
    }

    @Test
    public void pieceMovesRight() {
        game.start();
        mockRNG.piecePosition = 0;
        game.step();
        Piece piece = mockRenderer.currentPiece;
        int expectedPosition = mockRenderer.gameGrid.width - piece.getWidth();
        assertThat(piece.move(expectedPosition, 0), is(true));
        assertThat(piece.getX(), is(expectedPosition));
        assertThat(piece.move(1, 0), is(false));
        assertThat(piece.getX(), is(expectedPosition));
    }

    @Test
    public void pieceRotates() {
        game.start();
        mockRNG.piecePosition = 10;
        mockRNG.pieceType = Piece.Type.I;
        game.step();
        Piece p = mockRenderer.currentPiece;
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
        Piece firstPiece = mockRenderer.currentPiece;
        int numSteps = mockRenderer.gameGrid.height
                       - mockRenderer.currentPiece.getHeight();
        for (int i = 0; i < numSteps; i++)
            game.step();
        assertThat(mockRenderer.currentPiece, sameInstance(firstPiece));
        game.step();
        assertThat(mockRenderer.currentPiece, not(sameInstance(firstPiece)));
        assertThat(mockRenderer.gameGrid.height,
                   is(firstPiece.getY() + firstPiece.getHeight()));
    }
}
