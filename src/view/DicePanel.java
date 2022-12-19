package view;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JComponent;
/**
 * Class for displaying the dice as drawings.
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public final class DicePanel extends JComponent {
    /** Serial versioning.*/
    private static final long serialVersionUID = 7315469299802146002L;
    /** Constant for the size of the dots.*/
    private static final int DOT_SIZE = 16;
    /** Field for the dimension of the panel.*/
    private final Dimension myDimension;
    /** Field for the point of the panel (Upper-left corner).*/
    private final Point myPoint;
    /** Field for the center point of the panel.*/
    private final Point myCenter;
    /** Field for the current face of the die.*/
    private int myFace;
    
    /**
     * Constructs the die panel.
     * 
     * @param theDimension the preferred size of the panel
     * @param theRelative the point of the panel (Upper-left corner)
     */
    public DicePanel(final Dimension theDimension, final Point theRelative) {
        super();
        setPreferredSize(theDimension);
        myDimension = theDimension;
        myPoint = theRelative;
        myCenter = new Point(myPoint.x + theDimension.width / 2,
                             myPoint.y + theDimension.height / 2);
        changeDiceFace(0);
    }
    
    /**
     * Changes the face of the die, then repaints.
     * 
     * @param theFace the face to be changed to.
     */
    public void changeDiceFace(final int theFace) {
        myFace = theFace;
        repaint();
    }
    
    /**
     * Draws the face of the die depending on the current face.
     * 
     * @param theG the graphics
     */
    private void drawDiceFace(final Graphics theG) {
        switch (myFace) {
            case 1:
                drawDotCenter(theG);
                break;
            case 2:
                drawDotTwo(theG, false);
                break;
            case 3:
                drawDotCenter(theG);
                drawDotTwo(theG, true);
                break;
            case 4:
                drawDotFour(theG);
                break;
            case 5:
                drawDotCenter(theG);
                drawDotFour(theG);
                break;
            case 6:
                drawDotFour(theG);
                theG.fillOval(myPoint.x + DOT_SIZE, myCenter.y - DOT_SIZE / 2, 
                              DOT_SIZE, DOT_SIZE);
                theG.fillOval(myDimension.width - DOT_SIZE * 2, myCenter.y - DOT_SIZE / 2, 
                              DOT_SIZE, DOT_SIZE);
                break;
            default:
                break;
        }
    }
    
    /**
     * Helper method for drawing 4 dots on each corner.
     * @param theG the graphics
     */
    private void drawDotFour(final Graphics theG) {
        drawDotTwo(theG, true);
        drawDotTwo(theG, false);
    }
    
    /**
     * Helper method for drawing a dot at the center.
     * @param theG the graphics
     */
    private void drawDotCenter(final Graphics theG) {
        theG.fillOval(myCenter.x - DOT_SIZE / 2, myCenter.y - DOT_SIZE / 2, 
                      DOT_SIZE, DOT_SIZE);
    }
    
    /**
     * Helper method for drawing 2 dots either slanted forward or backward.
     * @param theG the graphics
     */
    private void drawDotTwo(final Graphics theG, final boolean theSlant) {
        final int x1 = myPoint.x + DOT_SIZE;
        final int x2 = myDimension.width - DOT_SIZE * 2;
        theG.fillOval(theSlant ? x1 : x2, myPoint.y + DOT_SIZE, DOT_SIZE, DOT_SIZE);
        theG.fillOval(theSlant ? x2 : x1, myDimension.height - DOT_SIZE * 2, 
                      DOT_SIZE, DOT_SIZE);
    }
    
    /**
     * Allows for painting. Paints the die to the panel. 
     * 
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics theG) {
        super.paintComponent(theG);
        theG.setColor(Color.black);
        theG.drawRect(myPoint.x, myPoint.y, getWidth() - 1, getHeight() - 1);
        drawDiceFace(theG);
    }
}
