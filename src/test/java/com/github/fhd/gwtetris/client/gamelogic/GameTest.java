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
        int expectedPosition =
            mockRenderer.getGameGrid().getWidth() - piece.getWidth();
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
        assertThat(rotatedMatrix, is(new int[][] {{1}, {1}, {1}, {1}}));
        p.rotate();
        assertThat(p.getMatrix(), is(originalMatrix));
        p.rotate();
        assertThat(p.getMatrix(), is(rotatedMatrix));
    }

    @Test
    public void pieceLands() {
        game.start();
        mockRNG.pieceType = Piece.Type.I;
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
    }

    private void landCurrentPiece() {
        Piece currentPiece = mockRenderer.getCurrentPiece();
        assertThat(currentPiece.getY(), is(0));
        int gridHeight = mockRenderer.getGameGrid().getHeight();
        for (int i = 0; i < gridHeight - currentPiece.getHeight(); i++)
            game.step();
        assertThat(currentPiece.getY() + currentPiece.getHeight(),
                   is(gridHeight));
    }

    @Test
    public void pieceLandsAndCompletesLine() {
        game.start();
        Grid gameGrid = mockRenderer.getGameGrid();
        int gridWidth = gameGrid.getWidth();
        assertThat(gridWidth % 2, is(0));
        mockRNG.setPieceType(Piece.Type.I);

        for (int i = 0; i < gridWidth / 4; i++) {
            mockRNG.setPiecePosition(i * 4);
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
    }

    @Test
    public void gameOver() {
        game.start();
        assertThat(mockRenderer.isGameOver(), is(false));
        Grid gameGrid = mockRenderer.getGameGrid();
        int gridHeight = gameGrid.getHeight();
        assertThat(gridHeight % 2, is(0));

        mockRNG.setPiecePosition(0);
        mockRNG.setPieceType(Piece.Type.I);
        for (int i = 0; i < gridHeight / 4; i++) {
            game.step();
            mockRenderer.getCurrentPiece().rotate();
            for (int j = 0; j < gridHeight; j++)
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
    }
}
