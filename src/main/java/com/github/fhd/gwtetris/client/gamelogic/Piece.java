package com.github.fhd.gwtetris.client.gamelogic;

/**
 * A piece (i.e. a tetromino) - the central element in this game. 
 */
class Piece {
    /**
     * The type of piece. Pieces come in several forms, looking like and hence
     * named after upper-case letters.
     */
    enum Type {
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

    private Type type;
    private Grid grid;
    private int x;
    private int y;

    /**
     * @param grid The grid in which the piece will reside.
     * @return A piece of random type and horizontal position.
     */
    static Piece createRandomPiece(Grid grid) {
        Piece p = new Piece(
                Type.values()[randomNum(0, Type.values().length - 1)],
                grid);
        p.x = randomNum(0, grid.width - p.getWidth());
        return p;
    }
    
    private static int randomNum(int start, int end) {
        return (int) Math.floor(Math.random() * end) + start;
    }

    /**
     * Creates a new piece of the specified type.
     * @param type The type of the piece.
     * @param grid The grid in which the piece will reside.
     */
    Piece(Type type, Grid grid) {
        this.type = type;
        this.grid = grid;
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
        return type.matrix()[0].length;
    }
    
    /**
     * The height of the piece in blocks.
     */
    int getHeight() {
        return type.matrix().length;
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
        if (this.x + x + getWidth() > grid.width
            || this.y + y + getHeight() > grid.height)
            return false;
        this.x += x;
        this.y += y;
        return true;
    }
}
