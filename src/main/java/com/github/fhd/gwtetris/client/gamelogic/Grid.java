package com.github.fhd.gwtetris.client.gamelogic;

/**
 * The grid which contains the pieces and represents the board of the game.
 */
class Grid {
    private int width;
    private int height;
    private int[][] matrix;

    /**
     * Creates a new grid with the specified dimensions.
     * @param width The width of the grid in blocks.
     * @param height The height of the grid in blocks.
     */
    Grid(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new int[height][width];
    }

    /**
     * @return The width of the grid in blocks.
     */
    int getWidth() {
        return width;
    }

    /**
     * @return The height of the grid in blocks.
     */
    int getHeight() {
        return height;
    }

    /**
     * @return The matrix representation of the grid, where 0 is an empty cell
     *         and 1 a cell that contains a block.
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Inserts all blocks of the supplied piece into the grid.
     * @param piece The piece whose blocks are to be added.
     */
    public void insertPiece(Piece piece) {
        int[][] pieceMatrix = piece.getMatrix();
        for (int i = 0; i < pieceMatrix.length; i++) {
            int[] line = pieceMatrix[i];
            for (int j = 0; j < line.length; j++)
                matrix[piece.getY() + i][piece.getX() + j] = line[j];
        }
        removeCompletedLines();
    }

    private void removeCompletedLines() {
        for (int i = 0; i < matrix[0].length; i++)
            if (matrix[0][i] > 0)
                return;

        for (int i = 0; i < matrix.length; i++) {
            boolean rowCompleted = true;
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] == 0) {
                    rowCompleted = false;
                    break;
                }

            if (rowCompleted)
                matrix[i] = null;
        }

        boolean swapped;
        do {
            swapped = false;
            for (int i = matrix.length - 1; i > 0; i--)
                if (matrix[i] == null && matrix[i - 1] != null) {
                    matrix[i] = matrix[i - 1];
                    matrix[i - 1] = null;
                    swapped = true;
                }
        } while (swapped);

        for (int i = 0; matrix[i] == null; i++)
            matrix[i] = new int[width];
    }

    /**
     * @return <code>true</code> if one column of the grid is completely
     *         filled.
     */
    boolean isFilled() {
        for (int i = 0; i < width; i++) {
            boolean filled = true;
            for (int j = 0; j < height; j++)
                if (matrix[j][i] == 0)
                    filled = false;
            if (filled)
                return true;
        }
        return false;
    }
}
