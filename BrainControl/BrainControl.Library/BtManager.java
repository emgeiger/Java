package BrainControl.Library;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.microedition.io.StreamConnection;
import javax.bluetooth.BluetoothConnectionException;
import javax.comm.CommPortIdentifier;
import javax.comm.CommPortOwnershipListener;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import BrainControl.Library.models.BtDataEventArgs;

/**
 * Manager for Bluetooth serial communication with EEG devices
 */
public class BtManager implements SerialPortEventListener
{

    /**
     * Interface for Bluetooth data event listeners
     */
    public interface BtEventListener extends EventListener
    {
        /**
         * Called when Bluetooth data is parsed
         *
         * @param sender The sender object
         * @param e The event arguments
         */
        void onBtDataParsed(Object sender, BtDataEventArgs e);
    }

    private List<BtEventListener> listeners = new ArrayList<>();

    private SerialPort serialPort;
    private InputStream inputStream;

    private static final int MAX_PACKET_LENGTH = 32;
    private static final int EEG_POWER_BANDS = 8;
    private byte lastByte = 0;
    private boolean inPacket = false;
    private boolean freshPacket = false;
    private int packetIndex = 0;
    private int checksumAccumulator = 0;
    private int packetLength = 0;
    private int checkSum = 0;
    private int[] eegPower = new int[EEG_POWER_BANDS];
    private byte[] packetData = new byte[MAX_PACKET_LENGTH];

    private int signalQuality = 200;
    private int focus = 0;
    private int meditation = 0;

    /**
     * Creates a new BtManager instance
     *
     * @param comPort The COM port to use
     * @throws Exception if there's an error opening the port
     */
    public BtManager(String comPort) throws Exception
    {
        // Find the port identifier
        CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(comPort);

        if (portId.isCurrentlyOwned())
        {
            throw new PortInUseException();
        }

        // Open the port
        serialPort = (SerialPort) portId.open("BrainControl", 2000);

        // Set port parameters
        serialPort.setSerialPortParams(
            9600,                           // Baudrate
            SerialPort.DATABITS_8,
            SerialPort.STOPBITS_1,
            SerialPort.PARITY_NONE
        );

        // Get the input stream
        inputStream = serialPort.getInputStream();

        // Add event listener
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
    }

    /**
     * Adds a listener for Bluetooth data events
     *
     * @param listener The listener to add
     */
    public void addBtEventListener(BtEventListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes a listener for Bluetooth data events
     *
     * @param listener The listener to remove
     */
    public void removeBtEventListener(BtEventListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * Fires the BtDataParsed event
     *
     * @param e The event arguments
     */
    protected void fireBtDataParsedEvent(BtDataEventArgs e)
    {
        for (BtEventListener listener : listeners)
        {
            listener.onBtDataParsed(this, e);
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event)
    {
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                int bytesAvailable = inputStream.available();
                byte[] buffer = new byte[bytesAvailable];
                inputStream.read(buffer, 0, bytesAvailable);

                byte[] sampleBuffer = new byte[256];

                if (buffer.length >= 256)
                {
                    System.arraycopy(buffer, 0, sampleBuffer, 0, 256);
                } else {
                    System.arraycopy(buffer, 0, sampleBuffer, 0, buffer.length);
                }

                for (byte b : sampleBuffer)
                {
                    if (inPacket) {
                        if (packetIndex == 0) {
                            packetLength = b;

                            if (packetLength > MAX_PACKET_LENGTH) {
                                inPacket = false;
                            }
                        } else if (packetIndex <= packetLength) {
                            packetData[packetIndex - 1] = b;
                            checksumAccumulator += b;
                        } else if (packetIndex > packetLength) {
                            checkSum = b;
                            checksumAccumulator = 255 - checksumAccumulator;

                            if (checkSum == checksumAccumulator) {
                                if (parsePacket()) {
                                    freshPacket = true;
                                } else {
                                    System.out.println("ERROR: PARSING PACKET FAILED");
                                }
                            } else {
                                // System.out.println("ERROR: CHECKSUM");
                            }
                            inPacket = false;
                        }
                        packetIndex++;
                    }

                    if (b == (byte)170 && lastByte == (byte)170 && !inPacket) {
                        inPacket = true;
                        packetIndex = 0;
                        checksumAccumulator = 0;
                    }

                    lastByte = b;
                }

                if (freshPacket) {
                    freshPacket = false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses a packet
     *
     * @return true if the packet was parsed successfully, false otherwise
     */
    private boolean parsePacket()
    {
        boolean parseSuccess = true;
        int rawValue = 0;

        clearEegPower();

        for (int i = 0; i < packetLength; i++) {
            switch (packetData[i]) {
                case 0x2:
                    signalQuality = packetData[++i];
                    break;
                case 0x4:
                    focus = packetData[++i];
                    break;
                case 0x5:
                    meditation = packetData[++i];
                    break;
                case (byte)0x83:
                    i++;
                    for (int j = 0; j < EEG_POWER_BANDS; j++) {
                        eegPower[j] = ((packetData[++i] & 0xFF) << 8) | (packetData[++i] & 0xFF);
                    }
                    break;
                case (byte)0x80:
                    i++;
                    rawValue = ((packetData[++i] & 0xFF) << 8) | (packetData[++i] & 0xFF);
                    break;
                default:
                    parseSuccess = false;
                    break;
            }
        }

        // Don't allow for outliers
        // if (rawValue <= 200)
        fireBtDataParsedEvent(new BtDataEventArgs(this, rawValue));

        return parseSuccess;
    }

    /**
     * Clears the EEG power array
     */
    private void clearEegPower() {
        for (int i = 0; i < EEG_POWER_BANDS; i++) {
            eegPower[i] = 0;
        }
    }

    /**
     * Starts the BtManager
     *
     * @throws Exception if there's an error starting the manager
     */
    public void start() throws Exception {
        if (!serialPort.isReceiveEnabled()) {
            serialPort.enableReceiveTimeout(100);
        }
    }

    /**
     * Stops the BtManager
     */
    public void stop() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Prints the current values
     */
    public void printValues() {
        System.out.println("Focus: " + focus);
        System.out.println("Meditation: " + meditation);
        System.out.println("Signal: " + signalQuality);
    }

    /**
     * Finalizer to ensure resources are closed
     */
    @Override
    protected void finalize() throws Throwable
    {
        try
        {
            stop();
        } finally {
            super.finalize();
        }
    }
}