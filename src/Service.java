import javax.swing.*;
import java.util.List;

public class Service {
    public Devices devices;
    private Info info;
    public Filter filter;


    public Service() {
        createAllElements();
    }

    private void createAllElements() {
        devices = new Devices();
        info = new Info();
        filter = new Filter();
    }

    public String addInfoToPanel(String text) {
        return info.changeData(text);
    }

    public void sortItems(String action) {
        if (action == "latest first") {
            System.out.println("sort latest");
        }
        else if (action == "earliest first"){
            System.out.println("sort early");
        }
    }

    public void changeFilters(Filter newFilter) {
        System.out.println("Change filters and update");
    }

    public void addNewFirmware(FirmwareElement newFirmware) {
        System.out.println("add firmware with elements " + newFirmware.platform + " " + newFirmware.version);
    }

    public void editFirmwareData(String name) {
        System.out.println("edit firmware");
    }

    public void searchFirmwareWithFilters() {
        System.out.println("search");
    }

    public void deleteFirmware(String name) {
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
