import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private DBConnector db;
    private Request request;
    public Devices devices;
    private DefaultTableModel table;
    public selectFilter filter;
    public boolean sortEarlier;
    private static final String[] tableColumns = {"name", "md5", "platform", "date", "branch", "stage"};


    public Service() {
        initAllElements();
    }

    public void finish() {
        db.release();
    }

    public  List<String> getAllPlatforms() {
        return devices.allDevices();
    }

    private void createTable() {
        for (String column: tableColumns) {
            table.addColumn(column);
        }
    }

    public DefaultTableModel getTable() {
        return table;
    }

    public Request getRequest() {
        return request;
    }

    private void initAllElements() {
        sortEarlier = false;
        db = DBConnector.getInstance();
        db.connect();
        request = new Request(db);

        // для того чтобы таблица не была редактируема
        table = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        createTable();

        devices = new Devices();
        filter = new selectFilter(devices.allDevices());
    }

    public void changeSortAction(boolean newSortEarlier) {
        sortEarlier = newSortEarlier;
    }

    public DefaultTableModel showItems() {
        // метод получает список всех прошивок из БД, сортирует их и возвращает отсортированную модель таблицы
        List<FirmwareElement> result = request.createDataRequest(filter);
        List<String> newModel = new ArrayList<String>();
        if (!sortEarlier) {

            //преобразовать список наоборот
            System.out.println("sort latest");
            table.setRowCount(0);
            for (int i = result.size() - 1; i > -1; --i){
                table.insertRow(result.size() - i -1, new Object[]{result.get(i).name, result.get(i).md5,
                        result.get(i).platform, result.get(i).date.resultDate, result.get(i).branch,
                        result.get(i).stage});
            }

        }
        else {
            System.out.println("sort early");
            table.setRowCount(0);
            for (int i = 0; i < result.size(); ++i) {
                table.insertRow(i, new Object[]{result.get(i).name, result.get(i).md5,
                        result.get(i).platform, result.get(i).date.resultDate, result.get(i).branch,
                        result.get(i).stage});
            }
        }

        return table;
    }

    public void changeFilters(selectFilter newFilter) {
        filter = newFilter;
        System.out.println("Change filters and update");
        request.createDataRequest(newFilter);
    }

    public selectFilter getCurrentFilter () {
        return filter;
    }

    public void addNewFirmware(FirmwareElement newFirmware) {
        System.out.println("add firmware with elements " + newFirmware.platform + " " + newFirmware.date.resultDate);
        // check return TODO
        request.createAddRequest(newFirmware);
    }

    public void editFirmwareData(Integer columnIndex) {

        System.out.println("edit firmware");
        // create request  to edit firmware TODO
        request.createEditRequest();
    }

    public void deleteFirmware(String md5) {

        System.out.println("delete soon");
        // check return TODO
        request.createDeleteRequest(md5);
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
