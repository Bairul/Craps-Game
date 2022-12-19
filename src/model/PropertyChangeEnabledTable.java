package model;
/*
 * Course: TCSS 305 Autumn 2022
 * Assignment: 5 - Game of Craps
 * Instructor: Tom
 */
import java.beans.PropertyChangeListener;
/**
 * Defines behaviors allowing PropertyChangeListeners to be added or removed.
 * Defines a set of Properties that may be listened to.
 * 
 * @author Bairu Li
 * @version 0.9.1
 *
 */
public interface PropertyChangeEnabledTable extends Table {
    /** Property of a bet placed.*/
    String PROPERTY_BET = "bet";
    /** Property of a wallet.*/
    String PROPERTY_WALLET = "wallet";
    /** Property of a reset.*/
    String PROPERTY_RESET = "reset";
    /** Property of a dice roll.*/
    String PROPERTY_ROLL = "roll";
    /** Property of player win.*/
    String PROPERTY_PLAYER_WON = "Player Won!";
    /** Property of house win.*/
    String PROPERTY_HOUSE_WON = "House Won!";
    /** Property of a point.*/
    String PROPERTY_POINT = "point";
    /** Property of starting.*/
    String PROPERTY_START = "start";
    /** Property of a stopping.*/
    String PROPERTY_STOP = "stop";
    
    
    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for 
     * all properties. The same listener object may be added more than once, and will be 
     * called as many times as it is added. If listener is null, no exception is thrown and 
     * no action is taken.
     * 
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener theListener);
    
    
    /**
     * Add a PropertyChangeListener for a specific property. The listener will be invoked only 
     * when a call on firePropertyChange names that specific property. The same listener object
     * may be added more than once. For each property, the listener will be invoked the number 
     * of times it was added for that property. If propertyName or listener is null, no 
     * exception is thrown and no action is taken.
     * 
     * @param thePropertyName The name of the property to listen on.
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a 
     * PropertyChangeListener that was registered for all properties. If listener was added 
     * more than once to the same event source, it will be notified one less time after being 
     * removed. If listener is null, or was never added, no exception is thrown and no action 
     * is taken.
     * 
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener theListener);
    
    /**
     * Remove a PropertyChangeListener for a specific property. If listener was added more than
     * once to the same event source for the specified property, it will be notified one less 
     * time after being removed. If propertyName is null, no exception is thrown and no action 
     * is taken. If listener is null, or was never added for the specified property, no 
     * exception is thrown and no action is taken.
     * 
     * @param thePropertyName The name of the property that was listened on.
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(String thePropertyName, 
                                      PropertyChangeListener theListener);
}
