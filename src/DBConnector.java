import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
    private List<FirmwareElement> firmwares;

    public DBConnector() {
        firmwares = new ArrayList<FirmwareElement>();
    }

    public void add(FirmwareElement f) {
        firmwares.add(f);
    }

    public void delete(String name) {
        firmwares.remove(getByName(name));
    }

    public List<FirmwareElement> getAll() {
        return firmwares;
    }

    public FirmwareElement getByName(String name) {
        for (FirmwareElement f: firmwares) {
            if (f.name.equals(name)) {
                return f;
            }
        }
        return new FirmwareElement();
    }

}
