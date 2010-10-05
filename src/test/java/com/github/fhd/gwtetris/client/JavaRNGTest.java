package com.github.fhd.gwtetris.client;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.OrderingComparisons.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.github.fhd.gwtetris.client.gamelogic.*;

public class JavaRNGTest {
    private RNG rng = new JavaRNG();
    
    @SuppressWarnings("unchecked")
    @Test
    public void randomPiecePosition() {
        for (int i = 0; i < 40; i++)
            assertThat(rng.randomPiecePosition(i),
                       allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(i)));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void randomPieceType() {
        for (int i = 0; i < 14; i++)
            assertThat(rng.randomPieceType().getTypeNo(),
                       allOf(greaterThanOrEqualTo(1),
                             lessThanOrEqualTo(Piece.Type.values().length)));
    }
}
