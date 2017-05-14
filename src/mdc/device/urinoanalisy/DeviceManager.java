package mdc.device.urinoanalisy;


import com.uroweb.device.bc01.BC01;
import com.uroweb.device.bc01.BC01Data;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import mdc.engine.ReadConfigXml;
import org.apache.log4j.Logger;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

class DeviceManager {
    private ArrayList<RemoteDevice> devices;
    private final SearchEvent searchEvent;
    private static final Logger log = Logger.getLogger(DeviceManager.class);
    private ReadConfigXml config;
    
    DeviceManager(SearchEvent searchEvent) {
        config = new ReadConfigXml();
        config.parse();
        this.devices = new ArrayList<>();
        this.searchEvent = searchEvent;
    }

    /**
     * Finds all available bluetooth devices
     */
    void findAllDevices() {
        DeviceSearchListener searchListener = new DeviceSearchListener(searchEvent);
        boolean started = false;

        synchronized (searchEvent) {
            try {
                started = LocalDevice
                        .getLocalDevice()
                        .getDiscoveryAgent()
                        .startInquiry(DiscoveryAgent.GIAC, searchListener);
            } catch (BluetoothStateException e) {
                log.info("[error] Bluetooth adapter is not available!",e);
            }
            if (started) {
                System.out.println("Scanning for devices...");
                try {
                    searchEvent.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.devices = searchEvent.getDevices();
        System.out.println("");
        log.info("-- Found " + this.devices.size() + " devices");
    }

    /**
     * Ask user to select device in console
     */
    String selectDevice() {
        if (this.devices.size() < 1) {
            log.info("No devices found!");
            return null;
        }
        System.out.println("Select device:");
        @SuppressWarnings("Convert2Diamond")
        ArrayList<String> deviceNames = new ArrayList<String>();
        int i = 0;
        for (RemoteDevice device : this.devices) {
            String name;
            try {
                name = device.getFriendlyName(false);
                if (config.getUrinoName().equals(name)) {
                    String addr = device.getBluetoothAddress();
                    return addr;
                }
            } catch (IOException e) {
                name = device.getBluetoothAddress();
            }
            deviceNames.add(name);
        }

        String input = System.console().readLine("\nEnter number:");
        int index = Integer.parseInt(input) - 1;

        String name;
        String addr;
        try {
            name = deviceNames.get(index);
            addr = devices.get(index).getBluetoothAddress();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("[error] Wrong devices number!");

            return null;
        }

        System.console().printf("Selected device %s, addr: %s\n", name, addr);

        return addr;
    }

    /**
     * Connect to specific device with specific service
     *
     * @param uri service connection url
     */
    String connectToDevice(String uri) {
        InterpreterParametrs inter = new InterpreterParametrs();
        try {
            System.out.println("Trying to connect to " + uri + "...");
            StreamConnection connection = (StreamConnection) Connector.open(uri);
            System.out.println("OK. Connected!");

            InputStream inputStream = connection.openInputStream();
            OutputStream outputStream = connection.openOutputStream();

            BC01 manager = new BC01(); // создаение объекта менеджера
            manager.synchronizeTime(inputStream, outputStream); // Синхронизация времени
            //noinspection unchecked
            ArrayList<BC01Data> dataList = manager.getData(inputStream, outputStream); // Получение данных
            if (dataList != null) {
                manager.deleteData(outputStream); // Удаление данных
                System.out.println("Uploaded records: " + dataList.size());
                String result = inter.interpreter("traditional",dataList);
                
                return result;                
            } else {
                System.out.println("[warning] No data was fetched!");
            }

            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*private void outputDataList(ArrayList<BC01Data> dataList) {
        Console out = System.console();
        for (BC01Data item : dataList) {
            out.printf("####### BC01 DATA ITEM #####\n");
            for (Field f : item.getClass().getDeclaredFields()) {
                if (f.getModifiers() != Modifier.PUBLIC) {
                    continue;
                }

                String fieldName = f.getName();
                try {
                    String deviceFieldView = item.getDeviceFieldView(fieldName);
                    out.printf("    %s: %s (%s)\n", fieldName, deviceFieldView, f.get(item));
                } catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
            out.writer().println("");
        }
    }
*/
    /**
     * Discover services and return capability service url if found
     *
     * @param addr Device address
     * @return Service URL or null
     */
    String discoverServices(String addr) {
        RemoteDevice rd = null;

        for (RemoteDevice d : devices) {
            if (d.getBluetoothAddress().equals(addr)) {
                rd = d;
            }
        }
        BC01 manager = new BC01();
        try {
            DeviceSearchListener searchListener = new DeviceSearchListener(searchEvent);
            synchronized (searchEvent) {

                LocalDevice localDevice = LocalDevice.getLocalDevice();
                DiscoveryAgent agent = localDevice.getDiscoveryAgent();
                String uuidString = manager.getUuid().toString().replace("-", "");
                UUID[] uuids = new UUID[]{new UUID(uuidString, false)};

                //noinspection ConstantConditions
                agent.searchServices(null, uuids, rd, searchListener);
                System.out.println("Searching for capability services...");
                searchEvent.wait();
            }
        } catch (BluetoothStateException | InterruptedException e) {
            e.printStackTrace();
        }

        return searchEvent.getConnectUrl();
    }
}
