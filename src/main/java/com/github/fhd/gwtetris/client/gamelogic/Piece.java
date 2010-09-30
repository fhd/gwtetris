package com.github.fhd.gwtetris.client.gamelogic;

/**
 * A piece (i.e. a tetromino) - the central element in this game. 
 */
public class Piece {
    /**
     * The type of piece. Pieces come in several forms, looking like and hence
     * named after upper-case letters.
     */
    public enum Type {
        I(1, new int[][] {{1, 1, 1, 1}}),
        J(2, new int[][] {{1, 0, 0},
                          {1, 1, 1}}),
        L(3, new int[][] {{0, 0, 1},
                          {1, 1, 1}}),
        O(4, new int[][] {{1, 1},
                          {1, 1}}),
        S(5, new int[][] {{0, 1, 1},
                          {1, 1, 0}}),
        T(6, new int[][] {{0, 1, 0},
                          {1, 1, 1}}),
        Z(7, new int[][] {{1, 1, 0},
                          {0, 1, 1}});

        private int typeNo;
        private int[][] matrix;
        
        private Type(int typeNo, int[][] matrix) {
            this.typeNo = typeNo;
            this.matrix = matrix;
        }
        
        public int getTypeNo() {
            return this.typeNo;
        }
        
        int[][] getMatrix() {
            return this.matrix;
        }
    }

    private Type type;
    private Grid grid;
    private int x;
    private int y;
    private int rotation;
    private int[][] matrix;

    /**
     * @param rng The random number generator used to create a random type and
     *            position.
     * @param grid The grid in which the piece will reside.
     * @return A piece of random type and horizontal position.
     */
    static Piece createRandomPiece(RNG rng, Grid grid) {
        Piece p = new Piece(rng.randomPieceType(), grid);
        p.x = rng.randomPiecePosition(grid.getWidth() - p.getWidth());
        return p;
    }

    /**
     * Creates a new piece of the specified type.
     * @param type The type of the piece.
     * @param grid The grid in which the piece will reside.
     */
    Piece(Type type, Grid grid) {
        this.type = type;
        this.grid = grid;
        matrix = type.getMatrix();
    }

    /**
     * @return The type of the piece.
     */
    Type getType() {
        return type;
    }

    /**
     * The horizontal position of the piece in blocks.
     */
    int getX() {
        return x;
    }
    
    /**
     * The vertical position of the piece in blocks.
     */
    int getY() {
        return y;
    }

    /**
     * The width of the piece in blocks.
     */
    int getWidth() {
        return type.getMatrix()[0].length;
    }
    
    /**
     * The height of the piece in blocks.
     */
    int getHeight() {
        return type.getMatrix().length;
    }


    /**
     * @return The matrix representation of this piece.
     */
    int[][] getMatrix() {
        return matrix;
    }

    /**
     * Moves the piece by the specified number of blocks.
     * @param x The number of blocks to move on the x-axis. Positive values
     *          move to the right.
     * @param y The number of blocks to move on the y-axis. Positive values
     *          move down.
     * @return <code>true</code> if it was possible to move.
     */
    boolean move(int x, int y) {
        if (this.x + x < 0
            || this.x + x + getWidth() > grid.getWidth()
            || this.y + y < 0
            || this.y + y + getHeight() > grid.getHeight())
            return false;
        this.x += x;
        this.y += y;
        return true;
    }

    /**
     * Rotates the piece clockwise.
     */
    void rotate() {
        if (rotation >= 3)
            rotation = 0;
        else
            rotation++;

        int[][] previousMatrix = matrix;
        matrix = new int[previousMatrix[0].length][previousMatrix.length];
        for (int y = 0; y < previousMatrix.length; y++)
            for (int x = 0; x < previousMatrix[y].length; x++)
                matrix[x][previousMatrix.length - 1 - y] = previousMatrix[y][x];
    }
}
