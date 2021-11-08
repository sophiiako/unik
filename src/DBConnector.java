import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
    private List<FirmwareElement> firmwares;

    public DBConnector() {
        String dates[] = {
                "01.01.2017",
                "05.05.2018",
                "05.05.2017",
                "02.06.2021",
                "08.12.2019",
                "09.10.2020",
                "08.11.2021",
                "04.11.2019",
                "05.08.2020",
                "03.09.2018","12.12.2020", "01.01.2018", "06.11.2021", "04.09.2020", "04.04.2019",
                "03.05.2019", "03.03.2021", "03.03.2021", "08.07.2021", "19.10.2021"
        };
        firmwares = new ArrayList<FirmwareElement>();

        int i = 0;
        while (i < 20) {
            FirmwareElement f = new FirmwareElement();
            f.md5 = "xdcfvgbhnm";
            f.version = "rolling";
            f.platform = "kls";
            f.name= "firmware" + Integer.toString(i);
            f.date = new Date(dates[i]);
            firmwares.add(f);
            i++;
        }

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
