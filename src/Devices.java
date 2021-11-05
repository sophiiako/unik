import java.util.ArrayList;
import java.util.List;

public class Devices {
    private List<String> usingDevices;
    public Devices() {
        // from file!!!
        usingDevices = new ArrayList<String>();
    }

    public List<String> allDevices() {
        return usingDevices;
    }

    public void addNewDevice(String element) {
        usingDevices.add(element);
        //test exists
        //add to file
    }

    public void deleteDevice(String element) {
        usingDevices.remove(element);
        // delete from file
    }
}
