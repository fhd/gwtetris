package com.github.fhd.gwtetris.client.gamelogic;

/**
 * A mock implementation of a random number generator. The numbers created by
 * this class can be specified.
 */
class MockRNG implements RNG {
    int piecePosition = 0;
    Piece.Type pieceType = Piece.Type.I;

    /**
     * @param piecePosition The fixed piece position that will be returned
     *        whenever {@link #randomPiecePosition(int)} is called.
     */
    void setPiecePosition(int piecePosition) {
        this.piecePosition = piecePosition;
    }

    /**
     * @param pieceType The fixed piece type that will be returned whenever
     *                  {@link #randomPieceType()} is called.
     */
    void setPieceType(Piece.Type pieceType) {
        this.pieceType = pieceType;
    }
    
    @Override
    public int randomPiecePosition(int maxPos) {
        return piecePosition;
    }

    @Override
    public Piece.Type randomPieceType() {
        return pieceType;
    }
}
