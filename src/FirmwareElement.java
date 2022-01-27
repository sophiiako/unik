import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FirmwareElement {
    public String name;
    public String md5;
    public fwDate date;
    public String branch;
    public String platform;
    public String stage;
    public byte[] binData;

    public FirmwareElement() {
        goToInitialValues();
    }

    public void goToInitialValues() {
        name = "";
        md5 = "";
        date = new fwDate("");
        stage = "";
        platform = "";
        branch = "";
        binData = "".getBytes(StandardCharsets.UTF_8);

    }

    public void setBinData(byte[] newBin) {
        binData = newBin.clone();
    }

    public FirmwareElement setAll(String n, String m, String p, String d, String b, String s) {
        name = n;
        md5 = m;
        platform = p;
        date = new fwDate(d);
        branch = b;
        stage = s;
        //setBinData(bin);
        return this;
    }
}
