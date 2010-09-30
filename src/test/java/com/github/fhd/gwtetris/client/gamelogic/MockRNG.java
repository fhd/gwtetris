package com.github.fhd.gwtetris.client.gamelogic;

/**
 * A mock implementation of a random number generator. The numbers created by
 * this class can be specified.
 */
class MockRNG implements RNG {
    int piecePosition = 0;
    Piece.Type pieceType = Piece.Type.I;
    
    @Override
    public int randomPiecePosition(int maxPos) {
        return piecePosition;
    }

    @Override
    public Piece.Type randomPieceType() {
        return pieceType;
    }
}
