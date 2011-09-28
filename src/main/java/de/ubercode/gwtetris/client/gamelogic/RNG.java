package de.ubercode.gwtetris.client.gamelogic;

/**
 * A random number generator. Creates all random numbers used by the game.
 */
public interface RNG {
    /**
     * @return A random piece type.
     */
    Piece.Type randomPieceType();

    /**
     * @param maxPos The maximum possible position.
     * @return A random x or y coordinate for a piece.
     */
    int randomPiecePosition(int maxPos);
}
