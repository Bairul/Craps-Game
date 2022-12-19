package view;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Class for creating a easy pane to be shown to the user. 
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public class NoticePanel extends JDialog {
    /** Serial versioning.*/
    private static final long serialVersionUID = 7315469299802146002L;
    /**
     * Constructs the pane to be shown.
     * 
     * @param theFrame the background frame
     * @param theTitle the title of the pane
     * @param theMessage the message of the pane
     */
    public NoticePanel(final JFrame theFrame, final String theTitle,
                       final String theMessage) {
        super(theFrame, true);
        final JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.add(new JLabel(theMessage));
        setTitle(theTitle);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
