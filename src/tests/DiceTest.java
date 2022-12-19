package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import model.Dice;
import org.junit.jupiter.api.Test;

class DiceTest {
    /** Rolling the dice this many times to see if all faces are rolled.*/
    private static final int NUM_ROLLS_FOR_RANDOMNESS = 120;
    /** Faces of a six-sided dice.*/
    private static final int[] FACES = new int[] {1, 2, 3, 4, 5, 6};

    @Test
    void testRoll() {
        final Set<Integer> facesRolled = new HashSet<Integer>();
        
        for (int i = 0; i < NUM_ROLLS_FOR_RANDOMNESS; i++) {
            facesRolled.add(Dice.roll());
        }
        assertEquals(facesRolled.size(), FACES.length, "Must be a six sided dice.");
        assertEquals(facesRolled.contains(FACES[0]), true, "No number 1 face.");
        assertEquals(facesRolled.contains(FACES[1]), true, "No number 2 face.");
        assertEquals(facesRolled.contains(FACES[2]), true, "No number 3 face.");
        assertEquals(facesRolled.contains(FACES[3]), true, "No number 4 face.");
        assertEquals(facesRolled.contains(FACES[4]), true, "No number 5 face.");
        assertEquals(facesRolled.contains(FACES[5]), true, "No number 6 face.");
    }

}
