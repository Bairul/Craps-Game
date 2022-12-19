package contoller;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.PropertyChangeEnabledTable;
/**
 * Class for creating the Panel responsible for making bets. 
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public class StatRowPanel extends JPanel implements PropertyChangeListener {
    /** Serial versioning.*/
    private static final long serialVersionUID = 7315469299802146002L;
    /** Formatting the money values.*/
    private static final NumberFormat NF = NumberFormat.getCurrencyInstance(Locale.US);
    /** Amount in Pixels for the Vertical margin. */
    private static final int VERTICALL_MARGIN = 20;
    /** Constant for the house win label.*/
    private static final JLabel HOUSE_LABEL = new JLabel("House Wins:");
    /** Constant for the point label.*/
    private static final JLabel POINT_LABEL = new JLabel("Point:");
    /** Constant for the player win label.*/
    private static final JLabel PLAYER_LABEL = new JLabel("Player Wins:");
    /** Constant for the blue color.*/
    private static final Color BLUE = new Color(0, 110, 255);
    /** Constant for the orange color.*/
    private static final Color ORANGE = new Color(240, 130, 0);
    /** Constant for the column amount.*/
    private static final int COL = 8;
    /** Field for the back end craps model.*/
    private final PropertyChangeEnabledTable myModel;
    /** Field for tracking the player wins.*/
    private final JLabel myPlayerWins;
    /** Field for tracking the house wins.*/
    private final JLabel myHouseWins;
    /** Field for tracking the current sum.*/
    private final JLabel mySum;
    /** Field for tracking the current point.*/
    private final JLabel myPoint;
    /** Field for tracking the current wallet.*/
    private final JLabel myWallet;
    /** Field for tracking the current bet amount.*/
    private final JTextField myBetField;
    
    /**
     * Constructs the panel for the statistics row on the middle.  
     * @param theModel the back end craps model
     */
    public StatRowPanel(final PropertyChangeEnabledTable theModel) {
        super(new GridLayout(2, COL));
        myModel = theModel;
        myPlayerWins = new JLabel();
        myHouseWins = new JLabel();
        mySum = new JLabel();
        myPoint = new JLabel();
        myBetField = new JTextField();
        myWallet = new JLabel();
        layoutComponents();
        myModel.addPropertyChangeListener(this);
    }
    
    /**
     * Helper method for laying out the labels to the panel.
     */
    private void layoutComponents() {
        setBorder(BorderFactory.createEmptyBorder(VERTICALL_MARGIN, 
                                                  0, 
                                                  VERTICALL_MARGIN, 
                                                  0));
        
        add(new JLabel("Sum:"));
        add(mySum);
        add(HOUSE_LABEL);
        add(myHouseWins);
        add(new JLabel("Your wallet:"));
        add(myWallet);
        add(POINT_LABEL);
        add(myPoint);
        add(PLAYER_LABEL);
        add(myPlayerWins);
        add(new JLabel("Your Bet:"));
        add(myBetField);
        myBetField.setEditable(false);
    }
    
    /**
     * Helper method for reseting.
     */
    private void reset() {
        mySum.setText(String.valueOf(myModel.getSum()));
        myPoint.setText(String.valueOf(myModel.getPoint()));
        myHouseWins.setText(String.valueOf(0));
        myPlayerWins.setText("0");
        roundOver();
    }
    
    /**
     * Helper method for setting the correct format.
     */
    private void roundOver() {
        myWallet.setText(NF.format(myModel.getPlayerWallet()));
        myBetField.setText(NF.format(myModel.getPlayerBet()));
    }
    
    /**
     * Helper method for reseting the label colors.
     */
    private void resetLabelColor() {
        PLAYER_LABEL.setForeground(Color.black);
        HOUSE_LABEL.setForeground(Color.black);
        POINT_LABEL.setForeground(Color.black);
    }

    /**
     * Listens for changes in property and displays the statistics correctly.
     * 
     * {@inheritDoc}
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        resetLabelColor();
        switch (theEvent.getPropertyName()) {
            case PropertyChangeEnabledTable.PROPERTY_BET:
                myBetField.setText(NF.format(myModel.getPlayerBet()));
                break;
            case PropertyChangeEnabledTable.PROPERTY_WALLET:
                myWallet.setText(NF.format(myModel.getPlayerWallet()));
                break;
            case PropertyChangeEnabledTable.PROPERTY_RESET:
                reset();
                break;
            case PropertyChangeEnabledTable.PROPERTY_PLAYER_WON:
                PLAYER_LABEL.setForeground(BLUE);
                mySum.setText(theEvent.getNewValue().toString());
                myPlayerWins.setText(String.valueOf(myModel.getPlayerWins()));
                roundOver();
                break;
            case PropertyChangeEnabledTable.PROPERTY_HOUSE_WON:
                HOUSE_LABEL.setForeground(Color.red);
                mySum.setText(theEvent.getNewValue().toString());
                myHouseWins.setText(String.valueOf(myModel.getHouseWins()));
                roundOver();
                break;
            case PropertyChangeEnabledTable.PROPERTY_POINT:
                POINT_LABEL.setForeground(ORANGE);
                mySum.setText(theEvent.getNewValue().toString());
                myPoint.setText(theEvent.getNewValue().toString());
                break;
            case PropertyChangeEnabledTable.PROPERTY_ROLL:
                mySum.setText(theEvent.getNewValue().toString());
                break;
            default:
                break;
        }
        
    }

}
