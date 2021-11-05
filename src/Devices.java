import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Devices {
    private List<String> usingDevices;
    private static final String configFilename = "src/devices.conf";
    public Devices() {
        usingDevices = new ArrayList<String>();
        try(FileReader reader = new FileReader(configFilename))
        {
            int c;
            String result = "";
            while ((c = reader.read()) != -1) {
                if ((char)c == '\n') {
                    usingDevices.add(result);
                    result = "";
                }
                else {
                    result += (char)c;
                }
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public List<String> allDevices() {
        return usingDevices;
    }

    private boolean isExist(String element) {
        for (String item : usingDevices) {
            if (element.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void addNewDevice(String element) {
        if (!isExist(element)) {
            usingDevices.add(element);
            try(FileWriter writer = new FileWriter(configFilename, true))
            {
                writer.write(element);
                writer.append('\n');
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void deleteDevice(String element) {
            usingDevices.remove(element);
            try(FileWriter writer = new FileWriter(configFilename, false)) {
                for (String item: usingDevices) {
                    writer.write(item);
                    writer.append('\n');
                }
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }

    }
}
