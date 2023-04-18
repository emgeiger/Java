import javax.bluetooth.*;

public class BluetoothDeviceManager {

    public static void main(String[] args) throws Exception {
        // Obtain a reference to the local Bluetooth adapter
        LocalDevice localDevice = LocalDevice.getLocalDevice();

        // Start device discovery
        DiscoveryAgent agent = localDevice.getDiscoveryAgent();
        agent.startInquiry(DiscoveryAgent.GIAC, null);

        // Get a list of discovered devices
        RemoteDevice[] remoteDevices = agent.retrieveDevices(DiscoveryAgent.CACHED);

        // Print out a list of discovered devices
        for (RemoteDevice device : remoteDevices) {
            System.out.println(device.getBluetoothAddress() + " - " + device.getFriendlyName(false));
        }
    }
}