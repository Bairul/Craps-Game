package contoller;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.PropertyChangeEnabledTable;
import view.DicePanel;

/**
 * Class for creating the Panel responsible for rolling of dice. 
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public class DiceRowPanel extends JPanel implements PropertyChangeListener {
    /** Serial versioning.*/
    private static final long serialVersionUID = 7315469299802146002L;
    /** Constant for the size dice panels.*/
    private static final Dimension DICE_SIZE = new Dimension(100, 100);
    /** Constant for the size buttons.*/
    private static final Dimension ROLL_BUTTON_SIZE = new Dimension(80, 40);
    /** Constant for the padding.*/
    private static final int PADDING = 22;
    /** Field for the left dice panels.*/
    private final DicePanel myDice1;
    /** Field for the right dice panels.*/
    private final DicePanel myDice2;
    /** Field for the back end craps model.*/
    private final PropertyChangeEnabledTable myModel;
    /** Field for the roll button.*/
    private final JButton myRollButton;
    /** Field for the play again button.*/
    private final JButton myAgainButton;
    
    /**
     * Constructs the panel for the dice row on the top. 
     * 
     * @param theModel the back end craps model
     * @param theLog the log added
     */
    public DiceRowPanel(final PropertyChangeEnabledTable theModel, final JScrollPane theLog) {
        super();
        myModel = theModel;
        myRollButton = new JButton("Roll");
        myAgainButton = new JButton("Play Again");
        myDice1 = new DicePanel(DICE_SIZE, this.getLocation());
        myDice2 = new DicePanel(DICE_SIZE, this.getLocation());
        layoutComponents();
        add(theLog);
        addListeners();
    }

    /**
     * Helper method for adding listeners to the buttons.
     */
    private void addListeners() {
        setBorder(BorderFactory.createEmptyBorder(0, 
                                                  PADDING, 
                                                  0, 
                                                  0));
        myModel.addPropertyChangeListener(this);
        myAgainButton.addActionListener(theE -> {
            myModel.start();
            myAgainButton.setEnabled(false);
        });
        myRollButton.addActionListener(theE -> {
            if (myModel.isContinueRolling()) {
                myModel.continueRoll();
            } else {
                myModel.firstRoll();
            }
            
        });
    }

    /**
     * Helper method for laying out the buttons to the panel.
     */
    private void layoutComponents() {
        final JPanel buttonPanel = new JPanel();
        myRollButton.setPreferredSize(ROLL_BUTTON_SIZE);
        myRollButton.setMnemonic('r');
        myAgainButton.setMnemonic('a');
        setEnabledButton(false, false);
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 
                                                              0, 
                                                              0, 
                                                              PADDING));
        buttonPanel.add(myRollButton);
        buttonPanel.add(myAgainButton);
        add(myDice1);
        add(myDice2);
        add(buttonPanel);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case PropertyChangeEnabledTable.PROPERTY_ROLL:
                myDice1.changeDiceFace(myModel.getDice1());
                myDice2.changeDiceFace(myModel.getDice2());
                break;
            case PropertyChangeEnabledTable.PROPERTY_BET:
                if (myModel.getPlayerBet().equals(BigDecimal.ZERO)) {
                    myRollButton.setEnabled(false);
                } else {
                    myRollButton.setEnabled(true);
                }
                break;
            case PropertyChangeEnabledTable.PROPERTY_HOUSE_WON:
                setEnabledButton(false, true);
                break;
            case PropertyChangeEnabledTable.PROPERTY_PLAYER_WON:
                setEnabledButton(false, true);
                break;
            case PropertyChangeEnabledTable.PROPERTY_RESET:
                reset();
                break;
            case PropertyChangeEnabledTable.PROPERTY_STOP:
                setEnabledButton(false, false);
                break;
            default:
                break;
        }
    }

    /**
     * Helper method for reseting.
     */
    private void reset() {
        myDice1.changeDiceFace(1);
        myDice2.changeDiceFace(1);
        setEnabledButton(false, false);
    }
    
    /**
     * Helper method for enabling or disabling buttons.
     *  
     * @param theRoll state of the roll button
     * @param theAgain state of the again button
     */
    private void setEnabledButton(final boolean theRoll, final boolean theAgain) {
        myRollButton.setEnabled(theRoll);
        myAgainButton.setEnabled(theAgain);
    }

}
