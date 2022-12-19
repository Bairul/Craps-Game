package contoller;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.CrapsModel;
import model.PropertyChangeEnabledTable;
import view.NoticePanel;
/**
 * Class for creating the Panel responsible for making bets. 
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public class CrapsController extends JPanel implements PropertyChangeListener {
    /** Serial versioning.*/
    private static final long serialVersionUID = 7315469299802146002L;
    /** The number of rows. */
    private static final int ROW = 5;
    /** Amount in Pixels for the Horizontal margin. */
    private static final int HORIZONATAL_MARGIN = 20; 
    /** Amount in Pixels for the Vertical margin. */
    private static final int VERTICALL_MARGIN = 10; 
    /** Amount in columns for the text area. */
    private static final int TEXT_AREA_COLS = 9;
    /** Frame for the class.*/
    private static JFrame myFrame;
    /** Field for the log text field.*/
    private final JTextArea myLog;
    /** Filed for the start in the menu bar.*/
    private final JMenuItem myStart;
    /** Filed for the reset in the menu bar.*/
    private final JMenuItem myReset;
    /** Field for the back end craps model.*/
    private final PropertyChangeEnabledTable myModel;
    /**
     * Controls the craps game.
     * 
     * @param theModel the back end craps model
     * @param theFrame the frame
     */
    public CrapsController(final CrapsModel theModel, final JFrame theFrame) {
        super(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(VERTICALL_MARGIN, 
                                                  HORIZONATAL_MARGIN, 
                                                  VERTICALL_MARGIN, 
                                                  HORIZONATAL_MARGIN));
        myModel = theModel;
        myLog = new JTextArea(ROW, TEXT_AREA_COLS);
        myStart = new JMenuItem(new AbstractAction("Start") {
            /** Serial versioning.*/
            private static final long serialVersionUID = 7315469299802146002L;
            @Override
            public void actionPerformed(final ActionEvent theE) {
                start();
            }
        });
        myReset = new JMenuItem(new AbstractAction("Reset") {
            /** Serial versioning.*/
            private static final long serialVersionUID = 7315469299802146002L;
            @Override
            public void actionPerformed(final ActionEvent theE) {
                myModel.reset();
            }
        });
        theFrame.setJMenuBar(createMenuBar(theFrame));
        layoutComponents();
    }
    
    /**
     * Helper method for the initial prompt for wallet inquiry.
     */
    private void start() {
        boolean validWallet = false;
        do {
            try {
                final BigDecimal input = new BigDecimal(JOptionPane.
                                                        showInputDialog("Please enter"
                                                        + " your Wallet:"));
                if (input.equals(BigDecimal.ZERO)) {
                    new NoticePanel(myFrame, "Empty Wallet!", 
                                    "Please enter a positive Integer.");
                } else {
                    myModel.setPlayerWallet(input.setScale(0));
                    myModel.reset();
                    myModel.start();
                    myStart.setEnabled(false);
                    myReset.setEnabled(true);
                    validWallet = true;
                }
            } catch (final NumberFormatException e) {
                System.out.println(e);
                new NoticePanel(myFrame, "Error!", "Please enter a Integer");
            } catch (final NullPointerException e) {
                new NoticePanel(myFrame, "No Wallet!", 
                                "Please set wallet before Starting.");
                validWallet = true;
            }
        } while (!validWallet);
    }
    
    /**
     * Helper method for laying out the panels. 
     */
    private void layoutComponents() {
        final GridBagConstraints gbc = new GridBagConstraints();
        final JScrollPane scrollPane = new JScrollPane(myLog);
        myLog.setEditable(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridy = 0;
        add(new DiceRowPanel(myModel, scrollPane), gbc);
        gbc.gridy++;
        add(new StatRowPanel(myModel), gbc);
        gbc.gridy++;
        add(new BetRowPanel(myModel), gbc);
    }

    /**
     * Create the GUI and show it. 
     */
    public static void createAndShowGUI() {
        myFrame = new JFrame("Game of Craps");
        myFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent theWE) {
                final int close = JOptionPane.showConfirmDialog(myFrame, "Are you sure?", 
                                                                "Exit confirmation", 
                                                                JOptionPane.YES_NO_OPTION);
                if (close == JOptionPane.YES_OPTION) {
                    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
        
        final CrapsModel craps = new CrapsModel();
        final CrapsController pane = new CrapsController(craps, myFrame);
        craps.addPropertyChangeListener(pane);
        myFrame.setContentPane(pane);
        myFrame.setResizable(false);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }
    
    /**
     * Creates the menu bar.
     * 
     * @param theFrame frame of the game
     * @return the menubar
     */
    private JMenuBar createMenuBar(final JFrame theFrame) {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu gameMenu = new JMenu("Game");
        final JMenu helpMenu = new JMenu("Help");
        
        gameMenu.add(myStart);
        gameMenu.add(myReset);
        myReset.setEnabled(false);
        gameMenu.add(new JMenuItem(new AbstractAction("Exit") {
            /** Serial versioning.*/
            private static final long serialVersionUID = 7315469299802146002L;
            @Override
            public void actionPerformed(final ActionEvent theE) {
                theFrame.dispatchEvent(new WindowEvent(theFrame, WindowEvent.WINDOW_CLOSING));
            }
        }));
        
        helpMenu.add(new JMenuItem(new AbstractAction("About") {
            /** Serial versioning.*/
            private static final long serialVersionUID = 7315469299802146002L;
            @Override
            public void actionPerformed(final ActionEvent theE) {
                JOptionPane.showMessageDialog(myFrame, "Game of Craps\nAuthor: Bairu Li\n"
                                              + "Version: 0.9.1\nJava: 17", "About ", 
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }));
        helpMenu.add(new JMenuItem(new AbstractAction("Rules") {
            /** Serial versioning.*/
            private static final long serialVersionUID = 7315469299802146002L;
            @Override
            public void actionPerformed(final ActionEvent theE) {
                JOptionPane.showMessageDialog(myFrame, "Start the game by clicking Start on"
                                              + " the Game menu.\nPlace a bet before "
                                              + "rolling the dice. The Sum of the 2 \n"
                                              + "dice determines the outcome.\n\nFirst Roll:"
                                              + "\nSum is 7 or 11 - Player wins\n"
                                              + "Sum is 2, 3 or 12 - House wins\n"
                                              + "Else - Point\n\n"
                                              + "Subsequent rolls:\n"
                                              + "Sum is the Point - Player Wins\n"
                                              + "Sum is a 7 - House Wins\n\n"
                                              + "If your Wallet is 0 on round end, you Lose.",
                                              "Rules ", 
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }));
        helpMenu.add(new JMenuItem(new AbstractAction("Shortcuts") {
            /** Serial versioning.*/
            private static final long serialVersionUID = 7315469299802146002L;
            @Override
            public void actionPerformed(final ActionEvent theE) {
                JOptionPane.showMessageDialog(myFrame, "Roll - Alt + r\nPlay Again - Alt + a",
                                              "Shortcut", 
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }));
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }
    
    /**
     * Helper method for logging the rolls.
     */
    private void logRoll() {
        myLog.append("Roll:" + myModel.getDice1());
        myLog.append(" + ");
        myLog.append(String.valueOf(myModel.getDice2()));
        myLog.append(" = ");
        myLog.append(String.valueOf(myModel.getSum()));
        myLog.append(System.lineSeparator());
    }
    
    /**
     * Detects changes in property and logs them accordingly.
     * 
     * {@inheritDoc}
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        myLog.setCaretPosition(myLog.getDocument().getLength());
        switch (theEvent.getPropertyName()) {
            case PropertyChangeEnabledTable.PROPERTY_PLAYER_WON:
                myLog.append(PropertyChangeEnabledTable.PROPERTY_PLAYER_WON);
                myLog.append(System.lineSeparator());
                break;
            case PropertyChangeEnabledTable.PROPERTY_HOUSE_WON:
                myLog.append(PropertyChangeEnabledTable.PROPERTY_HOUSE_WON);
                myLog.append(System.lineSeparator());
                break;
            case PropertyChangeEnabledTable.PROPERTY_POINT:
                myLog.append("Point: " + myModel.getPoint());
                myLog.append(System.lineSeparator());
                break;
            case PropertyChangeEnabledTable.PROPERTY_BET:
                myLog.append("+ $");
                myLog.append(String.valueOf(myModel.getPlayerBet().multiply(BigDecimal.
                                                                            valueOf(2))));
                myLog.append(" on win.");
                myLog.append(System.lineSeparator());
                break;
            case PropertyChangeEnabledTable.PROPERTY_ROLL:
                logRoll();
                break;
            case PropertyChangeEnabledTable.PROPERTY_STOP:
                myLog.append("Game Over!");
                myLog.append(System.lineSeparator());
                new NoticePanel(myFrame, "Game Over! ", "Player had ran out of money.");
                break;
            case PropertyChangeEnabledTable.PROPERTY_RESET:
                myLog.append("Game!");
                myLog.append(System.lineSeparator());
                break;
            case PropertyChangeEnabledTable.PROPERTY_START:
                myLog.append("Start!");
                myLog.append(System.lineSeparator());
                break;
            default:
                break;
        }
    }
}
