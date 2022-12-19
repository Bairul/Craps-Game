
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */

import contoller.CrapsController;
/**
 * The driver class for craps game. 
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public final class Application {
    
    /**
     * Private empty constructor to avoid accidental instantiation. 
     */
    private Application() { }
    
    /**
     * Main method for executable class.
     * 
     * @param theArgs args
     */
    public static void main(final String[] theArgs) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CrapsController.createAndShowGUI();
            }
        });
    }
}
