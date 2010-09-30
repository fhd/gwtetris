package com.github.fhd.gwtetris.client.gamelogic;

/**
 * A random number generator. Creates all random numbers used by the game.
 */
public interface RNG {
    /**
     * Returns a random typeNo identifying the type of a piece.
     * @param numTypes The number of available types.
     * @return A random number between 1 and the maximum possible typeNo.
     */
    int randomPieceTypeNo(int numTypes);

    /**
     * Returns a random position for a piece. Works for both x and y
     * coordinates.
     * @param maxPos The maximum possible position.
     * @return A random x or y coordinate for a piece.
     */
    int randomPiecePosition(int maxPos);
}
