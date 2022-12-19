package model;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
/**
 * Class for the brain of the game. 
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public class CrapsModel implements PropertyChangeEnabledTable {
    enum FirstRoll {
        /** Player wins.*/
        PLAYER_WIN,
        /** House wins.*/
        HOUSE_WIN,
        /** Point.*/
        POINT
    }
    /** Constant for the lose number.*/
    private static final int LOSE_ON_CONTINUE = 7;
    /** Used for firing property changes.*/
    private final PropertyChangeSupport myPcs;
    /** Field for player.*/
    private final Player myPlayer;
    /** Field for mapping the sums to its corresponding outcomes.*/
    private final Map<Integer, FirstRoll> myFirstRoll; 
    /** Field for initial wallet.*/
    private BigDecimal myInitialWallet;
    /** Field for left dice value.*/
    private int myDice1;
    /** Field for right dice value.*/
    private int myDice2;
    /** Field for sum value.*/
    private int mySum;
    /** Field for the current point value.*/
    private int myPoint;
    /** Field for tracking house wins.*/
    private int myWin;
    /** Field for determining subsequent rolls.*/
    private boolean myContinueRolling;
    
    /**
     * Constructs the brain of the game.
     */
    public CrapsModel() {
        super();
        myPcs = new PropertyChangeSupport(this);
        myPlayer = new Player();
        myFirstRoll = new HashMap<>();
        initFirstRolls();
    }
    
    /**
     * Helper method for putting sums to their outcomes.
     */
    private void initFirstRolls() {
        myFirstRoll.put(7, FirstRoll.PLAYER_WIN);
        myFirstRoll.put(11, FirstRoll.PLAYER_WIN);
        myFirstRoll.put(2, FirstRoll.HOUSE_WIN);
        myFirstRoll.put(3, FirstRoll.HOUSE_WIN);
        myFirstRoll.put(12, FirstRoll.HOUSE_WIN);
        myFirstRoll.put(4, FirstRoll.POINT);
        myFirstRoll.put(5, FirstRoll.POINT);
        myFirstRoll.put(6, FirstRoll.POINT);
        myFirstRoll.put(8, FirstRoll.POINT);
        myFirstRoll.put(9, FirstRoll.POINT);
        myFirstRoll.put(10, FirstRoll.POINT);
    }
    
    /**
     * Helper method to handle rolls.
     */
    private void roll() {
        myDice1 = Dice.roll();
        myDice2 = Dice.roll();
        mySum = myDice1 + myDice2;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getDice1() {
        return myDice1;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getDice2() {
        return myDice2;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        myPcs.firePropertyChange(PROPERTY_START, false, true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        myPcs.firePropertyChange(PROPERTY_STOP, false, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void firstRoll() {
        roll();
        myPcs.firePropertyChange(PROPERTY_ROLL, 0, mySum);
        switch (myFirstRoll.get(mySum)) {
            case PLAYER_WIN:
                myPlayer.win();
                myPcs.firePropertyChange(PROPERTY_PLAYER_WON, 0, mySum);
                break;
            case HOUSE_WIN:
                lose();
                break;
            case POINT:
                myPoint = mySum;
                myContinueRolling = true;
                myPcs.firePropertyChange(PROPERTY_POINT, 0, mySum);
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void continueRoll() {
        roll();
        myPcs.firePropertyChange(PROPERTY_ROLL, 0, mySum);
        if (mySum == LOSE_ON_CONTINUE) {
            myContinueRolling = false;
            lose();
        } else if (mySum == myPoint) {
            myContinueRolling = false;
            myPlayer.win();
            myPcs.firePropertyChange(PROPERTY_PLAYER_WON, 0, mySum);
        }
    }
    
    /**
     * Helper method for when player loses.
     */
    private void lose() {
        myWin++;
        myPlayer.lose();
        myPcs.firePropertyChange(PROPERTY_HOUSE_WON, 0, mySum);
        if (myPlayer.getWallet().equals(BigDecimal.ZERO)) {
            myPcs.firePropertyChange(PROPERTY_STOP, false, true);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void adjustPlayerBet(final BigDecimal theBet) {
        final BigDecimal oldBet = getPlayerBet();
        final BigDecimal oldWallet = getPlayerWallet();
        myPlayer.adjustBet(theBet);
        myPlayer.adjustWallet(theBet.negate());
        myPcs.firePropertyChange(PROPERTY_BET, oldBet, getPlayerBet());
        myPcs.firePropertyChange(PROPERTY_WALLET, oldWallet, getPlayerWallet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerWallet(final BigDecimal theWallet) {
        myInitialWallet = theWallet;
        myPlayer.setWallet(theWallet);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        mySum = 0;
        myPoint = 0;
        myWin = 0;
        myPlayer.reset();
        myPlayer.setWallet(myInitialWallet);
        myPcs.firePropertyChange(PROPERTY_RESET, false, true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getPlayerWallet() {
        return myPlayer.getWallet();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getPlayerBet() {
        return myPlayer.getBet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSum() {
        return mySum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPoint() {
        return myPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isContinueRolling() {
        return myContinueRolling;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerWins() {
        return myPlayer.getNumWins();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getHouseWins() {
        return myWin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);
    }
    /**
     * Shows the current state of the round in as a readable String. Shows the dice
     * rolls and sum.
     * 
     * @return String of the current roll
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(myDice1);
        sb.append(" and ");
        sb.append(myDice2);
        sb.append(" was rolled. Sum is ");
        sb.append(mySum);
        sb.append(" .");
        sb.append(myPlayer.toString());
        return sb.toString();
    }
}
