package com.github.fhd.gwtetris.client.gamelogic;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;
import org.junit.*;

public class GameTest {
    MockRenderer mockRenderer;
    MockRNG mockRNG;
    Game game;

    @Before
    public void setUp() {
        mockRenderer = new MockRenderer();
        mockRNG = new MockRNG();
        game = new Game(mockRenderer, mockRNG, 20, 32);
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
        assertThat(mockRenderer.getTimesPieceUpdated(), is(1));
    }

    @Test
    public void pieceMovesLeft() {
        mockRNG.setPiecePosition(1);
        game.start();
        game.step();
        Piece piece = mockRenderer.getCurrentPiece();
        assertThat(piece.move(-1, 0), is(true));
        assertThat(piece.getX(), is(0));
        assertThat(piece.move(-1, 0), is(false));
        assertThat(piece.getX(), is(0));
    }

    @Test
    public void pieceMovesRight() {
        mockRNG.setPiecePosition(0);
        game.start();
        game.step();
        Piece piece = mockRenderer.getCurrentPiece();
        int expectedPosition =
            mockRenderer.getGameGrid().getWidth() - piece.getWidth();
        assertThat(piece.move(expectedPosition, 0), is(true));
        assertThat(piece.getX(), is(expectedPosition));
        assertThat(piece.move(1, 0), is(false));
        assertThat(piece.getX(), is(expectedPosition));
    }

    @Test
    public void pieceRotates() {
        mockRNG.setPiecePosition(10);
        mockRNG.setPieceType(Piece.Type.I);
        game.start();
        game.step();
        Piece p = mockRenderer.getCurrentPiece();
        int[][] originalMatrix = p.getMatrix();
        p.rotate();
        int[][] rotatedMatrix = p.getMatrix();
        assertThat(rotatedMatrix, is(new int[][] {{1}, {1}, {1}, {1}}));
        p.rotate();
        assertThat(p.getMatrix(), is(originalMatrix));
        p.rotate();
        assertThat(p.getMatrix(), is(rotatedMatrix));
        /*
         * TODO: Test that the piece does not rotate if it would hit another
         *       piece or the grid boundary.
         */
    }

    @Test
    public void pieceLands() {
        mockRNG.setPieceType(Piece.Type.I);
        game.start();
        game.step();
        Piece firstPiece = mockRenderer.getCurrentPiece();
        landCurrentPiece();
        assertThat(mockRenderer.getCurrentPiece(), sameInstance(firstPiece));
        game.step();
        assertThat(mockRenderer.getCurrentPiece(),
                   not(sameInstance(firstPiece)));

        Grid gameGrid = mockRenderer.getGameGrid();
        int[] expectedLastLine = new int[gameGrid.getWidth()];
        Arrays.fill(expectedLastLine, 0, 4, 1);
        int[][] gridMatrix = gameGrid.getMatrix();
        assertThat(gridMatrix[gridMatrix.length - 1], is(expectedLastLine));
        assertThat(mockRenderer.getTimesGridUpdated(), is(1));
    }

    private void landCurrentPiece() {
        Piece currentPiece = mockRenderer.getCurrentPiece();
        assertThat(currentPiece.getY(), is(0));
        int gridHeight = mockRenderer.getGameGrid().getHeight();
        for (int i = 0; i < gridHeight - currentPiece.getHeight(); i++)
            game.step();
        assertThat(mockRenderer.getCurrentPiece(), is(currentPiece));
        assertThat(currentPiece.getY() + currentPiece.getHeight(),
                   is(gridHeight));
    }

    @Test
    public void pieceLandsAndCompletesLine() {
        mockRNG.setPiecePosition(0);
        mockRNG.setPieceType(Piece.Type.I);
        game.start();
        Grid gameGrid = mockRenderer.getGameGrid();
        int gridWidth = gameGrid.getWidth();
        assertThat(gridWidth % 4, is(0));

        for (int i = 0; i < gridWidth / 4; i++) {
            mockRNG.setPiecePosition((i + 1) * 4);
            game.step();
            landCurrentPiece();
        }

        int[] expectedLastLine = new int[gameGrid.getWidth()];
        Arrays.fill(expectedLastLine, 0, gameGrid.getWidth()
                    - mockRenderer.getCurrentPiece().getWidth(), 1);
        int[][] gridMatrix = gameGrid.getMatrix();
        assertThat(gridMatrix[gridMatrix.length - 1], is(expectedLastLine));
        game.step();
        Arrays.fill(expectedLastLine, 0);
        assertThat(gridMatrix[gridMatrix.length - 1], is(expectedLastLine));
        assertThat(mockRenderer.getTimesGridUpdated(), is(gridWidth / 4));
    }

    @Test
    public void gameOver() {
        mockRNG.setPiecePosition(0);
        mockRNG.setPieceType(Piece.Type.I);
        game.start();
        assertThat(mockRenderer.isGameOver(), is(false));
        Grid gameGrid = mockRenderer.getGameGrid();
        int gridHeight = gameGrid.getHeight();
        assertThat(gridHeight % 4, is(0));

        game.step();
        for (int i = 0; i < gridHeight / 4; i++) {
            Piece currentPiece = mockRenderer.getCurrentPiece();
            currentPiece.rotate();
            while (currentPiece == mockRenderer.getCurrentPiece())
                game.step();
        }

        int[][] matrix = gameGrid.getMatrix();
        int[] leftmostColumn = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++)
            leftmostColumn[i] = matrix[i][0];
        int[] expectedLeftmostColumn = new int[matrix.length];
        Arrays.fill(expectedLeftmostColumn, 1);
        assertThat(leftmostColumn, is(expectedLeftmostColumn));
        assertThat(mockRenderer.isGameOver(), is(true));
        assertThat(mockRenderer.getTimesGridUpdated(), is(gridHeight / 4));
    }

    @Test
    public void piecePreview() {
        game.start();
        assertThat(mockRenderer.getNextPiece(), nullValue());

        game.step();
        Piece nextPiece = mockRenderer.getNextPiece();
        for (int i = 0; i < mockRenderer.getGameGrid().getHeight(); i++)
            game.step();

        assertThat(mockRenderer.getCurrentPiece(), sameInstance(nextPiece));
        assertThat(mockRenderer.getNextPiece(), not(sameInstance(nextPiece)));
    }
}
