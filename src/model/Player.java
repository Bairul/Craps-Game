package model;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */
import java.math.BigDecimal;
/**
 * Class for creating a player in the game. Tracks their wallet and bet amount.  
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public final class Player {
    /** Field for the current wallet.*/
    private BigDecimal myWallet;
    /** Field for the current bet amount.*/
    private BigDecimal myBet;
    /** Field for the number of wins.*/
    private int myNumWins;
    
    /**
     * Constructs a player.
     */
    public Player() {
        myNumWins = 0;
        setWallet(BigDecimal.ZERO);
        setBet(BigDecimal.ZERO);
    }
    
    /**
     * Getter for the bet field.
     * @return the current bet amount
     */
    public BigDecimal getBet() {
        return myBet;
    }
    
    /**
     * Setter for the bet field.
     * 
     * @param theBet the new bet to be set
     */
    public void setBet(final BigDecimal theBet) {
        if (theBet.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Bet cannot be negative, it was " 
                                                + theBet);
        }
        myBet = theBet;
    }
    
    /**
     * Setter for the bet field but as a increment of a amount.
     * @param theBet the increment amount
     */
    public void adjustBet(final BigDecimal theBet) {
        if (BigDecimal.ZERO.compareTo(myBet.add(theBet)) > 0) {
            throw new IllegalStateException("Bet cannot be negative. It was a bet of "
                                            + myBet.add(theBet));
        }
        myBet = myBet.add(theBet);
    }
    
    /**
     * Method for when the player has won the round.
     */
    public void win() {
        myNumWins++;
        adjustWallet(myBet.multiply(BigDecimal.valueOf(2)));
        myBet = BigDecimal.ZERO;
    }
    
    /**
     * Method for thwne the player has lost the round.
     */
    public void lose() {
        myBet = BigDecimal.ZERO;
    }
    
    /**
     * Setter for the wallet field.
     * 
     * @param theAmount the new wallet amount to be set
     */
    public void setWallet(final BigDecimal theAmount) {
        if (theAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Wallet cannot be negative, it was " 
                                                + theAmount);
        }
        myWallet = theAmount;
    }
    
    /**
     * Getter for the wallet field.
     * @return the current wallet amount
     */
    public BigDecimal getWallet() {
        return myWallet;
    }
    
    /**
     * Setter for the wallet field but as a increment of a amount.
     * @param theAmount the amount to be incremented
     */
    public void adjustWallet(final BigDecimal theAmount) {
        if (BigDecimal.ZERO.compareTo(myWallet.add(theAmount)) > 0) {
            throw new IllegalStateException("Wallet cannot be negative. Wallet was "
                                            + myWallet.add(theAmount));
        }
        myWallet = myWallet.add(theAmount);
    }
    
    /**
     * Getter for the number of player wins.
     * 
     * @return number of wins
     */
    public int getNumWins() {
        return myNumWins;
    }
    
    /**
     * Method for reseting the player.
     */
    public void reset() {
        myBet = BigDecimal.ZERO;
        myNumWins = 0;
    }
    
    /**
     * Shows the current state of the player in a readable manner. Shows their bet and wallet.
     * 
     * @return a String showing player bet and wallet
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(32);
        sb.append("Betted $");
        sb.append(myBet);
        sb.append(" with $");
        sb.append(myWallet);
        sb.append(" left in wallet. ");
        
        return sb.toString();
    }
}
