import javax.swing.*;
import java.util.List;

public class service {
    private Devices devices;

    public service() {
        createAllElements();
    }

    public void addInfoToPanel(JTextArea area, String text) {
        area.append(text);
    }

    private void createAllElements() {
        devices = new Devices();
    }

    public void changeFilters() {
        System.out.println("Change filters and update");
    }

    public void addNewFirmware() {
        System.out.println("add firmware");
    }

    public void editFirmwareData() {
        System.out.println("edit firmware");
    }

    public void searchFirmwareWithFilters() {
        System.out.println("search");
    }

    public void deleteFirmware() {
        System.out.println("delete soon");
    }

    public void getAllFirmwareData() {
        System.out.println("get all data from db");
    }

    public void addAvailableDevice(String element) {
        devices.addNewDevice(element);
    }

    public List<String> availableDevices() {
        return devices.allDevices();
    }

    public void deleteDevice(String element) {
        devices.deleteDevice(element);
    }
}
