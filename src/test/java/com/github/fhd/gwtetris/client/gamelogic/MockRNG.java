package com.github.fhd.gwtetris.client.gamelogic;

/**
 * A mock implementation of a random number generator. The numbers created by
 * this class can be specified.
 */
class MockRNG implements RNG {
    int piecePosition = 0;
    int pieceTypeNo = 0;
    
    @Override
    public int randomPiecePosition(int maxPos) {
        return piecePosition;
    }

    @Override
    public int randomPieceTypeNo(int numTypes) {
        return pieceTypeNo;
    }
}
