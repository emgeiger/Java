package BrainControl.Library.models;

import java.util.EventObject;

/**
 * Event arguments for Bluetooth data events
 */
public class BtDataEventArgs extends EventObject {
    
    private static final long serialVersionUID = 1L;
    private int rawValue;
    
    /**
     * Creates a new BtDataEventArgs instance
     * 
     * @param source The source of the event
     * @param rawValue The raw value from the BT device
     */
    public BtDataEventArgs(Object source, int rawValue) {
        super(source);
        this.rawValue = rawValue;
    }
    
    /**
     * Gets the raw value from the BT device
     * 
     * @return The raw value
     */
    public int getRawValue() {
        return rawValue;
    }
    
    /**
     * Sets the raw value from the BT device
     * 
     * @param rawValue The raw value
     */
    public void setRawValue(int rawValue) {
        this.rawValue = rawValue;
    }
}
