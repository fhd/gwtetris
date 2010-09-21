package com.github.fhd.gwtetris.client.game;

/**
 * A piece (i.e. a tetromino) - the central element in this game. 
 */
public class Piece implements Cloneable {
    /**
     * The type of piece. Pieces come in several forms, looking like and hence
     * named after upper-case letters.
     */
    public enum Type {
        I(1, new int[][] {
                { 1, 1, 1, 1 }
        }),
        J(2, new int[][] {
                { 1, 0, 0 },
                { 1, 1, 1 }
        }),
        L(3, new int[][] {
                { 0, 0, 1 },
                { 1, 1, 1 }
        }),
        O(4, new int[][] {
                { 1, 1 },
                { 1, 1 }
        }),
        S(5, new int[][] {
                { 0, 1, 1 },
                { 1, 1, 0 }
        }),
        T(6, new int[][] {
                { 0, 1, 0 },
                { 1, 1, 1 }
        }),
        Z(7, new int[][] {
                { 1, 1, 0 },
                { 0, 1, 1 }
        });
        
        private int typeNo;
        private int[][] matrix;
        
        private Type(int typeNo, int[][] matrix) {
            this.typeNo = typeNo;
            this.matrix = matrix;
        }
        
        int typeNo() {
            return this.typeNo;
        }
        
        int[][] matrix() {
            return this.matrix;
        }
    }
    
    private int x;
    private int y;
    private Type type;
    private int[][] rotatedTypeMatrix;
    private int rotations;
    
    /**
     * @return A random piece.
     */
    public static Piece createRandomPiece() {
        return new Piece(Type.values()[(int) Math.floor(Math.random()
                                                        * Type.values()
                                                        .length)]);
    }
    
    /**
     * Creates a piece of the specified type.
     * @param type The type of piece.
     */
    public Piece(Type type) {
        x = 0;
        y = 0;
        this.type = type;
        rotatedTypeMatrix = type.matrix();
    }
    
    protected Object clone() {
        Piece p = new Piece(type);
        p.x = x;
        p.y = y;
        for (int i = 0; i < rotations; i++)
            p.rotate();
        return p;
    }
    
    /**
     * @return A matrix representation of the piece.
     */
    public int[][] getMatrix() {
        int[][] matrix = new int[y + rotatedTypeMatrix.length]
            [x + rotatedTypeMatrix[0].length];
        for (int y = 0; y < matrix.length; y++)
            for (int x = 0; x < matrix[y].length; x++) {
                int relX = x - this.x;
                int relY = y - this.y;
                matrix[y][x] = relX >= 0 && relY >= 0
                    && relY < rotatedTypeMatrix.length
                    && relX < rotatedTypeMatrix[relY].length
                    && rotatedTypeMatrix[relY][relX] != 0 ? type.typeNo : 0;
            }
        return matrix;
    }
    
    /**
     * @return The position of the piece on the x-axis.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The position of the piece on the y-axis.
     */
    public int getY() {
        return y;
    }
    
    /**
     * @return The width of the piece, depending on the current rotation.
     */
    public int getWidth() {
        return rotatedTypeMatrix[0].length;
    }
    
    /**
     * @return The height of the piece, depending on the current rotation.
     */
    public int getHeight() {
        return rotatedTypeMatrix.length;
    }
    
    /**
     * Moves the piece.
     * @param x The number of cells to move on the x-axis. Positive values move
     *          down, negative up.
     * @param y The number of cells to move on the y-axis. Positive values move
     *          right, negative left.
     */
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
    
    /**
     * Rotates the piece clockwise.
     */
    public void rotate() {
        if (rotations >= 3)
            rotations = 0;
        else
            rotations++;

        int[][] typeMatrix = rotatedTypeMatrix;
        rotatedTypeMatrix = new int[typeMatrix[0].length][typeMatrix.length];
        for (int y = 0; y < typeMatrix.length; y++)
            for (int x = 0; x < typeMatrix[y].length; x++)
                rotatedTypeMatrix[x][typeMatrix.length - 1 - y] = typeMatrix[y][x];
    }
}
