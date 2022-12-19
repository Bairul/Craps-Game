package model;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */
import java.math.BigDecimal;
/**
 * Defines behaviors for the craps table.
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public interface Table {
    /**
     * Rolls the dice and determines the outcome for the first roll.
     */
    void firstRoll();
    /**
     * Rolls the dice and determines the outcome for subsequent rolls after the first.
     */
    void continueRoll();
    /**
     * Getter for the sum of the 2 dice.
     * 
     * @return the sum of 2 dice
     */
    int getSum();
    /**
     * Getter for the point. Point is determined on the first roll.
     * 
     * @return the point number
     */
    int getPoint();
    /**
     * Getter for the face number for the left dice.
     * 
     * @return the dice face number
     */
    int getDice1();
    /**
     * Getter for the face number for the right dice.
     * 
     * @return the dice face number
     */
    int getDice2();
    /**
     * Indicates false if first roll has not been rolled or true otherwise. Used for 
     * determining whether the current roll is the first or subsequent.  
     * 
     * @return a boolean indicating whether the roll is first or subsequent
     */
    boolean isContinueRolling();
    /**
     * Starts up the game of craps.
     */
    void start();
    /**
     * Stops the game of craps.
     */
    void stop();
    /**
     * Changes the player bet amount by a certain increment.
     * 
     * @param theBet the increment
     */
    void adjustPlayerBet(BigDecimal theBet);
    /**
     * Sets the player wallet amount.
     * 
     * @param theWallet the amount to be set
     */
    void setPlayerWallet(BigDecimal theWallet);
    /**
     * Getter for the player's wallet.
     * 
     * @return the player's wallet
     */
    BigDecimal getPlayerWallet();
    /**
     * Getter for the player's bet.
     * 
     * @return the player's bet
     */
    BigDecimal getPlayerBet();
    /**
     * Resets the game if craps.
     */
    void reset();
    /**
     * Getter for the number of wins the player had got.
     * 
     * @return the number of player wins
     */
    int getPlayerWins();
    /**
     * Getter for the number of house wins.
     * 
     * @return the number of house wins
     */
    int getHouseWins();
}
