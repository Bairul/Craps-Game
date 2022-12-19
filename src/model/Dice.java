package model;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */
import java.util.Random;
/**
 * Class for a dice. 
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public final class Dice {
    /** Constant for a random roll.*/
    private static final Random RANDOM_CONSTANT = new Random();
    /** Constant for the number of faces on the dice.*/
    private static final int DICE_FACES = 6;
    
    /**
     * Private Constructor for accidental calls.
     */
    private Dice() {
        
    }
    
    /**
     * Rolls the dice and generates a random number.
     * 
     * @return a random number depending on the face of the dice
     */
    public static int roll() {
        return RANDOM_CONSTANT.nextInt(DICE_FACES) + 1;
    }
}
