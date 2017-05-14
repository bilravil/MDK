package mdc.device.urinoanalisy;

import javax.bluetooth.RemoteDevice;
import java.util.ArrayList;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

class SearchEvent {
    private ArrayList<RemoteDevice> devices;
    private String connectUrl;

    SearchEvent() {
        this.devices = new ArrayList<>();
    }

    void addDevice(RemoteDevice device) {
        this.devices.add(device);
    }

    ArrayList<RemoteDevice> getDevices() {
        return this.devices;
    }

    String getConnectUrl() {
        return connectUrl;
    }

    void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }
}
