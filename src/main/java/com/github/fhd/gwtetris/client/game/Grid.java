package com.github.fhd.gwtetris.client.game;

/**
 * The grid which contains the pieces.
 */
public class Grid {
    private int horizCells;
    private int vertCells;
    private int[][] matrix;
    private Piece curPiece;
    private Piece nextPiece;

    /**
     * Creates a new grid with the specified number of cells.
     * @param horizCells The number of horizontal cells in the grid.
     * @param vertCells The number of vertical cells in the grid.
     */
    public Grid(int horizCells, int vertCells) {
        this.horizCells = horizCells;
        this.vertCells = vertCells;
        matrix = new int[vertCells][horizCells];
        curPiece = null;
        nextPiece = Piece.createRandomPiece();
    }

    /**
     * @return A matrix representation of the grid.
     */
    public int[][] getMatrix() {
        int[][] combinedMatrix = new int[vertCells][horizCells];
        int[][] pieceMatrix = curPiece != null ? curPiece.getMatrix() : null;
        for (int y = 0; y < vertCells; y++)
            for (int x = 0; x < horizCells; x++)
                if (matrix[y][x] > 0)
                    combinedMatrix[y][x] = matrix[y][x];
                else if (pieceMatrix != null && y < pieceMatrix.length
                         && x < pieceMatrix[y].length)
                    combinedMatrix[y][x] = pieceMatrix[y][x];
                else
                    combinedMatrix[y][x] = 0;
        return combinedMatrix;
    }

    /**
     * @return The number of horizontal cells in the grid.
     */
    public int getHorizCells() {
        return horizCells;
    }

    /**
     * @return The number of vertical cells in the grid.
     */
    public int getVertCells() {
        return vertCells;
    }

    /**
     * Moves the pieces in the grid by one step.
     * @return <code>false</code> if the step ended the game.
     */
    public boolean nextStep() {
        if (curPiece == null) {
            curPiece = nextPiece;
            movePiece((int) Math.floor(Math.random() * Grid.this.horizCells),
                      0);
            nextPiece = Piece.createRandomPiece();
        } else if (!movePiece(0, 1)) {
            matrix = getMatrix();
            curPiece = null;

            for (int x = 0; x < matrix[0].length; x++)
                if (matrix[0][x] > 0)
                    return false;

            for (int y = 0; y < matrix.length; y++) {
                boolean rowCompleted = true;
                for (int x = 0; x < matrix[y].length; x++)
                    if (matrix[y][x] == 0) {
                        rowCompleted = false;
                        break;
                    }

                if (rowCompleted)
                    matrix[y] = null;
            }

            boolean swapped;
            do {
                swapped = false;
                for (int y = matrix.length - 1; y > 0; y--)
                    if (matrix[y] == null && matrix[y - 1] != null) {
                        matrix[y] = matrix[y - 1];
                        matrix[y - 1] = null;
                        swapped = true;
                    }
            } while (swapped);

            for (int y = 0; matrix[y] == null; y++)
                matrix[y] = new int[horizCells];
        }
        return true;
    }

    /**
     * Moves the piece controlled by the user.
     * @param x The number of cells to move to the right, negative values move
     *          left.
     * @param y The number of cells to move down, negative values move up.
     * @return <code>false</code> if the piece collided with anything.
     */
    public boolean movePiece(int x, int y) {
        if (curPiece == null)
            return false;

        Piece p = (Piece) curPiece.clone();
        p.move(x, y);
        if (!detectCollision(p)) {
            curPiece = p;
            return true;
        }
        return false;
    }

    /**
     * Rotates the piece controlled by the user.
     * @return <code>false</code> if the piece collided with anything.
     */
    public boolean rotatePiece() {
        if (curPiece == null)
            return false;

        Piece p = (Piece) curPiece.clone();
        p.rotate();
        if (!detectCollision(p)) {
            curPiece = p;
            return true;
        }
        return false;
    }

    /**
     * @return A matrix representation of the next piece.
     */
    public int[][] getNextPieceMatrix() {
        return nextPiece.getMatrix();
    }

    /**
     * Empties the grid, removing all pieces including the one controlled by
     * the user.
     */
    public void reset() {
        matrix = new int[vertCells][horizCells];
        curPiece = null;
    }
    
    private boolean detectCollision(Piece piece) {
        if (piece.getX() < 0 || piece.getX() + piece.getWidth() > horizCells
            || piece.getY() < 0 || piece.getY() + piece.getHeight() > vertCells)
            return true;

        int[][] pieceMatrix = piece.getMatrix();
        for (int y = 0; y < piece.getY() + piece.getHeight(); y++)
            for (int x = 0; x < piece.getX() + piece.getWidth(); x++)
                if (matrix[y][x] > 0)
                    return true;

        return false;
    }
}
