package de.ubercode.gwtetris.client.gamelogic;

import java.util.*;

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
    public int getX() {
        return x;
    }
    
    /**
     * The vertical position of the piece in blocks.
     */
    public int getY() {
        return y;
    }

    /**
     * The width of the piece in blocks.
     */
    int getWidth() {
        return matrix[0].length;
    }
    
    /**
     * The height of the piece in blocks.
     */
    int getHeight() {
        return matrix.length;
    }


    /**
     * @return The matrix representation of this piece.
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Moves the piece one block to the left.
     * @return <code>true</code> if it was possible to move.
     */
    public boolean moveLeft() {
        return move(-1, 0);
    }

    /**
     * Moves the piece one block to the right.
     * @return <code>true</code> if it was possible to move.
     */
    public boolean moveRight() {
        return move(1, 0);
    }
    
    /**
     * Moves the piece one block down.
     * @return <code>true</code> if it was possible to move.
     */
    public boolean moveDown() {
        return move(0, 1);
    }

    private boolean move(int x, int y) {
        int newX = this.x + x;
        int newY = this.y + y;
        if (detectCollision(newX, newY))
            return false;
        this.x = newX;
        this.y = newY;
        return true;
    }
    
    private boolean detectCollision(int x, int y) {
        int width = getWidth();
        int height = getHeight();

        if (x < 0 || x + width > grid.getWidth()
            || y < 0 || y + height > grid.getHeight())
            return true;

        int[][] gridMatrix = grid.getMatrix();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] > 0 && gridMatrix[i + y][j + x] > 0)
                    return true;

        return false;
    }

    /**
     * Rotates the piece clockwise.
     */
    public void rotate() {
        int[][] previousMatrix = matrix;
        matrix = new int[previousMatrix[0].length][previousMatrix.length];
        for (int y = 0; y < previousMatrix.length; y++)
            for (int x = 0; x < previousMatrix[y].length; x++)
                matrix[x][previousMatrix.length - 1 - y] = previousMatrix[y][x];
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(" + x + ", " + y + ")\n");
        for (int i = 0; i < matrix.length; i++)
            builder.append(Arrays.toString(matrix[i]) + "\n");
        return builder.toString();
    }
}
