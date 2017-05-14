package mdc.device.urinoanalisy;

import javax.bluetooth.*;
import java.io.IOException;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

class DeviceSearchListener implements DiscoveryListener {
    private final SearchEvent searchEvent;

    DeviceSearchListener(SearchEvent searchEvent) {
        this.searchEvent = searchEvent;
    }

    @Override
    public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
        //System.out.println("Device " + remoteDevice.getBluetoothAddress() + " found");
        searchEvent.addDevice(remoteDevice);
        String name;
        try {
            name = remoteDevice.getFriendlyName(false);
        } catch (IOException cantGetDeviceName) {
            name = remoteDevice.getBluetoothAddress();
        }
        //System.out.println("     name " + name + ", id: " +remoteDevice.getBluetoothAddress() );
    }

    @Override
    public void servicesDiscovered(int i, ServiceRecord[] serviceRecords) {
        for (ServiceRecord service : serviceRecords) {
            String connectionURL = service.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
            this.searchEvent.setConnectUrl(connectionURL);
        }

        synchronized(searchEvent){
            searchEvent.notifyAll();
        }
    }

    @Override
    public void serviceSearchCompleted(int i, int i1) {

    }

    @Override
    public void inquiryCompleted(int i) {
        //System.out.println("Device Inquiry completed!");
        synchronized(searchEvent){
            searchEvent.notifyAll();
        }
    }
}
