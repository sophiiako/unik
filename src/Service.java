import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private static final DBConnector db = new DBConnector();
    private static final Request request = new Request(db);
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


    public String getInfoToPanel(String name) {
        return info.changeData(getAllFirmwareData(name));
    }

    public String resetInfoPanel(){
        return info.initialPanel();
    }

    public List<String> sortItems(String action) {
        List<FirmwareElement> result = request.tempRequestToGetAllDates();

        List<FirmwareElement> sortedResult = (new Sort()).sortElements(result);

        List<String> newModel = new ArrayList<String>();
        if (action == "latest first") {

            //преобразовать список наоборот
            System.out.println("sort latest");

            for (int i = sortedResult.size() - 1; i > -1; i--){
                newModel.add(sortedResult.get(i).name);
                System.out.println("llll");
                System.out.println(sortedResult.get(i).name);
            }

        }
        else if (action == "earliest first"){
            System.out.println("sort early");
            for(FirmwareElement n: sortedResult) {
                newModel.add(n.name);
            }
        }

        return newModel;
    }

    public void changeFilters(Filter newFilter) {

        System.out.println("Change filters and update");
        searchFirmwareWithFilters(newFilter);
    }

    public void addNewFirmware(FirmwareElement newFirmware) {
        System.out.println("add firmware with elements " + newFirmware.platform + " " + newFirmware.version);
        // create request  to add firmware TODO
        request.createAddRequest(newFirmware);
    }

    public void editFirmwareData(String name) {

        System.out.println("edit firmware");
        // create request  to edit firmware TODO
        request.createEditRequest();
    }

    private void searchFirmwareWithFilters(Filter filter) {

        System.out.println("search");
        // create request  to search firmware TODO
        //return kuda to
        request.createSearchRequest();
    }

    public void deleteFirmware(String name) {

        System.out.println("delete soon");
        // create request  to delete firmware TODO
        request.createDeleteRequest(name);
    }

    private String getAllFirmwareData(String name) {

        System.out.println("get all data from db");
        // create request  to search concrete firmware TODO
        return request.createSearchRequestByName(name);
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
