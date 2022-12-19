package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import model.CrapsModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrapsModelTest {
    /** Rolling the dice this many times to see if all faces are rolled.*/
    private static final int NUM_ROLLS_FOR_RANDOMNESS = 120;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testCrapsModel() {
        fail("Not yet implemented");
    }

    
    @Test
    void testFirstRoll() {
        final CrapsModel model = new CrapsModel();
        
        for (int i = 0; i < NUM_ROLLS_FOR_RANDOMNESS; i++) {
            model.firstRoll();
        }
        assertEquals(model.getSum(), model.getDice1() + model.getDice2(), 
                     "Sum is not the sum of 2 dice.");
    }

    @Test
    void testContinueRoll() {
        fail("Not yet implemented");
    }

    @Test
    void testAdjustPlayerBet() {
        fail("Not yet implemented");
    }

    @Test
    void testSetPlayerWallet() {
        fail("Not yet implemented");
    }

    @Test
    void testReset() {
        fail("Not yet implemented");
    }

    @Test
    void testGetPlayerWallet() {
        fail("Not yet implemented");
    }

    @Test
    void testGetPlayerBet() {
        fail("Not yet implemented");
    }

    @Test
    void testGetSum() {
        fail("Not yet implemented");
    }

    @Test
    void testGetPoint() {
        fail("Not yet implemented");
    }

    @Test
    void testIsContinueRolling() {
        fail("Not yet implemented");
    }

    @Test
    void testGetPlayerWins() {
        fail("Not yet implemented");
    }

    @Test
    void testGetHouseWins() {
        fail("Not yet implemented");
    }

    @Test
    void testToString() {
        fail("Not yet implemented");
    }

}
