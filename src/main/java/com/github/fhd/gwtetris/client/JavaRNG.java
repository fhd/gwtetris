package com.github.fhd.gwtetris.client;

import com.github.fhd.gwtetris.client.gamelogic.*;

/**
 * Random number generator that uses Java's random mechanism.
 */
class JavaRNG implements RNG {
    @Override
    public int randomPiecePosition(int maxPos) {
        return randomNum(0, maxPos);
    }

    @Override
    public int randomPieceTypeNo(int numTypes) {
        return randomNum(0, numTypes - 1);
    }

    private static int randomNum(int start, int end) {
        return (int) Math.floor(Math.random() * end) + start;
    }
}
