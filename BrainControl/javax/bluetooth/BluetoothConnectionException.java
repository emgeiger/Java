/*
 *  BluetoothConnectionException.java
 *  
 *  $Revision$ $Date$ 
 * 
 *  (c) Copyright 2001, 2002 Motorola, Inc.  ALL RIGHTS RESERVED.
 */

package javax.bluetooth;

import java.io.*;
import java.lang.*;


/**
 * This <code>BluetoothConnectionException</code> is thrown when
 * a Bluetooth connection (L2CAP, RFCOMM, or OBEX over RFCOMM)
 * cannot be established
 * successfully. The fields in this exception class indicate the cause of
 * the exception.
 * For example, an L2CAP connection may fail due to a security problem.
 * This reason is passed on to the application through this class.
 *
 * @version 1.0 February 11, 2002
 *
 */
public class BluetoothConnectionException extends IOException {

   /**
    * Indicates the connection to the server failed because no service for
    * the given PSM was registered.
    * <P>
    * The value for <code>UNKNOWN_PSM</code> is 0x0001 (1).
    *
    */
    public static final int UNKNOWN_PSM = 0x0001;

   /**
    * Indicates the connection failed because the security
    * settings on the local device or the remote device were
    * incompatible with the request.
    * <P>
    * The value for <code>SECURITY_BLOCK</code> is 0x0002 (2).
    *
    */
    public static final int SECURITY_BLOCK = 0x0002;





   /**
    * Indicates the connection failed due to a lack of resources
    * either on the local device or on the remote device.
    * <P>
    * The value for <code>NO_RESOURCES</code> is 0x0003 (3).
    */
    public static final int NO_RESOURCES = 0x0003;





   /**
    * Indicates the connection to the server failed due to unknown reasons.
    * <P>
    * The value for <code>FAILED_NOINFO</code> is 0x0004 (4).
    */
    public static final int FAILED_NOINFO = 0x0004;





   /**
    * Indicates the connection to the server failed due to a timeout.
    * <P>
    * The value for <code>TIMEOUT</code> is 0x0005 (5).
    */
    public static final int TIMEOUT = 0x0005;





   /**
    * Indicates the connection failed because the configuration
    * parameters provided were not acceptable to either the remote device or
    * the local device.
    * <P>
    * The value for <code>UNACCEPTABLE_PARAMS</code> is 0x0006 (6).
    */
    public static final int UNACCEPTABLE_PARAMS = 0x0006;


   /*
    * The following section defines public, static and instance
    * member methods used in the implementation of the methods.
    */

   /**
    * Creates a new <code>BluetoothConnectionException</code>
    * with the error indicator specified.
    *
    * @param error indicates the exception condition; must be one of the
    * constants described in this class
    *
    * @exception IllegalArgumentException if the input value
    * is not one of the constants in this class
    *
    */
    public BluetoothConnectionException(int error) {
throw new RuntimeException("Not Implemented! Used to compile Code");
    }   /*  End of the constructor  */


   /**
    * Creates a new <code>BluetoothConnectionException</code>
    * with the error indicator and message specified.
    *
    * @param error indicates the exception condition; must be one of the
    * constants described in this class
    *
    * @param msg a description of the exception; may by <code>null</code>
    *
    * @exception IllegalArgumentException if the input value
    * is not one of the constants in this class
    *
    */
    public BluetoothConnectionException(int error, String msg) {
throw new RuntimeException("Not Implemented! Used to compile Code");
    }   /*  End of the constructor  */






   /**
    * Gets the status set in the constructor that will indicate
    * the reason for the exception.
    *
    * @return cause for the exception; will be one of the constants defined
    * in this class
    *
    */
    public int getStatus() {
throw new RuntimeException("Not Implemented! Used to compile Code");
    }   /*  End of the getStatus method */

}       /*  End of the BluetoothConnectionException     */
