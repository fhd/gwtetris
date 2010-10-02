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
        for (int y = 0; y < pieceMatrix.length; y++) {
            int[] line = pieceMatrix[y];
            for (int x = 0; x < line.length; x++)
                matrix[piece.getY() + y][piece.getX() + x] = line[x];
        }
        removeCompletedLines();
    }

    private void removeCompletedLines() {
        for (int x = 0; x < matrix[0].length; x++)
            if (matrix[0][x] > 0)
                return;

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
            matrix[y] = new int[width];
    }
}
