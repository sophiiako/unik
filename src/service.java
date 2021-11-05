import javax.swing.*;
import java.util.List;

public class service {
    private Devices devices;
    private Info info;

    public service() {
        createAllElements();
    }

    private void createAllElements() {
        devices = new Devices();
        info = new Info();
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
