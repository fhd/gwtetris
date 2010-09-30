package com.github.fhd.gwtetris.client;

import static org.junit.Assert.*;

import org.junit.*;

import com.github.fhd.gwtetris.client.gamelogic.*;

public class JavaRNGTest {
    private RNG rng = new JavaRNG();
    
    @Test
    public void randomPiecePosition() {
        for (int i = 0; i < 20; i++) {
            int r = rng.randomPiecePosition(i);
            assertTrue("r <" + r + "> was not within the expected range",
                       r >= 0 && r <= i);
        }
    }
    
    @Test
    public void randomPieceTypeNo() {
        for (int i = 2; i < 20; i++) {
            int r = rng.randomPieceTypeNo(i);
            assertTrue("r <" + r + "> was not within the expected range",
                       r > 0 && r < i);
        }
    }
}
