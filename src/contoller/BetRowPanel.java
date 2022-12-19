package contoller;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */
import static model.PropertyChangeEnabledTable.PROPERTY_START;
import static model.PropertyChangeEnabledTable.PROPERTY_ROLL;
import static model.PropertyChangeEnabledTable.PROPERTY_RESET;
import static model.PropertyChangeEnabledTable.PROPERTY_STOP;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import model.PropertyChangeEnabledTable;

/**
 * Class for creating the Panel responsible for making bets. 
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public class BetRowPanel extends JPanel implements PropertyChangeListener {
    /** Serial versioning.*/
    private static final long serialVersionUID = 7315469299802146002L;
    /** Constant for the button size.*/
    private static final Dimension BET_BUTTON_SIZE = new Dimension(90, 30);
    /** Constant for the bet $1 amount.*/
    private static final int BET_ONE = 1;
    /** Constant for the bet $5 amount.*/
    private static final int BET_FIVE = 5;
    /** Constant for the bet $10 amount.*/
    private static final int BET_TEN = 10;
    /** Constant for the bet $100 amount.*/
    private static final int BET_HUNDRED = 100;
    /** Field for the back end craps model.*/
    private final PropertyChangeEnabledTable myModel;
    /** Field for all the buttons in one place.*/
    private final JButton[] myButtons;
    /** Field for the bet $1 button.*/
    private final JButton myBetOne;
    /** Field for the bet $5 button.*/
    private final JButton myBetFive;
    /** Field for the bet $10 button.*/
    private final JButton myBetTen;
    /** Field for the bet $100 button.*/
    private final JButton myBetHundred;
    /** Field for the bet All button.*/
    private final JButton myBetAll;
    /** Field for the change negative button.*/
    private final JCheckBox myEnableNegative;
    /** Field for the negative state.*/
    private boolean myNegative;
    
    /**
     * Constructs the panel for the betting row on the bottom.  
     * @param theModel the back end craps model
     */
    public BetRowPanel(final PropertyChangeEnabledTable theModel) {
        super();
        myModel = theModel;
        myBetOne = makeButton();
        myBetFive = makeButton();   
        myBetTen = makeButton();    
        myBetHundred = makeButton();
        myBetAll = new JButton("Bet All");
        myEnableNegative = new JCheckBox("(-)");
        myButtons = new JButton[] {myBetOne, myBetFive, myBetTen, myBetHundred, myBetAll};
        layoutComponents();
    }
    
    /**
     * Helper method for laying out the buttons to the panel.
     */
    private void layoutComponents() {
        setButtonText("Bet +");
        add(myBetOne);
        add(myBetFive);
        add(myBetTen);
        add(myBetHundred);
        add(myBetAll);
        add(myEnableNegative);
        setEnabledButton(false);
        addListeners();
    }
    
    /**
     * Sets the signs of the bet buttons. When negate is checked, bet amounts is negative.
     * 
     * @param theSign sign of the bets
     */
    private void setButtonText(final String theSign) {
        myBetOne.setText(theSign + "$1");
        myBetFive.setText(theSign + "$5");   
        myBetTen.setText(theSign + "$10");    
        myBetHundred.setText(theSign + "$100");
    }
    
    /**
     * Helper method for adding listeners to the buttons. Used for changing the player bets in
     * the backing model.
     */
    private void addListeners() {
        myModel.addPropertyChangeListener(this);
        myBetOne.addActionListener(theE -> adjustBet(BigDecimal.valueOf(BET_ONE
                                                           * (myNegative ? -1 : 1))));
        myBetFive.addActionListener(theE -> adjustBet(BigDecimal.valueOf(BET_FIVE
                                                           * (myNegative ? -1 : 1))));
        myBetTen.addActionListener(theE -> adjustBet(BigDecimal.valueOf(BET_TEN
                                                           * (myNegative ? -1 : 1))));
        myBetHundred.addActionListener(theE -> adjustBet(BigDecimal.
                                                              valueOf(BET_HUNDRED
                                                           * (myNegative ? -1 : 1))));
        myBetAll.addActionListener(theE -> myModel.adjustPlayerBet(myModel.getPlayerWallet()));
        myEnableNegative.addActionListener(theE -> {
            if (myEnableNegative.isSelected()) {
                myNegative = true;
                setButtonText("Bet -");
            } else {
                myNegative = false;
                setButtonText("Bet +");
            }
        });
    }
    
    /**
     * Helper method for adjusting the bet amount. Used for avoiding invalid states.
     * 
     * @param theBet the bet amount to be adjusted
     */
    private void adjustBet(final BigDecimal theBet) {
        if (myModel.getPlayerWallet().add(theBet.negate()).compareTo(BigDecimal.ZERO) >= 0
            && myModel.getPlayerBet().add(theBet).compareTo(BigDecimal.ZERO) >= 0) {
            myModel.adjustPlayerBet(theBet);
        }
    }
    
    /**
     * Helper method for creating buttons.
     * 
     * @return a button with correct size
     */
    private JButton makeButton() {
        final JButton button = new JButton();
        button.setPreferredSize(BET_BUTTON_SIZE);
        return button;
    }
    
    /**
     * Helper method for enabling or disabling buttons.
     * 
     * @param theEnabled enabled or disabled
     */
    private void setEnabledButton(final boolean theEnabled) {
        for (final JButton jb: myButtons) {
            jb.setEnabled(theEnabled);
        }
        myEnableNegative.setEnabled(theEnabled);
    }
    
    /**
     * Changes the buttons to be enabled or disabled depending on the property change.
     * 
     * {@inheritDoc}
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(PROPERTY_ROLL)
            || theEvent.getPropertyName().equals(PROPERTY_STOP)) {
            setEnabledButton(false);
        } else if (theEvent.getPropertyName().equals(PROPERTY_START)
                        || theEvent.getPropertyName().equals(PROPERTY_RESET)) {
            setEnabledButton(true);
        }
    }
}
